package com.grandmaskitchen.myrecipes.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
public class Recipes {

    @ColumnInfo(name = "recipe_id")
    @PrimaryKey(autoGenerate = true)
    private int recipe_id;

    @ColumnInfo(name = "category_name")
    private String category_name;

    @ColumnInfo(name = "recipe_name")
    private String recipe_name;

    @ColumnInfo(name = "ingredients")
    private String ingredients;

    @ColumnInfo(name = "description")
    private String description;

    @Ignore
    public Recipes(){}

    public Recipes(int recipe_id, String category_name, String recipe_name, String ingredients, String description) {
        this.recipe_id = recipe_id;
        this.category_name = category_name;
        this.recipe_name = recipe_name;
        this.ingredients = ingredients;
        this.description = description;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
