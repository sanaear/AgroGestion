package com.mycompany.agrogestion.service;

import com.mycompany.agrogestion.dao.GenericDAO;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.exceptions.ParcelleOccupeeException;
import com.mycompany.agrogestion.model.Culture;
import java.util.ArrayList;
import java.util.List;

/** Logique métier des cultures. Règle : deux cultures ne se chevauchent pas sur une même parcelle. */
public class CultureService extends ServiceCrud<Culture> {

    public CultureService(GenericDAO<Culture> dao) {
        super(dao);
    }

    public void ajouterCulture(Culture culture) throws AgroException {
        culture.valider();
        verifierParcelleLibre(culture);
        ajouter(culture);
    }

    public void modifierCulture(Culture culture) throws AgroException {
        culture.valider();
        verifierParcelleLibre(culture);
        modifier(culture);
    }

    public void supprimerCulture(int id) throws AgroException {
        supprimer(id);
    }

    public List<Culture> listerCultures() throws AgroException {
        return lister();
    }

    public List<Culture> listerCulturesParParcelle(int parcelleId) throws AgroException {
        List<Culture> resultat = new ArrayList<>();
        for (Culture c : lister()) {
            if (c.getParcelle().getId() == parcelleId) {
                resultat.add(c);
            }
        }
        return resultat;
    }

    private void verifierParcelleLibre(Culture culture) throws AgroException {
        for (Culture autre : listerCulturesParParcelle(culture.getParcelle().getId())) {
            if (autre.getId() != culture.getId() && autre.chevauche(culture)) {
                throw new ParcelleOccupeeException(culture.getParcelle().getNom());
            }
        }
    }
}