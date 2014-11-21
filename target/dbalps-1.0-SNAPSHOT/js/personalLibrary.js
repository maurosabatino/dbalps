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

    
});
