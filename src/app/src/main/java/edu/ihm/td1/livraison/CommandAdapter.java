package edu.ihm.td1.livraison;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CommandAdapter extends BaseAdapter {

    private Context context;
    private List<Command> commandes;
    private LayoutInflater inflater;
    public CommandAdapter(Context ctx, List<Command> list){
        context = ctx;
        commandes = list;
        inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return commandes.size();
    }

    @Override
    public Object getItem(int position) {
        return commandes.get(position);
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
        textView.setText(commandes.get(position).getDescription());
        commandImg.setImageResource(commandes.get(position).getImage());
        deliveredImg.setImageResource(commandes.get(position).getDelivered() ? R.drawable.check : R.drawable.sablier);
        return convertView;

    }
}
