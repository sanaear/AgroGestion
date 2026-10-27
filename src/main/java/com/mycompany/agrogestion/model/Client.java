package com.mycompany.agrogestion.model;

import com.mycompany.agrogestion.exceptions.DonneeInvalideException;
import com.mycompany.agrogestion.util.Validation;

/** Client (acheteur). */
public class Client extends Personne {

    private String codeClient;
    private String typeClient;

    public Client() {
    }

    public Client(String nom, String prenom, String telephone, String adresse, String email,
            String codeClient, String typeClient) {
        super(nom, prenom, telephone, adresse, email);
        this.codeClient = codeClient;
        this.typeClient = typeClient;
    }

    public String getCodeClient() {
        return codeClient;
    }

    public void setCodeClient(String codeClient) {
        this.codeClient = codeClient;
    }

    public String getTypeClient() {
        return typeClient;
    }

    public void setTypeClient(String typeClient) {
        this.typeClient = typeClient;
    }


    @Override
    public void valider() throws DonneeInvalideException {
        super.valider();
        Validation.texteNonVide("code client", codeClient);
    }
}