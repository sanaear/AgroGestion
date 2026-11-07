package com.mycompany.agrogestion;

import com.mycompany.agrogestion.dao.EmployeDAO;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.model.Employe;
import com.mycompany.agrogestion.model.StatutEmploye;
import com.mycompany.agrogestion.model.TypeContrat;

/** Test manuel de EmployeDAO : à lancer avec Run File (Maj+F6). */
public class TestEmployeDAO {

    public static void main(String[] args) throws AgroException {
        EmployeDAO dao = new EmployeDAO();

        Employe e = new Employe("Sow", "Ibrahima", "0622222222", "Rue A", "ib@mail.fr",
                TypeContrat.PERMANENT, 1800, StatutEmploye.ACTIF);
        dao.add(e);
        System.out.println("Id attribué : " + e.getId());

        Employe relu = dao.findById(e.getId());
        System.out.println("Relu : " + relu + " | " + relu.getTypeContrat() + " | "
                + relu.getSalaireMensuel() + " | " + relu.getStatut());

        relu.setStatut(StatutEmploye.INACTIF);
        relu.setSalaireMensuel(1900);
        dao.update(relu);
        Employe modifie = dao.findById(e.getId());
        System.out.println("Après modification : " + modifie.getStatut() + " | " + modifie.getSalaireMensuel());
        System.out.println("Nombre d'employés : " + dao.findAll().size());

        dao.delete(e.getId());
        try {
            dao.findById(e.getId());
        } catch (AgroException ex) {
            System.out.println("Refusé : " + ex.getMessage());
        }
    }
}