package com.solution.mobileprinter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lvrenyang.myprinter.WorkService;
import com.lvrenyang.myprinter.Global;
import com.lvrenyang.utils.DataUtils;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;

/**
 * Created by sm.soo on 02/02/2016.
 */
public class PlainText{

    private static Handler mHandler = null;
    private static String TAG = "PlainTextActivity";
    private static CallbackContext plainTextCallbackContext = null;

    public PlainText() {
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
                if (WorkService.workThread.isConnected()) {

                    String text =  args.getString(0) + "\r\n\r\n\r\n"; // 加三行换行，避免走纸
                    String type =  args.getString(1);
                    byte header[] = null;
                    byte strbuf[] = null;

                    if (type.equals("GBK")) {
                        // 设置GBK编码
                        // Android手机默认也是UTF8编码
                        header = new byte[] { 0x1b, 0x40, 0x1c, 0x26, 0x1b, 0x39,
                                00 };
                        try {
                            strbuf = text.getBytes("GBK");
                        } catch (UnsupportedEncodingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else if (type.equals("UTF8")) {

                                // 设置UTF8编码
                        // Android手机默认也是UTF8编码
                        header = new byte[] { 0x1b, 0x40, 0x1c, 0x26, 0x1b, 0x39,
                                0x01 };
                        try {
                            strbuf = text.getBytes("UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else if (type.equals("euckr")) {
                        header = new byte[] { 0x1b, 0x40, 0x1c, 0x26, 0x1b, 0x39,
                                0x05 };
                        try {
                            strbuf = text.getBytes("euc-kr");
                        } catch (UnsupportedEncodingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    byte buffer[] = DataUtils.byteArraysToBytes(new byte[][]{
                            header, strbuf});
                    Bundle data = new Bundle();
                    data.putByteArray(Global.BYTESPARA1, buffer);
                    data.putInt(Global.INTPARA1, 0);
                    data.putInt(Global.INTPARA2, buffer.length);
                    WorkService.workThread.handleCmd(Global.CMD_POS_WRITE, data);

                    this.plainTextCallbackContext = callbackContext;

                    PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
                    pluginResult.setKeepCallback(true);
                    callbackContext.sendPluginResult(pluginResult);

                    /*final String responseText = "PlainText: Queueing... " + args.getString(0);
                    callbackContext.success(responseText);*/

                } else {
                    callbackContext.error("No connection");
                }

            } catch (JSONException e){
                callbackContext.error("Failed to parse parameters");
            }
            return true;
    }

    static class MHandler extends Handler {

        WeakReference<PlainText> mActivity;

        MHandler(PlainText activity) {
            mActivity = new WeakReference<PlainText>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            //PlainText theActivity = mActivity.get();
            switch (msg.what) {

                case Global.CMD_POS_WRITERESULT: {
                    int result1 = msg.arg1;
                    Log.v(TAG, "Result: " + result1);
                    if(result1 == 1) {
                        PluginResult result = new PluginResult(PluginResult.Status.OK);
                        result.setKeepCallback(false);
                        plainTextCallbackContext.sendPluginResult(result);
                    }
                    else
                    {
                        PluginResult result = new PluginResult(PluginResult.Status.ERROR);
                        result.setKeepCallback(false);
                        plainTextCallbackContext.sendPluginResult(result);
                    }
                    break;
                }

            }
        }
    }

}
