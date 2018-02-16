package com.telstra.telstraexcercise.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.telstra.telstraexcercise.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sopan on 13-02-18.
 */

public class FactsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.card_parent)
    CardView card_parent;

    @BindView(R.id.txt_fact_title)
    TextView txt_title;

    @BindView(R.id.txt_fact_description)
    TextView txt_description;

    @BindView(R.id.img_fact_picture)
    ImageView img_picture;

    public FactsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
