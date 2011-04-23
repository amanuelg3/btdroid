package org.radarman.btdroid;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**Clase para manejar las opciones
 * 
 * @author radarman
 *
 */
public class Options extends Activity {
	String opciones[];
	static TextView tvo;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		Button nt1=(Button)findViewById(R.id.button1);
		nt1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				BTControl.end();
				BTControl.init();
			}

		});
		tvo=(TextView)this.findViewById(R.id.textView1);
		tvo.setText("Conectar a:"+ BTControl.deviceSelected);
		TextView tvo2=(TextView)this.findViewById(R.id.textView2);
		tvo2.setText(this.getString((R.string.about)));
		//tvo.setText(ReadSettings(this));
		if(BTControl.dispositivos.length==0){
			Toast.makeText(this, this.getString((R.string.dfound)),Toast.LENGTH_SHORT).show();
			return;
		}
		ListView lv1=(ListView)findViewById(R.id.listView1);
		int num=0;
		opciones=new String[BTControl.dispositivos.length];
		while(num<BTControl.dispositivos.length){
			opciones[num]=BTControl.dispositivos[num].getName();
			num++;
		}
		lv1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1 , opciones));
		lv1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				tvo.setText(opciones[arg2]);
				BTControl.deviceSelected=opciones[arg2];
				tvo.setText("Conectado a:"+ BTControl.deviceSelected);
				//WriteSettings(opciones[arg2]);

			}
		});
	}
	/*
	// Save settings 
    public void WriteSettings( String data){ 
     Context context=this;
   	 FileOutputStream fOut = null; 
   	 OutputStreamWriter osw = null;

   	 try{
   	  fOut = context.openFileOutput("settings.dat",MODE_PRIVATE);       
         osw = new OutputStreamWriter(fOut); 
         osw.write(data); 
         osw.flush(); 
         Toast.makeText(context, "Settings saved",Toast.LENGTH_SHORT).show();
         } 
         catch (Exception e) {       
         e.printStackTrace(); 
         Toast.makeText(context, "Settings not saved",Toast.LENGTH_SHORT).show();
         } 
         finally { 
            try { 
                   osw.close(); 
                   fOut.close(); 
                   } catch (IOException e) { 
                   e.printStackTrace(); 
                   } 
         } 
    }
 // Read settings 
    public String ReadSettings(Context context){ 
   	 FileInputStream fIn = null; 
   	 InputStreamReader isr = null;

   	 char[] inputBuffer = new char[255]; 
   	 String data = null;

   	 try{
   	  fIn = openFileInput("settings.dat");       
         isr = new InputStreamReader(fIn); 
         isr.read(inputBuffer); 
         data = new String(inputBuffer);
         Toast.makeText(context, "Settings read",Toast.LENGTH_SHORT).show();
         } 
         catch (Exception e) {       
         e.printStackTrace(); 
         Toast.makeText(context, "Settings not read",Toast.LENGTH_SHORT).show();
         } 
         finally { 
            try { 
                   isr.close(); 
                   fIn.close(); 
                   } catch (IOException e) { 
                   e.printStackTrace(); 
                   } 
         }
		return data; 
    }*/
}
