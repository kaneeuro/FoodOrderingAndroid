package com.sadicomputing.foodordering.service;

import com.sadicomputing.foodordering.entity.Article;
import com.sadicomputing.foodordering.entity.Categorie;
import com.sadicomputing.foodordering.entity.Commande;
import com.sadicomputing.foodordering.entity.CommandeArticle;
import com.sadicomputing.foodordering.entity.CommandeArticleTemporaire;
import com.sadicomputing.foodordering.entity.Compte;
import com.sadicomputing.foodordering.entity.Employe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by modykane on 22/10/2017.
 */

public interface RetrofitService {

    @POST("loginandpassword")
    @FormUrlEncoded
    Call<Compte> authentification(@Field("login") String login, @Field("password") String password);

    @GET("articlesbycategorie/{id}")
    Call<List<Article>> getAllArticlesByCategorie(@Path("id") Long id);

    @GET("articles")
    Call<List<Article>> getAllArticles();

    @POST("temporaires")
    @FormUrlEncoded
    Call<CommandeArticleTemporaire> saveArticleTemporaire(@Field("id_employe") Long id_employe, @Field("id_article") Long id_article);

    @POST("articlestemporaires")
    @FormUrlEncoded
    Call<List<CommandeArticleTemporaire>> getArticlesTemporairesByEmploye(@Field("id_employe") Long id_employe);

    @POST("commandearticlesbyserveur")
    Call<List<CommandeArticle>> getCommandeArticlesByEmploye(@Body Employe employe);

    @GET("serveurCommandesDuJour/{serveur}")
    Call<List<CommandeArticle>> getServeurCommandesDuJour(@Path("serveur") String login);

    @GET("cuisinierCommandesDuJour/{cuisinier}")
    Call<List<CommandeArticle>> getCuisinierCommandesDuJour(@Path("cuisinier") String login);

    @GET("comptableCommandesDuJour/{comptable}")
    Call<List<Commande>> getComptableCommandesDuJour(@Path("comptable") String login);

    @DELETE("temporaires/{id}")
    Call<String> deleteArticleTemporaire(@Path("id") Long id);

    @POST("commandearticle")
    Call<CommandeArticle> saveCommandeArticle(@Body CommandeArticle commandeArticle);

    @POST("commandes")
    Call<Commande> saveCommande(@Body Commande commande);

    @POST("commandearticles")
    Call<List<CommandeArticle>> saveCommandeArticles(@Body List<CommandeArticle> commandeArticles);

    @HTTP(method = "DELETE", path = "temporaires", hasBody = true)
    Call<String> deleteAllArticlesTemporaires(@Body List<CommandeArticleTemporaire> temporaires);

    @DELETE("deletetemporairesbyemploye/{id}")
    Call<String> deleteAllArticlesTemporairesByEmploye(@Path("id") Long idemploye);

    @GET("categories")
    Call<List<Categorie>> getAllCategories();

    @GET("commandearticles")
    Call<List<CommandeArticle>> getAllCommandeArticles();

    @GET("articlesbystatutcommande/{statut}")
    Call<List<CommandeArticle>> getAllCommandeArticlesByStatutCommande(@Path("statut") int statut);

    @GET("articlesbystatutandstatutcommande/{statut}/{statutcommande}")
    Call<List<CommandeArticle>> getAllCommandeArticlesByStatutAndStatutCommande(@Path("statut") int statut, @Path("statutcommande") int statutcommande);

    @PUT("commandearticles/{id}")
    Call<CommandeArticle> updateCommandeArticle(@Path("id") Long id, @Body CommandeArticle commandeArticle);

    @PUT("commandes/{id}")
    Call<Commande> updateCommande(@Path("id") Long id, @Body Commande commande);

    @GET("commandesbystatut/{statut}")
    Call<List<Commande>> getCommandesByStatut(@Path("statut") int statut);

    @GET("articlesbycommande/{numero}")
    Call<List<CommandeArticle>> getCommandeArticlesByCommande(@Path("numero") Long numero);

}
