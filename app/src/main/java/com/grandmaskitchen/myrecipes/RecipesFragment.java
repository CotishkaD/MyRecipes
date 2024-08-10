package com.grandmaskitchen.myrecipes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.grandmaskitchen.myrecipes.adapters.RecipesAdapter;
import com.grandmaskitchen.myrecipes.db.RecipesAppDatabase;
import com.grandmaskitchen.myrecipes.db.entity.Recipes;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipesFragment extends Fragment {

    private RecipesAppDatabase recipesAppDatabase;
    private ArrayList<Recipes> recipesArrayList;
    private RecipesAdapter recipesAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CATEGORY_NAME = "category_name";
    private static final String ARG_CATEGORY_NAME_IN_DB = "categoryNameInBD";

    // TODO: Rename and change types of parameters
    private String category_name;
    private String categoryNameInBD;

    public RecipesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param category_name Parameter 1.
     * @param categoryNameInBD Parameter 2.
     * @return A new instance of fragment RecipesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipesFragment newInstance(String category_name, String categoryNameInBD) {
        RecipesFragment fragment = new RecipesFragment();
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

        recipesArrayList = new ArrayList<>(recipesAppDatabase.getRecipesDAO().getRecipesFromCategory(categoryNameInBD));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recipes, container, false);

        TextView recipes_top_bar_text_view = view.findViewById(R.id.recipes_top_bar_text_view);
        ImageButton back_button = view.findViewById(R.id.back_button);
        ImageButton add_fab_button = view.findViewById(R.id.add_fab_button);

        recipes_top_bar_text_view.setText(category_name);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        add_fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateFragment createFragment = CreateFragment.newInstance(category_name, categoryNameInBD);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, createFragment).addToBackStack(null).commit();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recipes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recipesAdapter = new RecipesAdapter(getContext(), recipesArrayList, (MainActivity) getActivity());
        recyclerView.setAdapter(recipesAdapter);

        // Настройка обработчика клика
        recipesAdapter.setRecipeClickListener(new RecipesAdapter.RecipeClickListener() {
            @Override
            public void onRecipeClick(Recipes recipe) {
                RecipeItemFragment recipeItemFragment = RecipeItemFragment.newInstance(
                        recipe.getRecipe_id()
                );
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, recipeItemFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        updateRecipesList();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateRecipesList();
    }

    public void updateRecipesList(){
        List<Recipes> recipesList = recipesAppDatabase.getRecipesDAO().getRecipesFromCategory(categoryNameInBD);
        recipesAdapter.updateRecipes(recipesList);
    }


}