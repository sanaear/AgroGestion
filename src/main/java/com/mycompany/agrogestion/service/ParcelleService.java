package com.mycompany.agrogestion.service;

import com.mycompany.agrogestion.dao.GenericDAO;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.exceptions.DonneeInvalideException;
import com.mycompany.agrogestion.model.Parcelle;
import java.util.List;

/** Logique métier des parcelles. Règle : le nom d'une parcelle est unique. */
public class ParcelleService extends ServiceCrud<Parcelle> {

    public ParcelleService(GenericDAO<Parcelle> dao) {
        super(dao);
    }

    public void ajouterParcelle(Parcelle parcelle) throws AgroException {
        parcelle.valider();
        verifierNomUnique(parcelle);
        ajouter(parcelle);
    }

    public void modifierParcelle(Parcelle parcelle) throws AgroException {
        parcelle.valider();
        verifierNomUnique(parcelle);
        modifier(parcelle);
    }

    public void supprimerParcelle(int id) throws AgroException {
        supprimer(id);
    }

    public List<Parcelle> listerParcelles() throws AgroException {
        return lister();
    }

    /** Superficie totale de l'exploitation, en hectares (tableau de bord). */
    public double calculerSuperficieTotale() throws AgroException {
        double somme = 0;
        for (Parcelle p : lister()) {
            somme += p.getSuperficie();
        }
        return somme;
    }

    private void verifierNomUnique(Parcelle parcelle) throws AgroException {
        for (Parcelle autre : lister()) {
            if (autre.getId() != parcelle.getId()
                    && autre.getNom().trim().equalsIgnoreCase(parcelle.getNom().trim())) {
                throw new DonneeInvalideException("Une parcelle s'appelle déjà « " + parcelle.getNom() + " ».");
            }
        }
    }
}