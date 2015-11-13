$(document).ready(function() {
	$('.check_box label').click(function() {
		$(this).prev('.check_box').toggleClass('active');
	});
    $('.check_box input').click(function() {
    	$(this).parent().toggleClass('active');
  	});
    $('.btn_reset_filters').click(function() {
    	$('.check_box input').prop( "checked", false );
    	$('.check_box').removeClass('active');
  	});

    //select "made in sam sklipav"

    $('.item_sort').click(function() {
    	$(this).children('.dropdown_sort').toggleClass('hidden');
    	$(this).toggleClass('active');
    });
    $('.item_sort .dropdown_sort li a').click(function() {
    	$('.item_sort .dropdown_sort li a').removeClass('active');
    	$(this).addClass('active');
    	var valueText = $(this).text();
    	var value = $(this).attr('value');
    	$('#current_value').text(valueText);
    	$('#current_value').attr('value', value);
    });


	$(document).click(function(e){
	  if ($(e.target).closest(".item_sort").length) return;
	  $('.item_sort').children('.dropdown_sort').addClass('hidden');
	  $('.item_sort').removeClass('active');
	  e.stopPropagation();
	  });

	//show/hide filters menus for mobile version 

	$('.btn_show_filters').click(function(){
		$(this).parent().addClass('hidden-xs');
		$('.filters').removeClass('hidden-xs');
	});
	$('.btn_hide_filters').click(function(){
		$('.filters').addClass('hidden-xs');
		$('.btn_show_filters').parent().removeClass('hidden-xs');
	});


            //wmark.init({
            //    /* config goes here */
            //    //"position": "top-left", // default "bottom-right"
            //    "opacity": 50, // default 50
            //    "className": "watermark", // default "watermark"
            //    "path": "../resources/img/sale_stik.png"
            //});
});

function initSlider(minV, maxV){
    //FA***NG PRICE SLIDER
    $('#slider_price').noUiSlider({
        start: [ minV, maxV ],
        connect: true,
        range: {
            'min': minV,
            'max': maxV
        }
    });

    console.log("#{filterPageBean.minPrice}");
    for ( var i = minV; i <= maxV; i++ ){
        $('#mainForm\\:price_min').append(i);
    }


    $('#slider_price').Link('lower').to($('#mainForm\\:price_min'), null, wNumb({
        decimals: 0
    }));
    $('#slider_price').Link('upper').to($('#mainForm\\:price_max'), null, wNumb({
        decimals: 0
    }));

    $('.filter_photo_block_last').on('mouseover', function() {
        $('.spin_on_hover').addClass('fa-spin');
    })
    $('.filter_photo_block_last').on('mouseleave', function() {
        $('.spin_on_hover').removeClass('fa-spin');
    })

    //needed to fire event on backend bean
    $("#slider_price").on({
        change: function(){
            $('#mainForm\\:price_min').change();
            //$('#mainForm\\:price_max').change();
        }
    });
}