package com.mycompany.agrogestion.dao;

import com.mycompany.agrogestion.exceptions.AccesDonneesException;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.exceptions.EntiteIntrouvableException;
import com.mycompany.agrogestion.model.Produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** DAO des produits (table « produits »). update() sert aussi à enregistrer le stock. */
public class ProduitDAO implements GenericDAO<Produit> {

    private static final String COLONNES =
            "id, nom, categorie, quantite, unite, seuil_alerte, prix_unitaire";

    @Override
    public void add(Produit produit) throws AgroException {
        String sql = "INSERT INTO produits (nom, categorie, quantite, unite, seuil_alerte, "
                + "prix_unitaire) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            remplir(ps, produit);
            ps.executeUpdate();
            try (ResultSet cles = ps.getGeneratedKeys()) {
                if (cles.next()) {
                    produit.setId(cles.getInt(1));
                }
            }
        } catch (SQLException ex) {
            throw new AccesDonneesException("Ajout du produit impossible : " + ex.getMessage(), ex);
        }
    }

    @Override
    public void update(Produit produit) throws AgroException {
        String sql = "UPDATE produits SET nom = ?, categorie = ?, quantite = ?, unite = ?, "
                + "seuil_alerte = ?, prix_unitaire = ? WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            remplir(ps, produit);
            ps.setInt(7, produit.getId());
            if (ps.executeUpdate() == 0) {
                throw new EntiteIntrouvableException("Produit", produit.getId());
            }
        } catch (SQLException ex) {
            throw new AccesDonneesException("Modification impossible : " + ex.getMessage(), ex);
        }
    }

    @Override
    public void delete(int id) throws AgroException {
        String sql = "DELETE FROM produits WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new EntiteIntrouvableException("Produit", id);
            }
        } catch (SQLException ex) {
            throw new AccesDonneesException("Suppression impossible (produit déjà utilisé dans des "
                    + "mouvements, achats ou ventes ?) : " + ex.getMessage(), ex);
        }
    }

    @Override
    public Produit findById(int id) throws AgroException {
        String sql = "SELECT " + COLONNES + " FROM produits WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return lire(rs);
                }
            }
        } catch (SQLException ex) {
            throw new AccesDonneesException("Lecture impossible : " + ex.getMessage(), ex);
        }
        throw new EntiteIntrouvableException("Produit", id);
    }

    @Override
    public List<Produit> findAll() throws AgroException {
        String sql = "SELECT " + COLONNES + " FROM produits ORDER BY nom";
        List<Produit> resultat = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultat.add(lire(rs));
            }
        } catch (SQLException ex) {
            throw new AccesDonneesException("Lecture impossible : " + ex.getMessage(), ex);
        }
        return resultat;
    }

    /** Remplit les 6 premiers « ? » (communs à l'INSERT et à l'UPDATE). */
    private void remplir(PreparedStatement ps, Produit produit) throws SQLException {
        ps.setString(1, produit.getNom());
        ps.setString(2, produit.getCategorie());
        ps.setDouble(3, produit.getQuantite());
        ps.setString(4, produit.getUnite());
        ps.setDouble(5, produit.getSeuilAlerte());
        ps.setDouble(6, produit.getPrixUnitaire());
    }

    /** Transforme la ligne courante du ResultSet en objet Produit. */
    private Produit lire(ResultSet rs) throws SQLException {
        Produit produit = new Produit(rs.getString("nom"), rs.getString("categorie"),
                rs.getDouble("quantite"), rs.getString("unite"),
                rs.getDouble("seuil_alerte"), rs.getDouble("prix_unitaire"));
        produit.setId(rs.getInt("id"));
        return produit;
    }
}