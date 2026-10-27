/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.agrogestion;

/**
 *
 * @author PC
 */
public class Agrogestion {

    public static void main(String[] args) {
com.mycompany.agrogestion.model.Employe e = new com.mycompany.agrogestion.model.Employe(
        "Sow", "Ibrahima", "0622222222", "Rue A", "ib@mail.fr",
        com.mycompany.agrogestion.model.TypeContrat.PERMANENT, -100,
        com.mycompany.agrogestion.model.StatutEmploye.ACTIF);
try {
    e.valider();
} catch (com.mycompany.agrogestion.exceptions.AgroException ex) {
    System.out.println("Refusé : " + ex.getMessage());
}    }
}
