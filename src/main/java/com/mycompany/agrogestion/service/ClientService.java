package com.mycompany.agrogestion.service;

import com.mycompany.agrogestion.dao.GenericDAO;
import com.mycompany.agrogestion.exceptions.AgroException;
import com.mycompany.agrogestion.exceptions.DonneeInvalideException;
import com.mycompany.agrogestion.model.Client;
import java.util.List;

/** Logique métier des clients. Règle : le code client est unique. */
public class ClientService extends ServiceCrud<Client> {

    public ClientService(GenericDAO<Client> dao) {
        super(dao);
    }

    public void ajouterClient(Client client) throws AgroException {
        client.valider();
        verifierCodeUnique(client);
        ajouter(client);
    }

    public void modifierClient(Client client) throws AgroException {
        client.valider();
        verifierCodeUnique(client);
        modifier(client);
    }

    public void supprimerClient(int id) throws AgroException {
        supprimer(id);
    }

    public List<Client> listerClients() throws AgroException {
        return lister();
    }

    private void verifierCodeUnique(Client client) throws AgroException {
        for (Client autre : lister()) {
            if (autre.getId() != client.getId()
                    && autre.getCodeClient().trim().equalsIgnoreCase(client.getCodeClient().trim())) {
                throw new DonneeInvalideException("Le code client « " + client.getCodeClient() + " » existe déjà.");
            }
        }
    }
}