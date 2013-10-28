package vt.netapps.project2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

public class serverSend extends AsyncTask<String, String, String> {
	Activity r;
	Activity act;
	Integer type;
	private TextView view;
	String resp = "";
	
	public serverSend(Activity a, int i)
	{
		act = a;
		type = i;
		view= (TextView) act.findViewById(R.id.roomID);
	}
		
	@Override
	public String doInBackground(String...strings)
	{

	    HttpClient httpClient = new DefaultHttpClient();
	    System.out.println("Starting post");
	    JSONObject room = new JSONObject();

	        HttpPost request = new HttpPost("http://" + CurrentRoom.ipad + ":" + Integer.toString(CurrentRoom.port));
	        request.setHeader("Content-Type", "application/json");
	        StringEntity params;
			try {
				room.put("room", strings[0]);
				params = new StringEntity(room.toString());
				request.setEntity(params);
		        System.out.println("Params: "+ room.toString());
				HttpResponse response = httpClient.execute(request);
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				StringBuilder builder = new StringBuilder();
				for (String line = null; (line = reader.readLine()) != null;) {
				    builder.append(line).append("\n");
				}
				  resp = builder.toString();
					System.out.println(resp);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("Ending post");
		return resp;
	}
	
	@Override
	public void onPostExecute(String s)
	{
		System.out.println("Setting resp: ");
		System.out.println(s);
		TextView t = (TextView)act.findViewById(R.id.roomID);
		ProgressBar bar = (ProgressBar)act.findViewById(R.id.pBar);
		bar.setProgress(100);
		t.setText(s);
	}

}
