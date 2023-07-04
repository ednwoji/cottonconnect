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
						<h1>Notification</h1>
					</div>
				</div>
			</div>

			<div class="row mb-4">
				<div class="col-12 mb-4">
					<div class="card">
						<div class="card-body">
							<form
								action=""
								id="add-form"
								method="post" enctype="multipart/form-data">
								<input type="hidden" name="id" id="id"> <input
									type="hidden" name="redirectUrl" id="redirectUrl"> <input
									type="hidden" name="subCategory" id="subCategory">
								<div class="row">
									<jsp:include page="ccfilter.jsp" />
								</div>
								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label>Notification Message</label>
											<textarea id="msg" name="msg" class="form-control"></textarea>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label>Description</label>
											<textarea maxlength="250" class="form-control" required
												name="description" id="description"></textarea>
										</div>
									</div>
								</div>


								<div class="modal-footer">
									<a href="notificationList.jsp" class="btn btn-outline-primary">Cancel</a>
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
		$.urlParam = function(name) {
			try {
				var results = new RegExp('[\?&]' + name + '=([^&#]*)')
						.exec(window.location.href);
				return results[1] || 0;
			} catch (e) {
			}
		}

		$("#farmGroup").change(function(){
			$.ajax({
				url : getUrl()
						+ '/master/learner/by-fg?fg='+$("#farmGroup").val(),
				beforeSend: function(request) {
	                    request.setRequestHeader("user-id", getUserName());
	            },
				success : function(result) {
					$("#learnerGroup").html('');
					$("#learnerGroup").append("<option value=''>Select</option>");
					$(result).each(function(k, v) {
						$("#learnerGroup").append(
								"<option value=" + v.id + ">" + v.name
								+ "</option>");
					});
				}
			});
		});

		$(document)
				.ready(
						function() {
							$("#img-div").hide();
							$("#menu-div").load("menu.html");
							$("#menu-header").load("nav.html");
							$("#page-footer").load("footer.html");
							$("#redirectUrl").val( getHomeUrl() +'/notificationList.jsp');
							$("#add-form").attr("action", getUrl() + "/notification/save/");

							var id = $.urlParam('id');
							if (id != null) {
								$("#img-div").show();
								$("body").addClass("show-spinner");
								$.ajax({
											url : getUrl()
													+ '/notification/by-id?id='
													+ id,
											beforeSend : function(request) {
												request.setRequestHeader(
														"user-id",
														getUserName());
											},
											success : function(result) {
												$("#id").val(id);
												$("#country").val(result.country);
												$("#brand").val(result.brand);
												$("#program").val(result.program);
												$("#partner").val(result.partner);
												$("#farmGroup").val(result.farmGroup);
												$("#name").val(result.name);
												$("#identification").val(
														result.identification);
												$("#msg").val(result.msg);
												$("#description").val(
														result.description);

												setUpdate(result);
												listLearnerGroup(result.farmGroup, result.learnerGroup);
												
												$("body").removeClass(
														"show-spinner");
												$("#mbtn").click();
											}
										});
							}

							$.ajax({
										url : getUrl()
												+ '/master/sub-categories/list',
										success : function(result) {
											$("#subCategory").html('');
											$("#subCategory")
													.append(
															"<option value=''>Select</option>");
											$(result)
													.each(
															function(k, v) {
																if (v.name == 'Diseases') {
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