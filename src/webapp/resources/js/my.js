/**
 * Created by 1 on 27.05.2015.
 */
/*FOR OWL_STRELKI*/
$(document).ready(function() {
    height =  $('#owl-demo2').innerHeight();
    //$('#owl-demo2 .owl-buttons div').css('line-height',height+'px');
    $(window).resize(function(){
        var height =  $('#owl-demo2').innerHeight();
        $('#owl-demo2 .owl-buttons div').css('line-height',height+'px');
    });
});
// for toTop button
$(window).on("scroll", function() {
    if ($(window).scrollTop() > 250) $('#toTop').addClass('active')
    else $('#toTop').removeClass('active');
});
$('#toTop').click(function(){
    $('body').animate({
        scrollTop:0}, 700);
    return false;
});


