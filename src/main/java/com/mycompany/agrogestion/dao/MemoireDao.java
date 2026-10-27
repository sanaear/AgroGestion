package com.mycompany.agrogestion.dao;

import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.exceptions.EntiteIntrouvableException;
import com.mycompany.agrogestion.model.Entite;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO « en mémoire » : respecte le contrat de GenericDAO sans base de données.
 * Sert à tester la logique métier (services) sans MySQL. Les objets sont conservés par référence.
 */
public class MemoireDao<T extends Entite> implements GenericDAO<T> {

    private final String nomEntite;
    private final Map<Integer, T> donnees = new LinkedHashMap<>();
    private int prochainId = 1;

    public MemoireDao(String nomEntite) {
        this.nomEntite = nomEntite;
    }

    @Override
    public void add(T element) {
        element.setId(prochainId++);
        donnees.put(element.getId(), element);
    }

    @Override
    public void update(T element) throws AgroException {
        if (!donnees.containsKey(element.getId())) {
            throw new EntiteIntrouvableException(nomEntite, element.getId());
        }
        donnees.put(element.getId(), element);
    }

    @Override
    public void delete(int id) throws AgroException {
        if (donnees.remove(id) == null) {
            throw new EntiteIntrouvableException(nomEntite, id);
        }
    }

    @Override
    public T findById(int id) throws AgroException {
        T element = donnees.get(id);
        if (element == null) {
            throw new EntiteIntrouvableException(nomEntite, id);
        }
        return element;
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(donnees.values());
    }
}