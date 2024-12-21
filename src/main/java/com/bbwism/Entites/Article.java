package com.bbwism.Entites;

import lombok.Data;

@Data
public class Article {
    private int id;
    private String nom;
    private String description;
    private int qteStock;
    private boolean archived;
    

    // public Article(String nom, String description, int qteStock) {
    //     this.nom = nom;
    //     this.description = description;
    //     this.qteStock = qteStock;
    //      // Par défaut, un nouvel article n'est pas archivé
    // }
    
}
