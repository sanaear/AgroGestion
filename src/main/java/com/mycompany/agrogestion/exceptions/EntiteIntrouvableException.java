
package com.mycompany.agrogestion.exceptions;


/** Aucun élément ne correspond à l'identifiant demandé. */
public class EntiteIntrouvableException extends AgroException {

    public EntiteIntrouvableException(String entite, int id) {
        super(entite + " introuvable (id = " + id + ").");
    }
}