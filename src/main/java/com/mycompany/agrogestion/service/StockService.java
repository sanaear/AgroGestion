package com.mycompany.agrogestion.service;

import com.mycompany.agrogestion.dao.GenericDAO;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.model.MouvementStock;
import com.mycompany.agrogestion.model.Produit;
import com.mycompany.agrogestion.model.TypeMouvement;
import java.util.ArrayList;
import java.util.List;

/**
 * Logique métier du stock. Toute modification de quantité passe par ajouterMouvement :
 * elle met à jour le produit ET garde la trace du mouvement.
 */
public class StockService {

    private final GenericDAO<Produit> produitDao;
    private final GenericDAO<MouvementStock> mouvementDao;

    public StockService(GenericDAO<Produit> produitDao, GenericDAO<MouvementStock> mouvementDao) {
        this.produitDao = produitDao;
        this.mouvementDao = mouvementDao;
    }

    /**
     * Applique un mouvement : lève StockInsuffisantException si une sortie dépasse le stock.
     * Le produit est relu depuis le DAO pour partir de la quantité réelle.
     */
    public void ajouterMouvement(MouvementStock mouvement) throws AgroException {
        mouvement.valider();
        Produit produit = produitDao.findById(mouvement.getProduit().getId());
        if (mouvement.getType() == TypeMouvement.ENTREE) {
            produit.ajouterQuantite(mouvement.getQuantite());
        } else {
            produit.retirerQuantite(mouvement.getQuantite());
        }
        produitDao.update(produit);
        mouvement.setProduit(produit);
        mouvementDao.add(mouvement);
    }

    /** Stock actuel d'un produit. */
    public double calculerStock(int produitId) throws AgroException {
        return produitDao.findById(produitId).getQuantite();
    }

    /** Valeur totale du stock : somme de quantité × prix unitaire (tableau de bord). */
    public double calculerValeurStock() throws AgroException {
        double somme = 0;
        for (Produit p : produitDao.findAll()) {
            somme += p.getQuantite() * p.getPrixUnitaire();
        }
        return somme;
    }

    /** Produits dont le stock est au niveau d'alerte ou en dessous. */
    public List<Produit> verifierStockMinimum() throws AgroException {
        List<Produit> alertes = new ArrayList<>();
        for (Produit p : produitDao.findAll()) {
            if (p.estSousSeuil()) {
                alertes.add(p);
            }
        }
        return alertes;
    }

    public List<MouvementStock> listerMouvements() throws AgroException {
        return mouvementDao.findAll();
    }

    public List<MouvementStock> listerMouvementsParProduit(int produitId) throws AgroException {
        List<MouvementStock> resultat = new ArrayList<>();
        for (MouvementStock m : mouvementDao.findAll()) {
            if (m.getProduit().getId() == produitId) {
                resultat.add(m);
            }
        }
        return resultat;
    }
}