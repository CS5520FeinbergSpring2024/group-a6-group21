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
        int sentStickerID = sentSticker.getStickerID();
        if(sentStickerID == 1) {
            holder.stickerImage.setImageResource(R.drawable.mexico);
        } else if(sentStickerID == 2) {
            holder.stickerImage.setImageResource(R.drawable.sahara);
        } else if(sentStickerID == 3) {
            holder.stickerImage.setImageResource(R.drawable.sydney);
        } else if(sentStickerID == 4) {
            holder.stickerImage.setImageResource(R.drawable.toronto);
        } else if(sentStickerID == 5) {
            holder.stickerImage.setImageResource(R.drawable.turkey);
        } else if(sentStickerID == 6) {
            holder.stickerImage.setImageResource(R.drawable.washington);
        }
        holder.stickerCount.setText(String.valueOf(sentSticker.getStickerCount()));
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
