/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.agrogestion;
import com.mycompany.agrogestion.dao.GenericDAO;
import com.mycompany.agrogestion.dao.MemoireDao;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.model.*;
import com.mycompany.agrogestion.service.*;
/**
 *
 * @author PC
 */

public class Agrogestion {

    public static void main(String[] args) {
        
        GenericDAO<Produit> produits = new MemoireDao<>("Produit");
StockService stock = new StockService(produits, new MemoireDao<>("Mouvement"));
ProduitService produitService = new ProduitService(produits);
AchatService achatService = new AchatService(new MemoireDao<>("Achat"), stock);
VenteService venteService = new VenteService(new MemoireDao<>("Vente"), produits, stock);

Fournisseur f = new Fournisseur("Martin", "Paul", "0600000000", "Rue A", "paul@semences.fr", "F001", "Semences");
Client c = new Client("Durand", "Awa", "0611111111", "Place du Marché", "awa@mail.fr", "C001", "Particulier");
try {
    Produit ble = new Produit("Blé", "Céréale", 0, "kg", 20, 0.45);
    produitService.ajouterProduit(ble);

    Achat achat = new Achat(f, "Coop Agri", EtatPaiement.PAYE);
    achat.ajouterLigne(new LigneAchat(ble, 100, 0.40));
    achatService.enregistrerAchat(achat);
    System.out.println("Total achat : " + achat.getMontant() + " | stock : " + stock.calculerStock(ble.getId()));

    Vente v1 = new Vente(c, "Marché", EtatPaiement.PAYE);
    v1.ajouterLigne(new LigneVente(ble, 30, 0.60));
    venteService.enregistrerVente(v1);
    System.out.println("Total vente : " + v1.getMontant() + " | stock : " + stock.calculerStock(ble.getId()));

    try {
        Vente trop = new Vente(c, "Marché", EtatPaiement.PAYE);
        trop.ajouterLigne(new LigneVente(ble, 100, 0.60));
        venteService.enregistrerVente(trop);
    } catch (AgroException e) {
        System.out.println("Refusé : " + e.getMessage());
    }
    System.out.println("Ventes enregistrées : " + venteService.listerVentes().size());

    try {
        Vente deuxLignes = new Vente(c, "Marché", EtatPaiement.PAYE);
        deuxLignes.ajouterLigne(new LigneVente(ble, 40, 0.60));
        deuxLignes.ajouterLigne(new LigneVente(ble, 40, 0.60));
        venteService.enregistrerVente(deuxLignes);
    } catch (AgroException e) {
        System.out.println("Refusé : " + e.getMessage());
    }

    Vente v2 = new Vente(c, "Marché", EtatPaiement.NON_PAYE);
    v2.ajouterLigne(new LigneVente(ble, 55, 0.60));
    venteService.enregistrerVente(v2);
    System.out.println("Stock : " + stock.calculerStock(ble.getId()) + " | alertes : " + stock.verifierStockMinimum());
    System.out.println("CA encaissé : " + venteService.calculerChiffreAffairesEncaisse());
} catch (AgroException e) {
    System.out.println("Erreur : " + e.getMessage());
}
        
}   
}
