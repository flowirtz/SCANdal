document.addEventListener("deviceready", onDeviceReady, false);

curData = null;
buyCallback = null;

function deleteTables() {
    db.transaction(function (tx) {
        tx.executeSql('DROP TABLE IF EXISTS BUY');
        tx.executeSql('DROP TABLE IF EXISTS ITEM');
    }, errorCB, successCB);   
}

function createTables() {
    db.transaction(function (tx) {
        tx.executeSql('CREATE TABLE IF NOT EXISTS BUY (id INTEGER PRIMARY KEY NOT NULL, storename, date DATETIME, store_id INT, total FLOAT)');
        tx.executeSql('CREATE TABLE IF NOT EXISTS ITEM (id INTEGER PRIMARY KEY NOT NULL, buy_id, name, store_id, item_group, ean INT, price FLOAT)');
    }, errorCB);

    return 1;
}

function insertBuy(data, callback) {
    curData = data;
    buyCallback = callback;

    db.transaction(function (tx) {
        console.log(JSON.stringify(data));
        tx.executeSql('INSERT INTO BUY (date, store_id, storename, total) VALUES (?, ?, ?,?)', [new Date(), data.store_id, data.storename, data.total], insertBuyItems, errorCB);
    }, errorCB);
}

function insertBuyItems(tx, results) {
    console.log('add ' + curData.products.length + ' product(s) to buy with ID ' + results.insertId);
    for (i = 0; i < curData.products.length; i++) {
        p = curData.products[i];
        console.log('add ' + p.name);
        tx.executeSql('INSERT INTO ITEM (buy_id, price, name, store_id, item_group) VALUES (?, ?, ?, ?, ?)', [results.insertId,
            p.price, p.name, p.store_id, p.item_group], function () { }, errorCB);
    }
    buyCallback(results.insertId);
}


function readBuys(callback) {
    db.transaction(function (tx) {
        tx.executeSql('SELECT B.* FROM BUY B', [], callback, errorCB);
    }, errorCB);
}

function readItems(buy_id, callback) {
    db.transaction(function (tx) {
        tx.executeSql('SELECT I.* FROM ITEM I WHERE buy_id = ?', [buy_id], callback, errorCB);
    }, errorCB);
}

function querySuccess(tx, results) {
    console.log('we are her: ' + results.rows.length);
    for (i = 0; i < results.rows.length; i++) {
        console.log(results.rows.item(i).id);
        console.log(results.rows.item(i).date);
        console.log(results.rows.item(i).total);
    }
}

function itemsRead(tx, results) {
    console.log('items: ' + results.rows.length);
    for (i = 0; i < results.rows.length; i++) {
        console.log(results.rows.item(i).id);
        console.log(results.rows.item(i).name);
        console.log(results.rows.item(i).price);
    }
}

function getBuys() {
    // hier wird eine Liste von Käufen zurückgeliefert

}

// dummy data

function insertBuyData() {
    myData = JSON.parse('{"store_id": "18406","storename": "Moin", "products": [{"name": "HARIBO GOLDBAEREN 200G BTL.","price": "3.25","retail_store_id": "18406","item_group": "F02022"},{"name": "ok.- ICE TEA ZITRONE 50 CL PET","price": "1.5","retail_store_id": "18406","item_group": "F08013"},{"name": "STARBUCKS TO GO","price": "4.7","retail_store_id": "18406","item_group": "F08071"},{"name": "CROISSANT FRANC.","price": "1.45","retail_store_id": "18406","item_group": "F06021"},{"name": "2-STRANG BUTTERZOEPFLI TK 100G","price": "1.65","retail_store_id": "18406","item_group": "F06022"}],"total": "20.53"}');
    insertBuy(myData, buyInserted);
}

function buyInserted(result) {
    console.log(result);
}

function insertRow(tx) {
    tx.executeSql('INSERT INTO BUY (date, store_id) VALUES (?, ?)', [new Date(), 17400], insertItems, errorCB);
}

function insertItems(tx, results) {
    for (i = 0; i < 3; i++) {
        var rand = Math.random();
        rand = Math.round(rand * 10000);
        var price = Math.random() * 10;
        tx.executeSql('INSERT INTO ITEM (buy_id, ean, price) VALUES (?, ?, ?)', [results.insertId, rand, price], null, errorCB);
    }
}

function onDeviceReady() {
    
}

// Transaction error callback
//
function errorCB(tx, err) {
    alert("Error processing SQL: " + err.message);
}

// Transaction success callback
//
function successCB() {
    alert("success!");
}
