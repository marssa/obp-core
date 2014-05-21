function doLayout() {
    var docWidth = $(document).width();
    $("body").css("font-size",docWidth/30+"px");
}

$(window).resize(function() {
    doLayout();
})

$(function() {
    doLayout();
})

