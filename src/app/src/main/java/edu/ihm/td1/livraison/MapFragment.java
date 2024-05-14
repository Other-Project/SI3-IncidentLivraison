package edu.ihm.td1.livraison;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {
    private MapView map;
    private List<Order> collection = new ArrayList<>();
    private ListAdapter adapter;
    private ItemizedOverlayWithFocus<OverlayItem> mOverlay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ListAdapter(getContext(), collection);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        Configuration.getInstance().load( getContext(),
                PreferenceManager.getDefaultSharedPreferences(getContext()));
        map = rootView.findViewById(R.id.map);
        map.setTileSource( TileSourceFactory.MAPNIK ); //render
        map.setBuiltInZoomControls( true ); // TODO: delete this for the demo
        map.setMultiTouchControls(true); // zoomable with 2 fingers
        GeoPoint startPoint = collection.stream().findFirst().get().getPosition();
        IMapController mapController = map.getController();
        mapController.setZoom( 16.0 );
        mapController.setCenter(startPoint);

        mOverlay = createOverlay();

        map.getOverlays().add(mOverlay);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    public void notifyCollectionReady(){
        Bundle bundle = getArguments();
        if (bundle != null) {
            collection.addAll((ArrayList<Order>) bundle.get("list"));
            adapter.notifyDataSetChanged();
        }
    }

    public void updateOrders(Order order) {
        collection.remove(order);
        /* map.getOverlays().clear();
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = createOverlay();
        map.getOverlays().add(mOverlay); */
        mOverlay.removeItem(orderToOverlayItem(order));
    }

    private  ItemizedOverlayWithFocus<OverlayItem> createOverlay() {
        ArrayList<OverlayItem> items = new ArrayList<>();
        int i = 1;
        for (Order o : collection) {
            items.add(new OverlayItem("Livraison n°" + i, o.getAddress(), o.getPosition()));
            i++;
        }
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<>(getContext(),
                items, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return true;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        });
        mOverlay.setFocusItemsOnTap( true );
        return mOverlay;
    }

    private OverlayItem orderToOverlayItem(Order order) {
        return mOverlay.getDisplayedItems().stream().filter(oi -> oi.getSnippet().equals(order.getAddress()) && oi.getPoint().equals(oi.getPoint())).findFirst().get();
    }
}
