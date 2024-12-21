package com.bbwism.repositories.impl;

import java.util.List;

import com.bbwism.Entites.Article;
import com.bbwism.repositories.bd.core.Repository;

public interface ArticleRepoImpl extends Repository <Article>{
   
    List<Article> findAvailable();
}
