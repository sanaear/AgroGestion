package com.mycompany.agrogestion.dao;

import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.model.Entite;
import java.util.List;

/**
 * CONTRAT que tous les DAO doivent respecter (interface générique).
 * Les services ne connaissent QUE cette interface : ils fonctionnent aussi bien avec les DAO MySQL
 * qu'avec MemoireDao (tests sans base).
 *
 * Règles du contrat :
 *  - add(e)    : insère et renseigne l'identifiant de e (e.setId(...)) ;
 *  - update(e) : met à jour la ligne d'identifiant e.getId(), lève EntiteIntrouvableException si absente ;
 *  - delete(id): supprime, lève EntiteIntrouvableException si absente ;
 *  - findById  : lève EntiteIntrouvableException si absente ;
 *  - findAll   : liste complète (jamais null).
 * Un DAO enveloppe toute SQLException dans une AccesDonneesException.
 * Pour les objets composés : AchatDAO/VenteDAO insèrent aussi les lignes (getLignes()), et les objets lus
 * doivent être COMPLETS (le Produit d'une ligne, le Client d'une vente, la Parcelle d'une culture...).
 */
public interface GenericDAO<T extends Entite> {

    void add(T element) throws AgroException;

    void update(T element) throws AgroException;

    void delete(int id) throws AgroException;

    T findById(int id) throws AgroException;

    List<T> findAll() throws AgroException;
}