package com.mycompany.agrogestion.dao;

import com.mycompany.agrogestion.exceptions.AccesDonneesException;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.exceptions.EntiteIntrouvableException;
import com.mycompany.agrogestion.model.Employe;
import com.mycompany.agrogestion.model.StatutEmploye;
import com.mycompany.agrogestion.model.TypeContrat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** DAO des employés (table « employes »). */
public class EmployeDAO implements GenericDAO<Employe> {

    private static final String COLONNES =
            "id, nom, prenom, telephone, adresse, email, type_contrat, salaire_mensuel, statut";

    @Override
    public void add(Employe e) throws AgroException {
        String sql = "INSERT INTO employes (nom, prenom, telephone, adresse, email, "
                + "type_contrat, salaire_mensuel, statut) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            remplir(ps, e);
            ps.executeUpdate();
            try (ResultSet cles = ps.getGeneratedKeys()) {
                if (cles.next()) {
                    e.setId(cles.getInt(1));
                }
            }
        } catch (SQLException ex) {
            throw new AccesDonneesException("Ajout de l'employé impossible : " + ex.getMessage(), ex);
        }
    }

    @Override
    public void update(Employe e) throws AgroException {
        String sql = "UPDATE employes SET nom = ?, prenom = ?, telephone = ?, adresse = ?, email = ?, "
                + "type_contrat = ?, salaire_mensuel = ?, statut = ? WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            remplir(ps, e);
            ps.setInt(9, e.getId());
            if (ps.executeUpdate() == 0) {
                throw new EntiteIntrouvableException("Employe", e.getId());
            }
        } catch (SQLException ex) {
            throw new AccesDonneesException("Modification impossible : " + ex.getMessage(), ex);
        }
    }

    @Override
    public void delete(int id) throws AgroException {
        String sql = "DELETE FROM employes WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new EntiteIntrouvableException("Employe", id);
            }
        } catch (SQLException ex) {
            throw new AccesDonneesException("Suppression impossible : " + ex.getMessage(), ex);
        }
    }

    @Override
    public Employe findById(int id) throws AgroException {
        String sql = "SELECT " + COLONNES + " FROM employes WHERE id = ?";
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
        throw new EntiteIntrouvableException("Employe", id);
    }

    @Override
    public List<Employe> findAll() throws AgroException {
        String sql = "SELECT " + COLONNES + " FROM employes ORDER BY nom, prenom";
        List<Employe> resultat = new ArrayList<>();
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

    /** Remplit les 8 premiers « ? » (communs à l'INSERT et à l'UPDATE). */
    private void remplir(PreparedStatement ps, Employe e) throws SQLException {
        ps.setString(1, e.getNom());
        ps.setString(2, e.getPrenom());
        ps.setString(3, e.getTelephone());
        ps.setString(4, e.getAdresse());
        ps.setString(5, e.getEmail());
        ps.setString(6, e.getTypeContrat().name());
        ps.setDouble(7, e.getSalaireMensuel());
        ps.setString(8, e.getStatut().name());
    }

    /** Transforme la ligne courante du ResultSet en objet Employe. */
    private Employe lire(ResultSet rs) throws SQLException {
        Employe e = new Employe(rs.getString("nom"), rs.getString("prenom"),
                rs.getString("telephone"), rs.getString("adresse"), rs.getString("email"),
                TypeContrat.valueOf(rs.getString("type_contrat")),
                rs.getDouble("salaire_mensuel"),
                StatutEmploye.valueOf(rs.getString("statut")));
        e.setId(rs.getInt("id"));
        return e;
    }
}