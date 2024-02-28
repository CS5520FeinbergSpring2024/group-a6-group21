package edu.northeastern.group21;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SentStickersAdapter extends RecyclerView.Adapter<SentStickersAdapter.ReceivedStickerHolder> {

    private final List<SentSticker> sentStickers;
    private Context context;

    public SentStickersAdapter(List<SentSticker> sentStickers, Context context) {
        this.sentStickers = sentStickers;
        this.context = context;
    }

    @NonNull
    @Override
    public ReceivedStickerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sent_sticker, parent, false);
        return new ReceivedStickerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceivedStickerHolder holder, int position) {
        SentSticker sentSticker = sentStickers.get(position);
        holder.stickerImage.setImageResource(R.drawable.frame1);
        holder.stickerCount.setText(String.valueOf(sentSticker.getCount()));
    }

    @Override
    public int getItemCount() {
        return sentStickers.size();
    }

    public static class ReceivedStickerHolder extends RecyclerView.ViewHolder {
        private ImageView stickerImage;
        private TextView stickerCount;

        public ReceivedStickerHolder(@NonNull View itemView) {
            super(itemView);
            this.stickerImage = itemView.findViewById(R.id.sentStickerImage);
            this.stickerCount = itemView.findViewById(R.id.sentStickerCount);
        }
    }
}
