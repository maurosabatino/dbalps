/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
   (function ($) {
  $(document).ready(function(){
    
	// hide .navbar first

	
	// fade in .navbar
	$(function () {
		$(window).scroll(function () {
            // set distance user needs to scroll before we fadeIn navbar
			if ($(this).scrollTop() < 100) {
				$('.header').fadeIn();
			} else {
					$(".header").hide();
			}
		});

	
	});

});
  }(jQuery));