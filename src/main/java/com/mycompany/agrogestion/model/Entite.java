package com.mycompany.agrogestion.model;

/** Base de toutes les entités : porte l'identifiant (clé primaire en base). */
public abstract class Entite implements Validable {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}