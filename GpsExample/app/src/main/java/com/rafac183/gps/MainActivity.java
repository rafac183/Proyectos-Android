package com.rafac183.gps;

import static com.mapbox.maps.plugin.gestures.GesturesUtils.getGestures;
import static com.mapbox.maps.plugin.locationcomponent.LocationComponentUtils.getLocationComponent;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.android.gestures.MoveGestureDetector;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor;
import com.mapbox.maps.plugin.LocationPuck2D;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.gestures.OnMoveListener;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener;

public class MainActivity extends AppCompatActivity {

    private MapView mapView;
    private FloatingActionButton floatingActionButton;
    private Point point;
    private DatabaseReference reference = null;
    private ShareLocation location;
    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean o) {
            if (o) {
                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
            }
        }
    });
    private final OnIndicatorBearingChangedListener onIndicatorBearingChangedListener = new OnIndicatorBearingChangedListener() {
        @Override
        public void onIndicatorBearingChanged(double v) {
            mapView.getMapboxMap().setCamera(new CameraOptions.Builder().bearing(v).build());
        }
    };
    private final OnIndicatorPositionChangedListener onIndicatorPositionChangedListener = new OnIndicatorPositionChangedListener() {
        @Override
        public void onIndicatorPositionChanged(@NonNull Point point) {
            mapView.getMapboxMap().setCamera(new CameraOptions.Builder().center(point).zoom(17.0).build());
            getGestures(mapView).setFocalPoint(mapView.getMapboxMap().pixelForCoordinate(point));
            MainActivity.this.point = point;
        }
    };
    private OnMoveListener onMoveListener = new OnMoveListener() {
        @Override
        public void onMoveBegin(@NonNull MoveGestureDetector moveGestureDetector) {
            getLocationComponent(mapView).removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
            getLocationComponent(mapView).removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
            getGestures(mapView).removeOnMoveListener(onMoveListener);
            floatingActionButton.show();
        }
        @Override
        public boolean onMove(@NonNull MoveGestureDetector moveGestureDetector) {
            return false;
        }
        @Override
        public void onMoveEnd(@NonNull MoveGestureDetector moveGestureDetector) {}
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        mapView = findViewById(R.id.mapView);
        floatingActionButton = findViewById(R.id.myLocation);
        MaterialButton shareLocation = findViewById(R.id.shareLocation);

        location = new ShareLocation();

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            activityResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        floatingActionButton.hide();
        mapView.getMapboxMap().loadStyleUri(Style.SATELLITE_STREETS, style -> {
            mapView.getMapboxMap().setCamera(new CameraOptions.Builder().zoom(17.0).build());
            LocationComponentPlugin locationComponentPlugin = getLocationComponent(mapView);
            locationComponentPlugin.setEnabled(true);
            LocationPuck2D locationPuck2D = new LocationPuck2D();
            locationPuck2D.setBearingImage(AppCompatResources.getDrawable(MainActivity.this, R.drawable.my_location_icon));
            locationComponentPlugin.setLocationPuck(locationPuck2D);
            locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
            locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
            getGestures(mapView).addOnMoveListener(onMoveListener);

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.location);
            AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
            PointAnnotationManager pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationPlugin, new AnnotationConfig());

            floatingActionButton.setOnClickListener(v -> {
                locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
                locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
                getGestures(mapView).addOnMoveListener(onMoveListener);
                floatingActionButton.hide();
            });

            FirebaseDatabase.getInstance().getReference().child("sharedLocations").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    pointAnnotationManager.deleteAll();
                    snapshot.getChildren().forEach(dataSnapshot -> {
                        ShareLocation location1 = dataSnapshot.getValue(ShareLocation.class);
                        if (location1 != null && !location1.getId().equals(MainActivity.this.location.getId())){
                            PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                                    .withTextAnchor(TextAnchor.CENTER)
                                    .withIconImage(bitmap)
                                    .withPoint(Point.fromLngLat(location1.getLongitude(), location1.getLatitude()));
                            pointAnnotationManager.create(pointAnnotationOptions);
                        }
                    });
                    pointAnnotationManager.addClickListener(pointAnnotation -> {
                        snapshot.getChildren().forEach(dataSnapshot -> {
                            ShareLocation location1 = dataSnapshot.getValue(ShareLocation.class);
                            if (location1 != null && pointAnnotation.getPoint().longitude() == location1.longitude && pointAnnotation.getPoint().latitude() == location1.latitude){
                                Toast.makeText(MainActivity.this, "Clicked" + location1.getId() + " Marker", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return true;
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            shareLocation.setOnClickListener(v -> {
                //Toast.makeText(MainActivity.this, "HOLA", Toast.LENGTH_SHORT).show();
                if (reference == null){
                    Toast.makeText(MainActivity.this, "Sharing location...", Toast.LENGTH_SHORT).show();
                    reference = FirebaseDatabase.getInstance().getReference().child("sharedLocations").push();
                    location = new ShareLocation();
                    location.setId(reference.getKey());
                    location.setName("User Name");
                    location.setLongitude(point.longitude());
                    location.setLatitude(point.latitude());
                    reference.setValue(location);
                    shareLocation.setText("Stop Sharing");
                } else {
                    Toast.makeText(MainActivity.this, "Stopped sharing location", Toast.LENGTH_SHORT).show();
                    reference.removeValue();
                    reference = null;
                    shareLocation.setText("Share Location");
                }
            });
        });
    }
}