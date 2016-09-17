cordova.define("com.mirasense.scanditsdk.plugin.License", function(require, exports, module) {

function License() {}

License.setAppKey = function(appKey) {
	cordova.exec(null, null, "ScanditSDK", "initLicense", [appKey]);
}

module.exports = License

});
