# SCANdal
Transmitting receipts via qr-codes and saving trees.

## Abstract
Did you ever get bothered by shop clerks, first printing your receipt and asking you afterwhise whether you actually want to keep it?
And if you say 'no', they just throw it away?
<br>Well, we did.

Offering the digital transfer of receipts from the shop counter right onto your phone  we remove the relict of the past of actually printing them.

## Ecological aspects

## How it works
Whenever a customer buys something in a store, the receipt will be made machine-readable by generating a qr-code that then will be displayed on the clerks screen visible to the customer. Our app, being built on the [Adobe Phonegap](http://phonegap.com/), leverages the powerful [Scanner API by Scandit](http://docs.scandit.com/stable/phonegap/index.html) to scan generated qr code that includes the receipt.<br>
Said receipt will then be saved on the users phone and analyzed: Not only does this allow us to present the user with all buys he made and their corresponding receipts in one place but also grant coupons in the form of loyalty programs to him.<br>
Right now, [Valoras API](http://www.autoidlabs.ch/valora-product-and-price-api-hackzurich-documentation/) is used to interpret the information  on the receipt (like retrieving the price for an item) mainly to keep the data on the qr code small but still enrich it and make it appealing to look at. It basically provides information about all stores and items and is the **backbone** of the app.

## Features
- transmit recipes digitally via qr-codes
- engage the user with personalised coupons
- get an overview over your shopping

### Advantages
- less waste
- eco-friendly  
- Coupons
