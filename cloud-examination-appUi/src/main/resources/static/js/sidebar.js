//检测window下有没有war模块
if (!("war" in window)) {
	window.war = {};
}
//设置点击方式是什么
jQuery(function(e) {
	window.war.click_event = e.fn.tap ? "tap" : "click";
});
//要执行的函数
jQuery(function(e) {
		war.handle_side_menu(jQuery);
		war.menu_collapse(jQuery);
//		war.Iframe_loadComplate(jQuery);
//		war.button_group(jQuery);
	})
	//
war.handle_side_menu = function($) {
	$(".nav-list").on(war.click_event, function(e) {
		var g = $(e.target).closest("a"); //
		if ($("#sidebar").hasClass("menu-min")) return false;
		if (!g || g.length == 0) return;
		var f = g.next().get(0);
		if (typeof(f) === "undefined") return;
		var d = $(f.parentNode).closest("ul");
		if (!$(f).is(":visible")) {
			d.find("> .active> .submenu").each(function() {
				if (this != f && $(this.parentNode).hasClass("active")) {
					$(this).slideUp(200).parent().removeClass("active");
				}
			});
		}
		$(f).slideToggle(200).parent().toggleClass("active");
		return false;
	});
}
war.menu_collapse = function($) {
	$("#menu-collapse").on(war.click_event, function(e) {
		$(".nav-list").find("li").each(function() {
			if ($(this).hasClass("active")) {
				$(this).removeClass("active");
			}
		})
		$("#sidebar").toggleClass("menu-min");
		$(".main-content").toggleClass("main-content-min")
	});
}




