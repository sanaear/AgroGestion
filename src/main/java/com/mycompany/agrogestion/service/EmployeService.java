package com.mycompany.agrogestion.service;

import com.mycompany.agrogestion.dao.GenericDAO;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.model.Employe;
import com.mycompany.agrogestion.model.StatutEmploye;
import java.util.ArrayList;
import java.util.List;

/** Logique métier des employés. */
public class EmployeService extends ServiceCrud<Employe> {

    public EmployeService(GenericDAO<Employe> dao) {
        super(dao);
    }

    public void ajouterEmploye(Employe employe) throws AgroException {
        ajouter(employe);
    }

    public void modifierEmploye(Employe employe) throws AgroException {
        modifier(employe);
    }

    public void supprimerEmploye(int id) throws AgroException {
        supprimer(id);
    }

    public List<Employe> listerEmployes() throws AgroException {
        return lister();
    }

    public Employe trouverEmploye(int id) throws AgroException {
        return trouver(id);
    }

    public List<Employe> listerEmployesActifs() throws AgroException {
        List<Employe> actifs = new ArrayList<>();
        for (Employe e : lister()) {
            if (e.getStatut() == StatutEmploye.ACTIF) {
                actifs.add(e);
            }
        }
        return actifs;
    }

    /** Somme des salaires mensuels des employés actifs (tableau de bord). */
    public double calculerMasseSalariale() throws AgroException {
        double somme = 0;
        for (Employe e : listerEmployesActifs()) {
            somme += e.getSalaireMensuel();
        }
        return somme;
    }
}