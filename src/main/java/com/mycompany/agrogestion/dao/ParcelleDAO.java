package com.mycompany.agrogestion.dao;

import com.mycompany.agrogestion.exceptions.AccesDonneesException;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.exceptions.EntiteIntrouvableException;
import com.mycompany.agrogestion.model.Parcelle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** DAO MODÈLE : à imiter pour écrire tous les autres. */
public class ParcelleDAO implements GenericDAO<Parcelle> {

    @Override
    public void add(Parcelle p) throws AgroException {
        String sql = "INSERT INTO parcelles (nom, superficie, localisation) VALUES (?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNom());
            ps.setDouble(2, p.getSuperficie());
            ps.setString(3, p.getLocalisation());
            ps.executeUpdate();
            try (ResultSet cles = ps.getGeneratedKeys()) {
                if (cles.next()) {
                    p.setId(cles.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new AccesDonneesException("Ajout de la parcelle impossible : " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Parcelle p) throws AgroException {
        String sql = "UPDATE parcelles SET nom = ?, superficie = ?, localisation = ? WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNom());
            ps.setDouble(2, p.getSuperficie());
            ps.setString(3, p.getLocalisation());
            ps.setInt(4, p.getId());
            if (ps.executeUpdate() == 0) {
                throw new EntiteIntrouvableException("Parcelle", p.getId());
            }
        } catch (SQLException e) {
            throw new AccesDonneesException("Modification impossible : " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) throws AgroException {
        String sql = "DELETE FROM parcelles WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new EntiteIntrouvableException("Parcelle", id);
            }
        } catch (SQLException e) {
            throw new AccesDonneesException("Suppression impossible : " + e.getMessage(), e);
        }
    }

    @Override
    public Parcelle findById(int id) throws AgroException {
        String sql = "SELECT id, nom, superficie, localisation FROM parcelles WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return lire(rs);
                }
            }
        } catch (SQLException e) {
            throw new AccesDonneesException("Lecture impossible : " + e.getMessage(), e);
        }
        throw new EntiteIntrouvableException("Parcelle", id);
    }

    @Override
    public List<Parcelle> findAll() throws AgroException {
        String sql = "SELECT id, nom, superficie, localisation FROM parcelles ORDER BY nom";
        List<Parcelle> resultat = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultat.add(lire(rs));
            }
        } catch (SQLException e) {
            throw new AccesDonneesException("Lecture impossible : " + e.getMessage(), e);
        }
        return resultat;
    }

    /** Transforme la ligne courante du ResultSet en objet Parcelle. */
    private Parcelle lire(ResultSet rs) throws SQLException {
        Parcelle p = new Parcelle(rs.getString("nom"), rs.getDouble("superficie"),
                rs.getString("localisation"));
        p.setId(rs.getInt("id"));
        return p;
    }
}