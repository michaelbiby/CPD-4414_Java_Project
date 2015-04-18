/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function() {

    var mainLetter = "";
    var returnString = "";

    var url = './biby/word/';
    
    $.getJSON(url, function(data) {
        if (!jQuery.isEmptyObject(data)) {
            mainLetter = data.name.toUpperCase();
            returnString = mainLetter.substring(0,1);
            for(var i=0; i < mainLetter.length-1; i++){
                returnString = returnString + "*";
            }
            $('#letterLabel').text(returnString);
        }
    });

    function setCharAt(str, index, chr) {
        if (index > str.length - 1)
            return str;
        return str.substr(0, index) + chr + str.substr(index + 1);
    }

    $('#game').click(function() {
        var myInput = $('#myInput').val();
        myInput = myInput.toUpperCase();
        var indexOfLetter = mainLetter.indexOf(myInput);
        //alert(mainLetter);
        //alert(indexOfLetter);
        if (indexOfLetter > -1) {
            returnString = setCharAt(returnString, indexOfLetter, myInput);
            $('#errorMsg').text("");
            $('#myInput').val("");
            if (mainLetter === returnString) {
                $('#workingArea').text("CONGRATULATIONS YOU WON!!!");
            }
        } else {
            $('#errorMsg').text("Wrong Letter");
        }
        $('#letterLabel').text(returnString);
    });


    $('#newGame').click(function() {
        window.location.href = "./index.html";
    });


});
