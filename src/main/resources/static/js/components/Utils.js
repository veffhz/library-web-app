export function showAlert(divId) {
   $(divId).fadeIn(1000);
   setTimeout(function() {
       $(divId).fadeOut(1000);
   }, 2000);
}