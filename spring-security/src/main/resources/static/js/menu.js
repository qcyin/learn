function hamburger_cross() {
    var trigger = $('.hamburger'), overlay = $('.overlay');
    if (trigger.hasClass('is-open')) {
        overlay.hide();
        trigger.removeClass('is-open');
        trigger.addClass('is-closed');
    } else {
        overlay.show();
        trigger.removeClass('is-closed');
        trigger.addClass('is-open');
    }
    $('#wrapper').toggleClass('toggled');

}
