function populateOverview() {
  //TODO haha
  //Steps:

  //1. GET data
  insertBuyData(); //TODO remove

  readBuys(function(tx, results) {
    for (i = 0; i < results.rows.length; i++) {
        console.log(results.rows.item(i).id);
        console.log(results.rows.item(i).date);
        console.log(results.rows.item(i).total);

        //2. GET template



    }
  });
  //3. iterate over data inserting it into Overview
}

function createDay(day) {

}

function populateDetails(detail_id) {
  //TODO have fun. :/
}
