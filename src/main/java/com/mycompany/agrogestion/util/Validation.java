
package com.mycompany.agrogestion.util;

import com.mycompany.agrogestion.exceptions.DonneeInvalideException;



/** Vérifications réutilisables, appelées depuis les méthodes valider() des entités. */
public final class Validation {

    private Validation() {
    }

    public static void texteNonVide(String champ, String valeur) throws DonneeInvalideException {
        if (valeur == null || valeur.trim().isEmpty()) {
            throw new DonneeInvalideException("Le champ « " + champ + " » est obligatoire.");
        }
    }

    public static void nonNull(String champ, Object valeur) throws DonneeInvalideException {
        if (valeur == null) {
            throw new DonneeInvalideException("Le champ « " + champ + " » est obligatoire.");
        }
    }

    public static void positifOuNul(String champ, double valeur) throws DonneeInvalideException {
        if (valeur < 0) {
            throw new DonneeInvalideException("Le champ « " + champ + " » ne peut pas être négatif.");
        }
    }

    public static void strictementPositif(String champ, double valeur) throws DonneeInvalideException {
        if (valeur <= 0) {
            throw new DonneeInvalideException("Le champ « " + champ + " » doit être strictement positif.");
        }
    }

    /** Contrôle volontairement simple : un @ suivi d'un point, si l'email est renseigné. */
    public static void emailValideSiRenseigne(String email) throws DonneeInvalideException {
        if (email == null || email.trim().isEmpty()) {
            return;
        }
        int arobase = email.indexOf('@');
        if (arobase < 1 || email.indexOf('.', arobase) < arobase + 2 || email.endsWith(".")) {
            throw new DonneeInvalideException("L'adresse email « " + email + " » n'est pas valide.");
        }
    }
}