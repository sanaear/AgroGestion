package com.mycompany.agrogestion.model;

import com.mycompany.agrogestion.exceptions.DonneeInvalideException;
import com.mycompany.agrogestion.util.Validation;

/** Données communes à toutes les personnes : employés, clients, fournisseurs. */
public abstract class Personne extends Entite {

    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private String email;

    protected Personne() {
    }

    protected Personne(String nom, String prenom, String telephone, String adresse, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public void valider() throws DonneeInvalideException {
        Validation.texteNonVide("nom", nom);
        Validation.texteNonVide("prénom", prenom);
        Validation.emailValideSiRenseigne(email);
    }

    @Override
    public String toString() {
        return prenom + " " + nom;
    }
}