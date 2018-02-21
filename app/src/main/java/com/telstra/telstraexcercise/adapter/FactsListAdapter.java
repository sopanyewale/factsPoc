package com.telstra.telstraexcercise.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.telstra.telstraexcercise.R;
import com.telstra.telstraexcercise.business.model.FactsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sopan on 13-02-18.
 */

public class FactsListAdapter extends RecyclerView.Adapter<FactsViewHolder> {

    Context context;
    ArrayList<FactsItem> factsItems;

    public FactsListAdapter(Context context, @NonNull List<FactsItem> factsList) {
        this.context = context;
        this.factsItems = (ArrayList<FactsItem>) factsList;
    }

    @Override
    public FactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fact, parent, false);
        return new FactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FactsViewHolder holder, int position) {
        FactsItem factsItem = factsItems.get(position);
        String title = factsItem.getTitle();
        String desc = factsItem.getDescription();
        String imgUrl = factsItem.getImageHref();

        holder.txt_title.setText(title != null ? title.trim() : "");
        holder.txt_description.setText(desc != null ? desc.trim() : "");

        if (URLUtil.isValidUrl(imgUrl)) {
            holder.img_picture.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(factsItem.getImageHref())
                    .resizeDimen(R.dimen.recycler_image_size, R.dimen.recycler_image_size)
                    .centerInside()
                    //.placeholder(R.drawable.ic_error_outline_black_24dp)
                    .into(holder.img_picture, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.img_picture.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {
                            holder.img_picture.setVisibility(View.GONE);
                        }
                    });

        } else {
            holder.img_picture.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return factsItems.size();
    }
}
