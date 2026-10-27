
package com.mycompany.agrogestion.exceptions;

/**
 * Exception de base de l'application : toute erreur « métier » en hérite.
 * L'interface graphique peut n'attraper que ce type et afficher getMessage().
 */
public abstract class AgroException extends Exception {

    protected AgroException(String message) {
        super(message);
    }

    protected AgroException(String message, Throwable cause) {
        super(message, cause);
    }
}