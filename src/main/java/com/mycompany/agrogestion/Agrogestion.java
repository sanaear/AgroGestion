/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.agrogestion;
import com.mycompany.agrogestion.dao.MemoireDao;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.model.*;
import com.mycompany.agrogestion.service.*;
import java.time.LocalDate;
/**
 *
 * @author PC
 */

public class Agrogestion {

    public static void main(String[] args) {
        ParcelleService ps = new ParcelleService(new MemoireDao<>("Parcelle"));
CultureService cs = new CultureService(new MemoireDao<>("Culture"));
ProduitService prs = new ProduitService(new MemoireDao<>("Produit"));
try {
    Parcelle nord = new Parcelle("Parcelle Nord", 12.5, "Route de la Forêt");
    ps.ajouterParcelle(nord);
    cs.ajouterCulture(new Culture(nord, "Blé", LocalDate.of(2026, 3, 1), LocalDate.of(2026, 7, 15), "SEMEE"));
    try {
        cs.ajouterCulture(new Culture(nord, "Orge", LocalDate.of(2026, 6, 1), LocalDate.of(2026, 9, 1), "PLANIFIEE"));
    } catch (AgroException e) {
        System.out.println("Refusé : " + e.getMessage());
    }
    cs.ajouterCulture(new Culture(nord, "Orge", LocalDate.of(2026, 8, 1), LocalDate.of(2026, 10, 1), "PLANIFIEE"));
    System.out.println("Cultures sur la parcelle : " + cs.listerCulturesParParcelle(nord.getId()).size());

    prs.ajouterProduit(new Produit("Blé", "Céréale", 0, "kg", 20, 0.45));
    prs.ajouterProduit(new Produit("blé", "Céréale", 0, "kg", 20, 0.45));
} catch (AgroException e) {
    System.out.println("Refusé : " + e.getMessage());
}
        
}   
}
