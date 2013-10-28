package vt.netapps.project2;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

public class CollectRoomData extends AsyncTask<Void, Void, Void> {
	private Activity act;
	private List<ScanResult> scan;
	private String roomName = "";
	private int flag;
	private int maxamp;
	private String lightAmp;
	public CollectRoomData(Activity a, int i, String room)
	{
		act = a;
		flag = i;
		roomName = room;
	}
	
	@Override
	public void onPreExecute()
	{
		TextView t = (TextView)act.findViewById(R.id.roomID);
		ProgressBar bar = (ProgressBar) act.findViewById(R.id.pBar);
		bar.setProgress(25);
		t.setText("Gathering room data...");
	}
	@Override
	public Void doInBackground(Void...voids)
	{
		WifiManager wifi = (WifiManager) act.getSystemService(Context.WIFI_SERVICE);
		wifi.startScan();
        SensorManager sensorManager = (SensorManager)act.getSystemService(Context.SENSOR_SERVICE);
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (lightSensor != null)
        {
        	float light = lightSensor.getMaximumRange();
        	lightAmp = Float.toString(light);
        }
        else
        {
        	lightAmp = "Not available";
        }

		MediaRecorder rec = new MediaRecorder();
		rec.setAudioSource(MediaRecorder.AudioSource.MIC);
		rec.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		rec.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		rec.setOutputFile("/dev/null");
		rec.getMaxAmplitude();
		try {
			rec.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rec.start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scan = wifi.getScanResults();
		maxamp = rec.getMaxAmplitude();
		rec.stop();
		
		
		return null;
	}
	
	@Override
	public void onPostExecute(Void v)
	{
		String room = roomName + "  ";
		for (int i = 0; i < scan.size(); i++)
		{
			room += "SSID: " + scan.get(i).SSID + "  BSID: " + scan.get(i).BSSID + "  Level: " + Integer.toString(scan.get(i).level);
			room += "\n";
		}
		room += "Noise: " + Integer.toString(maxamp) + "  Light: " + lightAmp;
		serverSend s = new serverSend(act, flag);
		s.execute(room);
		TextView t = (TextView)act.findViewById(R.id.roomID);
		ProgressBar bar = (ProgressBar) act.findViewById(R.id.pBar);
		bar.setProgress(75);
		t.setText("Sending room data...");
	}
}
