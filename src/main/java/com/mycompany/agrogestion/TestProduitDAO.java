package com.mycompany.agrogestion;

import com.mycompany.agrogestion.dao.ProduitDAO;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.model.Produit;

/** Test manuel de ProduitDAO : à lancer avec Run File (Maj+F6). */
public class TestProduitDAO {

    public static void main(String[] args) throws AgroException {
        ProduitDAO dao = new ProduitDAO();

        Produit ble = new Produit("Blé Test", "Céréale", 100, "kg", 20, 0.45);
        dao.add(ble);
        System.out.println("Id attribué : " + ble.getId());

        Produit relu = dao.findById(ble.getId());
        System.out.println("Relu : " + relu + " | " + relu.getQuantite() + " " + relu.getUnite()
                + " | seuil " + relu.getSeuilAlerte() + " | prix " + relu.getPrixUnitaire());

        // Simule ce que fera StockService : retirer du stock puis enregistrer le produit
        relu.retirerQuantite(30);
        dao.update(relu);
        System.out.println("Stock après sortie : " + dao.findById(ble.getId()).getQuantite());
        System.out.println("Nombre de produits : " + dao.findAll().size());

        dao.delete(ble.getId());
        try {
            dao.findById(ble.getId());
        } catch (AgroException ex) {
            System.out.println("Refusé : " + ex.getMessage());
        }
    }
}