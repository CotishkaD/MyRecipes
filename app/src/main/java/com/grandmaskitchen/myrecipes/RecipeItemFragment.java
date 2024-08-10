package com.grandmaskitchen.myrecipes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.grandmaskitchen.myrecipes.db.RecipesAppDatabase;
import com.grandmaskitchen.myrecipes.db.entity.Recipes;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeItemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match

    private static final String ARG_RECIPE_ID = "recipe_id";

    // TODO: Rename and change types of parameters
    private int recipe_id;


    public RecipeItemFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RecipeItemFragment newInstance(int recipe_id) {
        RecipeItemFragment fragment = new RecipeItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RECIPE_ID, recipe_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe_id = getArguments().getInt(ARG_RECIPE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_item, container, false);

        TextView name_recipe_top_bar_text_view = view.findViewById(R.id.name_recipe_top_bar_text_view);
        TextView textView_recipeName = view.findViewById(R.id.textView_recipeName);
        TextView ingredients_item_text_one = view.findViewById(R.id.ingredients_item_text_one);
        TextView ingredients_item_text_two = view.findViewById(R.id.ingredients_item_text_two);
        TextView description_text_item = view.findViewById(R.id.description_text_item);
        ImageButton back_button_recipe_item = view.findViewById(R.id.back_button_recipe_item);
        ImageButton delete_button_recipe_item = view.findViewById(R.id.delete_btn_recipe_item);
        ImageButton edit_btn_recipe_item = view.findViewById(R.id.edit_btn_recipe_item);

        back_button_recipe_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        RecipesAppDatabase recipesAppDatabase = Room.databaseBuilder(
                getContext(), RecipesAppDatabase.class, "recipes_db")
                .allowMainThreadQueries()
                .build();
        List<Recipes> recipesList = recipesAppDatabase.getRecipesDAO().getRecipe(recipe_id);

        if(recipesList != null && !recipesList.isEmpty()) {
            Recipes recipes = recipesList.get(0);

            name_recipe_top_bar_text_view.setText(recipes.getRecipe_name());
            textView_recipeName.setText(recipes.getRecipe_name());
            description_text_item.setText(recipes.getDescription());

            splitAndSetIngredients(recipes.getIngredients(), ingredients_item_text_one, ingredients_item_text_two);

        }

        edit_btn_recipe_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditFragment editFragment = EditFragment.newInstance(String.valueOf(recipe_id));
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, editFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        delete_button_recipe_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog deleteDialog = new Dialog(getContext());
                deleteDialog.setContentView(R.layout.delete_window);
                deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                ImageButton okButton = deleteDialog.findViewById(R.id.ok_btn);
                ImageButton cancelButton = deleteDialog.findViewById(R.id.not_btn);

                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recipesAppDatabase.getRecipesDAO().deleteRecipeById(recipe_id);
                        deleteDialog.dismiss();
                        getFragmentManager().popBackStack();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog.dismiss();
                    }
                });

                deleteDialog.show();
            }
        });

        return view;
    }

    private void splitAndSetIngredients(String ingredients, TextView textViewOne, TextView textViewTwo) {
        String[] ingredientsArray = ingredients.split("\n");
        int middleIndex = (ingredientsArray.length + 1) / 2;

        StringBuilder ingredientsOne = new StringBuilder();
        StringBuilder ingredientsTwo = new StringBuilder();

        for (int i = 0; i < middleIndex; i++) {
            ingredientsOne.append(ingredientsArray[i]).append("\n");
        }

        for (int i = middleIndex; i < ingredientsArray.length; i++) {
            ingredientsTwo.append(ingredientsArray[i]).append("\n");
        }

        if (ingredientsOne.length() > 0 && ingredientsOne.charAt(ingredientsOne.length() - 1) == '\n') {
            ingredientsOne.setLength(ingredientsOne.length() - 1);
        }
        if (ingredientsTwo.length() > 0 && ingredientsTwo.charAt(ingredientsTwo.length() - 1) == '\n') {
            ingredientsTwo.setLength(ingredientsTwo.length() - 1);
        }

        textViewOne.setText(ingredientsOne.toString());
        textViewTwo.setText(ingredientsTwo.toString());
    }
}