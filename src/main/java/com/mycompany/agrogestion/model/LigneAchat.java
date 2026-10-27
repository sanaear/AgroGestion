package com.mycompany.agrogestion.model;

import com.mycompany.agrogestion.exceptions.DonneeInvalideException;
import com.mycompany.agrogestion.util.Validation;

/** Une ligne d'achat : produit acheté, quantité, prix. */
public class LigneAchat extends Entite implements Facturable {

    private Produit produit;
    private double quantite;
    private double prixUnitaire;
    private double sousTotal;

    public LigneAchat() {
    }

    public LigneAchat(Produit produit, double quantite, double prixUnitaire) {
        this.produit = produit;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        calculerSousTotal();
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public double getSousTotal() {
        return sousTotal;
    }

    public void setSousTotal(double sousTotal) {
        this.sousTotal = sousTotal;
    }


    
    /** Recalcule (et mémorise) quantité × prix unitaire. */
    public double calculerSousTotal() {
        sousTotal = quantite * prixUnitaire;
        return sousTotal;
    }

    @Override
    public double getMontant() {
        return calculerSousTotal();
    }

    @Override
    public void valider() throws DonneeInvalideException {
        Validation.nonNull("produit", produit);
        Validation.strictementPositif("quantité", quantite);
        Validation.positifOuNul("prix unitaire", prixUnitaire);
    }
}