/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.agrogestion;
import com.mycompany.agrogestion.model.Produit;

/**
 *
 * @author PC
 */
public class Agrogestion {

    public static void main(String[] args) {

Produit ble = new Produit("Blé", "Céréale", 100, "kg", 20, 0.45);
try {
    ble.retirerQuantite(30);
    System.out.println("Stock : " + ble.getQuantite());   // 70.0
    ble.retirerQuantite(100);
} catch (com.mycompany.agrogestion.exceptions.AgroException e) {
    System.out.println("Refusé : " + e.getMessage());
}    }
}
