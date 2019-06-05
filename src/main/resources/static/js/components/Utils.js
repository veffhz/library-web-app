export function showAlert(divId, spanId, spanText) {
   $(spanId).text(spanText);
   $(divId).fadeIn(1000);
   setTimeout(function() {
       $(divId).fadeOut(1000);
   }, 2000);
}