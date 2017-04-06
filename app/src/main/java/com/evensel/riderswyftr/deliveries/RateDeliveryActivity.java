package com.evensel.riderswyftr.deliveries;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.evensel.riderswyftr.R;
import com.evensel.riderswyftr.util.AppController;
import com.evensel.riderswyftr.util.Constants;
import com.evensel.riderswyftr.util.JsonRequestManager;
import com.evensel.riderswyftr.util.Notifications;
import com.evensel.riderswyftr.util.ResponseModel;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by Prishan Maduka on 2/27/2017.
 */
public class RateDeliveryActivity extends AppCompatActivity {

    private Button btnSubmit;
    private Context context;
    private MaterialRatingBar rateUser,rateStore,rateSwyftr;
    private EditText edtReview;
    private ProgressDialog progressDialog;
    private String token = "";
    private View layout;
    private LayoutInflater inflate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_layout);

        context = this;

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Rate Experience");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnSubmit = (Button)findViewById(R.id.txtSubmit);
        context = this;
        rateUser = (MaterialRatingBar)findViewById(R.id.rateUser);
        rateStore = (MaterialRatingBar)findViewById(R.id.rateStore);
        rateSwyftr = (MaterialRatingBar)findViewById(R.id.rateSwyftr);
        edtReview = (EditText)findViewById(R.id.input_review);

        inflate = getLayoutInflater();
        layout = inflate.inflate(R.layout.custom_toast_layout, (ViewGroup) findViewById(R.id.toast_layout_root));
        token = context.getSharedPreferences(Constants.LOGIN_SHARED_PREF, Context.MODE_PRIVATE).getString(Constants.LOGIN_ACCESS_TOKEN, "");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(context, null,
                        "Loading...", true);
                /*float userRating = rateUser.getRating();
                float storeRating = rateStore.getRating();
                float swyftrRating = rateSwyftr.getRating();

                progressDialog = ProgressDialog.show(context, null,
                        "Loading...", true);
                JsonRequestManager.getInstance(context).rateUserRequest(AppURL.APPLICATION_BASE_URL + AppURL.RATE_USER_URL,token,"8",String.valueOf(userRating),String.valueOf(storeRating),String.valueOf(swyftrRating),edtReview.getText().toString(), callback);*/
                try {
                    GetText();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    //Response callback for "RATE"
    private final JsonRequestManager.rateUser callback = new JsonRequestManager.rateUser() {

        @Override
        public void onSuccess(ResponseModel model) {

            if(progressDialog!=null)
                progressDialog.dismiss();

            if(model.getStatus().equalsIgnoreCase("success")){
                Notifications.showToastMessage(layout,context,model.getMessage()).show();
            }else{
                Notifications.showToastMessage(layout,context,model.getMessage()).show();
            }


        }

        @Override
        public void onError(String status) {
            if(progressDialog!=null)
                progressDialog.dismiss();
            Notifications.showToastMessage(layout,context,status).show();
        }

        @Override
        public void onError(ResponseModel model) {
            if(progressDialog!=null)
                progressDialog.dismiss();

            Notifications.showToastMessage(layout,context,model.getMessage()).show();
        }
    };

    public  void  GetText()  throws UnsupportedEncodingException
    {
        // Get user defined values
        float userRating = rateUser.getRating();
        float storeRating = rateStore.getRating();
        float swyftrRating = rateSwyftr.getRating();
        String comment = edtReview.getText().toString();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                "http://api.swyftr.com/api/v1/rider/rate?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEsImlzcyI6Imh0dHA6XC9cL2FwaS5zd3lmdHIuY29tXC9hcGlcL3YxXC9sb2dpbiIsImlhdCI6MTQ5MTI4Mjg5NywiZXhwIjoxNDkyMTQ2ODk3LCJuYmYiOjE0OTEyODI4OTcsImp0aSI6ImZkZDY1YjM4YmRmY2M1MjljZDdjMzY3ZjViMjYzNmZmIn0.PFp0xGgkp_Qxzhdkp5Y9X0wKKO0iqsgr0GiE_4uETy4", null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("CCCCCCCCCCCCCCCCCCCC", response.toString());
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("CCCCCCCCCCCCCCCCCCCC", "Error: " + error.getMessage());
                if(progressDialog!=null)
                    progressDialog.dismiss();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("order_id","8");
                params.put("user_rating","3");
                params.put("store_rating","3");
                params.put("app_rating","3");
                params.put("comment","TEST");

                return params;
            }

        };

        String tag_json_obj = "json_obj_req";

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);


    }

}
