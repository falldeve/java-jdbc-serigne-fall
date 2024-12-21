package com.bbwism.Entites;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class User {
    private int id;

    private String login;
    private String nom;
    private String prenom;
    private String passWord;
    private String role;
    private Client client;
    private List<Client> clients = new ArrayList<>();
    private boolean actifs;

    public User(String nom, String email, String role, boolean actifs) {
        this.id = id;
        this.nom = nom;
        this.role = role;
        this.actifs = actifs;
        this.clients = new ArrayList<>();
    }

    public User() {
        //TODO Auto-generated constructor stub
    }

    public User(int i, String login2, String nom2, String prenom2, String passWord2, String role2, boolean b) {
        //TODO Auto-generated constructor stub
    }

    public void addClient(Client client) {
        if (client != null) {
            clients.add(client);
            client.setUser(this);
        }
    }
  

}
