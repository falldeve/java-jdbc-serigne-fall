package com.bbwism.services.impl;
import java.util.ArrayList;
import java.util.List;
import com.bbwism.Entites.Article;

public interface ArticleServiceImpl {
    void creerArticle(String nom, String description, int qteStock);
    ArrayList<Article> listerTousArticles();
    List<Article> listerArticlesDisponibles();
    
}
