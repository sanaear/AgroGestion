package com.mycompany.agrogestion.service;

import com.mycompany.agrogestion.dao.GenericDAO;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.exceptions.DonneeInvalideException;
import com.mycompany.agrogestion.model.Produit;
import java.util.List;

/**
 * Catalogue des produits. Le stock d'un produit ne doit être modifié que par StockService
 * (mouvements), pas en modifiant directement la quantité.
 */
public class ProduitService extends ServiceCrud<Produit> {

    public ProduitService(GenericDAO<Produit> dao) {
        super(dao);
    }

    public void ajouterProduit(Produit produit) throws AgroException {
        produit.valider();
        verifierNomUnique(produit);
        ajouter(produit);
    }

    public void modifierProduit(Produit produit) throws AgroException {
        produit.valider();
        verifierNomUnique(produit);
        modifier(produit);
    }

    public void supprimerProduit(int id) throws AgroException {
        supprimer(id);
    }

    public List<Produit> listerProduits() throws AgroException {
        return lister();
    }

    private void verifierNomUnique(Produit produit) throws AgroException {
        for (Produit autre : lister()) {
            if (autre.getId() != produit.getId()
                    && autre.getNom().trim().equalsIgnoreCase(produit.getNom().trim())) {
                throw new DonneeInvalideException("Un produit s'appelle déjà « " + produit.getNom() + " ».");
            }
        }
    }
}