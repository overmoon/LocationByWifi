package vt.netapps.project2;

import java.util.List;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CurrentRoom extends Activity {
	public static String ipad = "172.31.213.53";
	public static int port = 8080;
	CurrentRoom act = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		SetRoom.act = act;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current_room);
		CollectRoomData data = new CollectRoomData(this, 0, "");
		OnClickListener web = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), SetWebServer.class);
				startActivity(i);
			}
		};
		OnClickListener set = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), SetRoom.class);
				startActivity(i);
			}
		};
		OnClickListener find = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CollectRoomData find = new CollectRoomData(act, 0, "");
				find.execute();
			}
		};
		Button webSet = (Button)this.findViewById(R.id.webButton);
		webSet.setOnClickListener(web);
		Button setR = (Button)this.findViewById(R.id.setRoom);
		setR.setOnClickListener(set);
		Button findRoom = (Button)this.findViewById(R.id.findRoom);
		findRoom.setOnClickListener(find);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.current_room, menu);
		return true;
	}

}
