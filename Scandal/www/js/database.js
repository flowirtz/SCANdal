function modifyDB(trans) {
    if (db != undefined) {
        db.transaction(trans, errorCB, successCB);
    }
}

function queryDB(trans) {
    if (db != undefined) {
        db.transaction(trans, errorCB);
    }
}

function deleteTables(tx) {
    tx.executeSql('DROP TABLE IF EXISTS BUY');
    tx.executeSql('DROP TABLE IF EXISTS ITEM');
}

function createTables(tx) {
    tx.executeSql('CREATE TABLE IF NOT EXISTS BUY (id INTEGER PRIMARY KEY NOT NULL, date DATETIME, storeId INT)');
    tx.executeSql('CREATE TABLE IF NOT EXISTS ITEM (id INTEGER PRIMARY KEY NOT NULL, buyId INT, ean INT, price FLOAT)');
}

function insertRow(tx) {
    tx.executeSql('INSERT INTO BUY (date, storeId) VALUES (?, ?)', [new Date(), 17400], insertItems, errorCB);
}

function insertItems(tx, results) {
    for (i = 0; i < 3; i++) {
        var rand = Math.random();
        rand = Math.round(rand * 10000);
        var price = Math.random() * 10;
        tx.executeSql('INSERT INTO ITEM (buyId, ean, price) VALUES (?, ?, ?)', [results.insertId, rand, price], null, errorCB);
    }
}

function readBuys(tx) {
    tx.executeSql('SELECT B.*,  FROM BUY B LEFT JOIN ITEM I ON I.buyId = B.id GROUP BY B.id', [], querySuccess, errorCB);
}

function insertBuy(json) {
    // hier wird ein Kauf gespeichert
}

function getBuys() {
    // hier wird eine Liste von Käufen zurückgeliefert
}

// Wait for PhoneGap to load
//
document.addEventListener("deviceready", onDeviceReady, false);

// PhoneGap is ready
//
function onDeviceReady() {
    db = window.openDatabase("db", "1.0", "SCANdal", 200000);
    queryDB(createTables);
}


function querySuccess(tx, results) {
    // this will be empty since no rows were inserted.
    for (i = 0; i < results.rows.length; i++) {
        console.log(results.rows.item(i).id);
        console.log(results.rows.item(i).date);
        console.log(results.rows.item(i).price);
    }
    alert("rows in buys: " + results.rows.length);
}

// Transaction error callback
//
function errorCB(tx, err) {
    alert("Error processing SQL: " + err);
}

// Transaction success callback
//
function successCB() {
    alert("success!");
}