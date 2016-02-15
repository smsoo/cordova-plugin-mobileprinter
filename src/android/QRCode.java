package com.solution.mobileprinter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.lvrenyang.myprinter.Global;
import com.lvrenyang.myprinter.WorkService;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;

/**
 * Created by sm.soo on 06/02/2016.
 */
public class QRCode {
    private static Handler mHandler = null;
    private static String TAG = "Qrcode";
    private boolean useEpsonQRCmd = false;
    private static CallbackContext qrcodeCallbackContext = null;

    public static int nQrcodetype = 0, nQrcodeWidth = 4,
            nErrorCorrectionLevel = 3;

    public QRCode() {
        mHandler = new MHandler(this);
        WorkService.addHandler(mHandler);
    }

    /*@Override
    public void onDestroy() {
        super.onDestroy();
        WorkService.delHandler(mHandler);
        mHandler = null;
    }*/

    public boolean execute(JSONArray args, final CallbackContext callbackContext)
            throws JSONException {
        try {

            String strQrcode = args.getString(0);
            if (strQrcode.length() == 0)
                return false;
            int nWidthX = args.getInt(2) + 2;
            int necl = args.getInt(3) + 1;
            int sbSize= args.getInt(4);
            boolean useEpsonQRCmd = args.getBoolean(5);
            if (WorkService.workThread.isConnected()) {
                if (useEpsonQRCmd)
                {
                    Bundle data = new Bundle();
                    data.putString(Global.STRPARA1, strQrcode);
                    data.putInt(Global.INTPARA1, nWidthX);// ??????????
                    data.putInt(Global.INTPARA2, sbSize); // ????????
                    data.putInt(Global.INTPARA3, necl);
                    WorkService.workThread.handleCmd(Global.CMD_EPSON_SETQRCODE, data);
                }
                else
                {
                    // ?????nWidthX??????
                    // nVersion????????????????
                    Bundle data = new Bundle();
                    data.putString(Global.STRPARA1, strQrcode);
                    data.putInt(Global.INTPARA1, nWidthX);// ??????????
                    data.putInt(Global.INTPARA2, sbSize); // ????????
                    data.putInt(Global.INTPARA3, necl);
                    WorkService.workThread.handleCmd(
                            Global.CMD_POS_SETQRCODE, data);
                }
               /* final String responseText = "QRCode: Queueing..." + args.getString(0);
                callbackContext.success(responseText);*/
                this.qrcodeCallbackContext = callbackContext;

                PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
                pluginResult.setKeepCallback(true);
                callbackContext.sendPluginResult(pluginResult);

            } else {
                callbackContext.error("No connection");
            }
        }catch (JSONException e){
            callbackContext.error("Failed to parse parameters");
        }
        return true;
    }

    static class MHandler extends Handler {

        WeakReference<QRCode> mActivity;

        MHandler(QRCode activity) {
            mActivity = new WeakReference<QRCode>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
           /* QrcodeActivity theActivity = mActivity.get();*/
            switch (msg.what) {

                case Global.CMD_POS_SETQRCODERESULT:
                case Global.CMD_EPSON_SETQRCODERESULT:{
                    int result1 = msg.arg1;
                    Log.v(TAG, "Result: " + result1);
                    if(result1 == 1) {
                        PluginResult result = new PluginResult(PluginResult.Status.OK);
                        result.setKeepCallback(false);
                        qrcodeCallbackContext.sendPluginResult(result);
                    }
                    else
                    {
                        PluginResult result = new PluginResult(PluginResult.Status.ERROR);
                        result.setKeepCallback(false);
                        qrcodeCallbackContext.sendPluginResult(result);
                    }
                    break;
                }

            }
        }
    }
}
