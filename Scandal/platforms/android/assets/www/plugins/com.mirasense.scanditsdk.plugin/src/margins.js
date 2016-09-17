cordova.define("com.mirasense.scanditsdk.plugin.Margins", function(require, exports, module) {

function Margins(left, top, right, bottom) {
	this.left = left;
	this.top = top;
	this.right = right;
	this.bottom = bottom;
}

module.exports = Margins;

});
