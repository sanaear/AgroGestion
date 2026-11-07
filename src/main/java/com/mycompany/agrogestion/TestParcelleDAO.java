package com.mycompany.agrogestion;

import com.mycompany.agrogestion.dao.ParcelleDAO;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.model.Parcelle;

/** Test manuel du DAO : à lancer avec Run File (Maj+F6). */
public class TestParcelleDAO {

    public static void main(String[] args) throws AgroException {
        ParcelleDAO dao = new ParcelleDAO();

        Parcelle p = new Parcelle("Parcelle Test", 5.0, "Quelque part");
        dao.add(p);
        System.out.println("Id attribué : " + p.getId());

        p.setSuperficie(7.5);
        dao.update(p);
        System.out.println("Superficie relue : " + dao.findById(p.getId()).getSuperficie());
        System.out.println("Nombre de parcelles : " + dao.findAll().size());

        dao.delete(p.getId());
        try {
            dao.findById(p.getId());
        } catch (AgroException e) {
            System.out.println("Refusé : " + e.getMessage());
        }
    }
}