angular.module('app', ['onsen']);

angular.module('app').controller('AppController', function ($scope) {

   var picker = null;
   var margins = [0, 0, 0, 0];
   var landscapeMargins = [0, 0, 0, 0];
   var callback = null;
   $scope.ready = false;
   document.addEventListener('deviceready', function () { 
        $scope.ready = true;
        // required for windows. Without it, we can't register backbutton 
        // handling for full-screen pickers.
        ons.disableDeviceBackButtonHandler();
   });
   $scope.scannedCode = '';
   $scope.paused = false;
   $scope.startWhenClosed = false;
   $scope.isPickerActive = false;

   $scope.startPicker = function (customSettings) {
       if ($scope.isPickerActive) {
           $scope.startWhenClosed = true;
           return;
       }
       $scope.paused = false;
       var settings = getScanSettings();
       if (customSettings) {
           customSettings(settings);
       }
       picker = initPicker(settings, margins, landscapeMargins);
       applyUISettings(picker);
       picker.show({
           didScan: function (session) { callback(session, false) },
           didManualSearch: function (enteredData) { callback(enteredData, true) },
           didChangeState: function (newState) {
               $scope.isPickerActive = newState !== Scandit.BarcodePicker.State.STOPPED;
               if ($scope.isPickerActive) return;
               $scope.$apply(function() {
                    $scope.paused = false;
               });

               if ($scope.startWhenClosed) {
                   $scope.startWhenClosed = false;
                   setTimeout(function () {
                       $scope.startPicker();
                   }, 1);
               }
           }
       });
       picker.startScanning();
   };

   $scope.pausePicker = function () {
       picker.pauseScanning();
       $scope.paused = true;
   };

   $scope.resumePicker = function () {
       picker.resumeScanning();
       $scope.paused = false;
   };

   $scope.stopPicker = function () {
       if (picker !== null) {
           $scope.startWhenClosed = false;
           picker.cancel();
       }
   };

   $scope.setMargin = function (marginLeft, marginTop, marginRight, marginBottom) {
       margins = [marginLeft, marginTop, marginRight, marginBottom];
   };

   $scope.setLandscapeMargin = function (marginLeft, marginTop, marginRight, marginBottom) {
       landscapeMargins = [marginLeft, marginTop, marginRight, marginBottom];
   };

   $scope.setCallback = function (cb) {
        callback = cb;
   };

   $scope.continueScanning = function () {
       var scanResults = document.getElementById('scanResults');
       scanResults.style.display = 'none';
   };
   
   $scope.onTabChange = function () {
       if (mainTabbar.getActiveTabIndex() === 0) {
           loadTabHome($scope);
       } else if (mainTabbar.getActiveTabIndex() === 1) {
           loadTabSettings($scope);
       } else if (mainTabbar.getActiveTabIndex() === 2) {
           loadTabPickers($scope);
       }
   };

});


function initPicker(scanSettings, margins, lMargins) {
    Scandit.License.setAppKey("--- ENTER YOUR SCANDIT APP KEY HERE ---");
    
    // Instantiate the barcode picker by using the settings defined above.
    var picker = new Scandit.BarcodePicker(scanSettings);
    if ((parseInt(margins[0])>0) || (parseInt(margins[1])>0)
        || (parseInt(margins[2])>0) || (parseInt(margins[3])>0)
        || (parseInt(lMargins[0])>0) || (parseInt(lMargins[1])>0)
        || (parseInt(lMargins[2])>0) || (parseInt(lMargins[3])>0)) {
        
        var portraitConstraints = new Scandit.Constraints();
        portraitConstraints.leftMargin = margins[0];
        portraitConstraints.topMargin = margins[1];
        portraitConstraints.rightMargin = margins[2];
        portraitConstraints.bottomMargin = margins[3];
        
        var landscapeConstraints = new Scandit.Constraints();
        landscapeConstraints.leftMargin = lMargins[0];
        landscapeConstraints.topMargin = lMargins[1];
        landscapeConstraints.rightMargin = lMargins[2];
        landscapeConstraints.bottomMargin = lMargins[3];
        
        picker.setConstraints(portraitConstraints, landscapeConstraints);
    }
    // when continuous mode is false, scanning stops after the first code
    // has been successfully scanned. Set this property to true to continue
    // scanning. In that case it is up to you to stop/pause the scanning
    // process using PICKER.pauseScanning(), or PICKER.resumeScanning().
    picker.continuousMode = true;
    return picker;
}



(function () {
 "use strict";
 
 document.addEventListener( 'deviceready', onDeviceReady.bind( this ), false );
 
 function onDeviceReady() {
 // Handle the Cordova pause and resume events
 document.addEventListener( 'pause', onPause.bind( this ), false );
 document.addEventListener( 'resume', onResume.bind( this ), false );
 
 };
 
 function onPause() {
 };
 
 function onResume() {
 };
 } )();
