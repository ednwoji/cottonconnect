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
						<h1>Email Management</h1>
						<nav
							class="breadcrumb-container d-none d-sm-block d-lg-inline-block"
							aria-label="breadcrumb">
							<ol class="breadcrumb pt-0">
								<li class="breadcrumb-item"><a href="#">Service</a></li>
								<li class="breadcrumb-item"><a href="#">Email</a></li>
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
							<form action="" method="post" id="add-form">
								<input type="hidden" name="id" id="id"> <input
									type="hidden" name="redirectUrl" id="redirectUrl">

								<div class="row">
									<jsp:include page="ccfilter.jsp" />
									<div class="col-md-4">
										<label>Email 1</label> <input type="email" class="form-control"
											required id="e0" name="emails">
									</div>
									<div class="col-md-4">
										<label>Email 2</label> <input type="email"
											class="form-control" id="e1" name="emails">
									</div>
									<div class="col-md-4">
										<label>Email 3</label> <input type="email"
											class="form-control" id="e2" name="emails">
									</div>

									<div class="col-md-4">
										<label>Email 4</label> <input type="email"
											class="form-control" id="e3" name="emails">
									</div>
									<div class="col-md-4">
										<label>Email 5</label> <input type="email"
											class="form-control" id="e4" name="emails">
									</div>
									<div class="col-md-4">
										<label>Email 6</label> <input type="email"
											class="form-control" id="e5" name="emails">
									</div>
								</div>

								<div class="modal-footer">
									<a href="emailManagementList.jsp" class="btn btn-outline-primary">Cancel</a>
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

		$("#img-div").hide();
		$("#menu-div").load("menu.html");
		$("#menu-header").load("nav.html");
		$("#page-footer").load("footer.html");
		$("#redirectUrl").val(getHomeUrl() + "/emailManagementList.jsp");
		$("#add-form").attr("action", getUrl() + "/email/save");
		var id = $.urlParam('id');
		if (id != null) {
			$("body").addClass("show-spinner");
			$.ajax({
				url : getUrl()+ '/email/by-id?id='+ id,
				beforeSend : function(request) {
					request.setRequestHeader(
							"user-id",
							getUserName());
				},
				success : function(result) {
					$("#id").val(id);
					$("#country").val(result.country);
					setUpdate(result);
					$(result.emails).each(function(i,v){
						$("#e"+i).val(v);
					});
				}
			});
		}
	 });

	 
	</script>
</body>