package com.bbwism.repositories;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bbwism.Entites.User;
import com.bbwism.repositories.bd.core.Database;
import com.bbwism.repositories.impl.UserRepoImpl;

public class UserRepo implements UserRepoImpl {
    private Database database;
    private final String SQL_FIND_ALL_ACTIVE = "SELECT * FROM users WHERE active = 1"; // Exemple de requête
    private final String SQL_FIND_BY_ROLE = "SELECT * FROM users WHERE role = ?"; // Exemple de requête
    private final String SQL_FIND_BY_ID = "SELECT * FROM users WHERE id = ?"; // Exemple de requête
    private final String SQL_FIND_BY_TEL = "SELECT * FROM users WHERE tel = ?"; // Exemple de requête
    private final String SQL_FIND_ALL = "SELECT * FROM users"; // Exemple de requête
    private final String SQL_INSERT = "INSERT INTO users (login, nom, prenom, passWord, role) VALUES (?, ?, ?, ?, ?)"; // Exemple de requête

    public UserRepo(Database database) {
        this.database = database;
    }

    @Override
    public User findByID(int id) {
        return findById(id).orElse(null); // Utilise la méthode findById
    }

    @Override
    public List<User> findAllActive() {
        List<User> activeUsers = new ArrayList<>();
        try {
            database.openConnection();
            database.InitializePs(SQL_FIND_ALL_ACTIVE); // Assurez-vous d'avoir une requête SQL_FIND_ALL_ACTIVE
            ResultSet rs = database.executeSelect(); // Exécute la requête

            while (rs.next()) {
                User user = new User(); // Créez un nouvel objet User
                user.setId(rs.getInt("id")); // Récupère l'ID
                user.setLogin(rs.getString("login")); // Récupère le login
                user.setNom(rs.getString("nom")); // Récupère le nom
                user.setPrenom(rs.getString("prenom")); // Récupère le prénom
                user.setPassWord(rs.getString("passWord")); // Récupère le mot de passe
                user.setRole(rs.getString("role")); // Récupère le rôle
                if (user.isActifs()) { // Vérifie si l'utilisateur est actif
                    activeUsers.add(user); // Ajoute l'utilisateur actif à la liste
                }
            }
            database.closeConnexion();
            rs.close();
        } catch (Exception e) {
            System.out.printf("Erreur de récupération des utilisateurs actifs %s", UserRepo.class);
        }
        return activeUsers; // Retourne la liste des utilisateurs actifs
    }

    @Override
    public List<User> findByRole(String role) {
        List<User> usersByRole = new ArrayList<>();
        try {
            database.openConnection();
            database.InitializePs(SQL_FIND_BY_ROLE); // Assurez-vous d'avoir une requête SQL_FIND_BY_ROLE
            database.getPs().setString(1, role); // Utilise le rôle
            ResultSet rs = database.executeSelect(); // Exécute la requête

            while (rs.next()) {
                User user = new User(); // Créez un nouvel objet User
                user.setId(rs.getInt("id")); // Récupère l'ID
                user.setLogin(rs.getString("login")); // Récupère le login
                user.setNom(rs.getString("nom")); // Récupère le nom
                user.setPrenom(rs.getString("prenom")); // Récupère le prénom
                user.setPassWord(rs.getString("passWord")); // Récupère le mot de passe
                user.setRole(rs.getString("role")); // Récupère le rôle
                usersByRole.add(user); // Ajoute l'utilisateur à la liste
            }
            database.closeConnexion();
            rs.close();
        } catch (Exception e) {
            System.out.printf("Erreur de récupération des utilisateurs par rôle %s", UserRepo.class);
        }
        return usersByRole; // Retourne la liste des utilisateurs par rôle
    }

    @Override
    public Optional<User> findById(int id) {
        User user = null;
        try {
            database.openConnection();
            database.InitializePs(SQL_FIND_BY_ID); // Assurez-vous d'avoir une requête SQL_FIND_BY_ID
            database.getPs().setInt(1, id); // Utilise l'ID
            ResultSet rs = database.executeSelect(); // Exécute la requête

            if (rs.next()) {
                user = new User(); // Créez un nouvel objet User
                user.setId(rs.getInt("id")); // Récupère l'ID
                user.setLogin(rs.getString("login")); // Récupère le login
                user.setNom(rs.getString("nom")); // Récupère le nom
                user.setPrenom(rs.getString("prenom")); // Récupère le prénom
                user.setPassWord(rs.getString("passWord")); // Récupère le mot de passe
                user.setRole(rs.getString("role")); // Récupère le rôle
                // Récupérez d'autres attributs si nécessaire
            }
            database.closeConnexion();
            rs.close();
        } catch (Exception e) {
            System.out.printf("Erreur de recherche par ID %s", UserRepo.class);
        }
        return Optional.ofNullable(user); // Retourne un Optional
    }

    @Override
    public User findByTel(String tel) {
        User user = null;
        try {
            database.openConnection();
            database.InitializePs(SQL_FIND_BY_TEL); // Assurez-vous d'avoir une requête SQL_FIND_BY_TEL
            database.getPs().setString(1, tel); // Utilise le téléphone
            ResultSet rs = database.executeSelect(); // Exécute la requête

            if (rs.next()) {
                user = new User(); // Créez un nouvel objet User
                user.setId(rs.getInt("id")); // Récupère l'ID
                user.setLogin(rs.getString("login")); // Récupère le login
                user.setNom(rs.getString("nom")); // Récupère le nom
                user.setPrenom(rs.getString("prenom")); // Récupère le prénom
                user.setPassWord(rs.getString("passWord")); // Récupère le mot de passe
                user.setRole(rs.getString("role")); // Récupère le rôle
                // Récupérez d'autres attributs si nécessaire
            }
            database.closeConnexion();
            rs.close();
        } catch (Exception e) {
            System.out.printf("Erreur de recherche par téléphone %s", UserRepo.class);
        }
        return user; 
    }

    @Override
    public ArrayList<User> findAll() {
        ArrayList<User> users = new ArrayList<>();
        try {
            database.openConnection();
            database.InitializePs(SQL_FIND_ALL); // Assurez-vous d'avoir une requête SQL_FIND_ALL
            ResultSet rs = database.executeSelect(); // Exécute la requête

            while (rs.next()) {
                User user = new User(); // Créez un nouvel objet User
                user.setId(rs.getInt("id")); // Récupère l'ID
                user.setLogin(rs.getString("login")); // Récupère le login
                user.setNom(rs.getString("nom")); // Récupère le nom
                user.setPrenom(rs.getString("prenom")); // Récupère le prénom
                user.setPassWord(rs.getString("passWord")); // Récupère le mot de passe
                user.setRole(rs.getString("role")); // Récupère le rôle
                users.add(user); // Ajoute l'utilisateur à la liste
            }
            database.closeConnexion();
            rs.close();
        } catch (Exception e) {
            System.out.printf("Erreur de récupération des utilisateurs %s", UserRepo.class);
        }
        return users; // Retourne la liste des utilisateurs
    }

    @Override
    public int insert(User data) {
        int nbrLigne = 0;
        try {
            database.openConnection();
            database.InitializePs(SQL_INSERT); // Assurez-vous d'avoir une requête SQL_INSERT pour User
            database.getPs().setString(1, data.getLogin()); // Utilise le login
            database.getPs().setString(2, data.getNom()); // Utilise le nom
            database.getPs().setString(3, data.getPrenom()); // Utilise le prénom
            database.getPs().setString(4, data.getPassWord()); // Utilise le mot de passe
            database.getPs().setString(5, data.getRole()); // Utilise le rôle
            
            nbrLigne = database.executeUpdate(); // Exécute l'insertion de l'utilisateur
            
            database.closeConnexion();
        } catch (Exception e) {
            System.out.printf("Erreur d'insertion %s", UserRepo.class);
        }
        return nbrLigne; 
    }
}