package com.mycompany.agrogestion;

import com.mycompany.agrogestion.dao.ClientDAO;
import com.mycompany.agrogestion.dao.FournisseurDAO;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.model.Client;
import com.mycompany.agrogestion.model.Fournisseur;

/** Test manuel de ClientDAO et FournisseurDAO : à lancer avec Run File (Maj+F6). */
public class TestPersonnesDAO {

    public static void main(String[] args) throws AgroException {
        ClientDAO clients = new ClientDAO();
        Client c = new Client("Durand", "Awa", "0611111111", "Place du Marché",
                "awa@mail.fr", "C001", "Particulier");
        clients.add(c);
        System.out.println("Client ajouté, id = " + c.getId());
        Client cRelu = clients.findById(c.getId());
        System.out.println("Relu : " + cRelu + " | " + cRelu.getCodeClient() + " | " + cRelu.getTypeClient());
        cRelu.setTypeClient("Professionnel");
        clients.update(cRelu);
        System.out.println("Après modification : " + clients.findById(c.getId()).getTypeClient());
        System.out.println("Nombre de clients : " + clients.findAll().size());
        clients.delete(c.getId());

        FournisseurDAO fournisseurs = new FournisseurDAO();
        Fournisseur f = new Fournisseur("Martin", "Paul", "0600000000", "Rue des Champs",
                "paul@semences.fr", "F001", "Semences");
        fournisseurs.add(f);
        System.out.println("Fournisseur ajouté, id = " + f.getId());
        Fournisseur fRelu = fournisseurs.findById(f.getId());
        System.out.println("Relu : " + fRelu + " | " + fRelu.getCodeFournisseur() + " | " + fRelu.getSpecialite());
        fRelu.setSpecialite("Engrais");
        fournisseurs.update(fRelu);
        System.out.println("Après modification : " + fournisseurs.findById(f.getId()).getSpecialite());
        System.out.println("Nombre de fournisseurs : " + fournisseurs.findAll().size());
        fournisseurs.delete(f.getId());

        try {
            fournisseurs.findById(f.getId());
        } catch (AgroException ex) {
            System.out.println("Refusé : " + ex.getMessage());
        }
    }
}