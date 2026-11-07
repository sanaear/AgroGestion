package com.mycompany.agrogestion.dao;

import com.mycompany.agrogestion.exceptions.AccesDonneesException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/** Point d'accès unique à MySQL. Lit le fichier db.properties (racine du projet). */
public final class DatabaseConnection {

    private static final String FICHIER = "db.properties";

    private DatabaseConnection() {
    }

    /** Ouvre une connexion. À utiliser dans un try-with-resources : elle se ferme toute seule. */
    public static Connection getConnection() throws AccesDonneesException {
        Properties config = new Properties();
        try (FileInputStream in = new FileInputStream(FICHIER)) {
            config.load(in);
        } catch (IOException e) {
            throw new AccesDonneesException("Fichier " + FICHIER
                    + " introuvable : copiez db.properties.example en db.properties.", e);
        }
        try {
            return DriverManager.getConnection(
                    config.getProperty("db.url"),
                    config.getProperty("db.user"),
                    config.getProperty("db.password"));
        } catch (SQLException e) {
            throw new AccesDonneesException("Connexion à MySQL impossible : " + e.getMessage(), e);
        }
    }
}