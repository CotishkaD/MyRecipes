package com.grandmaskitchen.myrecipes.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grandmaskitchen.myrecipes.db.entity.Recipes;

import java.util.List;

@Dao
public interface RecipesDAO {

    @Insert
    public long addRecipe(Recipes recipes);

    @Update
    public void updateRecipe(Recipes recipes);

    @Delete
    public void deleteRecipe(Recipes recipes);

    @Query("delete from recipes where recipe_id ==:recipe_id")
    public void deleteRecipeById(long recipe_id);

    @Query("select * from recipes where category_name ==:category_name")
    public List<Recipes> getRecipesFromCategory(String category_name);

    @Query("select * from recipes where recipe_id ==:recipe_id")
    public List<Recipes> getRecipe(long recipe_id);

}
