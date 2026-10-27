package com.mycompany.agrogestion.service;

import com.mycompany.agrogestion.dao.GenericDAO;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.exceptions.StockInsuffisantException;
import com.mycompany.agrogestion.model.EtatPaiement;
import com.mycompany.agrogestion.model.LigneVente;
import com.mycompany.agrogestion.model.MouvementStock;
import com.mycompany.agrogestion.model.Produit;
import com.mycompany.agrogestion.model.TypeMouvement;
import com.mycompany.agrogestion.model.Vente;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Logique métier des ventes : enregistrer une vente diminue le stock, ou est refusé si le stock manque. */
public class VenteService extends ServiceCrud<Vente> {

    private final GenericDAO<Produit> produitDao;
    private final StockService stockService;

    public VenteService(GenericDAO<Vente> dao, GenericDAO<Produit> produitDao, StockService stockService) {
        super(dao);
        this.produitDao = produitDao;
        this.stockService = stockService;
    }

    /**
     * 1. valide la vente ; 2. vérifie le stock de TOUS les produits AVANT d'écrire quoi que ce soit ;
     * 3. enregistre la vente ; 4. crée un mouvement SORTIE par ligne.
     * Limite connue : sans transaction JDBC commune, une panne de base en cours de route peut laisser
     * la vente enregistrée sans tous ses mouvements. La vérification préalable évite le cas courant.
     */
    public void enregistrerVente(Vente vente) throws AgroException {
        vente.valider();
        verifierStockDisponible(vente);
        vente.calculerTotal();
        dao.add(vente);
        for (LigneVente ligne : vente.getLignes()) {
            stockService.ajouterMouvement(new MouvementStock(
                    ligne.getProduit(), TypeMouvement.SORTIE, ligne.getQuantite(), "Vente n°" + vente.getId()));
        }
    }

    public double calculerTotalVente(Vente vente) {
        return vente.calculerTotal();
    }

    public List<Vente> listerVentes() throws AgroException {
        return lister();
    }

    /** Chiffre d'affaires encaissé : somme des ventes à l'état PAYE (tableau de bord). */
    public double calculerChiffreAffairesEncaisse() throws AgroException {
        double somme = 0;
        for (Vente v : lister()) {
            if (v.getEtatPaiement() == EtatPaiement.PAYE) {
                somme += v.getTotal();
            }
        }
        return somme;
    }

    /** Cumule les quantités par produit (un même produit peut figurer sur plusieurs lignes). */
    private void verifierStockDisponible(Vente vente) throws AgroException {
        Map<Integer, Double> besoins = new LinkedHashMap<>();
        for (LigneVente ligne : vente.getLignes()) {
            besoins.merge(ligne.getProduit().getId(), ligne.getQuantite(), Double::sum);
        }
        for (Map.Entry<Integer, Double> besoin : besoins.entrySet()) {
            Produit produit = produitDao.findById(besoin.getKey());
            if (besoin.getValue() > produit.getQuantite()) {
                throw new StockInsuffisantException(produit.getNom(), produit.getQuantite(), besoin.getValue());
            }
        }
    }
}