
/* Dore Theme Select & Initializer Script 

Table of Contents

01. Css Loading Util
02. Theme Selector And Initializer
 */

/* 01. Css Loading Util */
function loadStyle(href, callback) {
	for (var i = 0; i < document.styleSheets.length; i++) {
		if (document.styleSheets[i].href == href) {
			return;
		}
	}
	var head = document.getElementsByTagName("head")[0];
	var link = document.createElement("link");
	link.rel = "stylesheet";
	link.type = "text/css";
	link.href = href;
	if (callback) {
		link.onload = function() {
			callback();
		};
	}
	var mainCss = $(head).find('[href$="main.css"]');
	if (mainCss.length !== 0) {
		mainCss[0].before(link);
	} else {
		head.appendChild(link);
	}
}

function getUrl() {
	//return "http://localhost:5000";
	//return "http://stage-api.cottonconnectelearning.com";
	return "http://server.cottonconnectelearning.com";
}

function getVideoUploadUrl() {
	//return "http://upload-stage.cottonconnectelearning.com";
	return "http://upload.cottonconnectelearning.com";
}

function getHomeUrl() {
	//return "http://localhost:8080";
	//return "http://stage.cottonconnectelearning.com";
	return "http://cottonconnectelearning.com";
  }

function getUserName() {
	var userName = "";
	if (typeof (Storage) !== "undefined") {
		if (!sessionStorage.isLogin) {
			window.location.href = "login.html";
		} else {
			if (sessionStorage.isLogin != "1") {
				window.location.href = "login.html";
			}
		}
		userName = sessionStorage.userName;
		return userName
	}
}

function deleteImg(val,imgId) {
	if (confirm("Are you sure want to delete? Can't restore record once deleted")) {
		$.ajax({
			url : getUrl() + '/service/knowledge-center/deleteImage?id='
					+ imgId,
			beforeSend : function(request) {
				request.setRequestHeader("user-id", getUserName());
			},
			success : function(result) {
				window.location.href = "#";
				$(val).parent().hide();
			}
		});
	}
}

jQuery.fn.ForceNumericOnly = function() {
	return this
			.each(function() {
				$(this)
						.keydown(
								function(e) {
									var key = e.charCode || e.keyCode || 0;
									// allow backspace, tab, delete, enter, arrows, numbers and keypad numbers ONLY
									// home, end, period, and numpad decimal
									return (key == 8 || key == 9 || key == 13
											|| key == 46 || key == 110
											|| key == 190
											|| (key >= 35 && key <= 40)
											|| (key >= 48 && key <= 57) || (key >= 96 && key <= 105));
								});
			});
};

/* 02. Theme Selector, Layout Direction And Initializer */
(function($) {
	if ($().dropzone) {
		Dropzone.autoDiscover = false;
	}

	var themeColorsDom = '<div class="theme-colors"><div class="p-4"><p class="text-muted mb-2">Light Theme</p><div class="d-flex flex-row justify-content-between mb-4"><a href="#" data-theme="dore.light.blue.min.css?v=1.0" class="theme-color theme-color-blue"></a><a href="#" data-theme="dore.light.purple.min.css" class="theme-color theme-color-purple"></a><a href="#" data-theme="dore.light.green.min.css" class="theme-color theme-color-green"></a><a href="#" data-theme="dore.light.orange.min.css" class="theme-color theme-color-orange"></a><a href="#" data-theme="dore.light.red.min.css" class="theme-color theme-color-red"></a></div><p class="text-muted mb-2">Dark Theme</p><div class="d-flex flex-row justify-content-between"><a href="#" data-theme="dore.dark.blue.min.css" class="theme-color theme-color-blue"></a><a href="#" data-theme="dore.dark.purple.min.css" class="theme-color theme-color-purple"></a><a href="#" data-theme="dore.dark.green.min.css" class="theme-color theme-color-green"></a><a href="#" data-theme="dore.dark.orange.min.css" class="theme-color theme-color-orange"></a><a href="#" data-theme="dore.dark.red.min.css" class="theme-color theme-color-red"></a></div></div><div class="p-4"><p class="text-muted mb-2">Border Radius</p><div class="custom-control custom-radio custom-control-inline"><input type="radio" id="roundedRadio" name="radiusRadio" class="custom-control-input radius-radio" data-radius="rounded"><label class="custom-control-label" for="roundedRadio">Rounded</label></div><div class="custom-control custom-radio custom-control-inline"><input type="radio" id="flatRadio" name="radiusRadio" class="custom-control-input radius-radio" data-radius="flat"><label class="custom-control-label" for="flatRadio">Flat</label></div></div><div class="p-4"><p class="text-muted mb-2">Direction</p><div class="custom-control custom-radio custom-control-inline"><input type="radio" id="ltrRadio" name="directionRadio" class="custom-control-input direction-radio" data-direction="ltr"><label class="custom-control-label" for="ltrRadio">Ltr</label></div><div class="custom-control custom-radio custom-control-inline"><input type="radio" id="rtlRadio" name="directionRadio" class="custom-control-input direction-radio" data-direction="rtl"><label class="custom-control-label" for="rtlRadio">Rtl</label></div></div><a href="#" class="theme-button"> <i class="simple-icon-magic-wand"></i> </a></div>';

	$("body").append(themeColorsDom);

	/* Default Theme Color, Border Radius and  Direction */
	var theme = "dore.light.blue.min.css?v=1.1";
	var direction = "ltr";
	var radius = "rounded";

	$(".theme-button").hide();

	if (typeof Storage !== "undefined") {
		if (localStorage.getItem("dore-theme")) {
			theme = localStorage.getItem("dore-theme");
		} else {
			localStorage.setItem("dore-theme", theme);
		}
		if (localStorage.getItem("dore-direction")) {
			direction = localStorage.getItem("dore-direction");
		} else {
			localStorage.setItem("dore-direction", direction);
		}
		if (localStorage.getItem("dore-radius")) {
			radius = localStorage.getItem("dore-radius");
		} else {
			localStorage.setItem("dore-radius", radius);
		}
	}

	$(".theme-color[data-theme='" + theme + "']").addClass("active");
	$(".direction-radio[data-direction='" + direction + "']").attr("checked",
			true);
	$(".radius-radio[data-radius='" + radius + "']").attr("checked", true);
	$("#switchDark").attr("checked", theme.indexOf("dark") > 0 ? true : false);

	loadStyle("css/" + theme, onStyleComplete);
	function onStyleComplete() {
		setTimeout(onStyleCompleteDelayed, 300);
	}

	function onStyleCompleteDelayed() {
		$("body").addClass(direction);
		$("html").attr("dir", direction);
		$("body").addClass(radius);
		$("body").dore();
	}

	$("body").on("click", ".theme-color", function(event) {
		event.preventDefault();
		var dataTheme = $(this).data("theme");
		if (typeof Storage !== "undefined") {
			localStorage.setItem("dore-theme", dataTheme);
			window.location.reload();
		}
	});

	$("input[name='directionRadio']").on("change", function(event) {
		var direction = $(event.currentTarget).data("direction");
		if (typeof Storage !== "undefined") {
			localStorage.setItem("dore-direction", direction);
			window.location.reload();
		}
	});

	$("input[name='radiusRadio']").on("change", function(event) {
		var radius = $(event.currentTarget).data("radius");
		if (typeof Storage !== "undefined") {
			localStorage.setItem("dore-radius", radius);
			window.location.reload();
		}
	});

	$("#switchDark").on("change", function(event) {
		var mode = $(event.currentTarget)[0].checked ? "dark" : "light";
		if (mode == "dark") {
			theme = theme.replace("light", "dark");
		} else if (mode == "light") {
			theme = theme.replace("dark", "light");
		}
		if (typeof Storage !== "undefined") {
			localStorage.setItem("dore-theme", theme);
			window.location.reload();
		}
	});

	$(".theme-button").on("click", function(event) {
		event.preventDefault();
		$(this).parents(".theme-colors").toggleClass("shown");
	});

	$(document).on(
			"click",
			function(event) {
				if (!($(event.target).parents().hasClass("theme-colors")
						|| $(event.target).parents().hasClass("theme-button")
						|| $(event.target).hasClass("theme-button") || $(
						event.target).hasClass("theme-colors"))) {
					if ($(".theme-colors").hasClass("shown")) {
						$(".theme-colors").removeClass("shown");
					}
				}
			});
})(jQuery);

function validateForm (formId) {
	let isValid = true;
	$(".err-msg").remove();
	$("#" + formId + " .req").each(function () {
		if ($.trim($(this).val()) == "") {
			$(this).after("<span class='err-msg' style='color: red;'> This field is required </span>");
			isValid = false;
		}
	});
	$("#" + formId + " .req-file").each(function () {
		if ($.trim($(this).val())) {
			const ext = "." + $(this).val().split('.').pop().toLowerCase();
			const acceptExt = $(this).attr("accept").split(',');
			if (!acceptExt.includes(ext)) {
				$(this).after("<span class='err-msg' style='color: red;'> Select valid file (" + acceptExt.join(',') + ") </span>");
				isValid = false;
			}
			
		}
	});
	return isValid;
}