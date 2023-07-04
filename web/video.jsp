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
						<h1>Farmer Videos</h1>
					</div>
				</div>
			</div>

			<div class="row mb-4">
				<div class="col-12 mb-4">
					<div class="card">
						<div class="card-body">
							<form action="" method="post" enctype="multipart/form-data" accept-charset="UTF-8"
								id="add-form">
								<input type="hidden" name="id" id="id"> <input type="hidden" name="redirectUrl"
									id="redirectUrl"> <input type="hidden" name="subCategory" id="subCategory">
								<input type="hidden" name="type" value="1">

								<div class="row">
									<jsp:include page="ccfilter.jsp" />
								</div>
								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label>Video Title</label> 
											<input type="text" pattern="^[a-zA-Z0-9]*$" class="form-control" required id="name" name="name">
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label>Description</label>
											<textarea maxlength="250" class="form-control" required name="description" id="description" oninput="validateInput('description')"></textarea>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label>Upload(Video Format)</label> 
											<input type="file" class="form-control" name="file" accept="video/mp4" id="file" required>
											<div>
												<a href="" target="_blank" id="file-view">View Video</a>
											</div>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label>Link</label> 
											<input type="url" class="form-control" id="link" name="link">
										</div>
									</div>
									<!-- <div class="col-md-4">
										<div class="form-group">
											<label>Thumbnail</label> <input type="file"
												class="form-control" name="file">
										</div>
									</div> -->
								</div>


								<div class="modal-footer">
									<a href="videolist.jsp" class="btn btn-outline-primary">Cancel</a>
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
			$("#img-div").hide();
			$('#file-view').hide();
			$("#menu-div").load("menu.html");
			$("#menu-header").load("nav.html");
			$("#page-footer").load("footer.html");
			$("#redirectUrl").val(getHomeUrl() + "/videolist.jsp");
			$("#add-form").attr("action", getVideoUploadUrl() + "/video/save");


			$.ajax({
				url: getUrl() + '/location/country/getCountries',
				success: function (result) {
					$("#country").html('');
					$("#country")
						.append("<option value=''>Select</option>");
					$(result).each(
						function (k, v) {
							$("#country").append(
								"<option value=" + v.id + ">"
								+ v.name + "</option>");

						});
					var id = $.urlParam('id');
					if (id != null) {
						edit(id);
					}
				}
			});


			$
				.ajax({
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
									if (v.name == 'Nutrients') {
										$(
											"#subCategory")
											.val(
												v.id);
									}
								});

					}
				});

		});

		function edit (id) {
			var id = $.urlParam('id');
			if (id != null) {
				$("#file").hide();
				$("body").addClass("show-spinner");
				$("#img-div").show();
				$.ajax({
					url: getUrl()
						+ '/video/by-id?id='
						+ id,
					beforeSend: function (request) {
						request.setRequestHeader(
							"user-id",
							getUserName());
					},
					success: function (result) {
						$("#id").val(id);
						$("#name").val(result.name);
						$("#country").val(
							result.country);
						$("#brand").val(result.brand);
						setUpdate(result);
						$("#identification").val(
							result.identification);
						$("#notes").val(result.notes);
						$("#description").val(
							result.description);
						$("#link").val(
							result.link);
						if(result.sourceUrl) {
							$('#file-view').show();
							$("#file-view").attr('href', result.sourceUrl);
						}
						listLearnerGroup(result.farmGroups, result.learners);

						$("body").removeClass(
							"show-spinner");
						$("#mbtn").click();
					}
				});
			}
		}
	</script>

</body>

</html>