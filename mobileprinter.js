   var exec = require('cordova/exec'),
       settings = require('./settings');

     var mobileprinter = {
               connectBT:function(successCallback, failureCallback, message) {

                  exec(successCallback,failureCallback,"BluetoothPrint","connect",message);
              },

               printPlainText:function(successCallback, failureCallback, message) {

                  exec(successCallback,failureCallback,"BluetoothPrint","printPlainText", message);
              },

              printBarCode:function(successCallback, failureCallback, message) {

                  exec(successCallback,failureCallback,"BluetoothPrint","printBarCode",message);
              },

              printQRCode:function(successCallback, failureCallback, message) {

                  exec(successCallback,failureCallback,"BluetoothPrint","printQRCode",message);
              },

              disconnectBT:function(successCallback, failureCallback, message) {

                  exec(successCallback,failureCallback,"BluetoothPrint","disconnect",message);
              }

          };

       module.exports = mobileprinter;