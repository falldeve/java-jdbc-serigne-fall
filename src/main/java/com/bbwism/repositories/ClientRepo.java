package com.bbwism.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bbwism.Entites.Client;
import com.bbwism.Entites.Dette;
import com.bbwism.Entites.User;
import com.bbwism.repositories.bd.core.Database;
import com.bbwism.repositories.impl.ClientRepoImpl;

public class ClientRepo implements ClientRepoImpl {
    private Database database;

    private final String SQL_INSERT = "INSERT INTO clients (surname, tel, addresse, user_id) VALUES (?, ?, ?, ?)"; // Ajoutez la requête SQL appropriée

    private final String SQL_INSERT_DETTE = "INSERT INTO dettes (montant, solde, client_id) VALUES (?, ?, ?)"; // Ajoutez la requête SQL appropriée

    private final String SQL_FIND_ALL = "SELECT * FROM clients"; // Ajoutez la requête SQL_FIND_ALL

    private final String SQL_SELECT_BY_TEL = "SELECT * FROM clients WHERE tel = ?"; // Ajoutez la requête SQL_SELECT_BY_TEL

    private final String SQL_FIND_BY_ID = "SELECT * FROM clients WHERE id = ?"; // Ajoutez la requête SQL_FIND_BY_ID

    public ClientRepo(Database database){
        this.database=database;
    }

        @Override 
        public int insert(Client data) {
            int nbrLigne = 0;
            try {
                database.openConnection();
                database.InitializePs(SQL_INSERT); // Assurez-vous d'avoir une requête SQL_INSERT pour Client
                database.getPs().setString(1, data.getSurname()); // Utilise le surname
                database.getPs().setString(2, data.getTel()); // Utilise le tel
                database.getPs().setString(3, data.getAddresse()); // Utilise l'adresse
                
                // Ajout de l'utilisateur
                if (data.getUser() != null) {
                    database.getPs().setInt(4, data.getUser().getId()); // Assurez-vous que User a une méthode getId()
                } else {
                    database.getPs().setNull(4, java.sql.Types.INTEGER); // Si pas d'utilisateur, mettre NULL
                }
    
                nbrLigne = database.executeUpdate(); // Exécute l'insertion du client
                
                // Insertion des dettes
                for (Dette dette : data.getDettes()) {
                    database.InitializePs(SQL_INSERT_DETTE); // Assurez-vous d'avoir une requête SQL_INSERT_DETTE
                    database.getPs().setDouble(1, dette.getMontant()); // Utilise le montant
                    database.getPs().setBoolean(2, dette.isSolde()); // Utilise l'état solde
                    database.getPs().setInt(3, data.getId()); // Lier la dette au client inséré
                    
                    database.executeUpdate(); // Exécute l'insertion de la dette
                }
                
                database.closeConnexion();
            } catch (Exception e) {
                System.out.printf("Erreur d'insertion %s", ClientRepo.class);
            }
            return nbrLigne; 
        }

        @Override
        public ArrayList<Client> findAll() {
    List<Client> clients = new ArrayList<>();
    try {
        database.openConnection();
        database.InitializePs(SQL_FIND_ALL); // Assurez-vous d'avoir une requête SQL_FIND_ALL pour les clients
        ResultSet rs = database.executeSelect();

        while (rs.next()) {
            Client client = new Client();
            client.setId(rs.getInt("id"));
            client.setSurname(rs.getString("surname"));
            client.setTel(rs.getString("tel"));
            client.setAddresse(rs.getString("addresse"));
            // Si User et Dette sont des objets complexes, vous devrez les récupérer séparément

            clients.add(client); // Ajoute le client à la liste
        }
        database.closeConnexion();
        rs.close();
    } catch (Exception e) {
        System.out.printf("Erreur d'affichage %s", ClientRepo.class);
    }
    return new ArrayList<>(clients); // Retourne la liste des clients
}


    // ... code existant ...

    @Override
    public Client selectByTel(String tel) {
        // Implémentation de la méthode pour sélectionner un client par téléphone
        Client foundClient = null;
        try {
            database.openConnection();
            database.InitializePs(SQL_SELECT_BY_TEL); // Assurez-vous d'avoir une requête SQL_SELECT_BY_TEL
            database.getPs().setString(1, tel);
            ResultSet rs = database.executeSelect();

            if (rs.next()) {
                foundClient = new Client();
                foundClient.setId(rs.getInt("id"));
                foundClient.setSurname(rs.getString("surname"));
                foundClient.setTel(rs.getString("tel"));
                foundClient.setAddresse(rs.getString("addresse"));
                // Récupérer User et Dette si nécessaire
            }
            database.closeConnexion();
            rs.close();
        } catch (Exception e) {
            System.out.printf("Erreur de sélection par téléphone %s", ClientRepo.class);
        }
        return foundClient; // Retourne le client trouvé ou null
    }

    // ... code existant ...

    @Override
    public Client findByID(int id) {
        Client foundClient = null;
        try {
            database.openConnection();
            database.InitializePs(SQL_FIND_BY_ID); // Assurez-vous d'avoir une requête SQL_FIND_BY_ID
            database.getPs().setInt(1, id);
            ResultSet rs = database.executeSelect();

            if (rs.next()) {
                foundClient = new Client();
                foundClient.setId(rs.getInt("id"));
                foundClient.setSurname(rs.getString("surname"));
                foundClient.setTel(rs.getString("tel"));
                foundClient.setAddresse(rs.getString("addresse"));
                // Récupérer User et Dette si nécessaire
            }
            database.closeConnexion();
            rs.close();
        } catch (Exception e) {
            System.out.printf("Erreur de sélection par ID %s", ClientRepo.class);
        }
        return foundClient; // Retourne le client trouvé ou null
    }
}

    //    public void insert(Client client){
    //     list.add(client);
    // }
    // public List<Client> selectAll() {
    //     return list;
    // }

    // public Client selectByTel(String tel) {
    //     return list.stream()
    //     .filter(client -> client.getTel() != null && tel.equals(client.getTel()))
    //     .findFirst()
    //     .orElse(null);
    // }

   
