
function scanning(){

	Scandit.License.setAppKey("ptxGMCdm5Miis76+JubgFp25R5G2tkNfqTwjl7EWg0Q");
	var settings = new Scandit.ScanSettings();
	settings.setSymbologyEnabled(Scandit.Barcode.Symbology.EAN13, true);
	settings.setSymbologyEnabled(Scandit.Barcode.Symbology.UPC12, true);
	settings.setSymbologyEnabled(Scandit.Barcode.Symbology.EAN8, true);

	//BarcodeScanner instantiieren
	var picker = new Scandit.BarcodePicker(settings);

	picker.show(success, null, failure);
	picker.startScanning();
}

function success(session){
	alert("Scanned " + session.newlyRecognizedCodes[0].symbology + " code: " + session.newlyRecognizedCodes[0].data);
	session.stopScanning();
}

function failure(error){
	alert("error");
}