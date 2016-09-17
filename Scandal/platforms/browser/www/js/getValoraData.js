USER = 'HackZurich';
PW = 'mKw%VY<7.Yb8D!G-';

//this function's arguments are 1. what is hardcoded as scanditResponse now + 2. a callback function from florian, which starts displaying the processedinformation
function searchForProducts() {
  var scanditResponse = '{"store-id": "18406", "products": ["9002975301268", "7610469295645", "2050000719073", "2050000771606", "5937"],  "total": "20.53"}'; //as given to me by Emanuel / Scandit

  var scanditResponseJson = JSON.parse(scanditResponse);

  processedResponse = JSON.parse(JSON.stringify(scanditResponseJson));

  for(var j = 0; j < scanditResponseJson.products.length; j++) {
    processedResponse.products[j] = null;
  }

  var currentStore = "18406";

  for(var i = 0; i < scanditResponseJson.products.length; i++) {
    getProductInfo(currentStore, scanditResponseJson.products[i], i);
  }
}

function getProductInfo(storeId, productId, index) {
  console.log("got this: " + storeId + productId + index);
  document.getElementById("infoGoesHere").innerHTML = "wow this works";

  var request = new XMLHttpRequest();

  request.open("GET", "https://backend.scango.ch/api/v01/items/find-by-ean/?ean=" + productId + "&retail_store_id=" + storeId + "&format=json", true);

  request.onreadystatechange = function() {
    console.log("Response received, readyState: " + request.readyState + " status: " + request.status);

    if(request.readyState == 4) {
      if(request.status == 200 || request.status == 0) {
        document.getElementById("infoGoesHere").innerHTML = request.responseText;
        var returnedJson = JSON.parse(request.responseText);

        var filteredJson = JSON.parse('{"name": "' + returnedJson.name + '", "price": "' + returnedJson.current_price.price + '", "retail_store_id": "' + storeId + '", "item_group": "' + returnedJson.item_group + '"}');

        console.log(JSON.stringify(filteredJson)); //result for current productid

        processedResponse.products[index] = filteredJson;

        for(var h = 0; h < processedResponse.products.length; h++) {
          if(processedResponse.products[h] == null) {
            return;
          }
        }

        //product infos for all array elements have been loaded from api
        if(index = processedResponse.products.length - 1) {
            doDominiksStuff();
        }

        //OLD SOLUTION
        // if(index == processedResponse.products.length - 1) { //last array element has been reached, looking up product information has been finished for this receipt
        //   doDominiksStuff();
        // }
      }
    }
  }

  request.setRequestHeader("Authorization", "Basic " + btoa(USER + ":" + PW));

  request.send();
  console.log("Request sent...")
}

function doDominiksStuff() {
  console.log("this is it:" + JSON.stringify(processedResponse));
}
