<!DOCTYPE html>
<html lang="en">
<jsp:include page="header.jsp" />

<body id="app-container" class="menu-sub-hidden show-spinner">
	<div id="menu-header"></div>
	<div id="menu-div"></div>
	<main>
		<div class="container-fluid">
			<div class="row">
				<div class="col-12">
					<div class="mb-2">
						<h1>Weather Advisory Broadcast Upload</h1>
						<div class="top-right-button-container">
							<a href="weatherBroadcastAdd.jsp" class="btn btn-primary">ADD NEW</a>
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
										<th>Country</th>
										<th>State</th>
										<th>District</th>
										<th>Taluk</th>
										<th>Village</th>
										<th>Video</th>
										<th>Audio</th>
										<th>Document</th>
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

	<script type='text/javascript'>
		$(document).ready(
			function () {
				$("#menu-div").load("menu.html");
				$("#menu-header").load("nav.html");
				$("#page-footer").load("footer.html");
				$("#redirectUrl").val(window.location.href);

				fillDataTable();
			});

		function fillDataTable () {
			$('#country-table').DataTable({
				"processing": false,
				"serverSide": false,
				"ajax": getUrl() + '/weatherBroadcast/get/all',
			});
		}

		function deletez (id) {
			$("body").addClass("show-spinner");

			var txt;
			if (confirm("Are you sure want to delete? Can't restore record once deleted")) {
				$.ajax({
					url: getUrl() + '/weatherBroadcast/delete?id='
						+ id,
					beforeSend: function (request) {
						request.setRequestHeader("user-id", getUserName());
					},
					success: function (result) {
						$("body").removeClass("show-spinner");
						window.location.href = "weatherBroadcast.jsp";
					}
				});
			} else {
				$("body").removeClass("show-spinner");
			}

		}

		
		function editz(id) {
			$("body").addClass("show-spinner");
			window.location.href = "weatherBroadcastAdd.jsp?id=" + id;
		}
	</script>

</body>

</html>