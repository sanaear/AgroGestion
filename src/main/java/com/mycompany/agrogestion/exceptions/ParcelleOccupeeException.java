
package com.mycompany.agrogestion.exceptions;

/** Une autre culture occupe déjà la parcelle pendant la période demandée. */
public class ParcelleOccupeeException extends AgroException {

    public ParcelleOccupeeException(String parcelle) {
        super("La parcelle « " + parcelle + " » est déjà occupée par une culture sur cette période.");
    }
}