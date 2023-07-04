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
							<form action=""
							id="add-form"
								method="post" enctype="multipart/form-data">

								<input type="hidden" name="id" id="id"> <input
									type="hidden" name="redirectUrl" id="redirectUrl"> <input
									type="hidden" name="subCategory" id="subCategory">
								<div class="row">
									<jsp:include page="ccfilter.jsp" />
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

								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label>Users<sup>*</sup></label> <select id="user" name="users"
												class="form-control select2-multiple" multiple="multiple"></select>
										</div>

									</div>
								</div>

								<div class="modal-footer">
									<a href="notificationFieldStaffList.jsp" class="btn btn-outline-primary">Cancel</a>
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

		$(document).ready(function() {
			$("#menu-div").load("menu.html");
			$("#menu-header").load("nav.html");
			$("#page-footer").load("footer.html");
			$("#redirectUrl").val(getHomeUrl() +'/notificationFieldStaffList.jsp');
			$("#add-form").attr("action", getUrl() + "/notification/save/");
			loadUsers();
			var id = $.urlParam('id');
			if (id != null) {
				
			}
		});

		function loadUsers(){
			$.ajax({
				url : getUrl() + '/service/user/getUsers',
				beforeSend : function(request) {
					request.setRequestHeader("user-id", getUserName());
				},
				success : function(result) {
					$("#user").html('');
					$("#user").append(
							"<option value=''>Select</option>");
					$(result)
							.each(
									function(k, v) {
										$("#user").append(
												"<option value=" + v.id + ">"
														+ v.name
														+ "</option>");
									});
					loadProgram();
				}
			});
			
		}
	</script>
</body>