package com.mycompany.agrogestion.model;

import com.mycompany.agrogestion.exceptions.DonneeInvalideException;
import com.mycompany.agrogestion.util.Validation;

/** Employé de l'exploitation (permanent ou saisonnier). */
public class Employe extends Personne {

    private TypeContrat typeContrat;
    private double salaireMensuel;
    private StatutEmploye statut;

    public Employe() {
    }

    public Employe(String nom, String prenom, String telephone, String adresse, String email,
            TypeContrat typeContrat, double salaireMensuel, StatutEmploye statut) {
        super(nom, prenom, telephone, adresse, email);
        this.typeContrat = typeContrat;
        this.salaireMensuel = salaireMensuel;
        this.statut = statut;
    }

    public TypeContrat getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(TypeContrat typeContrat) {
        this.typeContrat = typeContrat;
    }

    public double getSalaireMensuel() {
        return salaireMensuel;
    }

    public void setSalaireMensuel(double salaireMensuel) {
        this.salaireMensuel = salaireMensuel;
    }

    public StatutEmploye getStatut() {
        return statut;
    }

    public void setStatut(StatutEmploye statut) {
        this.statut = statut;
    }


    @Override
    public void valider() throws DonneeInvalideException {
        super.valider();
        Validation.nonNull("type de contrat", typeContrat);
        Validation.strictementPositif("salaire mensuel", salaireMensuel);
        Validation.nonNull("statut", statut);
    }
}