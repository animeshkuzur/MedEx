package com.hashinclude.medex.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hashinclude.medex.R;
import com.hashinclude.medex.models.Cards;

import java.util.List;

/**
 * Created by pankaj on 11/2/17.
 */

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Cards> cardsList;
    private String parent;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, content;
        ImageView thumbnail;

        MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            content = (TextView) view.findViewById(R.id.content);
            thumbnail = (ImageView) view.findViewById(R.id.product_image);
        }
    }


    public CardsAdapter(Context mContext, List<Cards> cardsList, String parent) {
        this.mContext = mContext;
        this.cardsList = cardsList;
        this.parent = parent;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Cards card = cardsList.get(position);
        holder.title.setText(card.getTitle());
        holder.content.setText(card.getContent());


        // loading album cover using Glide library
        Glide.with(mContext).load(card.getThumbnail()).into(holder.thumbnail);
    }

//    /**
//     * Showing popup menu when tapping on 3 dots
//     */
//    private void showPopupMenu(View view) {
//        // inflate menu
//        PopupMenu popup = new PopupMenu(mContext, view);
//        MenuInflater inflater = popup.getMenuInflater();
//        if (parent.contentEquals("Pickup")){
//            inflater.inflate(R.menu.menu_pickup, popup.getMenu());
//        } else if (parent.contentEquals("MyOrders"))
//            inflater.inflate(R.menu.menu_orders, popup.getMenu());
//        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
//        popup.show();
//    }

    /**
     * Click listener for popup menu items
     */
//    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
//
//        MyMenuItemClickListener() {
//        }
//
//        @Override
//        public boolean onMenuItemClick(MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.action_add_favourite:
//                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.action_play_next:
//                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.action_schedule_pickup:
//                    Toast.makeText(mContext, "Schedule Pickup", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.action_cancel:
//                    Toast.makeText(mContext, "Cancel", Toast.LENGTH_SHORT).show();
//                    return true;
//                default:
//            }
//            return false;
//        }
//    }

    @Override
    public int getItemCount() {
        return cardsList.size();
    }
}

