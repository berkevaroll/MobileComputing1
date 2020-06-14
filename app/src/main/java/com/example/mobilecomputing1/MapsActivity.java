package com.example.mobilecomputing1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 15f;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40,-168), new LatLng(71, 136));

    private List<MyMarker> markerList;
    private EditText mSearchText;
    //private GoogleApiClient mGoogleApiClient;
    private ImageView mGps;
    private CustomInfoWindowAdapter mCustomInfoWindowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mSearchText = (EditText) findViewById(R.id.input_search);
        mGps = (ImageView) findViewById(R.id.ic_gps);
        markerList = new ArrayList<>();
        mCustomInfoWindowAdapter = new CustomInfoWindowAdapter(this);
        getLocationPermission();
        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }


    }


    private void init() {


        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER) {
                    //execute method for search
                    Toast.makeText(MapsActivity.this, "Locating searched Address", Toast.LENGTH_SHORT).show();
                    geoLocate();
                }
                Toast.makeText(MapsActivity.this, "Cant listen editor", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
            }
        });
        hideSoftKeyboard();
    }
    private void geoLocate(){
        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString,1);
        }catch(IOException e){

        }
        if(list.size()>0){
            Address address = list.get(0);

            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()),DEFAULT_ZOOM, address.getAddressLine(0));
        }

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        if(mLocationPermissionsGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;

            }
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Toast.makeText(MapsActivity.this, "Clicked"+marker.getTitle(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(MapsActivity.this, "Longitude = "+marker.getPosition().longitude+"  Latitude = " + marker.getPosition().latitude, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MapsActivity.this, EditMarker.class);
                    intent.putExtra("lat",marker.getPosition().latitude);
                    intent.putExtra("lng",marker.getPosition().longitude);
                    intent.putExtra("title_",marker.getTitle());

                    startActivity(intent);
                    finish();
                }
            });
            mMap.setOnMapClickListener(this);
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.clear();
            GetMarkers();
            if(markerList.isEmpty()) Toast.makeText(this, "Dizi Bos!!!!", Toast.LENGTH_SHORT).show();
            init();


        }

    }
    @Override
    public void onMapClick(LatLng latLng) {
        MarkerOptions mOptions = new MarkerOptions().position(latLng).title("Custom marker");
        //Add marker and get details
        mMap.addMarker(mOptions);

    }
    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }
    private void getLocationPermission(){

        String[] permissions = {FINE_LOCATION,COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted=false;
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i =0; i<grantResults.length;i++){
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted =false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted =true;
                    //initialize map
                    initMap();
                }
            }
        }
    }

    private void getDeviceLocation(){

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Location currentLocation = (Location) task.getResult();
                        moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),
                        DEFAULT_ZOOM,"My Location");
                    }
                }
            });

            }

        catch (SecurityException e){

        }
    }
    private void GetMarkers(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_LIST_MARKERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("error"))
                            {
                                JSONArray markers = jsonObject.getJSONArray("markers");
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                                for (int i = 0; i < markers.length(); i++) {

                                    JSONObject obj = markers.getJSONObject(i);
                                    LatLng latLng = new LatLng(Double.valueOf(obj.getString("lat")),Double.valueOf(obj.getString("lng")));
                                    MarkerOptions options  = new MarkerOptions().position(latLng).title(obj.getString("title"));
                                    mMap.addMarker(options);
//                                    markerList.add(new MyMarker(
//                                            obj.getInt("id"),
//                                            obj.getString("lng"),
//                                            obj.getString("lat"),
//                                            obj.getString("title"),
//                                            obj.getString("description"),
//                                            obj.getString("img_url"),
//                                            obj.getInt("user_id")
//                                    ));
                                }

                            }
                            else{
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
    private void moveCamera(LatLng latLng, float zoom, String title){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
        mMap.setInfoWindowAdapter(mCustomInfoWindowAdapter);

        if(!title.equals("My Location")){
            MarkerOptions options  = new MarkerOptions().position(latLng).title(title);
            mMap.addMarker(options);
        }
        hideSoftKeyboard();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logoutmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).Logout();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
        }
        return true;
    }
    private void hideSoftKeyboard() {
        View view = this.getCurrentFocus();
        if(view!= null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }



}
