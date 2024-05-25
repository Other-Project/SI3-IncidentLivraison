package edu.ihm.td1.livraison;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
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

public class MapFragment extends Fragment {


    private final String TAG = getClass().getSimpleName();

    private MapView map;
    private final List<Order> collection = new ArrayList<>();
    private ItemizedOverlayWithFocus<OverlayItem> mOverlay;
    private ImageButton centerOnPos;

    private LocationUtility locationUtility;

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
        locationUtility = new LocationUtility(map);
        map.setTileSource(TileSourceFactory.MAPNIK); //render
        map.setBuiltInZoomControls(Build.PRODUCT.contains("sdk"));
        map.setMultiTouchControls(true); // zoomable with 2 fingers
        IMapController mapController = map.getController();
        mapController.setZoom(15.0);

        Optional<Order> firstPoint = collection.stream().findFirst();
        mapController.setCenter(firstPoint.isPresent()
                ? firstPoint.get().getPosition()
                : new GeoPoint(43.61, 7.07));

        map.getOverlays().add(locationUtility);
        locationUtility.setOnFollowPositionChange(followPosition -> centerOnPos.setVisibility(followPosition ? View.INVISIBLE : View.VISIBLE));

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
            LocationServices.getFusedLocationProviderClient(requireContext()).requestLocationUpdates(locationRequest,
                    locationUtility,
                    Looper.getMainLooper());
        } else {
            LocationManager locationManager = (LocationManager) (requireContext().getSystemService(LOCATION_SERVICE));
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, locationUtility);
        }

        SensorManager mSensorManager = (SensorManager) requireContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor mSensorMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        mSensorManager.registerListener(locationUtility, mSensorAccelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(locationUtility, mSensorMagneticField, SensorManager.SENSOR_DELAY_UI);
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
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<>(requireContext(),
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
