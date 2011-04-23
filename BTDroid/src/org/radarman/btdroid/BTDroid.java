package org.radarman.btdroid;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
/**Clase principal.Gestiona la pantalla multimedia,de raton,etc
 * 
 * @author radarman
 *
 */
public class BTDroid extends Activity{
	/** Called when the activity is first created. */
	BluetoothDevice dispositivo;
	OnClickListener ocl;
	BluetoothSocket bsock;
	OutputStream outm;
	boolean mouseon=false;
	float posXant=0;
	float posYant=0;
	static RadioButton rb1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lytmultimedia();
		BTControl.init();
	}
	public void onStop(){
		BTControl.end();
		super.onStop();
	}
	/**Layout multimedia
	 * 
	 */
	public void lytmultimedia(){
		mouseon=false;
		setContentView(R.layout.multimedia);
		OnClickListener ocl=new View.OnClickListener() {
			public void onClick(View view) {
				if (view.getId() == R.id.button1) { 
					enviar("prev");
				}else if (view.getId() == R.id.button2) {
					enviar("play");
				}else if (view.getId() == R.id.button3) {
					enviar("next");
				}else if (view.getId() == R.id.button4) {
					enviar("mute");
				}else if (view.getId() == R.id.button5) {
					enviar("volmenos");
				}else if (view.getId() == R.id.button6) {
					enviar("volmas");
				}else if (view.getId() == R.id.button7) {
					enviar("stop");
				}
			}
		};
		ImageButton but1=(ImageButton)this.findViewById(R.id.button1);
		but1.setOnClickListener(ocl);
		ImageButton but2=(ImageButton)this.findViewById(R.id.button2);
		but2.setOnClickListener(ocl);
		ImageButton but3=(ImageButton)this.findViewById(R.id.button3);
		but3.setOnClickListener(ocl);
		ImageButton but4=(ImageButton)this.findViewById(R.id.button4);
		but4.setOnClickListener(ocl);
		ImageButton but5=(ImageButton)this.findViewById(R.id.button5);
		but5.setOnClickListener(ocl);
		ImageButton but6=(ImageButton)this.findViewById(R.id.button6);
		but6.setOnClickListener(ocl);
		ImageButton but7=(ImageButton)this.findViewById(R.id.button7);
		but7.setOnClickListener(ocl);
		rb1=(RadioButton)findViewById(R.id.radioButton1);
		rb1.setClickable(false);
		if(BTControl.conected){
			rb1.setChecked(true);
		}
	}
	/**REfrescar estado de la conexion bluetooth
	 * 
	 */
	public static void refreshConectionState(){
		if(BTControl.conected){
			rb1.setChecked(true);
		}else{
			rb1.setChecked(false);
		}
	}
	public void lytmouse(){
		mouseon=true;
		setContentView(R.layout.mouse);
		OnClickListener ocl=new View.OnClickListener() {
			public void onClick(View view) {
				if (view.getId() == R.id.button1) { 
					enviar("mright");
				}else if (view.getId() == R.id.button2) {
					enviar("mleft");
				}
			}
		};
		Button but1=(Button)this.findViewById(R.id.button1);
		but1.setOnClickListener(ocl);
		Button but2=(Button)this.findViewById(R.id.button2);
		but2.setOnClickListener(ocl);
	}
	/**enviar instrucciones
	 * 
	 * @param comando
	 */
	public void enviar(String comando){
		BTControl.send(comando);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if(mouseon){
			if(event.getAction()==MotionEvent.ACTION_DOWN){
				posYant=event.getY();
				posXant=event.getX();
				return super.onTouchEvent(event);
			}
			if(event.getAction()==MotionEvent.ACTION_UP){
			}
			enviar("movement "+(int)(event.getX()-posXant)+" movement "+(int)(event.getY()-posYant));
			posYant=event.getY();
			posXant=event.getX();
		}
		return super.onTouchEvent(event);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.selremote, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.mouse:
			lytmouse();
			return true;
		case R.id.multimedia:
			lytmultimedia();
			return true;
		case R.id.desktop:
			Toast.makeText(this, "Comming soon...",Toast.LENGTH_SHORT).show();
			return true;
		case R.id.options:
			Intent myIntent = new Intent(BTDroid.this, Options.class);
			this.startActivity(myIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}