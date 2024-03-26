package edu.ihm.td1.livraison;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DeliveryAdapter extends BaseAdapter {
    private final List<Delivery> list;
    private final Context context;
    private final LayoutInflater mInflater;

    public DeliveryAdapter(Context context, List<Delivery> list) {
        this.context = context;
        this.list = list;
        mInflater = LayoutInflater.from(this.context);
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View layoutItem = (convertView == null ? mInflater.inflate(R.layout.adapter_next_delivery, parent, false) : convertView);
        Resources res = context.getResources();
        Delivery element = list.get(position);

        layoutItem.<TextView>findViewById(R.id.address).setText(element.getAddress());
        String s = res.getString(R.string.next_delivery_distance);
        layoutItem.<TextView>findViewById(R.id.distance).setText(String.format(s, element.getDistance(0, 0)));

        return layoutItem;
    }
}