package edu.ihm.td1.livraison;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MapFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();

    private MapView map;
    private final List<Order> collection = new ArrayList<>();
    private ItemizedOverlayWithFocus<OverlayItem> mOverlay;
    private Button centerOnPos;

    private boolean followPosition = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        Configuration.getInstance().load(getContext(),
                PreferenceManager.getDefaultSharedPreferences(getContext()));
        map = rootView.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK); //render
        map.setBuiltInZoomControls(true); // TODO: delete this for the demo
        map.setMultiTouchControls(true); // zoomable with 2 fingers
        IMapController mapController = map.getController();
        mapController.setZoom(15.0);

        Optional<Order> firstPoint = collection.stream().findFirst();
        mapController.setCenter(firstPoint.isPresent()
                ? firstPoint.get().getPosition()
                : new GeoPoint(43.61, 7.07));

        MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getContext()), map);
        mLocationOverlay.enableMyLocation();
        map.getOverlays().add(mLocationOverlay);
        map.setOnTouchListener((v, event) -> {
            if (!followPosition) return false;
            setFollowPosition(false);
            return true;
        });

        centerOnPos = rootView.findViewById(R.id.centerPos);
        centerOnPos.setOnClickListener(view -> setFollowPosition(!followPosition));

        mOverlay = createOverlay();
        map.getOverlays().add(mOverlay);
        return rootView;
    }

    private void initGpsListener() {
        assert getContext() != null;

        boolean permissionGranted = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        Log.d(TAG, "GPS permissionGranted = " + permissionGranted);
        if (!permissionGranted) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
            return;
        }

        LocationManager locationManager = (LocationManager) (getContext().getSystemService(LOCATION_SERVICE));
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                if (!followPosition) {
                    locationManager.removeUpdates(this);
                    return;
                }
                GeoPoint point = new GeoPoint(location.getLatitude(), location.getLongitude());
                map.getController().animateTo(point);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.d(TAG, "status changed=" + s);
            }

            @Override
            public void onProviderEnabled(@NonNull String s) {
                Log.d(TAG, s + " sensor ON");
            }

            @Override
            public void onProviderDisabled(@NonNull String s) {
                Log.d(TAG, s + " sensor OFF");
                setFollowPosition(false);
            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, listener);
    }

    private void setFollowPosition(boolean followPosition) {
        if (this.followPosition == followPosition) return;
        Log.d(TAG, "Follow position: " + followPosition);
        this.followPosition = followPosition;
        centerOnPos.setVisibility(followPosition ? View.INVISIBLE : View.VISIBLE);
        if (followPosition) initGpsListener();
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

    public void notifyCollectionReady() {
        Bundle bundle = getArguments();
        if (bundle == null) return;
        ArrayList<Order> list = bundle.getParcelableArrayList("list");
        if (list != null) collection.addAll(list);
    }

    public void updateOrders(Order order) {
        collection.remove(order);
        orderToOverlayItem(order).ifPresent(mOverlay::removeItem);
    }

    private ItemizedOverlayWithFocus<OverlayItem> createOverlay() {
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
        mOverlay.setFocusItemsOnTap(true);
        return mOverlay;
    }

    private Optional<OverlayItem> orderToOverlayItem(Order order) {
        return mOverlay.getDisplayedItems().stream().filter(oi -> oi.getSnippet().equals(order.getAddress()) && oi.getPoint().equals(oi.getPoint())).findFirst();
    }
}
