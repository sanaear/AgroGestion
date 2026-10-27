/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.agrogestion;

import com.mycompany.agrogestion.model.*;
import java.time.LocalDate;
public class Agrogestion {

    public static void main(String[] args) {


Parcelle p = new Parcelle("Parcelle Nord", 12.5, "Route de la Forêt");
Culture c1 = new Culture(p, "Blé", LocalDate.of(2026, 3, 1), LocalDate.of(2026, 7, 15), "SEMEE");
Culture c2 = new Culture(p, "Orge", LocalDate.of(2026, 6, 1), LocalDate.of(2026, 9, 1), "PLANIFIEE");
Culture c3 = new Culture(p, "Orge", LocalDate.of(2026, 8, 1), LocalDate.of(2026, 10, 1), "PLANIFIEE");
System.out.println(c1.chevauche(c2)); // true  : juin est entre mars et juillet
System.out.println(c1.chevauche(c3)); // false : août est après la récolte de juillet    }
}
}