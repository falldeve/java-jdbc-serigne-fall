package com.bbwism.services;

import java.util.List;

import com.bbwism.Entites.Client;
import com.bbwism.Entites.User;
import com.bbwism.repositories.ClientRepo;
import com.bbwism.repositories.UserRepo;
import com.bbwism.services.impl.UserServiceImpl;
public class UserService implements UserServiceImpl {
    private UserRepo userRepository;
    private ClientRepo clientRepository;
    private ClientCreateImpl clientCreateImpl;

    public UserService(UserRepo userRepository, ClientRepo clientRepository, ClientCreateImpl clientCreateImpl) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.clientCreateImpl = clientCreateImpl;
    }
    public UserService(UserRepo userRepo, ClientCreateImpl clientCreateImpl) {
        this.userRepository = userRepo;
        this.clientCreateImpl = clientCreateImpl;
    }
    // public UserService(UserRepo userRepo) {
    //     this.userRepo = userRepo; // Initialisation du dépôt
    // }


    public UserService(UserRepo userRepo) {
        this.userRepository = userRepo;
    }
    public void create(User user) {
        userRepository.insert(user);
    }

    @Override
    public User creerCompteUtilisateurPourClient(String telephone) {
        Client client = clientCreateImpl.search(telephone);
        if (client == null) {
            System.out.println("Aucun client trouvé avec ce numéro de téléphone.");
            return null;
        }

        User existingUser = userRepository.findByTel(telephone);
        if (existingUser != null) {
            System.out.println("Ce client a déjà un compte utilisateur.");
            return null;
        }

        User newUser = new User(client.getSurname(), telephone, "Client", true);
        newUser.setClient(client);
        userRepository.insert(newUser);

        System.out.println("Compte utilisateur créé avec succès pour le client : " + client.getSurname());
        return newUser;
    }

    @Override
    public User creerUtilisateurBoutique(String nom, String telephone, String adresse) {
        if (nom == null || nom.isEmpty() || telephone == null || telephone.isEmpty()) {
            System.out.println("Le nom et le téléphone sont obligatoires pour créer un utilisateur boutique.");
            return null;
        }

        try {
            User existingUser = userRepository.findByTel(telephone);
            if (existingUser != null) {
                System.out.println("Un utilisateur avec ce numéro de téléphone existe déjà.");
                return null;
            }

            User newUser = new User(nom, telephone, "Boutiquier", true);
            userRepository.insert(newUser);

            System.out.println("Utilisateur boutique créé avec succès : " + nom);
            return newUser;
        } catch (Exception e) {
            System.out.println("Erreur lors de la création de l'utilisateur boutique : " + e.getMessage());
            return null;
        }
    }
    
    @Override
       public List<User> getUtilisateursActifs() {
        return userRepository.findAllActive();
    }

    @Override
    public List<User> getUtilisateursByRole(String role) {
        return userRepository.findByRole(role);
    }


    
    public boolean activerDesactiverUtilisateur(String telephone) {
        User user = userRepository.findByTel(telephone);
        if (user != null) {
            user.setActifs(!user.isActifs());
            // userRepository.update(user);
            return true;
        }
        return false;
    }
}