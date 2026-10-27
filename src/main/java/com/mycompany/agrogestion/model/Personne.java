
package com.mycompany.agrogestion.model;


public class Personne {
    private int id;
    String nom;
    String tele;
    String address;
    String email;

    public Personne(int id, String nom, String tele, String address, String email) {
        this.id = id;
        this.nom = nom;
        this.tele = tele;
        this.address = address;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
    
}
