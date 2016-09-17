
count = 0;

function scanning(){

	Scandit.License.setAppKey("ptxGMCdm5Miis76+JubgFmEULaZUmWbdajgz9zTFn88");

	var settings = new Scandit.ScanSettings();
	settings.setSymbologyEnabled(Scandit.Barcode.Symbology.QR, true);

	var picker = new Scandit.BarcodePicker(settings);
	
	picker.show(success, null, failure);
	picker.startScanning();
	console.log("Moin");
}

function success(session){
	var receiptData = session.newlyRecognizedCodes[0].data;

	count++;
	
	searchForProducts(JSON.parse(receiptData.replace(/\'/g, '\"')));
}

function failure(error){
	alert("error");
}

