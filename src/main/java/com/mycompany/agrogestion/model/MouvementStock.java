package com.mycompany.agrogestion.model;

import com.mycompany.agrogestion.exceptions.DonneeInvalideException;
import com.mycompany.agrogestion.util.Validation;
import java.time.LocalDate;

/** Trace d'une entrée ou d'une sortie de stock pour un produit. */
public class MouvementStock extends Entite {

    private Produit produit;
    private LocalDate date;
    private TypeMouvement type;
    private double quantite;
    private String motif;

    public MouvementStock() {
        this.date = LocalDate.now();
    }

    public MouvementStock(Produit produit, TypeMouvement type, double quantite, String motif) {
        this.produit = produit;
        this.type = type;
        this.quantite = quantite;
        this.motif = motif;
        this.date = LocalDate.now();
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public TypeMouvement getType() {
        return type;
    }

    public void setType(TypeMouvement type) {
        this.type = type;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }


    @Override
    public void valider() throws DonneeInvalideException {
        Validation.nonNull("produit", produit);
        Validation.nonNull("date", date);
        Validation.nonNull("type de mouvement", type);
        Validation.strictementPositif("quantité", quantite);
    }
}