package edu.ihm.td1.livraison;

import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class DeliveryAdapter extends BaseAdapter {
    private final List<Order> list;
    private final Context context;
    private final LayoutInflater mInflater;

    private Location currentGPSPostion;

    public DeliveryAdapter(Context context, List<Order> list) {
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
        Order element = list.get(position);

        ImageButton validate = layoutItem.<ImageButton>findViewById(R.id.delivery_done);
        ImageButton cancel = layoutItem.findViewById(R.id.cancel_delivery);

        layoutItem.setAlpha(element.getDelivered() ? 0.5f : 1);
        cancel.setVisibility(element.getDelivered() ? View.VISIBLE : View.GONE);
        validate.setVisibility(element.getDelivered() ? View.GONE : View.VISIBLE);

        layoutItem.<TextView>findViewById(R.id.address).setText(element.getAddress());
        layoutItem.<TextView>findViewById(R.id.distance).setText(currentGPSPostion == null ? null : String.format(res.getString(R.string.next_delivery_distance), element.getDistance(currentGPSPostion.getLatitude(), currentGPSPostion.getLongitude())));

        layoutItem.<ImageButton>findViewById(R.id.delivery_issue).setOnClickListener(view -> {
            if (onDeliveryIssue != null) onDeliveryIssue.accept(element);
        });

        validate.setOnClickListener(view -> {
            if (onDeliveryDone != null) onDeliveryDone.accept(element);
            refreshList();
        });

        cancel.setOnClickListener(view -> {
            if (onDeliveryCancel != null) onDeliveryCancel.accept(element);
            refreshList();
        });

        return layoutItem;
    }

    private Consumer<Order> onDeliveryDone;
    private Consumer<Order> onDeliveryCancel;
    private Consumer<Order> onDeliveryIssue;

    public void setOnDeliveryDone(Consumer<Order> onDeliveryDone) {
        this.onDeliveryDone = onDeliveryDone;
    }

    public void setOnDeliveryCancel(Consumer<Order> onDeliveryCancel) {
        this.onDeliveryCancel = onDeliveryCancel;
    }

    public void setOnDeliveryIssue(Consumer<Order> onDeliveryIssue) {
        this.onDeliveryIssue = onDeliveryIssue;
    }

    public void onLocationChanged(@NonNull Location location) {
        this.currentGPSPostion = location;
        refreshList();
    }

    private void refreshList() {
        list.sort(Comparator
                .<Order>comparingInt(o -> o.getDelivered() ? 1 : 0)
                .thenComparingDouble(o -> o.getDistance(currentGPSPostion.getLatitude(), currentGPSPostion.getLongitude())));
        notifyDataSetChanged();
    }
}