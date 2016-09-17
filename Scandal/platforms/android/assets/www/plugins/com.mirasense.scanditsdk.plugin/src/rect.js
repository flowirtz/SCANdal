cordova.define("com.mirasense.scanditsdk.plugin.Rect", function(require, exports, module) {

function Rect(x, y, width, height) {
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
}

module.exports = Rect;
});
