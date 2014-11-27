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
    
    /*$("#login").submit(function(){
      $.post('Servlet',$(this).serialize(),function (data) {
                window.location = 'index.jsp';
                $("#loginStatus").innerHTML=data;
                });
    });*/
    
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
