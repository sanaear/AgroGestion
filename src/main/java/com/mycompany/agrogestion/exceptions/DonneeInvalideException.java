
package com.mycompany.agrogestion.exceptions;

/** Une donnée ne respecte pas les règles (champ vide, montant négatif, doublon...). */

public class DonneeInvalideException extends AgroException {

    public DonneeInvalideException(String message) {
        super(message);
    }
}