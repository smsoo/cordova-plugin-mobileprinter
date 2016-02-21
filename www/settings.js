
var settings = {
"plaintext" :{
               "plaintext": "Hello World 123",
               "type": {
                                  "GBK": "GBK" ,
                                  "UTF8": "UTF8" ,
                                  "euckr": "euckr"
                        }
               },
"barcode" :{
               "barcode": "123456789012",
               "barcodeType": {
                   					 "UPC_A": 0 ,
                   					 "UPC_E": 1 ,
                   					 "EAN_13": 2 ,
                                     "EAN_8": 3 ,
                   					 "CODE_39": 4 ,
                   					 "ITF": 5 ,
                                     "CODEBAR": 6 ,
                   					 "CODE_93": 7 ,
                   					 "CODE_128": 8
                   			},
               "startPosition": {
                   					 "No_indent": 0 ,
                                     "Indent_1_character": 1 ,
                                     "Indent_2_character": 2 ,
                                     "Indent_3_character": 3 ,
                                     "Indent_4_character": 4 ,
                                     "Indent_5_character": 5 ,
                                     "Indent_6_character": 6 ,
                                     "Indent_7_character": 7,
                                     "Indent_8_character": 8 ,
                                     "Indent_9_character": 9 ,
                                     "Indent_10_character": 10 ,
                                     "Indent_11_character": 11 ,
                                     "Indent_12_character": 12 ,
                                     "Indent_13_character": 13 ,
                                     "Indent_14_character": 14 ,
                                     "Indent_15_character": 15 ,
                                     "Indent_16_character": 16
                   			},
               "barcodeWidth": {
                   					 "Barcode_width_2": 0 ,
                   					 "Barcode_width_3": 1 ,
                   					 "Barcode_width_4": 2 ,
                   					 "Barcode_width_5": 3 ,
                   					 "Barcode_width_6": 4
                   			},
               "barcodeHeight":{
                   					 "Barcode_height_24_point": 0 ,
                   					 "Barcode_height_48_point": 1 ,
                   					 "Barcode_height_72_point": 2 ,
                   					 "Barcode_height_96_point": 3 ,
                   					 "Barcode_height_120_point": 4,
                   					 "Barcode_height_144_point": 5 ,
                   					 "Barcode_height_168_point": 6 ,
                   					 "Barcode_height_192_point": 7
                   			},
               "barcodeTextFontType":{
                   					 "Standard_font": 0 ,
                   					 "Compress_font": 1 ,
                   					 "No_assign": 2
                   			},
               "barcodeTextPosition":{
                   					 "No_print_barcode_text": 0 ,
                   					 "Print_upper_barcode": 1 ,
                   					 "Print_under_barcode": 2 ,
                   					 "Barcode_upper_and_under_printing": 3
                   			}
               },

"qrcode" :{
               "qrcode": "Welcome to use",


               "qrcodeType": {
                   		     "QR_code": 0
                   			},
               "qrcodeWidth": {
                   		     "QR_code_width_2": 0 ,
                                     "QR_code_width_3": 1 ,
                                     "QR_code_width_4": 2 ,
                                     "QR_code_width_5": 3 ,
                                     "QR_code_width_6": 4
                   			},
               "startPosition": {
                   		     "No_indent": 0 ,
                                     "Indent_1_character": 1 ,
                                     "Indent_2_character": 2 ,
                                     "Indent_3_character": 3 ,
                                     "Indent_4_character": 4 ,
                                     "Indent_5_character": 5 ,
                                     "Indent_6_character": 6 ,
                                     "Indent_7_character": 7 ,
                                     "Indent_8_character": 8 ,
                                     "Indent_9_character": 9 ,
                                     "Indent_10_character": 10 ,
                                     "Indent_11_character": 11 ,
                                     "Indent_12_character": 12 ,
                                     "Indent_13_character": 13 ,
                                     "Indent_14_character": 14 ,
                                     "Indent_15_character": 15 ,
                                     "Indent_16_character": 16
                   			},
               "errorLevel": {
                   					 "Error_level_1": 0 ,
                   					 "Error_level_2": 1 ,
                   					 "Error_level_3": 2 ,
                   					 "Error_level_4": 3
                   			},
               "size":{
                   					 "Size_0": 0 ,
                   					 "Size_1": 1 ,
                   					 "Size_2": 2 ,
                   					 "Size_3": 3 ,
                   					 "Size_4": 4,
                   					 "Size_5": 5 ,
                   					 "Size_6": 6 ,
                   					 "Size_7": 7,
                                     "Size_8": 8 ,
                   					 "Size_9": 9 ,
                   					 "Size_10": 10 ,
                   					 "Size_11": 11,
                   					 "Size_12": 12 ,
                   					 "Size_13": 13 ,
                   					 "Size_14": 14 ,
                   					 "Size_15": 15
                   			},
               "useEpsonCommand":{
                   					 "False": false ,
                   					 "True": true
                   			}
               }

};



module.exports = settings;