
var mock = {sweets: 4.59, tobacco: 4.99, non_alcoholic_beverages: 9.89, bakery_goods: 5.78, cosmetics: 13.99};

function calculateAnalytics(finances){
	var sum = mock.sweets + mock.tobacco + mock.non_alcoholic_beverages + mock.bakery_goods + mock.cosmetics;

	var percentages = [mock.sweets / sum, mock.tobacco / sum, mock.non_alcoholic_beverages / sum, mock.bakery_goods / sum, mock.cosmetics / sum];

	var colors = ["#FF00FF", "000000", "#0000CD", "#F4A460", "#FF0000"];

	var labels = ["Sweets", "Tobacco", "Non Alcoholic Beverages", "Bakery Goods", "Cosmetics"];
}

