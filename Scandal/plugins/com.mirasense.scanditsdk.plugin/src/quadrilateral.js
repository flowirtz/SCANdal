
var Point = cordova.require("com.mirasense.scanditsdk.plugin.Point");


function Quadrilateral(topLeft, topRight, bottomLeft, bottomRight) {
	if (topLeft instanceof Point && topRight instanceof Point
			&& bottomLeft instanceof Point && bottomRight instanceof Point) {
    	this.topLeft = topLeft;
		this.topRight = topRight;
		this.bottomLeft = bottomLeft;
		this.bottomRight = bottomRight;
	}
}


module.exports = Quadrilateral;