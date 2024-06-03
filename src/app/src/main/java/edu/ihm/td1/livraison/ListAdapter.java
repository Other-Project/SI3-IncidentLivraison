package edu.ihm.td1.livraison;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    private final Context context;
    private final List<? extends Parcelable> list;
    private final LayoutInflater inflater;

    public ListAdapter(Context ctx, List<? extends Parcelable> list) {
        context = ctx;
        this.list = list;
        inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.command_list_item, null);
        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        ImageView commandImg = (ImageView)convertView.findViewById(R.id.image);
        ImageView deliveredImg = (ImageView)convertView.findViewById(R.id.logo);
        if(list.get(0) instanceof Order){
            textView.setText(((Order) list.get(position)).getDescription());
            commandImg.setImageResource(((Order) list.get(position)).getImage());
            deliveredImg.setImageResource(((Order) list.get(position)).getDelivered() ? R.drawable.check : R.drawable.sablier);
        }else{
            textView.setText(((Report) list.get(position)).getDescriptionProbleme());
            commandImg.setImageResource(((Report) list.get(position)).getOrder().getImage());
            deliveredImg.setImageResource(((Report) list.get(position)).isTreated() ? R.drawable.check : R.drawable.arrow);
        }

        ImageView image = convertView.findViewById(R.id.image);
        image.setOnClickListener(v -> {
            if (context instanceof OrderListActivity) {
                ((OrderListActivity)context).startActivity(new Intent(context, ReportCommandActivity.class));

            }
        });
        return convertView;

    }
}
