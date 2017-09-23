package de.hvv.hackathon.citytour;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hvv.hackathon.citytour.Model.POI;

/**
 * Created by irti1de on 22.09.2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    OnClickListerner mainActivity;

    private Context context;
    public interface OnClickListerner{
        public void click(POI path);

    }

    public EventsAdapter(OnClickListerner mainActivity){
        this.mainActivity = mainActivity;
    }

    public ArrayList<POI> pois = new ArrayList<>();

    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.events_adapter_view,parent,false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventsViewHolder holder, int position) {

        POI poi = pois.get(position);

        holder.titleTextView.setText(poi.title);
        holder.subTitleTextView.setText(poi.tag);

        int viewTrainVisibility = poi.train ? View.VISIBLE : View.INVISIBLE;
        int viewBikeVisibility = poi.bike ? View.VISIBLE : View.INVISIBLE;

        holder.bahnImageView.setVisibility(viewTrainVisibility);
        holder.bikeImageView.setVisibility(viewBikeVisibility);

        Glide.with(context).load(poi.imageUrl).into(holder.coverImageView);
        holder.itemView.setTag(poi);

        holder.dauerTextView.setText((poi.timereq / 60) + "Minuten");
        holder.checkedImageView.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getItemCount() {
        return pois.size();
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTextView;
        private final TextView subTitleTextView;
        private final TextView dauerTextView;

        private final ImageView coverImageView;
        private final ImageView checkedImageView;

        private final ImageView bahnImageView;
        private final ImageView bikeImageView;


        public EventsViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.event_title_name_textview);
            subTitleTextView = itemView.findViewById(R.id.event_subtitle_textview);
            coverImageView = itemView.findViewById(R.id.event_image);
            bahnImageView = itemView.findViewById(R.id.imageViewBahn);
            bikeImageView = itemView.findViewById(R.id.imageViewRad);
            dauerTextView = itemView.findViewById(R.id.textViewDauer);

            checkedImageView = itemView.findViewById(R.id.imageViewChecked);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.click((POI) view.getTag());
                }
            });
        }
    }

    public void swapList( ArrayList<POI> newList){
        pois.clear();
        pois.addAll(newList);
        notifyDataSetChanged();
    }
}
