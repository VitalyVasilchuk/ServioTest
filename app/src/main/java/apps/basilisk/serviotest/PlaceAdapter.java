package apps.basilisk.serviotest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import apps.basilisk.serviotest.model.Place;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {
    private ArrayList<HashMap<String, Object>> placeList;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewCode;
        public TextView textViewPrefix;

        public PlaceViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_name);
            textViewCode = itemView.findViewById(R.id.text_code);
            textViewPrefix = itemView.findViewById(R.id.text_prefix);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public PlaceAdapter(ArrayList<HashMap<String, Object>> placeList) {
        this.placeList = placeList;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_place, parent, false);
        return new PlaceViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        HashMap<String, Object> placeItem = placeList.get(position);
        Place place = (Place) placeItem.get("placeObject");

        holder.textViewPrefix.setText(placeItem.get("prefix").toString());
        holder.textViewName.setText(place.getName());
        holder.textViewCode.setText(place.getCode());

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }
}
