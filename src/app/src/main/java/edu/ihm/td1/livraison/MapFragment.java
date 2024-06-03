package edu.ihm.td1.livraison;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

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
import java.util.Optional;
import java.util.function.Consumer;

public class MapFragment extends Fragment {


    private final String TAG = getClass().getSimpleName();

    private MapView map;
    private final List<Order> ordersToDeliver = new ArrayList<>();
    private List<Order> ordersDelivered = new ArrayList<>();

    private ItemizedOverlayWithFocus<OverlayItem> mOverlay;
    private final List<OverlayItem> tempOverlay = new ArrayList<>();
    private ImageButton centerOnPos;

    private LocationUtility locationUtility;
    private Consumer<Location> onLocationChanged;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context ctx = requireContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        map = rootView.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK); //render
        map.setBuiltInZoomControls(Build.PRODUCT.contains("sdk"));
        map.setMultiTouchControls(true); // zoomable with 2 fingers
        IMapController mapController = map.getController();
        mapController.setZoom(15.0);

        Optional<Order> firstPoint = ordersToDeliver.stream().findFirst();
        mapController.setCenter(firstPoint.isPresent()
                ? firstPoint.get().getPosition()
                : new GeoPoint(43.61, 7.07));

        locationUtility = new LocationUtility(map);
        locationUtility.setOnFollowPositionChange(followPosition -> centerOnPos.setVisibility(followPosition ? View.INVISIBLE : View.VISIBLE));
        locationUtility.setOnLocationChanged(onLocationChanged);
        map.getOverlays().add(locationUtility);

        centerOnPos = rootView.findViewById(R.id.centerPos);
        centerOnPos.setOnClickListener(view -> locationUtility.setFollowPosition(true));

        mOverlay = createOverlay();

        map.getOverlays().add(mOverlay);

        initGpsListener();

        return rootView;
    }

    private void initGpsListener() {
        boolean permissionGranted = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        Log.d(TAG, "GPS permissionGranted = " + permissionGranted);
        if (!permissionGranted) {
            locationUtility.setFollowPosition(false);
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                    .setWaitForAccurateLocation(false)
                    .setMinUpdateIntervalMillis(500)
                    .setMaxUpdateDelayMillis(1000)
                    .build();
            LocationServices.getFusedLocationProviderClient(requireContext()).requestLocationUpdates(locationRequest, locationUtility, Looper.getMainLooper());
        } else {
            LocationManager locationManager = (LocationManager) (requireContext().getSystemService(LOCATION_SERVICE));
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, locationUtility);
        }

        SensorManager mSensorManager = (SensorManager) requireContext().getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(locationUtility, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(locationUtility, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_UI);
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
        if (list != null) ordersToDeliver.addAll(list);
    }

    public void updateOrders(Order order) {
        if (order.getDelivered()) {
            ordersDelivered.remove(order);
            Optional<OverlayItem> item = orderToOverlayItem(order);
            if (item.isPresent()) {
                tempOverlay.add(item.get());
                mOverlay.removeItem(item.get());
            }
        } else {
            Optional<OverlayItem> itemToAdd = tempOverlay.stream().filter(
                    oi -> oi.getSnippet().equals(order.getAddress()) && oi.getPoint().equals(oi.getPoint())).findFirst();
            itemToAdd.ifPresent(overlayItem -> mOverlay.addItem(overlayItem));
            itemToAdd.ifPresent(tempOverlay::remove);
        }

        map.invalidate();
    }

    private ItemizedOverlayWithFocus<OverlayItem> createOverlay() {
        ArrayList<OverlayItem> items = new ArrayList<>();
        int i = 1;
        for (Order o : ordersToDeliver) {
            OverlayItem item = new OverlayItem("Livraison n°" + i, o.getAddress(), o.getPosition());
            if (o.getDelivered()) tempOverlay.add(item);
            else items.add(item);
            i++;
        }

        Drawable icon = ResourcesCompat.getDrawable(getResources(), R.drawable.mapmarker, null);

        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<>(
                items, icon, null, ItemizedOverlayWithFocus.NOT_SET, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return false;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        }, getContext());
        mOverlay.setFocusItemsOnTap(true);
        return mOverlay;
    }

    private Optional<OverlayItem> orderToOverlayItem(Order order) {
        return mOverlay.getDisplayedItems().stream().filter(oi -> oi.getSnippet().equals(order.getAddress()) && oi.getPoint().equals(oi.getPoint())).findFirst();
    }

    public void setOnLocationChanged(Consumer<Location> onLocationChanged) {
        this.onLocationChanged = onLocationChanged;
    }
}
