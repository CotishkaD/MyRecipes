package com.grandmaskitchen.myrecipes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.grandmaskitchen.myrecipes.adapters.CategoryAdapter;
import com.grandmaskitchen.myrecipes.models.CategoriesModel;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {

    GridView category_grid_view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        category_grid_view = view.findViewById(R.id.category_grid_view);

        ArrayList<CategoriesModel> categoriesModelArrayList = new ArrayList<>();

        categoriesModelArrayList.add(new CategoriesModel(R.string.soups, R.drawable.category_soup, "soup"));
        categoriesModelArrayList.add(new CategoriesModel(R.string.salads, R.drawable.category_salad, "salad"));
        categoriesModelArrayList.add(new CategoriesModel(R.string.mains, R.drawable.category_hot_dishes, "mains"));
        categoriesModelArrayList.add(new CategoriesModel(R.string.desserts, R.drawable.category_desserts, "desserts"));
        categoriesModelArrayList.add(new CategoriesModel(R.string.sauce, R.drawable.category_sause, "sauce"));
        categoriesModelArrayList.add(new CategoriesModel(R.string.jams, R.drawable.category_jam, "jam"));
        categoriesModelArrayList.add(new CategoriesModel(R.string.pickles, R.drawable.category_pickles, "pickles"));
        categoriesModelArrayList.add(new CategoriesModel(R.string.bake, R.drawable.category_bake, "bake"));
        categoriesModelArrayList.add(new CategoriesModel(R.string.drinks, R.drawable.category_drinks, "drinks"));

        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categoriesModelArrayList);

        category_grid_view.setAdapter(categoryAdapter);
        
        category_grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategoriesModel categoriesModel = categoriesModelArrayList.get(position);

                openRecipesFragment(categoriesModel);
            }
        });

        return view;
    }

    private void openRecipesFragment(CategoriesModel categoriesModel) {
        String categoryName = getString(categoriesModel.getCategoryName());

        RecipesFragment recipesFragment = RecipesFragment.newInstance(categoryName, categoriesModel.getCategoryNameInBD());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, recipesFragment).addToBackStack(null).commit();
    }
}