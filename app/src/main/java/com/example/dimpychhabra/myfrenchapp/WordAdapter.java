package com.example.dimpychhabra.myfrenchapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dimpy Chhabra on 3/9/2017.
 */

public class WordAdapter extends ArrayAdapter<Word>{

    private  int ColorResourceId;

    public WordAdapter(Context context, ArrayList<Word> wordArrayList, int colorResourceId){
        super(context, 0, wordArrayList);
        ColorResourceId=colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        //getting the view into a variable listItemView
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.items_list, parent, false);
        }
        //This is to ensure the reusablity of a view

        Word currentWord = getItem(position);

        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentWord.getDefaultLangTranslationId());

        TextView frenchTextView = (TextView) listItemView.findViewById(R.id.french_text_view);
        frenchTextView.setText(currentWord.getFrenchLangTranslationId());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        if(currentWord.hasImage()){
            imageView.setImageResource(currentWord.getImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
            //Visibility is set as per requirement
        }

        View textContainer = listItemView.findViewById(R.id.sub_linear_layout);
        int color = ContextCompat.getColor(getContext(), ColorResourceId);
        textContainer.setBackgroundColor(color);
        // we do the extra step to get tha actual value of color in form yyrrggbb or whatever

        return listItemView;
    }
}
