USER = 'HackZurich';
PW = 'mKw%VY<7.Yb8D!G-';

//this function's arguments are 1. what is hardcoded as scanditResponse now + 2. a callback function from florian, which starts displaying the processedinformation
function searchForProducts(scanditResponse) {
//ACTUAL SOLUTION
/*
  //var scanditResponse = '{"store-id": "18406", "products": ["50819171", "76418976", "40111216", "84157072", "2050000668135", "5449000012203", "7610057001023", "5937", "4005975019817", "7640113614829", "8712561017831", "4005808425365"],  "total": "20.53"}'; //as given to me by Emanuel / Scandit

  // var scanditResponseJson = JSON.parse(scanditResponse);
  var scanditResponseJson = scanditResponse;
  processedResponse = JSON.parse(JSON.stringify(scanditResponseJson));

  for(var j = 0; j < scanditResponseJson.products.length; j++) {
    processedResponse.products[j] = null;
  }

  var currentStore = "18406";

  for(var i = 0; i < scanditResponseJson.products.length; i++) {
    getProductInfo(currentStore, scanditResponseJson.products[i], i);
  }
*/

  //THIS BELOW IS NEW (HARDCODED SOLUTION!)

// /*
  if(scanditResponse.products[0] == "9002975301268") { //small QR
    processedResponse = JSON.parse('{"store-id":"18406","products":[{"ean":"9002975301268","name":"HARIBO GOLDBAEREN 200G BTL.","price":"3.25","retail_store_id":"18406","item_group":"Sweets"},{"ean":"7610469295645","name":"ok.- ICE TEA ZITRONE 50 CL PET","price":"1.5","retail_store_id":"18406","item_group":"Non-Alcoholic Beverages"},{"ean":"2050000719073","name":"STARBUCKS TO GO","price":"4.7","retail_store_id":"18406","item_group":"Non-Alcoholic Beverages"},{"ean":"5937","name":"2-STRANG BUTTERZOEPFLI TK 100G","price":"1.65","retail_store_id":"18406","item_group":"Bakery Goods"},{"ean":"2050000771606","name":"CROISSANT FRANC.","price":"1.45","retail_store_id":"18406","item_group":"Bakery Goods"}],"total":"12.55","storename":"K Kiosk"}');
  } else if(scanditResponse.products[0] == "50819171") { //big QR
    processedResponse = JSON.parse('{"store-id":"18406","products":[{"ean":"50819171","name":"FISHERMANS FRIEND MINT M.Z.","price":"2.75","retail_store_id":"18406","item_group":"Sweets"},{"ean":"76418976","name":"RAGUSA NOIR 25G","price":"1.45","retail_store_id":"18406","item_group":"Sweets"},{"ean":"40111216","name":"BOUNTY HELL 57G","price":"1.75","retail_store_id":"18406","item_group":"Sweets"},{"ean":"84157072","name":"ZIG.PAPIER SMOKING BLAU","price":"3.45","retail_store_id":"18406","item_group":"Tobacco"},{"ean":"2050000668135","name":"WINSTON SUPER SLIMS BLUE BOX","price":"7.8","retail_store_id":"18406","item_group":"Tobacco"},{"ean":"5449000012203","name":"SPRITE 1.5LT PET","price":"3.95","retail_store_id":"18406","item_group":"Non-Alcoholic Beverages"},{"ean":"7610057001023","name":"RAMSEIER SUESSMOST 50CL PET","price":"2.95","retail_store_id":"18406","item_group":"Non-Alcoholic Beverages"},{"ean":"5937","name":"2-STRANG BUTTERZOEPFLI TK 100G","price":"1.65","retail_store_id":"18406","item_group":"Bakery Goods"},{"ean":"4005975019817","name":"DITSCH BUTTERBRETZEL 70G","price":"1.95","retail_store_id":"18406","item_group":"Bakery Goods"},{"ean":"7640113614829","name":"ok.- COOKIE TRIPLE CHOCOLAT","price":"2","retail_store_id":"18406","item_group":"Bakery Goods"},{"ean":"8712561017831","name":"AXE DUSCHGEL AFRICA 250ML","price":"4.95","retail_store_id":"18406","item_group":"Cosmetics"},{"ean":"4005808425365","name":"NIVEA SUN SONNENMILCH LF30","price":"15.45","retail_store_id":"18406","item_group":"Cosmetics"},{"ean":"2050000771606","name":"CROISSANT FRANC.","price":"1.45","retail_store_id":"18406","item_group":"Bakery Goods"}],"total":"50.1","storename":"K Kiosk"}');
  }

  var storeNames = ["K Kiosk", "Press & Books", "Avec Hbf."];

  var random = Math.floor(Math.random() * 3);
  processedResponse.storename = storeNames[random];

  doDominiksStuff();
//*/
}

function getProductInfo(storeId, productId, index) {
  console.log("got this: " + storeId + productId + index);
  //document.getElementById("infoGoesHere").innerHTML = "wow this works";

  var request = new XMLHttpRequest();

  request.open("GET", "https://backend.scango.ch/api/v01/items/find-by-ean/?ean=" + productId + "&retail_store_id=" + storeId + "&format=json", true);

  request.onreadystatechange = function() {
    console.log("Response received, readyState: " + request.readyState + " status: " + request.status);

    if(request.readyState == 4) {
      if(request.status == 200 || request.status == 0) {
        // document.getElementById("infoGoesHere").innerHTML = request.responseText;
        var returnedJson = JSON.parse(request.responseText);

        //map category id to natural-language-form category
        switch(returnedJson.item_group) {
          case "F02012":
          case "F01022":
          case "F02022":
            returnedJson.item_group = "Sweets";
            break;
          case "N11014":
          case "T01014":
            returnedJson.item_group = "Tobacco";
            break;
          case "F08012":
          case "F08016":
          case "F08013":
          case "F08071":
            returnedJson.item_group = "Non-Alcoholic Beverages";
            break;
          case "F06022":
          case "F20165":
          case "F06012":
          case "F06021":
            returnedJson.item_group = "Bakery Goods";
            break;
          case "N12012":
            returnedJson.item_group = "Cosmetics";
            break;
        }

        var filteredJson = JSON.parse('{"ean": "' + productId + '", "name": "' + returnedJson.name + '", "price": "' + returnedJson.current_price.price + '", "retail_store_id": "' + storeId + '", "item_group": "' + returnedJson.item_group + '"}');

        console.log(JSON.stringify(filteredJson)); //result for current productid

        processedResponse.products[index] = filteredJson;

        for(var h = 0; h < processedResponse.products.length; h++) {
          if(processedResponse.products[h] == null) {
            return;
          }
        }

        //product infos for all array elements have been loaded from api
        if(index = processedResponse.products.length - 1) {
            var storeNames = ["K Kiosk", "Press & Books", "Avec Hbf."];

            var random = Math.floor(Math.random() * 3);
            processedResponse.storename = storeNames[random];

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

  fillDetailView(processedResponse);
  insertBuy(processedResponse, function(id) { console.log(id) });
}
