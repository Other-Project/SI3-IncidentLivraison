package edu.ihm.td1.livraison;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.Overlay;

import java.util.function.Consumer;

public class LocationUtility extends Overlay implements LocationListener, com.google.android.gms.location.LocationListener, SensorEventListener {

    private final String TAG = getClass().getSimpleName();
    private final static int ROTATION_ACCURACY = 30;
    private final static Paint PAINT_CIRCLE;
    private final static Paint PAINT_BORDER;
    private final static Paint PAINT_ACCURACY;

    static {
        PAINT_CIRCLE = new Paint();
        PAINT_CIRCLE.setARGB(255, 66, 134, 245);

        PAINT_BORDER = new Paint();
        PAINT_BORDER.setColor(Color.WHITE);

        PAINT_ACCURACY = new Paint(PAINT_CIRCLE);
        PAINT_ACCURACY.setAlpha(100);
    }

    private final MapView map;

    private Location currentLocation;
    private int currentRotation = 0;

    private boolean followPosition = false;

    private Consumer<Boolean> onFollowPositionChange;

    public LocationUtility(MapView map) {
        this.map = map;
    }

    public void setOnFollowPositionChange(Consumer<Boolean> onFollowPositionChange) {
        this.onFollowPositionChange = onFollowPositionChange;
    }

    public void setFollowPosition(boolean followPosition) {
        if (this.followPosition == followPosition) return;
        Log.d(TAG, "Follow position: " + followPosition);
        this.followPosition = followPosition;
        map.setFlingEnabled(!this.followPosition);
        if (onFollowPositionChange != null) onFollowPositionChange.accept(followPosition);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        currentLocation = location;
        if (followPosition) map.getController().setCenter(new GeoPoint(location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d(TAG, "status changed=" + s);
    }

    @Override
    public void onProviderEnabled(@NonNull String s) {
        Log.d(TAG, s + " sensor ON");
        setFollowPosition(followPosition);
    }

    @Override
    public void onProviderDisabled(@NonNull String s) {
        Log.d(TAG, s + " sensor OFF");
        setFollowPosition(false);
    }

    @Override
    public void draw(Canvas c, MapView osmv, boolean shadow) {
        if (!isEnabled() || shadow || currentLocation == null) return;

        c.save();

        final Projection pj = osmv.getProjection();

        Point pxPos = pj.toPixels(new GeoPoint(currentLocation.getLatitude(), currentLocation.getLongitude()), null);
        float radius = Math.max(pj.metersToPixels(1), 15);
        if (currentLocation.hasAccuracy())
            c.drawCircle(pxPos.x, pxPos.y, pj.metersToPixels(currentLocation.getAccuracy()), PAINT_ACCURACY);
        c.drawCircle(pxPos.x, pxPos.y, radius * 1.5f, PAINT_BORDER);
        c.drawCircle(pxPos.x, pxPos.y, radius, PAINT_CIRCLE);

        c.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event, MapView mapView) {
        boolean result = super.onTouchEvent(event, mapView);
        if (!followPosition) return result;
        setFollowPosition(false);
        return true;
    }

    private float[] mGravity;
    private float[] mGeomagnetic;

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) mGeomagnetic = event.values;

        if (!followPosition) currentRotation = 0;
        if (!followPosition || mGravity == null || mGeomagnetic == null) return;

        float[] R = new float[9];
        if (!SensorManager.getRotationMatrix(R, new float[9], mGravity, mGeomagnetic)) return;
        float[] orientation = SensorManager.getOrientation(R, new float[3]);

        double rotation = Math.toDegrees(-orientation[0]) % 360;
        if (rotation < 0) rotation += 360;
        if (rotation > currentRotation + ROTATION_ACCURACY || rotation < currentRotation - ROTATION_ACCURACY) currentRotation = (int) rotation;
        map.setMapOrientation(currentRotation);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
