package edu.ihm.td1.livraison;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
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
    private final static Paint PAINT_ROTATION;

    static {
        PAINT_CIRCLE = new Paint();
        PAINT_CIRCLE.setARGB(255, 66, 134, 245);

        PAINT_BORDER = new Paint();
        PAINT_BORDER.setColor(Color.WHITE);

        PAINT_ACCURACY = new Paint(PAINT_CIRCLE);
        PAINT_ACCURACY.setAlpha(100);

        PAINT_ROTATION = new Paint(PAINT_CIRCLE);
        PAINT_ROTATION.setAlpha(200);
    }

    private final MapView map;

    private Location currentLocation;
    private Integer currentRotation;

    private boolean followPosition = false;

    private Consumer<Boolean> onFollowPositionChange;
    private Consumer<Location> onLocationChanged;

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
        if (followPosition) zoomIn = true;
        if (onFollowPositionChange != null) onFollowPositionChange.accept(followPosition);
    }


    boolean zoomIn = false;

    @Override
    public void onLocationChanged(@NonNull Location location) {
        currentLocation = location;
        if (onLocationChanged != null) onLocationChanged.accept(location);
        if (followPosition)
            map.getController().animateTo(new GeoPoint(location.getLatitude(), location.getLongitude()), zoomIn ? 19.0 : null, 250L);
        zoomIn = false;
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
        float rotationRadius = radius * 5;
        if (currentRotation != null)
            c.drawArc(
                    new RectF(pxPos.x - rotationRadius, pxPos.y - rotationRadius, pxPos.x + rotationRadius, pxPos.y + rotationRadius),
                    currentRotation - 90 - ROTATION_ACCURACY,
                    ROTATION_ACCURACY * 2,
                    true,
                    PAINT_ROTATION
            );

        if (currentLocation.hasAccuracy()) c.drawCircle(pxPos.x, pxPos.y, pj.metersToPixels(currentLocation.getAccuracy()), PAINT_ACCURACY);

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
        if (!followPosition) return;

        if (mGravity == null || mGeomagnetic == null) {
            currentRotation = null;
            return;
        }

        float[] R = new float[9];
        if (!SensorManager.getRotationMatrix(R, new float[9], mGravity, mGeomagnetic)) return;
        float[] orientation = SensorManager.getOrientation(R, new float[3]);

        int rotation = Math.round(((float) Math.toDegrees(orientation[0]) + 360) % 360);
        if (currentRotation == null || Math.abs(rotation - currentRotation) > ROTATION_ACCURACY / 6)
            currentRotation = rotation;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, sensor.getStringType() + " accuracy changed " + accuracy);
    }

    public void setOnLocationChanged(Consumer<Location> onLocationChanged) {
        this.onLocationChanged = onLocationChanged;
    }
}
