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
						<div class="top-right-button-container">
							<a href="notificationFieldStaff.jsp" class="btn btn-primary">ADD NEW</a>
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
										<th>Notification Message</th>
										<th>Send By</th>
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
				function() {
					$("#menu-div").load("menu.html");
					$("#menu-header").load("nav.html");
					$("#page-footer").load("footer.html");
					$("#redirectUrl").val(window.location.href);


					 $('#country-table').DataTable({
			                "processing": true,
			                "serverSide": true,
			                "ajax": getUrl() + '/notification/getAllRecords',
			            });
				});

		

		function edit(id) {
			$("body").addClass("show-spinner");
			window.location.href = "notification.jsp?id="+id;
		}

		function deletez(id) {
			$("body").addClass("show-spinner");

			var txt;
			if (confirm("Are you sure want to delete? Can't restore record once deleted")) {
				$.ajax({
					url : getUrl() + '/notification/delete?id='+id,
					beforeSend : function(request) {
						request.setRequestHeader("user-id", getUserName());
					},
					success : function(result) {
						$("body").removeClass("show-spinner");
						window.location.href = "notificationList.jsp";
					}
				});
			} else {
				$("body").removeClass("show-spinner");
			}

		}

		function detail(id){
			$("body").addClass("show-spinner");
			window.location.href = "inspectDetail.jsp?id="+id;
		}
	</script>

</body>
</html>