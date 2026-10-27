package com.mycompany.agrogestion.service;

import com.mycompany.agrogestion.dao.GenericDAO;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.exceptions.DonneeInvalideException;
import com.mycompany.agrogestion.model.Fournisseur;
import java.util.List;

/** Logique métier des fournisseurs. Règle : le code fournisseur est unique. */
public class FournisseurService extends ServiceCrud<Fournisseur> {

    public FournisseurService(GenericDAO<Fournisseur> dao) {
        super(dao);
    }

    public void ajouterFournisseur(Fournisseur fournisseur) throws AgroException {
        fournisseur.valider();
        verifierCodeUnique(fournisseur);
        ajouter(fournisseur);
    }

    public void modifierFournisseur(Fournisseur fournisseur) throws AgroException {
        fournisseur.valider();
        verifierCodeUnique(fournisseur);
        modifier(fournisseur);
    }

    public void supprimerFournisseur(int id) throws AgroException {
        supprimer(id);
    }

    public List<Fournisseur> listerFournisseurs() throws AgroException {
        return lister();
    }

    private void verifierCodeUnique(Fournisseur fournisseur) throws AgroException {
        for (Fournisseur autre : lister()) {
            if (autre.getId() != fournisseur.getId()
                    && autre.getCodeFournisseur().trim().equalsIgnoreCase(fournisseur.getCodeFournisseur().trim())) {
                throw new DonneeInvalideException("Le code fournisseur « " + fournisseur.getCodeFournisseur() + " » existe déjà.");
            }
        }
    }
}