/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.agrogestion;

import com.mycompany.agrogestion.model.*;

public class Agrogestion {

    public static void main(String[] args) {

Fournisseur f = new Fournisseur("Martin", "Paul", "0600000000", "Rue A", "paul@semences.fr", "F001", "Semences");
Produit ble = new Produit("Blé", "Céréale", 0, "kg", 20, 0.45);
Achat achat = new Achat(f, "Coop Agri", EtatPaiement.PAYE);
try {
    achat.valider();                       // refusé : aucune ligne
} catch (com.mycompany.agrogestion.exceptions.AgroException e) {
    System.out.println("Refusé : " + e.getMessage());
}
achat.ajouterLigne(new LigneAchat(ble, 100, 0.40));
achat.ajouterLigne(new LigneAchat(ble, 50, 0.42));
System.out.println(achat.getMontant());    // 61.0    }
}
}