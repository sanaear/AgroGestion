package com.mycompany.agrogestion.model;

import com.mycompany.agrogestion.exceptions.DonneeInvalideException;
import com.mycompany.agrogestion.util.Validation;

/** Fournisseur de l'exploitation (semences, engrais, matériel...). */
public class Fournisseur extends Personne {

    private String codeFournisseur;
    private String specialite;

    public Fournisseur() {
    }

    public Fournisseur(String nom, String prenom, String telephone, String adresse, String email,
            String codeFournisseur, String specialite) {
        super(nom, prenom, telephone, adresse, email);
        this.codeFournisseur = codeFournisseur;
        this.specialite = specialite;
    }

    public String getCodeFournisseur() {
        return codeFournisseur;
    }

    public void setCodeFournisseur(String codeFournisseur) {
        this.codeFournisseur = codeFournisseur;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }


    @Override
    public void valider() throws DonneeInvalideException {
        super.valider();
        Validation.texteNonVide("code fournisseur", codeFournisseur);
    }
}