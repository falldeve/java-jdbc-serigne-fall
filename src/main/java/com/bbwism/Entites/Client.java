package com.bbwism.Entites;

import java.util.List;
import java.util.ArrayList;


import lombok.Data;


@Data

public class Client {
    private int id;

    private String surname;
    private String tel;
    private String addresse;

    private User user;
    private List<Dette> dettes= new ArrayList<>();
    
}
