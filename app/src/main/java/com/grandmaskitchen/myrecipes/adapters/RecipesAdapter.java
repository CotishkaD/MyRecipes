package com.grandmaskitchen.myrecipes.adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grandmaskitchen.myrecipes.MainActivity;
import com.grandmaskitchen.myrecipes.R;
import com.grandmaskitchen.myrecipes.db.entity.Recipes;

import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Recipes> recipesArrayList;
    MainActivity mainActivity;
    private RecipeClickListener recipeClickListener;

    public interface RecipeClickListener {
        void onRecipeClick(Recipes recipe);
    }

    public void setRecipeClickListener(RecipeClickListener listener) {
        this.recipeClickListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name_recipe;
        public ImageView right_arrow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name_recipe = itemView.findViewById(R.id.name_recipe_list_item);
            this.right_arrow = itemView.findViewById(R.id.right_arrow);

            //Click
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (recipeClickListener != null && position != RecyclerView.NO_POSITION) {
                        recipeClickListener.onRecipeClick(recipesArrayList.get(position));
                    }
                }
            };

            itemView.setOnClickListener(clickListener);
            name_recipe.setOnClickListener(clickListener);
            right_arrow.setOnClickListener(clickListener);

        }
    }

    public RecipesAdapter(Context context, ArrayList<Recipes> recipesArrayList) {
        this.context = context;
        this.recipesArrayList = recipesArrayList;
    }

    public RecipesAdapter(Context context, ArrayList<Recipes> recipesArrayList, MainActivity mainActivity) {
        this.context = context;
        this.recipesArrayList = recipesArrayList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipes_list_item, parent, false);

        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Recipes recipes = recipesArrayList.get(position);

        holder.name_recipe.setText(recipes.getRecipe_name());

        if (position == 0) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            int topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 42, context.getResources().getDisplayMetrics());
            layoutParams.topMargin = topMargin;
            holder.itemView.setLayoutParams(layoutParams);
        } else {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            layoutParams.topMargin = 0;
            holder.itemView.setLayoutParams(layoutParams);
        }
    }

    @Override
    public int getItemCount() {
        return recipesArrayList.size();
    }

    public void updateRecipes(List<Recipes> newRecipes) {
        recipesArrayList.clear();
        recipesArrayList.addAll(newRecipes);
        notifyDataSetChanged();
    }
}
