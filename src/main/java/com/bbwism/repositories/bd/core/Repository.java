package com.bbwism.repositories.bd.core;
import java.util.ArrayList;

public interface Repository<T>  {

    int insert(T data);

    // int update(T data);

    ArrayList<T> findAll();

    T findByID (int id);

    // int delete (int id);

    // int indexOf (int id);

    
}
