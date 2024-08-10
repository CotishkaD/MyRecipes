package com.grandmaskitchen.myrecipes.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.grandmaskitchen.myrecipes.db.entity.Recipes;

@Database(entities = {Recipes.class}, version = 1)
public abstract class RecipesAppDatabase extends RoomDatabase {

    public abstract RecipesDAO getRecipesDAO();

}
