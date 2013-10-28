package vt.netapps.project2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SetRoom extends Activity {
	EditText name;
	SetRoom setRoom = this;
	public static CurrentRoom act;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_room);
		Button cancel = (Button)this.findViewById(R.id.cancel2);
		Button sendButton = (Button)this.findViewById(R.id.send);
		name = (EditText)this.findViewById(R.id.roomLabel);
		OnClickListener can2Listen = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		};
		OnClickListener sendListen = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CollectRoomData post = new CollectRoomData(act, 1, name.getText().toString());
				post.execute();
				finish();
			}
		};
		cancel.setOnClickListener(can2Listen);
		sendButton.setOnClickListener(sendListen);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.current_room, menu);
		return true;
	}
}
