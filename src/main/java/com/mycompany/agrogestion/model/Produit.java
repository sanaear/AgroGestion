package com.mycompany.agrogestion.model;

import com.mycompany.agrogestion.exceptions.DonneeInvalideException;
import com.mycompany.agrogestion.exceptions.StockInsuffisantException;
import com.mycompany.agrogestion.util.Validation;

/** Article géré en stock (semences, engrais, récoltes...). « quantite » = stock actuel. */
public class Produit extends Entite {

    private String nom;
    private String categorie;
    private double quantite;
    private String unite;
    private double seuilAlerte;
    private double prixUnitaire;

    public Produit() {
    }

    public Produit(String nom, String categorie, double quantite, String unite,
            double seuilAlerte, double prixUnitaire) {
        this.nom = nom;
        this.categorie = categorie;
        this.quantite = quantite;
        this.unite = unite;
        this.seuilAlerte = seuilAlerte;
        this.prixUnitaire = prixUnitaire;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public double getSeuilAlerte() {
        return seuilAlerte;
    }

    public void setSeuilAlerte(double seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }


    /** Vrai si le stock est au niveau d'alerte ou en dessous. */
    public boolean estSousSeuil() {
        return quantite <= seuilAlerte;
    }

    public void ajouterQuantite(double q) throws DonneeInvalideException {
        Validation.strictementPositif("quantité", q);
        quantite += q;
    }

    public void retirerQuantite(double q) throws DonneeInvalideException, StockInsuffisantException {
        Validation.strictementPositif("quantité", q);
        if (q > quantite) {
            throw new StockInsuffisantException(nom, quantite, q);
        }
        quantite -= q;
    }

    @Override
    public void valider() throws DonneeInvalideException {
        Validation.texteNonVide("nom", nom);
        Validation.texteNonVide("unité", unite);
        Validation.positifOuNul("quantité", quantite);
        Validation.positifOuNul("seuil d'alerte", seuilAlerte);
        Validation.positifOuNul("prix unitaire", prixUnitaire);
    }

    @Override
    public String toString() {
        return nom;
    }
}