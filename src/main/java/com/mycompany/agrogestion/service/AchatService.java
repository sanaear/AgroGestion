package com.mycompany.agrogestion.service;

import com.mycompany.agrogestion.dao.GenericDAO;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.model.Achat;
import com.mycompany.agrogestion.model.LigneAchat;
import com.mycompany.agrogestion.model.MouvementStock;
import com.mycompany.agrogestion.model.TypeMouvement;
import java.util.List;

/** Logique métier des achats : enregistrer un achat augmente le stock des produits achetés. */
public class AchatService extends ServiceCrud<Achat> {

    private final StockService stockService;

    public AchatService(GenericDAO<Achat> dao, StockService stockService) {
        super(dao);
        this.stockService = stockService;
    }

    /** Valide, calcule le total, enregistre l'achat puis crée un mouvement ENTREE par ligne. */
    public void enregistrerAchat(Achat achat) throws AgroException {
        achat.valider();
        achat.calculerTotal();
        dao.add(achat);
        for (LigneAchat ligne : achat.getLignes()) {
            stockService.ajouterMouvement(new MouvementStock(
                    ligne.getProduit(), TypeMouvement.ENTREE, ligne.getQuantite(), "Achat n°" + achat.getId()));
        }
    }

    public double calculerTotalAchat(Achat achat) {
        return achat.calculerTotal();
    }

    public List<Achat> listerAchats() throws AgroException {
        return lister();
    }
}