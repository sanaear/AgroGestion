package com.mycompany.agrogestion.dao;

import com.mycompany.agrogestion.exceptions.AccesDonneesException;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.exceptions.EntiteIntrouvableException;
import com.mycompany.agrogestion.model.Fournisseur;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** DAO des fournisseurs (table « fournisseurs »). */
public class FournisseurDAO implements GenericDAO<Fournisseur> {

    private static final String COLONNES =
            "id, nom, prenom, telephone, adresse, email, code_fournisseur, specialite";

    @Override
    public void add(Fournisseur fournisseur) throws AgroException {
        String sql = "INSERT INTO fournisseurs (nom, prenom, telephone, adresse, email, "
                + "code_fournisseur, specialite) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            remplir(ps, fournisseur);
            ps.executeUpdate();
            try (ResultSet cles = ps.getGeneratedKeys()) {
                if (cles.next()) {
                    fournisseur.setId(cles.getInt(1));
                }
            }
        } catch (SQLException ex) {
            throw new AccesDonneesException("Ajout du fournisseur impossible : " + ex.getMessage(), ex);
        }
    }

    @Override
    public void update(Fournisseur fournisseur) throws AgroException {
        String sql = "UPDATE fournisseurs SET nom = ?, prenom = ?, telephone = ?, adresse = ?, email = ?, "
                + "code_fournisseur = ?, specialite = ? WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            remplir(ps, fournisseur);
            ps.setInt(8, fournisseur.getId());
            if (ps.executeUpdate() == 0) {
                throw new EntiteIntrouvableException("Fournisseur", fournisseur.getId());
            }
        } catch (SQLException ex) {
            throw new AccesDonneesException("Modification impossible : " + ex.getMessage(), ex);
        }
    }

    @Override
    public void delete(int id) throws AgroException {
        String sql = "DELETE FROM fournisseurs WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new EntiteIntrouvableException("Fournisseur", id);
            }
        } catch (SQLException ex) {
            throw new AccesDonneesException("Suppression impossible : " + ex.getMessage(), ex);
        }
    }

    @Override
    public Fournisseur findById(int id) throws AgroException {
        String sql = "SELECT " + COLONNES + " FROM fournisseurs WHERE id = ?";
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
        throw new EntiteIntrouvableException("Fournisseur", id);
    }

    @Override
    public List<Fournisseur> findAll() throws AgroException {
        String sql = "SELECT " + COLONNES + " FROM fournisseurs ORDER BY nom, prenom";
        List<Fournisseur> resultat = new ArrayList<>();
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

    /** Remplit les 7 premiers « ? » (communs à l'INSERT et à l'UPDATE). */
    private void remplir(PreparedStatement ps, Fournisseur fournisseur) throws SQLException {
        ps.setString(1, fournisseur.getNom());
        ps.setString(2, fournisseur.getPrenom());
        ps.setString(3, fournisseur.getTelephone());
        ps.setString(4, fournisseur.getAdresse());
        ps.setString(5, fournisseur.getEmail());
        ps.setString(6, fournisseur.getCodeFournisseur());
        ps.setString(7, fournisseur.getSpecialite());
    }

    /** Transforme la ligne courante du ResultSet en objet Fournisseur. */
    private Fournisseur lire(ResultSet rs) throws SQLException {
        Fournisseur fournisseur = new Fournisseur(rs.getString("nom"), rs.getString("prenom"),
                rs.getString("telephone"), rs.getString("adresse"), rs.getString("email"),
                rs.getString("code_fournisseur"), rs.getString("specialite"));
        fournisseur.setId(rs.getInt("id"));
        return fournisseur;
    }
}