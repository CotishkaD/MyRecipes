package com.grandmaskitchen.myrecipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.grandmaskitchen.myrecipes.db.RecipesAppDatabase;
import com.grandmaskitchen.myrecipes.db.entity.Recipes;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment {

    private static final String ARG_RECIPE_ID = "recipe_id";
    private int recipe_id;

    private ScrollView scrollView;
    private EditText editText_nameRecipe_edit;
    private EditText editText_ingredients_edit;
    private EditText editText_description_edit;


    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(String recipe_id) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RECIPE_ID, Integer.parseInt(recipe_id));
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
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        scrollView = view.findViewById(R.id.scrollView);
        editText_nameRecipe_edit = view.findViewById(R.id.editText_nameRecipe_edit);
        editText_ingredients_edit = view.findViewById(R.id.editText_ingredients_edit);
        editText_description_edit = view.findViewById(R.id.editText_description_edit);
        Button btn_edit = view.findViewById(R.id.btn_edit);
        ImageButton back_button_edit = view.findViewById(R.id.back_button_edit);

        back_button_edit.setOnClickListener(new View.OnClickListener() {
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

        if (recipesList != null && !recipesList.isEmpty()) {
            Recipes recipe = recipesList.get(0);
            editText_nameRecipe_edit.setText(recipe.getRecipe_name());
            editText_ingredients_edit.setText(recipe.getIngredients());
            editText_description_edit.setText(recipe.getDescription());
        }

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recipesList != null && !recipesList.isEmpty()) {
                    Recipes recipeToUpdate = recipesList.get(0);
                    recipeToUpdate.setRecipe_name(editText_nameRecipe_edit.getText().toString());
                    recipeToUpdate.setIngredients(editText_ingredients_edit.getText().toString());
                    recipeToUpdate.setDescription(editText_description_edit.getText().toString());

                    recipesAppDatabase.getRecipesDAO().updateRecipe(recipeToUpdate);

                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });


        return view;
    }

}
