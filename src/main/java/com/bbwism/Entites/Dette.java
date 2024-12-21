package com.bbwism.Entites;

import lombok.Data;

@Data
public class Dette {
    private int id;

    private double montant;
    private boolean solde;
    private Client client;
    
}
