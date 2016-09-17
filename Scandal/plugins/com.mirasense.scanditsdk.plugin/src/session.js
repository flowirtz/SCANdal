
function ScanSession(newlyRecognizedCodes, newlyLocalizedCodes, allRecognizedCodes, picker) {
	this.newlyRecognizedCodes = newlyRecognizedCodes;
	this.newlyLocalizedCodes = newlyLocalizedCodes;
	this.allRecognizedCodes = allRecognizedCodes;
	this.picker = picker;
	this.rejectedCodes = [];
}

ScanSession.prototype.stopScanning = function() {
	this.picker.stopScanning();
}

ScanSession.prototype.pauseScanning = function() {
	this.picker.pauseScanning();
}

ScanSession.prototype.rejectCode = function(code) {
	this.rejectedCodes.push(code.uniqueId);
}

module.exports = ScanSession;
