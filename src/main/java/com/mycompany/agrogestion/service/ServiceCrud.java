package com.mycompany.agrogestion.service;

import com.mycompany.agrogestion.dao.GenericDAO;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.model.Entite;
import java.util.List;

/**
 * Base générique des services : valide l'entité puis délègue au DAO.
 * Chaque service concret expose des méthodes nommées pour son métier (ajouterEmploye...)
 * et ajoute ses propres règles (unicité, stock, chevauchement...).
 */
public abstract class ServiceCrud<T extends Entite> {

    protected final GenericDAO<T> dao;

    protected ServiceCrud(GenericDAO<T> dao) {
        this.dao = dao;
    }

    protected void ajouter(T element) throws AgroException {
        element.valider();
        dao.add(element);
    }

    protected void modifier(T element) throws AgroException {
        element.valider();
        dao.update(element);
    }

    protected void supprimer(int id) throws AgroException {
        dao.delete(id);
    }

    protected List<T> lister() throws AgroException {
        return dao.findAll();
    }

    protected T trouver(int id) throws AgroException {
        return dao.findById(id);
    }
}