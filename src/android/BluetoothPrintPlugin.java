package com.solution.mobileprinter;

/**
 * Created by sm.soo on 26/01/2016.
 */

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import com.lvrenyang.myprinter.WorkService;
import com.lvrenyang.myprinter.Global;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class BluetoothPrintPlugin extends CordovaPlugin {

   // private ProgressDialog dialog;
    public static final String ICON = "ICON";
    public static final String PRINTERNAME = "PRINTERNAME";
    public static final String PRINTERMAC = "PRINTERMAC";
    private static List<Map<String, Object>> boundedPrinters;
    private static Handler mHandler = null;
    private static String TAG = "ConnectBTMacActivity";
    private static PlainText plainText = null;
    private static BarCode barCode = null;
    private static QRCode qrCode = null;

    private static CallbackContext btCallbackContext = null;
    @Override
    protected void pluginInitialize() {
        //dialog = new ProgressDialog(this);
        boundedPrinters = getBoundedPrinters();
        mHandler = new MHandler(this);
        WorkService.addHandler(mHandler);

        Log.v(TAG, "Bluetooth initialization called : ");
        plainText = new PlainText();
        barCode = new BarCode();
        qrCode = new QRCode();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        WorkService.delHandler(mHandler);
        mHandler = null;
        plainText = null;
        barCode = null;
        qrCode = null;

        //temporary
        WorkService.workThread.disconnectBle();
        WorkService.workThread.disconnectBt();
        WorkService.workThread.disconnectNet();
        WorkService.workThread.disconnectUsb();
    }

    static class MHandler extends Handler {

        WeakReference<BluetoothPrintPlugin> mActivity;

        MHandler(BluetoothPrintPlugin activity) {
            mActivity = new WeakReference<BluetoothPrintPlugin>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
           // BluetoothPrintPlugin theActivity = mActivity.get();
            switch (msg.what) {
                /**
                 * DrawerService ? onStartCommand???????
                 */

                case Global.MSG_WORKTHREAD_SEND_CONNECTBTRESULT: {
                    int result1 = msg.arg1;
                    Log.v(TAG, "Connect Result: " + result1);
                    if(result1 == 1) {
                        PluginResult result = new PluginResult(PluginResult.Status.OK);
                        result.setKeepCallback(false);
                        btCallbackContext.sendPluginResult(result);
                    }
                    else
                    {
                        PluginResult result = new PluginResult(PluginResult.Status.ERROR);
                        result.setKeepCallback(false);
                        btCallbackContext.sendPluginResult(result);
                    }
                    //theActivity.dialog.cancel();
                    break;
                }

            }
        }
    }

    private List<Map<String, Object>> getBoundedPrinters() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            return list;
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a
                // ListView
                Map<String, Object> map = new HashMap<String, Object>();
                //map.put(ICON, android.R.drawable.stat_sys_data_bluetooth);
                map.put(PRINTERNAME, device.getName());
                map.put(PRINTERMAC, device.getAddress());
                list.add(map);
            }
        }
        return list;
    }

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext)
            throws JSONException {
        if (action.equals("connect")){
            if (!WorkService.workThread.isConnected()) {
                try {
                /*if (this.btCallbackContext != null) {
                    callbackContext.error( "Bluetooth plugin is already running.");
                    return true;
                }*/

                    int index = args.getInt(0);
                    String address = (String) boundedPrinters.get(index).get(PRINTERMAC);
                    Log.e(TAG, "Sawmeng: address: " + address);

                    WorkService.workThread.connectBt(address);

                    this.btCallbackContext = callbackContext;

                    PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
                    pluginResult.setKeepCallback(true);
                    callbackContext.sendPluginResult(pluginResult);

                    //callbackContext.success("Bluetooth...Connecting");
                } catch (Exception e) {

                    callbackContext.error("Failed to parse parameters");
                }
                return true;
            } else
            {
                callbackContext.error("Already connected. Please disconnect before attempting another connection");
            }
        }
        else if (action.equals("printPlainText")) {
            plainText.execute(args,callbackContext);
            return true;
        }
        else if (action.equals("printBarCode")) {
            barCode.execute(args,callbackContext);
            return true;
        }
        else if (action.equals("printQRCode")) {
            qrCode.execute(args,callbackContext);
            return true;
        }
        else if (action.equals("disconnect")) {
            try {
                WorkService.workThread.disconnectBle();
                WorkService.workThread.disconnectBt();
                WorkService.workThread.disconnectNet();
                WorkService.workThread.disconnectUsb();
                callbackContext.success("Disconnected");
            }catch (Exception e){
                callbackContext.error("Failed");
            }
            return true;
        }
        return false;
    }
}
