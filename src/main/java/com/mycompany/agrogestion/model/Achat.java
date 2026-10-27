package com.mycompany.agrogestion.model;

import com.mycompany.agrogestion.exceptions.DonneeInvalideException;
import com.mycompany.agrogestion.util.Validation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Achat de produits auprès d'un fournisseur (composé de lignes d'achat). */
public class Achat extends Entite implements Facturable {

    private Fournisseur fournisseur;
    private LocalDate date;
    private String magasin;
    private EtatPaiement etatPaiement;
    private double total;
    private final List<LigneAchat> lignes = new ArrayList<>();

    public Achat() {
        this.date = LocalDate.now();
        this.etatPaiement = EtatPaiement.EN_COURS;
    }

    public Achat(Fournisseur fournisseur, String magasin, EtatPaiement etatPaiement) {
        this();
        this.fournisseur = fournisseur;
        this.magasin = magasin;
        this.etatPaiement = etatPaiement;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMagasin() {
        return magasin;
    }

    public void setMagasin(String magasin) {
        this.magasin = magasin;
    }

    public EtatPaiement getEtatPaiement() {
        return etatPaiement;
    }

    public void setEtatPaiement(EtatPaiement etatPaiement) {
        this.etatPaiement = etatPaiement;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


    public List<LigneAchat> getLignes() {
        return lignes;
    }

    public void ajouterLigne(LigneAchat ligne) {
        lignes.add(ligne);
        calculerTotal();
    }

    /** Recalcule (et mémorise) le total à partir des lignes. */
    public double calculerTotal() {
        double somme = 0;
        for (LigneAchat ligne : lignes) {
            somme += ligne.getMontant();
        }
        total = somme;
        return total;
    }

    @Override
    public double getMontant() {
        return calculerTotal();
    }

    @Override
    public void valider() throws DonneeInvalideException {
        Validation.nonNull("fournisseur", fournisseur);
        Validation.nonNull("date", date);
        Validation.nonNull("état de paiement", etatPaiement);
        if (lignes.isEmpty()) {
            throw new DonneeInvalideException("Il faut au moins une ligne.");
        }
        for (LigneAchat ligne : lignes) {
            ligne.valider();
        }
    }
}