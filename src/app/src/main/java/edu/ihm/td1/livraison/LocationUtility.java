package edu.ihm.td1.livraison;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
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

public class LocationUtility extends Overlay implements LocationListener {

    private final static Paint circlePaint;
    private final static Paint borderPaint;
    private final static Paint accuracyPaint;

    static {
        circlePaint = new Paint();
        circlePaint.setARGB(255, 66, 134, 245);

        borderPaint = new Paint();
        borderPaint.setColor(Color.WHITE);

        accuracyPaint = new Paint(circlePaint);
        accuracyPaint.setAlpha(100);
    }

    private final String TAG = getClass().getSimpleName();

    private final MapView map;

    private Location currentLocation;

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
        if (!followPosition) return;
        map.getController().setCenter(new GeoPoint(location.getLatitude(), location.getLongitude()));
        map.setMapOrientation(location.hasBearing() ? location.getBearing() : 0);
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
            c.drawCircle(pxPos.x, pxPos.y, pj.metersToPixels(currentLocation.getAccuracy()), accuracyPaint);
        c.drawCircle(pxPos.x, pxPos.y, radius * 1.5f, borderPaint);
        c.drawCircle(pxPos.x, pxPos.y, radius, circlePaint);

        c.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event, MapView mapView) {
        boolean result = super.onTouchEvent(event, mapView);
        if (!followPosition) return result;
        setFollowPosition(false);
        return true;
    }
}
