package com.bbwism.repositories.bd.core;

public class MySQLImpl extends DatabaseImpl {
    public MySQLImpl() {
        super("com.mysql.cj.jdbc.Driver","jdbc:mysql://localhost:3306/gestion_dette");
    }
    
}
