package com.mycompany.agrogestion.dao;

import com.mycompany.agrogestion.exceptions.AccesDonneesException;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.exceptions.EntiteIntrouvableException;
import com.mycompany.agrogestion.model.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** DAO des clients (table « clients »). */
public class ClientDAO implements GenericDAO<Client> {

    private static final String COLONNES =
            "id, nom, prenom, telephone, adresse, email, code_client, type_client";

    @Override
    public void add(Client client) throws AgroException {
        String sql = "INSERT INTO clients (nom, prenom, telephone, adresse, email, "
                + "code_client, type_client) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            remplir(ps, client);
            ps.executeUpdate();
            try (ResultSet cles = ps.getGeneratedKeys()) {
                if (cles.next()) {
                    client.setId(cles.getInt(1));
                }
            }
        } catch (SQLException ex) {
            throw new AccesDonneesException("Ajout du client impossible : " + ex.getMessage(), ex);
        }
    }

    @Override
    public void update(Client client) throws AgroException {
        String sql = "UPDATE clients SET nom = ?, prenom = ?, telephone = ?, adresse = ?, email = ?, "
                + "code_client = ?, type_client = ? WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            remplir(ps, client);
            ps.setInt(8, client.getId());
            if (ps.executeUpdate() == 0) {
                throw new EntiteIntrouvableException("Client", client.getId());
            }
        } catch (SQLException ex) {
            throw new AccesDonneesException("Modification impossible : " + ex.getMessage(), ex);
        }
    }

    @Override
    public void delete(int id) throws AgroException {
        String sql = "DELETE FROM clients WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new EntiteIntrouvableException("Client", id);
            }
        } catch (SQLException ex) {
            throw new AccesDonneesException("Suppression impossible : " + ex.getMessage(), ex);
        }
    }

    @Override
    public Client findById(int id) throws AgroException {
        String sql = "SELECT " + COLONNES + " FROM clients WHERE id = ?";
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
        throw new EntiteIntrouvableException("Client", id);
    }

    @Override
    public List<Client> findAll() throws AgroException {
        String sql = "SELECT " + COLONNES + " FROM clients ORDER BY nom, prenom";
        List<Client> resultat = new ArrayList<>();
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
    private void remplir(PreparedStatement ps, Client client) throws SQLException {
        ps.setString(1, client.getNom());
        ps.setString(2, client.getPrenom());
        ps.setString(3, client.getTelephone());
        ps.setString(4, client.getAdresse());
        ps.setString(5, client.getEmail());
        ps.setString(6, client.getCodeClient());
        ps.setString(7, client.getTypeClient());
    }

    /** Transforme la ligne courante du ResultSet en objet Client. */
    private Client lire(ResultSet rs) throws SQLException {
        Client client = new Client(rs.getString("nom"), rs.getString("prenom"),
                rs.getString("telephone"), rs.getString("adresse"), rs.getString("email"),
                rs.getString("code_client"), rs.getString("type_client"));
        client.setId(rs.getInt("id"));
        return client;
    }
}