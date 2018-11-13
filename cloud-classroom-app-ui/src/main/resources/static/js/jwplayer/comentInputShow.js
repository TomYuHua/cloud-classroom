//   document.body.onclick=function(event){           
//	$(event.currentTarget).closest('.coments-each-root').find('.coments-each-textarea').attr("style","display:block");
//}
//                                     
//$(function(){
//	
//    // https://stackoverflow.com/questions/1403615/use-jquery-to-hide-a-div-when-the-user-clicks-outside-of-it
//    var targetNodeList = [
//        $(".coments-each-textarea")
//    ];
//    $(document).mouseup(function (e) {
//        _.forEach(targetNodeList, function(T){
//            var container = T;
//            // if the target of the click isn't the container nor a descendant
//			// of the container
//            if (!container.is(e.target) && container.has(e.target).length === 0) {
//                container.hide();
//            }
//        });
//    });
//    
//});