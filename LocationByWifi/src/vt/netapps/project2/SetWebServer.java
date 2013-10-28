package vt.netapps.project2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SetWebServer extends Activity {
	
	public EditText add;
	public EditText port;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_server);
		Button ok = (Button)this.findViewById(R.id.findRoom);
		Button cancel = (Button)this.findViewById(R.id.cancel);
		add = (EditText)this.findViewById(R.id.serveraddress);
		port = (EditText)this.findViewById(R.id.serverport);
		OnClickListener canListen = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		};
		OnClickListener okListen = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println(CurrentRoom.ipad);
				System.out.println(Integer.toString(CurrentRoom.port));
				CurrentRoom.ipad = add.getText().toString();
				CurrentRoom.port = Integer.parseInt(port.getText().toString());
				System.out.println(CurrentRoom.ipad);
				System.out.println(Integer.toString(CurrentRoom.port));
				finish();
			}
		};
		cancel.setOnClickListener(canListen);
		ok.setOnClickListener(okListen);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.current_room, menu);
		return true;
	}

}
