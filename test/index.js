/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
  // var settings = require('../plugins/cordova-plugin-mobileprinter/settings.js');

var app = {
    // Application Constructor
    initialize: function() {
        this.bindEvents();
    },
    // Bind Event Listeners
    //
    // Bind any events that are required on startup. Common events are:
    // 'load', 'deviceready', 'offline', and 'online'.
    bindEvents: function() {
        document.addEventListener('deviceready', this.onDeviceReady, false);
    },
    // deviceready Event Handler
    //
    // The scope of 'this' is the event. In order to call the 'receivedEvent'
    // function, we must explicitly call 'app.receivedEvent(...);'
    onDeviceReady: function() {
        app.receivedEvent('deviceready');
		var btnConnect = document.getElementById("btnConnect");
        btnConnect.addEventListener('click',callConnect, false);
        var btnPrintPlainText = document.getElementById("btnPrintPlainText");
        btnPrintPlainText.addEventListener('click',callPrintPlainText, false);
        var btnPrintBarCode = document.getElementById("btnPrintBarCode");
        btnPrintBarCode.addEventListener('click',callPrintBarCode, false);
        var btnPrintQRCode = document.getElementById("btnPrintQRCode");
        btnPrintQRCode.addEventListener('click',callPrintQRCode, false);
        var btnDisconnect = document.getElementById("btnDisconnect");
        btnDisconnect.addEventListener('click',callDisconnect, false);


    },
    // Update DOM on a Received Event
    receivedEvent: function(id) {
        var parentElement = document.getElementById(id);
        var listeningElement = parentElement.querySelector('.listening');
        var receivedElement = parentElement.querySelector('.received');

        listeningElement.setAttribute('style', 'display:none;');
        receivedElement.setAttribute('style', 'display:block;');

        console.log('Received Event: ' + id);
    }
};

function success(data){
    alert("Response from plug-in: "+ data);
};

function failure(data){
    alert("Response from plug-in: "+ data);
};

function callConnect() {
    var msg = 0; //the index (zero-based) of the bluetooth printer in the list of paired bluetooth devices.
    navigator.mobileprinter.connectBT(success, failure, [msg]);
}

function callDisconnect() {
    var msg = "";
    navigator.mobileprinter.disconnectBT(success, failure, [msg]);
}

function callPrintPlainText() {
    var arrmessage = [];
    arrmessage.push(settings.plaintext.plaintext);
    arrmessage.push(settings.plaintext.type.UTF8);

    navigator.mobileprinter.printPlainText(success, failure, arrmessage);
}

function callPrintBarCode() {
    var arrmessage = [];
    arrmessage.push(settings.barcode.barcode);
    arrmessage.push(settings.barcode.barcodeType.UPC_A);
    arrmessage.push(settings.barcode.startPosition.No_indent);
    arrmessage.push(settings.barcode.barcodeWidth.Barcode_width_2);
    arrmessage.push(settings.barcode.barcodeHeight.Barcode_height_96_point);
    arrmessage.push(settings.barcode.barcodeTextFontType.Standard_font);
    arrmessage.push(settings.barcode.barcodeTextPosition.Barcode_upper_and_under_printing);

    navigator.mobileprinter.printBarCode(success, failure, arrmessage);
}


function callPrintQRCode() {
    var arrmessage = [];
    arrmessage.push(settings.qrcode.qrcode);
    arrmessage.push(settings.qrcode.qrcodeType.QR_code);
    arrmessage.push(settings.qrcode.qrcodeWidth.QR_code_width_6);
    arrmessage.push(settings.qrcode.errorLevel.Error_level_4);
    arrmessage.push(settings.qrcode.size.Size_4);
    arrmessage.push(settings.qrcode.useEpsonCommand.False);

    navigator.mobileprinter.printQRCode(success, failure, arrmessage);
}
app.initialize();