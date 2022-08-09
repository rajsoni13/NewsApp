package com.example.mynewsapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    TextView text_title, text_source, text_publish, text_discription;
    ImageView img_headline;
    CardView cardView;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        text_title = itemView.findViewById(R.id.text_title);
        text_source = itemView.findViewById(R.id.text_source);
        img_headline = itemView.findViewById(R.id.img_headline);
        text_publish = itemView.findViewById(R.id.text_publish);
        text_discription = itemView.findViewById(R.id.text_description);
        cardView = itemView.findViewById(R.id.main_container);

    }
}
