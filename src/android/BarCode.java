package com.solution.mobileprinter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.lvrenyang.myprinter.Global;
import com.lvrenyang.myprinter.WorkService;
import com.lvrenyang.utils.DataUtils;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;

/**
 * Created by sm.soo on 03/02/2016.
 */
public class BarCode{

    private static Handler mHandler = null;
    private static String TAG = "BarCode";
    private static CallbackContext barcodeCallbackContext = null;

    private static int nBarcodetype = 0, nStartOrgx = 0, nBarcodeWidth = 1,
            nBarcodeHeight = 3, nBarcodeFontType = 0, nBarcodeFontPosition = 2;

    public BarCode() {
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

            String strBarcode = args.getString(0);
            if (strBarcode.length() == 0)
                return false;
            int nType = 0x41 + args.getInt(1);
            int nOrgx = args.getInt(2) * 12;
            int nWidthX = args.getInt(3) + 2;
            int nHeight = (args.getInt(4) + 1) * 24;
            int nHriFontType = args.getInt(5);
            int nHriFontPosition = args.getInt(6);

            if (WorkService.workThread.isConnected()) {
                Bundle data = new Bundle();
                data.putString(Global.STRPARA1, strBarcode);
                data.putInt(Global.INTPARA1, nOrgx);
                data.putInt(Global.INTPARA2, nType);
                data.putInt(Global.INTPARA3, nWidthX);
                data.putInt(Global.INTPARA4, nHeight);
                data.putInt(Global.INTPARA5, nHriFontType);
                data.putInt(Global.INTPARA6, nHriFontPosition);
                WorkService.workThread.handleCmd(Global.CMD_POS_SETBARCODE, data);

                this.barcodeCallbackContext = callbackContext;

                PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
                pluginResult.setKeepCallback(true);
                callbackContext.sendPluginResult(pluginResult);

                /*callbackContext.success("BarCode: Queueing..." + args.getString(0));*/

            } else {
                callbackContext.error("No connection");
            }

        } catch (JSONException e){
            callbackContext.error("Failed to parse parameters");
        }
        return true;
    }

    static class MHandler extends Handler {

        WeakReference<BarCode> mActivity;

        MHandler(BarCode activity) {
            mActivity = new WeakReference<BarCode>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            //BarcodeActivity theActivity = mActivity.get();
            switch (msg.what) {

                case Global.CMD_POS_SETBARCODERESULT: {
                    int result1 = msg.arg1;
                    Log.v(TAG, "Result: " + result1);
                    if(result1 == 1) {
                        PluginResult result = new PluginResult(PluginResult.Status.OK);
                        result.setKeepCallback(false);
                        barcodeCallbackContext.sendPluginResult(result);
                    }
                    else
                    {
                        PluginResult result = new PluginResult(PluginResult.Status.ERROR);
                        result.setKeepCallback(false);
                        barcodeCallbackContext.sendPluginResult(result);
                    }
                    break;
                }

            }
        }
    }


}
