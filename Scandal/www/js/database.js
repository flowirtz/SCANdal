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
    tx.executeSql('DROP TABLE BUYS');
    tx.executeSql('DROP TABLE ITEMS');
}

function createTables(tx) {
    tx.executeSql('CREATE TABLE IF NOT EXISTS BUYS (id INTEGER PRIMARY KEY NOT NULL, date DATETIME, price FLOAT)');
    tx.executeSql('CREATE TABLE IF NOT EXISTS ITEMS (id INTEGER PRIMARY KEY NOT NULL, buyId INT, ean INT)');
}

function insertRow(tx) {
    tx.executeSql('INSERT INTO BUYS (date, price) VALUES (?, 1.5)', [new Date()]);
}

function readBuys(tx) {
    tx.executeSql('SELECT * FROM BUYS', [], querySuccess, errorCB);
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
        console.log(results.rows.item(i).date);
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