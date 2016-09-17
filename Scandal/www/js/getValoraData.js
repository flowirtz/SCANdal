USER = 'HackZurich';
PW = 'mKw%VY<7.Yb8D!G-';

function getProductInfo() {
  document.getElementById("infoGoesHere").innerHTML = "wow this works";

  var request = new XMLHttpRequest();

  request.open("GET", "https://backend.scango.ch/api/v01/items/find-by-ean/?ean=9002975301268&retail_store_id=18406&format=json", true);

  request.onreadystatechange = function() {
    console.log("Response received, readyState: " + request.readyState + " status: " + request.status);
    if(request.readyState == 4) {
      if(request.status == 200 || request.status == 0) {
        document.getElementById("infoGoesHere").innerHTML = request.responseText;
      }
    }
  }

  request.setRequestHeader("Authorization", "Basic " + btoa(USER + ":" + PW));

  request.send();
  console.log("Request sent...")
}
