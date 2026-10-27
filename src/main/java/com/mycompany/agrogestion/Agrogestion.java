/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.agrogestion;

/**
 *
 * @author PC
 */
import com.mycompany.agrogestion.dao.MemoireDao;
import com.mycompany.agrogestion.model.Parcelle;
public class Agrogestion {

    public static void main(String[] args) {
MemoireDao<Parcelle> dao = new MemoireDao<>("Parcelle");
Parcelle p = new Parcelle("Parcelle Nord", 12.5, "Route de la Forêt");
try {
    dao.add(p);
    System.out.println("Id attribué : " + p.getId());          // 1
    System.out.println("Nombre : " + dao.findAll().size());    // 1
    dao.findById(99);
} catch (com.mycompany.agrogestion.exceptions.AgroException e) {
    System.out.println("Refusé : " + e.getMessage());
}    }
}
