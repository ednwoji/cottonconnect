<!DOCTYPE html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
<meta charset="UTF-8">
<title>CottonConnect - ELearning Portal</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">

<link rel="stylesheet" href="font/iconsmind-s/css/iconsminds.css" />
<link rel="stylesheet"
	href="font/simple-line-icons/css/simple-line-icons.css" />

<link rel="stylesheet" href="css/vendor/bootstrap.min.css" />
<link rel="stylesheet" href="css/vendor/bootstrap.rtl.only.min.css" />
<link rel="stylesheet" href="css/vendor/component-custom-switch.min.css" />
<link rel="stylesheet" href="css/vendor/perfect-scrollbar.css" />
<link rel="stylesheet" href="css/main.css" />
</head>

<body id="app-container" class="menu-sub-hidden show-spinner">
	<div id="menu-header"></div>
	<div id="menu-div"></div>
	<main>
		<div class="container-fluid">
			<div class="row ">
				<div class="col-12">
					<div class="mb-2">
						<h1>FAQ</h1>
					</div>
				</div>
			</div>
			<div class="row mb-4">
				<div class="col-12 mb-4">
					<div class="card">
						<div class="card-body">
							<form action="" method="post" accept-charset="UTF-8" id="add-form">
								<input type="hidden" name="id" id="id"> <input
									type="hidden" name="redirectUrl" id="redirectUrl">

								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label>Question</label>
											<textarea id="question" name="question" class="form-control"></textarea>
										</div>
									</div>

									<div class="col-md-6">
										<div class="form-group">
											<label>Answer</label>
											<textarea id="answer" name="answer" class="form-control"></textarea>
										</div>
									</div>
								</div>

								<button class="btn btn-primary">Submit</button>
								<a href="faqList.jsp" class="btn btn-primary">Cancel</a>
							</form>
						</div>
					</div>
				</div>
			</div>

		</div>
	</main>


	<footer class="page-footer"> </footer>

	<script src="js/vendor/jquery-3.3.1.min.js"></script>
	<script src="js/vendor/bootstrap.bundle.min.js"></script>
	<script src="js/vendor/perfect-scrollbar.min.js"></script>
	<script src="js/dore.script.js"></script>
	<script src="js/scripts.js?v=1.1"></script>

	<script type='text/javascript'>

	$.urlParam = function(name) {
		try {
			var results = new RegExp('[\?&]' + name + '=([^&#]*)')
					.exec(window.location.href);
			return results[1] || 0;
		} catch (e) {
		}
	}
	
		var count = 0;
		$(document).ready(function() {
			$("#menu-div").load("menu.html");
			$("#menu-header").load("nav.html");
			$("#page-footer").load("footer.html");
			$("#redirectUrl").val(getHomeUrl() + '/faqList.jsp');
			$("#add-form").attr("action", getUrl() + "/faq/save");
			var id = $.urlParam('id');
			if (id != null) {
				$("body").addClass("show-spinner");
				$.ajax({
					url : getUrl()+ '/faq/faq/by-id?id='+ id,
					beforeSend : function(request) {
						request.setRequestHeader(
								"user-id",
								getUserName());
					},
					success : function(result) {
						$("#id").val(id);
						$("#question").val(result.question);
						$("#answer").val(result.answer);
					},
				});
			}
		});

		function addQuestionAnswer() {
			var tr = $("<tr/>").appendTo("tbody");
			var question = $("#question").val();
			var answer = $("#answer").val();
			var tdQuestion = $("<td/>").appendTo(tr);
			var tdAnswer = $("<td/>").appendTo(tr);

			var ques = $("<textarea>",{value : question,class:"form-control"}).appendTo(tdQuestion);
			
			var ans = $("<textarea>",{value : answer,class:"form-control"}).appendTo(tdAnswer);

			$(ques).val(question);
			$(ans).val(answer);

			$("#question").val('');
			$("#answer").val('');
		}

	</script>

</body>

</html>