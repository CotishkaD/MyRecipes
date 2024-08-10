package com.grandmaskitchen.myrecipes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.grandmaskitchen.myrecipes.db.RecipesAppDatabase;
import com.grandmaskitchen.myrecipes.db.entity.Recipes;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class CreateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CATEGORY_NAME = "category_name";
    private static final String ARG_CATEGORY_NAME_IN_DB = "categoryNameInBD";

    // TODO: Rename and change types of parameters
    private String category_name;
    private String categoryNameInBD;

    private RecipesAppDatabase recipesAppDatabase;

    public CreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param category_name Parameter 1.
     * @param categoryNameInBD Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateFragment newInstance(String category_name, String categoryNameInBD) {
        CreateFragment fragment = new CreateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY_NAME, category_name);
        args.putString(ARG_CATEGORY_NAME_IN_DB, categoryNameInBD);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category_name = getArguments().getString(ARG_CATEGORY_NAME);
            categoryNameInBD = getArguments().getString(ARG_CATEGORY_NAME_IN_DB);
        }

        recipesAppDatabase = Room.databaseBuilder(getContext(), RecipesAppDatabase.class, "recipes_db").allowMainThreadQueries().build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create, container, false);

        ImageButton back_button_create = view.findViewById(R.id.back_button_create);
        Button btn_create = view.findViewById(R.id.btn_create);

        back_button_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText_nameRecipe_create = view.findViewById(R.id.editText_nameRecipe_create);
                EditText editText_ingredients_create = view.findViewById(R.id.editText_ingredients_create);
                EditText editText_description_create = view.findViewById(R.id.editText_description_create);

                String categoryName = categoryNameInBD;
                String recipeName = editText_nameRecipe_create.getText().toString();
                String ingredients = editText_ingredients_create.getText().toString();
                String description = editText_description_create.getText().toString();

                Recipes recipe = new Recipes();
                recipe.setCategory_name(categoryName);
                recipe.setRecipe_name(recipeName);
                recipe.setIngredients(ingredients);
                recipe.setDescription(description);

                recipesAppDatabase.getRecipesDAO().addRecipe(recipe);

                getFragmentManager().popBackStack();
            }
        });

        return view;
    }
}