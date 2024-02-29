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

public class ReceivedStickersAdapter extends RecyclerView.Adapter<ReceivedStickersAdapter.ReceivedStickerHolder>{

    private final List<ReceivedSticker> receivedStickers;
    private final Context context;

    public ReceivedStickersAdapter(List<ReceivedSticker> receivedStickers, Context context) {
        this.receivedStickers = receivedStickers;
        this.context = context;
    }

    @NonNull
    @Override
    public ReceivedStickerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_received_sticker, parent, false);
        return new ReceivedStickersAdapter.ReceivedStickerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceivedStickerHolder holder, int position) {
        ReceivedSticker receivedSticker = receivedStickers.get(position);
        int receivedStickerID = receivedSticker.getStickerID();
        if(receivedStickerID == 1) {
            holder.stickerImage.setImageResource(R.drawable.mexico);
        } else if(receivedStickerID == 2) {
            holder.stickerImage.setImageResource(R.drawable.sahara);
        } else if(receivedStickerID == 3) {
            holder.stickerImage.setImageResource(R.drawable.sydney);
        } else if(receivedStickerID == 4) {
            holder.stickerImage.setImageResource(R.drawable.toronto);
        } else if(receivedStickerID == 5) {
            holder.stickerImage.setImageResource(R.drawable.turkey);
        } else if(receivedStickerID == 6) {
            holder.stickerImage.setImageResource(R.drawable.washington);
        }
        holder.stickerFrom.setText(String.valueOf(receivedSticker.getReceivedFrom()));
        holder.stickerReceivedOn.setText(String.valueOf(receivedSticker.getReceivedDate()));
    }

    @Override
    public int getItemCount() {
        return receivedStickers.size();
    }

    public static class ReceivedStickerHolder extends RecyclerView.ViewHolder {
        private ImageView stickerImage;
        private TextView stickerFrom;
        private TextView stickerReceivedOn;


        public ReceivedStickerHolder(@NonNull View itemView) {
            super(itemView);
            this.stickerImage = itemView.findViewById(R.id.receivedStickerImage);
            this.stickerFrom = itemView.findViewById(R.id.receivedFrom);
            this.stickerReceivedOn = itemView.findViewById(R.id.receivedOn);
        }
    }
}
