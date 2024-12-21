package com.bbwism.services;

import java.util.ArrayList;
import java.util.List;

import com.bbwism.Entites.Article;
import com.bbwism.repositories.ArticleRepo;
import com.bbwism.services.impl.ArticleServiceImpl;

public class ArticleService implements ArticleServiceImpl {
    
      private ArticleRepo articleRepo;
   
    public ArticleService(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }
    public ArticleService() {
        
    }

    @Override
    public void creerArticle(String nom, String description, int qteStock) {
        Article article = new Article();
        article.setNom(nom);
        article.setDescription(description);
        article.setQteStock(qteStock);
        articleRepo.insert(article);
    }
    @Override
    public ArrayList<Article> listerTousArticles() {
        return articleRepo.findAll();
    }
    @Override
    public List<Article> listerArticlesDisponibles() {
        return articleRepo.findAvailable();
    }
}
