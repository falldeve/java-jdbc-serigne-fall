package com.bbwism.repositories.bd.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface Database {
    void openConnection();
    void closeConnexion();
    ResultSet executeSelect();
    int executeUpdate();
    PreparedStatement getPs();
    PreparedStatement InitializePs(String sql);


}
