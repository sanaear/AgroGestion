/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.agrogestion;
import com.mycompany.agrogestion.dao.MemoireDao;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.model.*;
import com.mycompany.agrogestion.service.*;

/**
 *
 * @author PC
 */

public class Agrogestion {

    public static void main(String[] args) {
        ParcelleService ps = new ParcelleService(new MemoireDao<>("Parcelle"));
EmployeService es = new EmployeService(new MemoireDao<>("Employe"));
try {
    es.ajouterEmploye(new Employe("Sow", "Ibrahima", "0622222222", "Rue A", "ib@mail.fr",
            TypeContrat.PERMANENT, 1800, StatutEmploye.ACTIF));
    es.ajouterEmploye(new Employe("Diop", "Awa", "0633333333", "Rue B", "awa@mail.fr",
            TypeContrat.SAISONNIER, 1500, StatutEmploye.INACTIF));
    System.out.println("Masse salariale : " + es.calculerMasseSalariale());   // 1800.0

    ps.ajouterParcelle(new Parcelle("Parcelle Nord", 12.5, "Route de la Forêt"));
    ps.ajouterParcelle(new Parcelle("parcelle nord", 3, "ailleurs"));
} catch (AgroException e) {
    System.out.println("Refusé : " + e.getMessage());
}
        
}   
}

