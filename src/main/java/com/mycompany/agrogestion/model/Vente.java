package com.mycompany.agrogestion.model;

import com.mycompany.agrogestion.exceptions.DonneeInvalideException;
import com.mycompany.agrogestion.util.Validation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Vente à un client (composée de lignes de vente). */
public class Vente extends Entite implements Facturable {

    private Client client;
    private LocalDate date;
    private String magasin;
    private EtatPaiement etatPaiement;
    private double total;
    private final List<LigneVente> lignes = new ArrayList<>();

    public Vente() {
        this.date = LocalDate.now();
        this.etatPaiement = EtatPaiement.EN_COURS;
    }

    public Vente(Client client, String magasin, EtatPaiement etatPaiement) {
        this();
        this.client = client;
        this.magasin = magasin;
        this.etatPaiement = etatPaiement;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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


    public List<LigneVente> getLignes() {
        return lignes;
    }

    public void ajouterLigne(LigneVente ligne) {
        lignes.add(ligne);
        calculerTotal();
    }

    /** Recalcule (et mémorise) le total à partir des lignes. */
    public double calculerTotal() {
        double somme = 0;
        for (LigneVente ligne : lignes) {
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
        Validation.nonNull("client", client);
        Validation.nonNull("date", date);
        Validation.nonNull("état de paiement", etatPaiement);
        if (lignes.isEmpty()) {
            throw new DonneeInvalideException("Il faut au moins une ligne.");
        }
        for (LigneVente ligne : lignes) {
            ligne.valider();
        }
    }
}