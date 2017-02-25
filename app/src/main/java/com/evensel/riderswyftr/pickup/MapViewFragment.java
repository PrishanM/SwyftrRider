package com.evensel.riderswyftr.pickup;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.evensel.riderswyftr.R;
import com.evensel.riderswyftr.util.AppController;
import com.evensel.riderswyftr.util.AppURL;
import com.evensel.riderswyftr.util.Constants;
import com.evensel.riderswyftr.util.Data;
import com.evensel.riderswyftr.util.DetectApplicationFunctionsAvailability;
import com.evensel.riderswyftr.util.JsonRequestManager;
import com.evensel.riderswyftr.util.Notifications;
import com.evensel.riderswyftr.util.ResponseModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Prishan Maduka on 2/12/2017.
 */
public class MapViewFragment extends Fragment implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private Context context;
    private ProgressDialog progressDialog;
    private SupportMapFragment fragment;
    private FragmentManager fm;
    private GoogleMap map;
    private View layout;
    private LayoutInflater inflate;
    private final Handler handler = new Handler();
    private Runnable runnable;

    private String token;
    private double currentLatitude=0.0,currentLongitude=0.0;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 140;
    private HashMap<Marker,Data> markerMap = new HashMap<>();

    public MapViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mapview, container, false);
        context = getActivity();

        inflate = inflater;
        layout = inflate.inflate(R.layout.custom_toast_layout, (ViewGroup) rootView.findViewById(R.id.toast_layout_root));
        token = context.getSharedPreferences(Constants.LOGIN_SHARED_PREF, Context.MODE_PRIVATE).getString(Constants.LOGIN_ACCESS_TOKEN, "");

        setHasOptionsMenu(true);
        DetectApplicationFunctionsAvailability.setmContext(context);
        if(DetectApplicationFunctionsAvailability.isConnected()){
            if(!DetectApplicationFunctionsAvailability.isLocationEnabled(context)){
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = ProgressDialog.show(getActivity(), null,
                                "Loading...", true);
                        JsonRequestManager.getInstance(getActivity()).getPickUpLocationRequest(AppURL.APPLICATION_BASE_URL + AppURL.GET_PICKUP__URL,1, token, requestCallback);
                    }
                };

                handler.postDelayed(runnable, 2000);
                //JsonRequestManager.getInstance(getActivity()).getPickUpLocationRequest(AppURL.APPLICATION_BASE_URL + AppURL.GET_PICKUP__URL,1, token, requestCallback);

            }else{
                Notifications.showGPSDisabledNotification(context).show();
            }

        }else{
            Notifications.showGeneralDialog(context,getString(R.string.no_network_error)).show();
        }
        return rootView;
    }

    //Response callback for "Pick Up Location"
    private final JsonRequestManager.getPickUpLocation requestCallback = new JsonRequestManager.getPickUpLocation() {

        @Override
        public void onSuccess(ResponseModel model) {

            if(progressDialog!=null)
                progressDialog.dismiss();

            if(model.getStatus().equalsIgnoreCase("success")){
                addPickUpMarkers(model.getData());
                progressDialog = ProgressDialog.show(getActivity(), null,
                        "Loading...", true);
                JsonRequestManager.getInstance(getActivity()).getPickUpLocationRequest(AppURL.APPLICATION_BASE_URL + AppURL.GET_DROP_OFF__URL,1, token, dropOffCallback);
            }else{
                Notifications.showToastMessage(layout,getActivity(),model.getMessage()).show();
            }


        }

        @Override
        public void onError(String status) {
            if(progressDialog!=null)
                progressDialog.dismiss();
            Notifications.showToastMessage(layout,getActivity(),status).show();
        }

        @Override
        public void onError(ResponseModel model) {
            if(progressDialog!=null)
                progressDialog.dismiss();
            String message = "";
            for (int i=0;i<model.getDetails().size();i++){
                message = message + "\n" + model.getDetails().get(i);
            }

            Notifications.showToastMessage(layout,getActivity(),message).show();
        }
    };

    //Response callback for "Drop Off Location"
    private final JsonRequestManager.getPickUpLocation dropOffCallback = new JsonRequestManager.getPickUpLocation() {

        @Override
        public void onSuccess(ResponseModel model) {

            if(progressDialog!=null)
                progressDialog.dismiss();

            if(model.getStatus().equalsIgnoreCase("success")){
                addDelivaryMarkers(model.getData());

            }else{
                Notifications.showToastMessage(layout,getActivity(),model.getMessage()).show();
            }


        }

        @Override
        public void onError(String status) {
            if(progressDialog!=null)
                progressDialog.dismiss();
            Notifications.showToastMessage(layout,getActivity(),status).show();
        }

        @Override
        public void onError(ResponseModel model) {
            if(progressDialog!=null)
                progressDialog.dismiss();
            String message = "";
            for (int i=0;i<model.getDetails().size();i++){
                message = message + "\n" + model.getDetails().get(i);
            }

            Notifications.showToastMessage(layout,getActivity(),message).show();
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setMap();
    }

    private void setMap(){
        fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm
                .findFragmentById(R.id.map);
        MapsInitializer.initialize(getActivity());
        fragment.getMapAsync(this);
    }

    private void enableLocation(GoogleMap googleMap) {
        if (googleMap != null) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_REQUEST_CODE);
                return;
            }else{
                googleMap.setMyLocationEnabled(true);
                LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
                List<String> providers = lm.getProviders(true);
                Location l;

                for (int i = 0; i < providers.size(); i++) {
                    l = lm.getLastKnownLocation(providers.get(i));
                    if (l != null) {
                        currentLatitude = l.getLatitude();
                        currentLongitude = l.getLongitude();
                        break;
                    }
                }
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(currentLatitude, currentLongitude)).title("You Are Here").snippet("");
                marker.icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(currentLatitude, currentLongitude)).zoom(15).build();

                googleMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));

                googleMap.addMarker(marker);
            }

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setOnMarkerClickListener(this);
        enableLocation(map);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        final Data data = markerMap.get(marker);
        marker.hideInfoWindow();
        final Dialog dialog1 = new Dialog(getActivity());
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

        if(data.getPickupLat()!=null){
            dialog1.setContentView(R.layout.custom_pickup_dialog);
            dialog1.show();
            Button btnOk = (Button)dialog1.findViewById(R.id.btnParcels);
            Button btnCancel = (Button)dialog1.findViewById(R.id.btnCancel);
            TextView pickupLocation = (TextView)dialog1.findViewById(R.id.txtPickupLocation);
            TextView contact = (TextView)dialog1.findViewById(R.id.txtContactPerson);
            pickupLocation.setText(data.getPickupAddress());
            contact.setText(data.getPickupContactPerson());
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog1.dismiss();
                    AppController.setCurrentDetails(data);
                    Intent intent = new Intent(context,ParcelDetailsActivity.class);
                    context.startActivity(intent);
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog1.dismiss();
                }
            });
        }else if(data.getDropoffLat()!=null){
            dialog1.setContentView(R.layout.custom_dilivary_dialog);
            dialog1.show();
            Button btnOk = (Button)dialog1.findViewById(R.id.btnLocation);
            Button btnCancel = (Button)dialog1.findViewById(R.id.btnCancel);

            TextView deliveryLocation = (TextView)dialog1.findViewById(R.id.txtDeliveryLocation);
            deliveryLocation.setText(data.getDropoffAddress());

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog1.dismiss();
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog1.dismiss();
                }
            });
        }



        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.idOnline:
                if(AppController.isOffline()){
                    AppController.setIsOffline(false);
                    item.setTitle("Go Offline");
                }else{
                    AppController.setIsOffline(true);
                    item.setTitle("Go Online");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addPickUpMarkers(Data data){
        Marker marker = map.addMarker(new MarkerOptions()
                .position(new LatLng(data.getPickupLon(),data.getPickupLat()))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.rider_pickup_pin)));
        markerMap.put(marker,data);
    }

    private void addDelivaryMarkers(Data data){
        Marker marker = map.addMarker(new MarkerOptions()
                .position(new LatLng(data.getDropoffLon(),data.getDropoffLat()))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.rider_dropoff_flag)));
        markerMap.put(marker,data);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}
