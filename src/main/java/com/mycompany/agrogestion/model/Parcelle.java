package com.mycompany.agrogestion.model;

import com.mycompany.agrogestion.exceptions.DonneeInvalideException;
import com.mycompany.agrogestion.util.Validation;

/** Terrain de l'exploitation. Superficie en hectares. */
public class Parcelle extends Entite {

    private String nom;
    private double superficie;
    private String localisation;

    public Parcelle() {
    }

    public Parcelle(String nom, double superficie, String localisation) {
        this.nom = nom;
        this.superficie = superficie;
        this.localisation = localisation;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }


    @Override
    public void valider() throws DonneeInvalideException {
        Validation.texteNonVide("nom", nom);
        Validation.strictementPositif("superficie", superficie);
    }

    @Override
    public String toString() {
        return nom;
    }
}