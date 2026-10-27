package com.mycompany.agrogestion.model;

import com.mycompany.agrogestion.exceptions.DonneeInvalideException;
import com.mycompany.agrogestion.util.Validation;
import java.time.LocalDate;

/** Culture plantée sur une parcelle (association « Parcelle contient Culture »). */
public class Culture extends Entite {

    private Parcelle parcelle;
    private String typeCulture;
    private LocalDate dateSemis;
    private LocalDate dateRecoltePrevue;
    private String statut;

    public Culture() {
    }

    public Culture(Parcelle parcelle, String typeCulture, LocalDate dateSemis,
            LocalDate dateRecoltePrevue, String statut) {
        this.parcelle = parcelle;
        this.typeCulture = typeCulture;
        this.dateSemis = dateSemis;
        this.dateRecoltePrevue = dateRecoltePrevue;
        this.statut = statut;
    }

    public Parcelle getParcelle() {
        return parcelle;
    }

    public void setParcelle(Parcelle parcelle) {
        this.parcelle = parcelle;
    }

    public String getTypeCulture() {
        return typeCulture;
    }

    public void setTypeCulture(String typeCulture) {
        this.typeCulture = typeCulture;
    }

    public LocalDate getDateSemis() {
        return dateSemis;
    }

    public void setDateSemis(LocalDate dateSemis) {
        this.dateSemis = dateSemis;
    }

    public LocalDate getDateRecoltePrevue() {
        return dateRecoltePrevue;
    }

    public void setDateRecoltePrevue(LocalDate dateRecoltePrevue) {
        this.dateRecoltePrevue = dateRecoltePrevue;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }


    /**
     * Vrai si les périodes [semis, récolte prévue] des deux cultures se recouvrent.
     * Une récolte non renseignée est considérée comme « sans fin ».
     */
    public boolean chevauche(Culture autre) {
        LocalDate finA = (dateRecoltePrevue == null) ? LocalDate.MAX : dateRecoltePrevue;
        LocalDate finB = (autre.dateRecoltePrevue == null) ? LocalDate.MAX : autre.dateRecoltePrevue;
        return !(finA.isBefore(autre.dateSemis) || finB.isBefore(dateSemis));
    }

    @Override
    public void valider() throws DonneeInvalideException {
        Validation.nonNull("parcelle", parcelle);
        Validation.texteNonVide("type de culture", typeCulture);
        Validation.nonNull("date de semis", dateSemis);
        Validation.texteNonVide("statut", statut);
        if (dateRecoltePrevue != null && dateRecoltePrevue.isBefore(dateSemis)) {
            throw new DonneeInvalideException("La récolte prévue ne peut pas précéder le semis.");
        }
    }
}