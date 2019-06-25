package com.jayshah.newsapp.HeadlinesUI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jayshah.newsapp.R;
import com.jayshah.newsapp.models.TopHeadline;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class NewsHeadlinesAdapter extends RecyclerView.Adapter<NewsHeadlinesAdapter.NewsHeadlinesViewHolder> {

    List<TopHeadline> allHeadlines;
    Context mContext;
    OnItemClickListener itemClickListener;

    NewsHeadlinesAdapter(Context context){
        this.allHeadlines = new ArrayList<>();
        mContext = context;
        setHasStableIds(true);
//        Picasso.get().setIndicatorsEnabled(true);
//        Picasso.get().setLoggingEnabled(true);
    }

    interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setData(List<TopHeadline> headlines) {
        this.allHeadlines = headlines;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsHeadlinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_headline_item, parent, false);

        return new NewsHeadlinesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHeadlinesViewHolder holder, int position) {
        holder.title.setText(allHeadlines.get(position).title);
        holder.sourceName.setText(allHeadlines.get(position).source.name);
        if(allHeadlines.get(position).publishedAt != null) {
            holder.publishedAt.setText(allHeadlines.get(position).publishedAt.toString());
        }
        Picasso.get().load(allHeadlines.get(position).urlToImage)
                    .resizeDimen(R.dimen.headline_image_width, R.dimen.headline_image_height)
                    .tag(mContext)
                    .into(holder.itemImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null) {
                    itemClickListener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return allHeadlines.get(position).url.hashCode();
    }

    @Override
    public int getItemCount() {
        return allHeadlines.size();
    }

    public static class NewsHeadlinesViewHolder extends RecyclerView.ViewHolder {
        TextView publishedAt, title, sourceName;
        ImageView itemImage;
        public NewsHeadlinesViewHolder(@NonNull View itemView) {
            super(itemView);
            publishedAt = (TextView) itemView.findViewById(R.id.publishedAt);
            title = (TextView) itemView.findViewById(R.id.title);
            sourceName = (TextView) itemView.findViewById(R.id.source_name);
            itemImage = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }
}
