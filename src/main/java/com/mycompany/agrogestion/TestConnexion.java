package com.mycompany.agrogestion;

import com.mycompany.agrogestion.dao.DatabaseConnection;
import java.sql.Connection;

/** Test à lancer en premier (Run File) : vérifie db.properties et la connexion à MySQL. */
public class TestConnexion {

    public static void main(String[] args) {
        try (Connection c = DatabaseConnection.getConnection()) {
            System.out.println("Connexion réussie : " + c.getMetaData().getURL());
        } catch (Exception e) {
            System.out.println("Échec : " + e.getMessage());
        }
    }
}