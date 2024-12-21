package com.bbwism.repositories;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.bbwism.Entites.Article;
import com.bbwism.repositories.bd.core.Database;
import com.bbwism.repositories.impl.ArticleRepoImpl;

public class ArticleRepo implements ArticleRepoImpl{
      private Database database;
    // private ModuleRepo moduleRepository;

    private final String SQL_INSERT = "INSERT INTO `article` (`nom`, `description`, `qteStock`, `archived`) VALUES (?, ?, ?, ?)"; // Requête d'insertion
    private final String SQL_UPDATE = "UPDATE `article` SET `nom`=?, `description`=?, `qteStock`=?, `archived`=? WHERE `id` = ?"; // Requête de mise à jour
    private final String SQL_FIND_ALL = "SELECT * FROM `article`"; // Requête pour trouver tous les articles
    private final String SQL_DELETE = "DELETE FROM `article` WHERE id = ?"; // Requête de suppression
    private final String SQL_FIND_BY_ID = "SELECT * FROM `article` WHERE id = ?"; // Requête pour trouver un article par ID
   
    public ArticleRepo(Database database) {

        this.database = database;
        // this.articleRepo = articleRepo;
    }
    //  private List<Article> articles = new ArrayList<>();
    // private int nextId = 1;

    // public void save(Article article) {
    //     if (article.getId() == 0) {
    //         article.setId(nextId++);
    //     }
    //     articles.add(article);
    // }

    // public List<Article> findAll() {
    //     return new ArrayList<>(articles);
    // }
    // @Override
    // public Article findByID(int id) {
    //     return articles.stream()
    //             .filter(article -> article.getId() == id)
    //             .findFirst()
    //             .orElse(null); // Retourne null si l'article n'est pas trouvé
    // }

    @Override
    public int insert(Article data) {
        int nbrLigne = 0;
        try {
            database.openConnection();
            database.InitializePs(SQL_INSERT);
            database.getPs().setString(1, data.getNom()); // Utilise le nom en premier
            database.getPs().setString(2, data.getDescription());
            database.getPs().setInt(3, data.getQteStock());
            database.getPs().setBoolean(4, data.isArchived()); // Utilise l'état archivé
            
            nbrLigne = database.executeUpdate(); // Exécute l'insertion
            database.closeConnexion();
        } catch (Exception e) {
            System.out.printf("Erreur d'insertion %s", ArticleRepo.class);
        }
        return nbrLigne; 
    }


    @Override
    public ArrayList<Article> findAll() {
        ArrayList<Article> articles = new ArrayList<>();
        try {
            database.openConnection();
            database.InitializePs(SQL_FIND_ALL);
            ResultSet rs = database.executeSelect();

            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt("id"));
                article.setDescription(rs.getString("description"));
                article.setNom(rs.getString("nom"));
                article.setQteStock(rs.getInt("qteStock"));
                article.setArchived(rs.getBoolean("archived"));

                // Suppression de la logique de modules, car elle n'est pas nécessaire ici
                articles.add(article); // Ajoute l'article à la liste
            }
            database.closeConnexion();
            rs.close();
        } catch (Exception e) {
            System.out.printf("Erreur d'affichage %s", ArticleRepo.class);
        }
        return articles; // Retourne la liste des articles
    }

    @Override
    public Article findByID(int id) {
        Article article = null;
        try {
            database.openConnection();
            database.InitializePs(SQL_FIND_BY_ID);
            database.getPs().setInt(1, id);
            ResultSet rs = database.executeSelect();

            if (rs.next()) {
                article = new Article();
                article.setId(rs.getInt("id"));
                article.setDescription(rs.getString("description"));
                article.setNom(rs.getString("nom"));
                article.setQteStock(rs.getInt("qteStock"));
                article.setArchived(rs.getBoolean("archived"));
            }
            database.closeConnexion();
            rs.close();
        } catch (Exception e) {
            System.out.printf("Erreur de recherche %s", ArticleRepo.class);
        }
        return article; // Retourne l'article trouvé ou null
    }
    @Override
    public List<Article> findAvailable() {
        List<Article> articles = findAll(); // Récupère tous les articles
        return articles.stream()
                .filter(a -> a.getQteStock() > 0 && !a.isArchived())
                .collect(Collectors.toList());
    }
}
