function populateDetails(detail_id) {
    //alert(detail_id);
    readItems(detail_id, function (tx, res) {
        data = '{"store-id": "18406",  "total": "17.13", "products": [';
        for (i = 0; i < res.rows.length; i++) {
            data += (i>0) ? ', ' : '';
            data += '"' + res.rows.name + '"';
        }
        data += ']}';

        fillDetailView(JSON.parse(data));
    })
}
