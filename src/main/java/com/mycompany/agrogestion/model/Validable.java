package com.mycompany.agrogestion.model;

import com.mycompany.agrogestion.exceptions.DonneeInvalideException;

/** Un objet qui sait vérifier ses propres règles avant d'être enregistré. */
public interface Validable {

    void valider() throws DonneeInvalideException;
}