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
								<li class="breadcrumb-item"><a href="#">Email
										Management</a></li>
								<li class="breadcrumb-item active" aria-current="page">List</li>
							</ol>
						</nav>

						<div class="top-right-button-container">
							<a href="emailmanagement.jsp" class="btn btn-primary">ADD NEW</a>
						</div>

					</div>
				</div>
			</div>

			<div class="row mb-4">
				<div class="col-12 mb-4">
					<div class="card">
						<div class="card-body">
							<table class="data-table data-table-feature" id="country-table">
								<thead>
									<tr>
										<th>Name</th>
										<th>Country</th>
										<th>Brand</th>
										<th>Program</th>
										<th>Farmer Group</th>
										<th>Action</th>
									</tr>
								</thead>
							</table>
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

		$(document)
				.ready(
						function() {

							$("#img-div").hide();
							$("#menu-div").load("menu.html");
							$("#menu-header").load("nav.html");
							$("#page-footer").load("footer.html");
							$("#redirectUrl")
									.val(
											"http://cottonconnectelearning.in:10000/emailManagementList.jsp");

							$('#country-table').DataTable({
								"processing" : true,
								"serverSide" : true,
								"ajax" : getUrl() + '/email/getAllEmails/',
								// "ajax" : 'http://localhost:5000/app/email/getAllEmails/',
							});

						});

		function edit(id) {
			$("body").addClass("show-spinner");
			window.location.href = "emailmanagement.jsp?id=" + id;
		}

		function deletez(id) {
			$("body").addClass("show-spinner");

			var txt;
			if (confirm("Are you sure want to delete? Can't restore record once deleted")) {
				$.ajax({
					url : getUrl() + '/email/delete?id=' + id+"&user="+getUserName(),
					beforeSend : function(request) {
						request.setRequestHeader("user-id", getUserName());
					},
					success : function(result) {
						$("body").removeClass("show-spinner");
						window.location.href = "emailManagementList.jsp";
					}
				});
			} else {
				$("body").removeClass("show-spinner");
			}

		}
	</script>
</body>