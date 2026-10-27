package com.mycompany.agrogestion;

import com.mycompany.agrogestion.dao.GenericDAO;
import com.mycompany.agrogestion.dao.MemoireDao;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.model.*;
import com.mycompany.agrogestion.service.*;
import java.time.LocalDate;

/**
 * Démonstration console de la logique métier, SANS base de données (DAO en mémoire).
 * À lancer pour vérifier que les services respectent les règles. Quand les vrais DAO MySQL existeront,
 * il suffira de remplacer les MemoireDao par EmployeDAO, ProduitDAO... (même interface GenericDAO).
 */
public class DemoMetier {

    public static void main(String[] args) throws AgroException {
        GenericDAO<Produit> produits = new MemoireDao<>("Produit");
        GenericDAO<MouvementStock> mouvements = new MemoireDao<>("Mouvement");

        ParcelleService parcelleService = new ParcelleService(new MemoireDao<>("Parcelle"));
        CultureService cultureService = new CultureService(new MemoireDao<>("Culture"));
        ProduitService produitService = new ProduitService(produits);
        StockService stockService = new StockService(produits, mouvements);
        FournisseurService fournisseurService = new FournisseurService(new MemoireDao<>("Fournisseur"));
        ClientService clientService = new ClientService(new MemoireDao<>("Client"));
        AchatService achatService = new AchatService(new MemoireDao<>("Achat"), stockService);
        VenteService venteService = new VenteService(new MemoireDao<>("Vente"), produits, stockService);
        EmployeService employeService = new EmployeService(new MemoireDao<>("Employe"));

        titre("1. Données de base");
        Fournisseur fournisseur = new Fournisseur("Martin", "Paul", "0600000000", "Rue des Champs",
                "paul@semences.fr", "F001", "Semences");
        fournisseurService.ajouterFournisseur(fournisseur);
        Client client = new Client("Durand", "Awa", "0611111111", "Place du Marché",
                "awa@mail.fr", "C001", "Particulier");
        clientService.ajouterClient(client);
        Produit ble = new Produit("Blé", "Céréale", 0, "kg", 20, 0.45);
        produitService.ajouterProduit(ble);
        System.out.println("Fournisseur, client et produit créés. Stock de blé : "
                + stockService.calculerStock(ble.getId()));

        titre("2. Achat de 100 kg de blé : le stock augmente");
        Achat achat = new Achat(fournisseur, "Coop Agri", EtatPaiement.PAYE);
        achat.ajouterLigne(new LigneAchat(ble, 100, 0.40));
        achatService.enregistrerAchat(achat);
        System.out.println("Total achat : " + achatService.calculerTotalAchat(achat)
                + " | stock de blé : " + stockService.calculerStock(ble.getId()));

        titre("3. Vente de 30 kg : le stock diminue");
        Vente vente = new Vente(client, "Marché", EtatPaiement.PAYE);
        vente.ajouterLigne(new LigneVente(ble, 30, 0.60));
        venteService.enregistrerVente(vente);
        System.out.println("Total vente : " + venteService.calculerTotalVente(vente)
                + " | stock de blé : " + stockService.calculerStock(ble.getId()));

        titre("4. Vente de 100 kg : refusée (stock insuffisant)");
        try {
            Vente trop = new Vente(client, "Marché", EtatPaiement.PAYE);
            trop.ajouterLigne(new LigneVente(ble, 100, 0.60));
            venteService.enregistrerVente(trop);
        } catch (AgroException e) {
            System.out.println("Refusé : " + e.getMessage());
        }
        System.out.println("Stock inchangé : " + stockService.calculerStock(ble.getId())
                + " | ventes enregistrées : " + venteService.listerVentes().size());

        titre("5. Alerte de stock minimum");
        Vente grosse = new Vente(client, "Marché", EtatPaiement.NON_PAYE);
        grosse.ajouterLigne(new LigneVente(ble, 55, 0.60));
        venteService.enregistrerVente(grosse);
        System.out.println("Stock de blé : " + stockService.calculerStock(ble.getId())
                + " | produits en alerte : " + stockService.verifierStockMinimum());
        System.out.println("Mouvements du blé : " + stockService.listerMouvementsParProduit(ble.getId()).size());

        titre("6. Règles sur les parcelles et les cultures");
        Parcelle nord = new Parcelle("Parcelle Nord", 12.5, "Route de la Forêt");
        parcelleService.ajouterParcelle(nord);
        essayer("Doublon de nom", () -> parcelleService.ajouterParcelle(new Parcelle("parcelle nord", 3, "x")));
        cultureService.ajouterCulture(new Culture(nord, "Blé", LocalDate.of(2026, 3, 1),
                LocalDate.of(2026, 7, 15), "SEMEE"));
        essayer("Culture qui chevauche la précédente", () -> cultureService.ajouterCulture(
                new Culture(nord, "Orge", LocalDate.of(2026, 6, 1), LocalDate.of(2026, 9, 1), "PLANIFIEE")));
        cultureService.ajouterCulture(new Culture(nord, "Orge", LocalDate.of(2026, 8, 1),
                LocalDate.of(2026, 10, 1), "PLANIFIEE"));
        System.out.println("Cultures sur la parcelle : " + cultureService.listerCulturesParParcelle(nord.getId()).size()
                + " | superficie totale : " + parcelleService.calculerSuperficieTotale() + " ha");

        titre("7. Validation d'un employé");
        essayer("Salaire négatif", () -> employeService.ajouterEmploye(
                new Employe("Sow", "Ibrahima", "0622222222", "Rue A", "ib@mail.fr",
                        TypeContrat.PERMANENT, -100, StatutEmploye.ACTIF)));
        employeService.ajouterEmploye(new Employe("Sow", "Ibrahima", "0622222222", "Rue A", "ib@mail.fr",
                TypeContrat.PERMANENT, 1800, StatutEmploye.ACTIF));
        System.out.println("Masse salariale (actifs) : " + employeService.calculerMasseSalariale());

        titre("8. Indicateurs");
        System.out.println("Valeur du stock : " + stockService.calculerValeurStock());
        System.out.println("CA encaissé : " + venteService.calculerChiffreAffairesEncaisse());
    }

    private interface Action {

        void executer() throws AgroException;
    }

    private static void essayer(String description, Action action) {
        try {
            action.executer();
            System.out.println(description + " : accepté (inattendu !)");
        } catch (AgroException e) {
            System.out.println(description + " -> refusé : " + e.getMessage());
        }
    }

    private static void titre(String texte) {
        System.out.println("\n=== " + texte + " ===");
    }
}