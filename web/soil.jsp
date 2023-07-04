<!DOCTYPE html>
<html lang="en">
<jsp:include page="header.jsp" />

<body id="app-container" class="menu-sub-hidden show-spinner">
	<div id="menu-header"></div>
	<div id="menu-div"></div>
	<main>
		<div class="container-fluid">
			<div class="row ">
				<div class="col-12">
					<div class="mb-2">
						<h1>Soil</h1>
						<nav class="breadcrumb-container d-none d-sm-block d-lg-inline-block" aria-label="breadcrumb">
							<ol class="breadcrumb pt-0">
								<li class="breadcrumb-item"><a href="#">Service</a></li>
								<li class="breadcrumb-item"><a href="soilList.jsp">Soil</a></li>
								<li class="breadcrumb-item active" aria-current="page">Add</li>
							</ol>
						</nav>
					</div>
				</div>
			</div>

			<div class="row mb-4">
				<div class="col-12 mb-4">
					<div class="card">
						<div class="card-body">
							<form action="" id="add-form" method="post" enctype="multipart/form-data"
								accept-charset="UTF-8">
								<input type="hidden" name="id" id="id"> <input type="hidden" name="redirectUrl"
									id="redirectUrl"> <input type="hidden" name="subCategory" id="subCategory">
								<input type="hidden" name="type" value="1">
								<div class="row">
									<jsp:include page="ccfilter.jsp" />
								</div>
								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label>Soil in Agriculture</label> <input type="text" class="form-control"
												required id="name" name="name" oninput="validateInput('name')">
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label>Soil conservation methods</label>
											<textarea maxlength="500" class="form-control" required
												name="identification" id="identification" oninput="validateInput('identification')"></textarea>
											(Max. 500 chars)
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label>Notes on soil with title</label>
											<textarea maxlength="500" class="form-control" required name="notes"
												id="notes" oninput="validateInput('notes')"></textarea>
											(Max. 500 chars)
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label>Upload<b style="color: red">[.jpg, .png]</b> [Max size : Upto to
												1MB]</label> <input type="file" accept="image/jpeg, image/png" name="file" class="form-control">
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label>Upload<b style="color: red">[.jpg, .png]</b> [Max size : Upto to
												1MB]</label> <input type="file" accept="image/jpeg, image/png" name="file" class="form-control">
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label>Upload<b style="color: red">[.pdf,
													.xls,.pptx, .doc]</b> [Max size : Upto to 1MB]</label> <input
												type="file" accept=".pdf, .xls, .xlsx, .pptx, .doc, .docx" name="file" class="form-control">
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label>Upload<b style="color: red">[.pdf,
													.xls,.pptx, .doc]</b> [Max size : Upto to 1MB]</label> <input
												type="file" accept=".pdf, .xls, .xlsx, .pptx, .doc, .docx" name="file" class="form-control">
										</div>
									</div>
								</div>

								<div class="row">
									<div class="form-group" id="img-div"></div>
								</div>


								<div class="modal-footer">
									<a href="soilList.jsp" class="btn btn-outline-primary">Cancel</a>
									<button class="btn btn-primary">Submit</button>
								</div>
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
	<script src="js/vendor/datatables.min.js"></script>
	<script src="js/dore.script.js"></script>
	<script src="js/scripts.js?v=1.1"></script>
	<script src="js/vendor/select2.full.js"></script>

	<script type='text/javascript'>
		$.urlParam = function (name) {
			try {
				var results = new RegExp('[\?&]' + name + '=([^&#]*)')
					.exec(window.location.href);
				return results[1] || 0;
			} catch (e) {
			}
		};

		function validateInput(idTag) {
  var textarea = document.getElementById(idTag);
  var input = textarea.value;
  var regex = /^[A-Za-z0-9\s]+$/;;
  
  if (!regex.test(input)) {
    textarea.setCustomValidity("Only alphabets and numbers are allowed");
  } else {
    textarea.setCustomValidity("");
  }
}


		$(document).ready(function () {


			var currentUrl = window.location.href;
 	 	console.log(currentUrl);

		  if (currentUrl.indexOf('failed') !== -1) {
			$('#add-form').prepend('<div class="alert alert-danger text-center border border-info">Uploaded Failed. Try again later</div>')
				.find('.alert')
				.fadeIn(300)
				.delay(3000)
				.fadeOut(300, function() {$(this).remove();});
  			}

			else if (currentUrl.indexOf('success') !== -1){

				$('#add-form').prepend('<div class="alert alert-info text-center border border-info">Uploaded Successfully</div>')
				.find('.alert')
				.fadeIn(300)
				.delay(3000)
				.fadeOut(300, function() {$(this).remove();});
  			}



			$("#img-div").hide();
			$("#menu-div").load("menu.html");
			$("#menu-header").load("nav.html");
			$("#page-footer").load("footer.html");
			$("#redirectUrl").val(getHomeUrl() + "/soil.jsp");
			$("#add-form").attr("action", getUrl() + "/service/knowledge-center/save/");
			var id = $.urlParam('id');
			if (id != null) {
				$("body").addClass("show-spinner");
				$("#img-div").show();
				$.ajax({
					url: getUrl() + '/service/knowledge-center/by-id?id=' + id,
					beforeSend: function (request) {
						request.setRequestHeader(
							"user-id",
							getUserName());
					},
					success: function (result) {
						$("#id").val(id);
						$("#country").val(
							result.country);
						$("#brand").val(result.brand);
						$("#program").val(
							result.program);
						$("#partner").val(
							result.partner);
						$("#farmGroup").val(
							result.farmGroup);
						$("#name").val(result.name);
						$("#identification").val(
							result.identification);
						$("#notes").val(result.notes);
						$("#description").val(
							result.description);
						$('input:radio[name=typez]')
							.val([result.typez]);

						setUpdate(result);

						$(result.images).each(function (i, v) {
							var ext = v.imageUrl.substr(v.imageUrl.lastIndexOf('.') + 1);
							var imgDiv = document.getElementById('img-div');
							var imgWrap = $("<div>", { class: "img_wrp" }).appendTo(imgDiv);

							if (ext == "jpg" || ext == "png") {
								$("<img />", {
									class: "zoom",
									src: v.imageUrl,
									height: '200',
									width: '200',
								}).appendTo(imgWrap);

								var anchor = $("<a />", {
									onclick: 'deleteImg(' + v.id + ')'
								}).appendTo(imgWrap);

								$("<img />", {
									class: "close",
									src: "img/close.png",
								}).appendTo(anchor);
							} else {
								var link = $("<a target='_blank'>");
								link.attr("href", v.imageUrl);
								link.attr("title", v.imageUrl);
								link.text("Click to open");
								link.addClass("link");

								var anchor = $("<a />", {
									onclick: 'deleteImg(' + v.id + ')'
								}).appendTo(imgWrap);

								$("<img />", {
									class: "close, ml5",
									src: "img/close.png",
								}).appendTo(anchor);

								imgWrap.append(link);
							}

						});

						$("body").removeClass(
							"show-spinner");
						$("#mbtn").click();
					}
				});
			}

			$.ajax({
				url: getUrl()
					+ '/master/sub-categories/list',
				success: function (result) {
					$("#subCategory").html('');
					$("#subCategory")
						.append(
							"<option value=''>Select</option>");
					$(result)
						.each(
							function (k, v) {
								if (v.name == 'Soil') {
									$(
										"#subCategory")
										.val(
											v.id);
								}
							});

				}
			});

		});

	</script>

</body>

</html>