
function scanning(){
	Scandit.License.setAppKey("ptxGMCdm5Miis76+JubgFmEULaZUmWbdajgz9zTFn88");

	var settings = new Scandit.ScanSettings();
	settings.setSymbologyEnabled(Scandit.Barcode.Symbology.QR, true);

	var picker = new Scandit.BarcodePicker(settings);
	
	picker.show(success, null, failure);
	picker.startScanning();
}

function success(session){
	alert("Scanned " + session.newlyRecognizedCodes[0].data);
}

function failure(error){
	alert("error");
}

