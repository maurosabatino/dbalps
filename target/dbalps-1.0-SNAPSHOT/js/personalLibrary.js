/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    $("#buttonIT").click(function () {
        $.ajax({
            url: 'Servlet',
            type: 'POST',
            data: {operazione: 'changeLanguage', loc: 'it-IT'},
            success: function () {
            window.location.reload();
            }
        });
    });
    $("#buttonENG").click(function () {
        $.ajax({
            url: 'Servlet',
            type: 'POST',
            data: {operazione: 'changeLanguage', loc: 'en-US'},
            success: function () {
               window.location.reload();
            }
        });
    });
    
    $("#home").click(function (){
        window.location = 'index.jsp';
    });
    
    $("#loginButton").click(function(){
        var usr =  $("#username").val();
        var pwd =$("#password").val();
         $.ajax({
            url: 'Servlet',
            type: 'GET',
            data: {operazione: 'login', username: usr,password : pwd},
            dataType: "json",
            success: function (data) {
                if(data==true){
                     window.location.reload();
                }
                else {
                    alert("Error Login");
                    window.location = 'index.jsp';
                }
            }
        });
    });
    
    $("#logout").click(function(){
        $.ajax({
            url: 'Servlet',
            type: 'POST',
            data: {operazione: 'logout'},
            success: function () {
               window.location = 'index.jsp';
            }
        });
    });
   
   

    
});
