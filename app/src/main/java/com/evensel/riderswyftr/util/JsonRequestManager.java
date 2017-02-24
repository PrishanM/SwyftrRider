package com.evensel.riderswyftr.util;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author prishanm 02/14/2017
 *
 */
public class JsonRequestManager {

	private static JsonRequestManager mInstance;
	private RequestQueue mRequestQueue;
	private static Context mCtx;

	/**
	 * Log or request TAG
	 */
	public static final String TAG = "VolleyInstance";
	private final String tag_json_arry = "json_array_req";

	/* Volley */
	public static synchronized JsonRequestManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new JsonRequestManager(context);
		}
		return mInstance;
	}

	private JsonRequestManager(Context context) {
		mCtx = context;
		mRequestQueue = getRequestQueue();
	}

	private RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley
					.newRequestQueue(mCtx.getApplicationContext());
		}
		return mRequestQueue;
	}

	/******************************************************************************************************************************************/

	/**
	 * Register User
	 **/
	public interface registerUser{
		void onSuccess(ResponseModel model);

		void onError(String status);

		void onError(ResponseModel model);


	}

	public void registerUserRequest(String url, final String email,String password,String conPassword,String phoneNo,
							   final registerUser callback) {

		//Log.d("test Request", image);
		String finalUrl = url;
		HashMap<String, String> params = new HashMap<>();
		params.put("email",email);
		params.put("password",password);
		params.put("password_confirmation",conPassword);
		params.put("phone_no",phoneNo);

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
				finalUrl, new JSONObject(params),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						ObjectMapper mapper = new ObjectMapper();

						try {
							if(response!=null){
								ResponseModel responseModel = mapper.readValue(response.toString(), ResponseModel.class);
								callback.onSuccess(responseModel);
							}else{
								callback.onError("Error occured");
							}

						} catch (Exception e) {
							e.printStackTrace();
							callback.onError("Error occured");
						}
					}
				}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onError(errorResponse(error.networkResponse.data,HttpHeaderParser.parseCharset(error.networkResponse.headers)));
			}
		});

		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq,
				tag_json_arry);

	}

	/******************************************************************************************************************************************/

	/**
	 * Login User
	 **/
	public interface loginUser{
		void onSuccess(LoginResponse model);

		void onError(LoginResponse model);

		void onError(String s);
	}

	public void loginUserRequest(String url, final String email,String password,
									final loginUser callback) {

		//Log.d("test Request", image);
		String finalUrl = url;
		HashMap<String, String> params = new HashMap<>();
		params.put("email",email);
		params.put("password",password);
		params.put("user","rider");

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
				finalUrl, new JSONObject(params),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						ObjectMapper mapper = new ObjectMapper();

						try {
							if(response!=null){
								LoginResponse responseModel = mapper.readValue(response.toString(), LoginResponse.class);
								callback.onSuccess(responseModel);
							}else{
								callback.onError("Error occured");
							}

						} catch (Exception e) {
							e.printStackTrace();
							callback.onError("Error occured");
						}
					}
				}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onError(errorLoginResponse(error.networkResponse.data,HttpHeaderParser.parseCharset(error.networkResponse.headers)));
			}
		});

		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq,
				tag_json_arry);

	}

	/******************************************************************************************************************************************/

	/**
	 * Update User Info
	 **/
	public interface updateUser{
		void onSuccess(ResponseModel model);

		void onError(String status);

		void onError(ResponseModel model);


	}

	public void updateUserRequest(String url, String token, String name,String email,String mobile ,String home_address,String office_address,
									final updateUser callback) {

		//Log.d("test Request", image);
		String finalUrl = url+"?token="+token;
		HashMap<String, String> params = new HashMap<>();
		params.put("email",email);
		params.put("name",name);
		params.put("phone_no",mobile);
		params.put("home_address",home_address);
		params.put("office_address",office_address);

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
				finalUrl, new JSONObject(params),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						ObjectMapper mapper = new ObjectMapper();

						try {
							if(response!=null){
								ResponseModel responseModel = mapper.readValue(response.toString(), ResponseModel.class);
								callback.onSuccess(responseModel);
							}else{
								callback.onError("Error occured");
							}

						} catch (Exception e) {
							e.printStackTrace();
							callback.onError("Error occured");
						}
					}
				}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onError(errorResponse(error.networkResponse.data,HttpHeaderParser.parseCharset(error.networkResponse.headers)));
			}
		});

		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq,
				tag_json_arry);

	}


	/******************************************************************************************************************************************/

	/**
	 * Send password reset code
	 **/
	public interface sendResetCode{
		void onSuccess(ResponseModel model);

		void onError(String status);

		void onError(ResponseModel model);


	}

	public void sendResetCodeRequest(String url,String email,
								  final sendResetCode callback) {

		//Log.d("test Request", image);
		String finalUrl = url;
		HashMap<String, String> params = new HashMap<>();
		params.put("email",email);
		params.put("user","rider");

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
				finalUrl, new JSONObject(params),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						ObjectMapper mapper = new ObjectMapper();

						try {
							if(response!=null){
								ResponseModel responseModel = mapper.readValue(response.toString(), ResponseModel.class);
								callback.onSuccess(responseModel);
							}else{
								callback.onError("Error occured");
							}

						} catch (Exception e) {
							e.printStackTrace();
							callback.onError("Error occured");
						}
					}
				}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onError(errorResponse(error.networkResponse.data,HttpHeaderParser.parseCharset(error.networkResponse.headers)));
			}
		});

		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq,
				tag_json_arry);

	}

	/******************************************************************************************************************************************/

	/**
	 * Validate reset code
	 **/
	public interface validateResetCode{
		void onSuccess(ResponseModel model);

		void onError(String status);

		void onError(ResponseModel model);


	}

	public void validateResetCodeRequest(String url,String code,
									 final validateResetCode callback) {

		//Log.d("test Request", image);
		String finalUrl = url;
		HashMap<String, String> params = new HashMap<>();
		params.put("code",code);

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
				finalUrl, new JSONObject(params),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						ObjectMapper mapper = new ObjectMapper();

						try {
							if(response!=null){
								ResponseModel responseModel = mapper.readValue(response.toString(), ResponseModel.class);
								callback.onSuccess(responseModel);
							}else{
								callback.onError("Error occured");
							}

						} catch (Exception e) {
							e.printStackTrace();
							callback.onError("Error occured");
						}
					}
				}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onError(errorResponse(error.networkResponse.data,HttpHeaderParser.parseCharset(error.networkResponse.headers)));
			}
		});

		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq,
				tag_json_arry);

	}

	/******************************************************************************************************************************************/

	/**
	 * Reset password
	 **/
	public interface resetPassword{
		void onSuccess(ResponseModel model);

		void onError(String status);

		void onError(ResponseModel model);


	}

	public void resetPasswordRequest(String url,String email,String password,
										 final resetPassword callback) {

		//Log.d("test Request", image);
		String finalUrl = url;
		HashMap<String, String> params = new HashMap<>();
		params.put("email",email);
		params.put("password",password);
		params.put("password_confirmation",password);
		params.put("user","user");

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
				finalUrl, new JSONObject(params),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						ObjectMapper mapper = new ObjectMapper();

						try {
							if(response!=null){
								ResponseModel responseModel = mapper.readValue(response.toString(), ResponseModel.class);
								callback.onSuccess(responseModel);
							}else{
								callback.onError("Error occured");
							}

						} catch (Exception e) {
							e.printStackTrace();
							callback.onError("Error occured");
						}
					}
				}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onError(errorResponse(error.networkResponse.data,HttpHeaderParser.parseCharset(error.networkResponse.headers)));
			}
		});

		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq,
				tag_json_arry);

	}

	/******************************************************************************************************************************************/

	/**
	 * Get Pick Up Location
	 **/
	public interface getPickUpLocation{
		void onSuccess(ResponseModel model);

		void onError(String status);

		void onError(ResponseModel model);


	}

	public void getPickUpLocationRequest(String url,int id,String token,
										 final getPickUpLocation callback) {

		//Log.d("test Request", image);
		String finalUrl = url+"/"+id+"?token="+token;
		HashMap<String, String> params = new HashMap<>();

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
				finalUrl, new JSONObject(params),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						ObjectMapper mapper = new ObjectMapper();

						try {
							if(response!=null){
								ResponseModel responseModel = mapper.readValue(response.toString(), ResponseModel.class);
								callback.onSuccess(responseModel);
							}else{
								callback.onError("Error occured");
							}

						} catch (Exception e) {
							e.printStackTrace();
							callback.onError("Error occured");
						}
					}
				}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onError(errorResponse(error.networkResponse.data,HttpHeaderParser.parseCharset(error.networkResponse.headers)));
			}
		});

		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq,
				tag_json_arry);

	}


	/******************************************************************************************************************************************/

	/******************************************************************************************************************************************/


	/**
	 * Method to convert 400,401,500 error to response model class
	 * @param bytes
	 * @param charset
     * @return ResponseModel
     */
	private ResponseModel errorResponse(byte[] bytes,String charset){
		ResponseModel responseModel = new ResponseModel();
		responseModel.setMessage("Error Occured.");
		responseModel.setStatus("error");
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = new String(bytes, charset);
			JSONObject errorResponse = new JSONObject(json);
			if(errorResponse!=null){
				responseModel = mapper.readValue(errorResponse.toString(),ResponseModel.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseModel;
	}

	/**
	 * Method to convert 400,401,500 error to response model class
	 * @param bytes
	 * @param charset
	 * @return ResponseModel
	 */
	private LoginResponse errorLoginResponse(byte[] bytes,String charset){
		LoginResponse responseModel = new LoginResponse();
		responseModel.setMessage("Error Occured.");
		responseModel.setStatus("error");
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = new String(bytes, charset);
			JSONObject errorResponse = new JSONObject(json);
			if(errorResponse!=null){
				responseModel = mapper.readValue(errorResponse.toString(),LoginResponse.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseModel;
	}

	/**
	 * Validate reset code
	 **/
	public interface test{
		void onSuccess(ResponseModel model);

		void onError(String status);

		void onError(ResponseModel model);


	}

	public void testRequest(String url,String email,String password,String conPassword,
										 final test callback) {

		//Log.d("test Request", image);
		String finalUrl = "http://api.swyftr.com/api/v1/password/reset";
		HashMap<String, String> params = new HashMap<>();
		params.put("email",email);
		params.put("user","rider");
		params.put("password",password);
		params.put("password_confirmation",conPassword);

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
				finalUrl, new JSONObject(params),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						ObjectMapper mapper = new ObjectMapper();

						try {
							if(response!=null){
								ResponseModel responseModel = mapper.readValue(response.toString(), ResponseModel.class);
								callback.onSuccess(responseModel);
							}else{
								callback.onError("Error occured");
							}

						} catch (Exception e) {
							e.printStackTrace();
							callback.onError("Error occured");
						}
					}
				}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onError(errorResponse(error.networkResponse.data,HttpHeaderParser.parseCharset(error.networkResponse.headers)));
			}
		}){

			/**
			 * Passing some request headers
			 * */
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<>();
				headers.put("Content-Type", "application/json;charset=utf-8");
				return headers;
			}

		};

		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq,
				tag_json_arry);

	}


}
