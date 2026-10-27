
package com.mycompany.agrogestion.exceptions;


/** Problème d'accès à la base (connexion, SQL...). Les DAO y enveloppent leurs SQLException. */
public class AccesDonneesException extends AgroException {

    public AccesDonneesException(String message, Throwable cause) {
        super(message, cause);
    }
}