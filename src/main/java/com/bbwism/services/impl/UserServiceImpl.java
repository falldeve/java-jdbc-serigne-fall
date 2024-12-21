package com.bbwism.services.impl;

import java.util.List;

import com.bbwism.Entites.User;

public interface UserServiceImpl {
     User creerCompteUtilisateurPourClient(String telephone);
     boolean activerDesactiverUtilisateur(String telephone);
     User creerUtilisateurBoutique(String nom, String telephone, String adresse);
     List<User> getUtilisateursActifs();
     List<User> getUtilisateursByRole(String role);
}
