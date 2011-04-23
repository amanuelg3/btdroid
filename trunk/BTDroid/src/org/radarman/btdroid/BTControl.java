package org.radarman.btdroid;

import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
/** BTControl es la clase que gestiona el bluettoth
 * 
 * @author radarman
 *
 */
public class BTControl {
	static BluetoothDevice dispositivo;
	static BluetoothDevice[] dispositivos;
	static BluetoothSocket bsock;
	static OutputStream outm;
	static boolean conected=false;
	static String deviceSelected="Default";
	/**Iniciar conexion bluetooth
	 * 
	 */
	public static void init(){
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		Set<BluetoothDevice> devices = adapter.getBondedDevices();
		dispositivos=new BluetoothDevice[devices.size()];
		int num=0;
		if(deviceSelected.equals("Default")){
			for (BluetoothDevice device : devices) {
				dispositivos[num]=device;
				Log.i("bbbb","Found device: " + device);
				dispositivo=device;
				num++;
			}
		}else{
			for (BluetoothDevice device : devices) {
				dispositivos[num]=device;
				if(device.getName().equals(deviceSelected)){
					dispositivo=device;
				}
				num++;
			}
		}
		try {
			conected=false;
			bsock=dispositivo.createRfcommSocketToServiceRecord(UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee"));
			bsock.connect();
			outm=bsock.getOutputStream();
			//outm.write("hello".getBytes());
			conected=true;
		} catch (Exception e) {
			conected=false;      	  
			e.printStackTrace();
		}
		BTDroid.refreshConectionState();
	}
	/**Para enviar instrucciones al servidor
	 * 
	 * @param command
	 */
	public static void send(String command){
		try {
			outm.write(command.getBytes());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**Cerrar conexion bluetooth
	 * 
	 */
	public static void end(){
		try {
			conected=false;
			BTDroid.refreshConectionState();
			outm.close();
			bsock.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
