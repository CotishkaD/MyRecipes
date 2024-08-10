package com.grandmaskitchen.myrecipes.adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;

import com.grandmaskitchen.myrecipes.R;
import com.grandmaskitchen.myrecipes.models.CategoriesModel;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<CategoriesModel> {

    public CategoryAdapter(@NonNull Context context, ArrayList<CategoriesModel> categoriesModelArrayList) {
        super(context, 0, categoriesModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.category_card_item, parent, false);
        }

        CategoriesModel categoriesModel = getItem(position);
        TextView categoryName = listItemView.findViewById(R.id.categoryName);
        ImageView categoryImage = listItemView.findViewById(R.id.categoryImage);

        categoryName.setText(categoriesModel.getCategoryName());
        categoryImage.setImageResource(categoriesModel.getCategoryImage());

        // Получаем общее количество элементов
        int itemCount = getCount();
        int numColumns = 2; // Указали количество колонок
        int topPadding = 0;
        int bottomPadding = 0;

        // Устанавливаем верхний отступ для первых элементов
        if (position < numColumns) {
            topPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 42, getContext().getResources().getDisplayMetrics());
        }

        // Устанавливаем нижний отступ для последнего элемента
        if (position == itemCount - 1) {
            bottomPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 42, getContext().getResources().getDisplayMetrics());
        }

        listItemView.setPadding(
                listItemView.getPaddingLeft(),
                topPadding,
                listItemView.getPaddingRight(),
                bottomPadding
        );

        return listItemView;
    }
}
