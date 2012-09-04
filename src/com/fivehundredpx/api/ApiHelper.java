package com.fivehundredpx.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.util.Log;

import com.fivehundredpx.api.auth.AccessToken;

public class ApiHelper {
	private static final String TAG = "ApiHelper";

	private static String HOST = "https://api.500px.com/v1";
	
	private AccessToken accessToken;
	private String consumerKey;
	private String consumerSecret;
	
	public ApiHelper(AccessToken accessToken, String consumerKey,
			String consumerSecret) {
		super();
		this.accessToken = accessToken;
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
	}
	
	public ApiHelper(String consumerKey) {
		super();
		this.consumerKey = consumerKey;
	}
	
	public JSONObject get(String url) {
		HttpGet request = new HttpGet(HOST + url);
		return handle(request);

	}

	public JSONObject post(String url) {
		HttpPost request = new HttpPost(HOST + url);
		return handle(request);

	}

	private JSONObject handle(HttpUriRequest request) {
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(
					consumerKey, consumerSecret);

			consumer.setTokenWithSecret(accessToken.getToken(),
					accessToken.getTokenSecret());
			consumer.sign(request);

			HttpResponse response = client.execute(request);
			final int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				final String msg = String.format(
						"Error, statusCode not OK(%d). for url: %s",
						statusCode, request.getURI().toString());
				Log.e(TAG, msg);
				return null;
			}

			HttpEntity responseEntity = response.getEntity();
			InputStream inputStream = responseEntity.getContent();
			BufferedReader r = new BufferedReader(new InputStreamReader(
					inputStream));
			StringBuilder total = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null) {
				total.append(line);
			}

			JSONObject json = new JSONObject(total.toString());
			return json;
		} catch (Exception e) {
			Log.e(TAG, "", e);
		}
		return null;
	}

}
