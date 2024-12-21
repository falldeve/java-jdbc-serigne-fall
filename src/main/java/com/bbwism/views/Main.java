package com.bbwism.views;

import java.util.List;
import java.util.Scanner;

import com.bbwism.Entites.Client; 
import com.bbwism.Entites.User;   
import com.bbwism.repositories.ArticleRepo;
import com.bbwism.repositories.UserRepo;
import com.bbwism.repositories.bd.core.Database;
import com.bbwism.repositories.bd.core.MySQLImpl;
import com.bbwism.repositories.impl.ArticleRepoImpl;
import com.bbwism.repositories.impl.UserRepoImpl;
import com.bbwism.services.ArticleService;
import com.bbwism.services.ClientCreateImpl;
import com.bbwism.services.UserService;
import com.bbwism.services.impl.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        int choix;
        Scanner scanner = new Scanner(System.in);
        
        Database db = new MySQLImpl();

        UserRepo userRepo = new UserRepoImpl(db); // Utilisation de l'interface UserRepo
        UserService userService = new UserServiceImpl(userRepo); // Utilisation de l'implémentation UserServiceImpl
        ArticleRepo articleRepo = new ArticleRepoImpl(db); // Ajout de l'initialisation de ArticleRepo
        ArticleService articleService = new ArticleService(articleRepo); // Utilisation de ArticleService
        ClientCreateImpl clientCreateImpl = new ClientCreateImpl(); // Initialisation de ClientCreateImpl

        do {
            afficherMenu();
            choix = scanner.nextInt();
            scanner.nextLine(); 

            switch (choix) {
                case 1:
                    Client client = new Client();
                    System.out.println("Entrer le surnom");
                    client.setSurname(scanner.nextLine());
                    System.out.println("Entrer le téléphone");
                    client.setTel(scanner.nextLine());
                    System.out.println("Entrer l'adresse");
                    client.setAddresse(scanner.nextLine());
                    clientCreateImpl.create(client);
                    break;

                case 2:
                    listerClients(clientCreateImpl);
                    break;

                case 3:
                    rechercherClient(scanner, clientCreateImpl);
                    break;

                case 4:
                    creerUtilisateurBoutique(scanner, userService);
                    break;

                case 5:
                    creerComptePourClient(scanner, userService);
                    break;

                case 6:
                    activerDesactiverUtilisateur(scanner, userService);
                    break;

                case 7:
                    afficherUtilisateursActifs(userService);
                    break;

                case 8:
                    afficherUtilisateursParRole(scanner, userService);
                    break;

                case 9:
                    creerArticle(scanner, articleService);
                    break;

                case 10:
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        } while (choix != 10); 

        scanner.close(); 
    }

    private static void afficherMenu() {
        System.out.println("1. Créer client");
        System.out.println("2. Lister clients");
        System.out.println("3. Rechercher client par téléphone");
        System.out.println("4. Créer un utilisateur boutiquier");
        System.out.println("5. Créer compte utilisateur pour un client existant");
        System.out.println("6. Désactiver/Activer un compte utilisateur");
        System.out.println("7. Afficher les comptes utilisateurs actifs");
        System.out.println("8. Afficher les comptes utilisateurs par rôle");
        System.out.println("9. Créer des articles");
        System.out.println("10. Quitter");
    }

    private static void listerClients(ClientCreateImpl clientCreateImpl) {
        System.out.println("Liste des clients :");
        List<Client> list = clientCreateImpl.findAll();
        list.forEach(System.out::println);
    }

    private static void rechercherClient(Scanner scanner, ClientCreateImpl clientCreateImpl) {
        System.out.println("Recherche du client par téléphone :");
        String tel = scanner.nextLine();
        Client client = clientCreateImpl.search(tel);
        if (client != null) {
            System.out.println("Client trouvé : " + client);
        } else {
            System.out.println("Aucun client trouvé avec ce numéro.");
        }
    }

    private static void creerUtilisateurBoutique(Scanner scanner, UserService userService) {
        System.out.println("Création d'un utilisateur boutique :");
        System.out.println("Entrez le nom :");
        String nom = scanner.nextLine();
        System.out.println("Entrez le numéro de téléphone :");
        String telephone = scanner.nextLine();
        System.out.println("Entrez l'adresse :");
        String adresse = scanner.nextLine();

        User nouveauBoutiquier = userService.creerUtilisateurBoutique(nom, telephone, adresse);
        if (nouveauBoutiquier != null) {
            System.out.println("Utilisateur boutique créé avec succès : " + nouveauBoutiquier);
        } else {
            System.out.println("Échec de la création de l'utilisateur boutique.");
        }
    }

    private static void creerComptePourClient(Scanner scanner, UserService userService) {
        System.out.println("Création d'un compte utilisateur pour un client existant :");
        System.out.println("Entrez le numéro de téléphone du client :");
        String telClient = scanner.nextLine();
        User nouveauCompte = userService.creerCompteUtilisateurPourClient(telClient);
        if (nouveauCompte != null) {
            System.out.println("Compte utilisateur créé avec succès : " + nouveauCompte);
        } else {
            System.out.println("Échec de la création du compte utilisateur.");
        }
    }

    private static void activerDesactiverUtilisateur(Scanner scanner, UserService userService) {
        System.out.print("Entrez le numéro de téléphone de l'utilisateur : ");
        String telUltilisateur = scanner.nextLine();
        boolean resultat = userService.activerDesactiverUtilisateur(telUltilisateur);
        if (resultat) {
            System.out.println("Le statut de l'utilisateur a été modifié avec succès.");
        } else {
            System.out.println("Impossible de trouver l'utilisateur ou de modifier son statut.");
        }
    }

    private static void afficherUtilisateursActifs(UserService userService) {
        System.out.println("\nComptes utilisateurs actifs :");
        List<User> utilisateursActifs = userService.getUtilisateursActifs();
        if (utilisateursActifs.isEmpty()) {
            System.out.println("Aucun compte utilisateur actif trouvé.");
        } else {
            for (User user : utilisateursActifs) {
                afficherDetailsUtilisateur(user);
            }
        }
    }

    private static void afficherUtilisateursParRole(Scanner scanner, UserService userService) {
        System.out.print("Entrez le rôle (CLIENT, BOUTIQUIER, etc.) : ");
        String role = scanner.nextLine().toUpperCase();
        List<User> utilisateursDuRole = userService.getUtilisateursByRole(role);
        if (utilisateursDuRole.isEmpty()) {
            System.out.println("Aucun compte utilisateur trouvé pour le rôle : " + role);
        } else {
            System.out.println("Comptes utilisateurs pour le rôle " + role + " :");
            for (User user : utilisateursDuRole) {
                afficherDetailsUtilisateur(user);
            }
        }
    }

    private static void creerArticle(Scanner scanner, ArticleService articleService) {
        System.out.print("Entrez le nom de l'article : ");
        String nomArticle = scanner.nextLine();
        System.out.print("Entrez la description de l'article : ");
        String description = scanner.nextLine();
        System.out.print("Entrez la quantité en stock : ");
        int qteStock = 0;
        try {
            qteStock = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erreur : Veuillez entrer un nombre entier pour la quantité.");
            return; // Sortir de la méthode en cas d'erreur
        }
        
        try {
            articleService.creerArticle(nomArticle, description, qteStock);
            System.out.println("L'article a été créé avec succès.");
        } catch (Exception e) {
            System.out.println("Impossible de créer l'article : " + e.getMessage());
        }
    }

    private static void afficherDetailsUtilisateur(User user) {
        System.out.println("ID : " + user.getId());
        System.out.println("Nom : " + user.getNom());
        System.out.println("Rôle : " + user.getRole());
        System.out.println("Actif : " + (user.isActifs() ? "Oui" : "Non"));
        if (user.getClient() != null) {
            System.out.println("Téléphone client : " + user.getClient().getTel());
        }
        System.out.println("--------------------");
    }
}