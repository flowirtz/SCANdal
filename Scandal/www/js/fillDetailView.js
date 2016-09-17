
function fillDetailView(jsonfile) {
    var rawhtml = "<section id='details' style='display:none'> <style>.demo-card-event.mdl-card{width: 100%; height: 176px; background: #3E4EB8;}.demo-card-event > .mdl-card__actions{border-color: rgba(255, 255, 255, 0.2);}.demo-card-event > .mdl-card__actions{display: flex; box-sizing:border-box; align-items: center;}.demo-card-event > .mdl-card__actions > .material-icons{padding-right: 10px;}.demo-card-event > .mdl-card__title, .demo-card-event > .mdl-card__actions, .demo-card-event > .mdl-card__actions > .mdl-button{color: #fff;}</style> <div class='demo-card-event mdl-card mdl-shadow--2dp' onclick='window.open('> <div class='mdl-card__title mdl-card--expand' id='header_card'> <h2 id='d-title' style='position: relative; bottom: -10px;'> Ditsch</h2> <p id='d-address' style='position: absolute; bottom: 0;'>Berlin central station<br>10557 Berlin</p></div></div><table class='mdl-data-table mdl-js-data-table' style='width: 100%'> <thead> <tr> <th class='mdl-data-table__cell--non-numeric'>Product</th> <th>Price</th> </tr></thead> <tbody> <tr id='removeme'></tr><tr style='font-weight: bold;'> <td class='mdl-data-table__cell--non-numeric'>TOTAL</td><td id='d-total'>73</td></tr></tbody> </table></section>";
    var parsedHTML = $.parseHTML(rawhtml);

    $(parsedHTML).find("#d-title").text(jsonfile.storename);
    $(parsedHTML).find("#d-address").html("Z&uuml;rich HB<br>8001 Z&uuml;rich");
    $(parsedHTML).find("#d-total").text(jsonfile.total + " CHF");

    //Fill the fuckin table
    var products = "";
    var origItem = $.parseHTML("<tr><td class='mdl-data-table__cell--non-numeric' id='d-name'>John Lennon</td><td id='d-val'>40</td></tr>");

    for(var i=0; i<jsonfile.products.length; i++) {
      var prod = jsonfile.products[i];

      $(origItem).find("#d-name").text(prod.name);
      $(origItem).find("#d-val").text(prod.price + " CHF");

      products += origItem[0].outerHTML;


    }

    var str = parsedHTML[0].outerHTML.replace('<tr id="removeme"></tr>', products);
    //var str = parsedHTML.stringify('outerHTML').replace("{{d-items}}", products);;



    $("#insertItHere").html(str);


    showDetails("hallo");

    if(count >= 2){
      $("#coupon").show();
    }
}
