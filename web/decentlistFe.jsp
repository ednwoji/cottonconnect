<!DOCTYPE html>
<html lang="en">

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

<link rel="stylesheet" href="css/vendor/dataTables.bootstrap4.min.css" />
<link rel="stylesheet"
	href="css/vendor/datatables.responsive.bootstrap4.min.css" />
</head>

<body id="app-container" class="menu-sub-hidden show-spinner">
	<div id="menu-header"></div>
	<div id="menu-div"></div>
	<main>
		<div class="container-fluid">
			<div class="row ">
				<div class="col-12">
					<div class="mb-2">
						<h1>Decent Work</h1>
						<nav
							class="breadcrumb-container d-none d-sm-block d-lg-inline-block"
							aria-label="breadcrumb">
							<ol class="breadcrumb pt-0">
								<li class="breadcrumb-item"><a href="#">Service</a></li>
								<li class="breadcrumb-item"><a href="decentlistFe.jsp">Decent Work</a></li>
								<li class="breadcrumb-item active" aria-current="page">Add</li>
							</ol>
						</nav>
						<div class="top-right-button-container">
							<a href="decentFe.jsp" class="btn btn-primary">ADD NEW</a>
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
										<th>Type</th>
										<th>Brand</th>
										<th>Program</th>
										<th>Farmer Group</th>
										<th>Status</th>
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

					$.ajax({
						url : getUrl() + '/master/sub-categories/list',
						success : function(result) {
							$("#subCategory").html('');
							$("#subCategory").append(
									"<option value=''>Select</option>");
							$(result).each(function(k, v) {
								if (v.name == 'Decent') {
									$("#subCategory").val(v.id);
									fillDataTable(v.id);
								}
							});
							
						}
					});
				});

		function fillDataTable(id) {
			$('#country-table').DataTable(
					{
						"processing" : true,
						"serverSide" : true,
						"ajax" : getUrl()
								+ '/service/knowledge-center/getAllRecords/'
								+ id 
								+ '?type=2',
					});
		}

		function edit(id) {
			$("body").addClass("show-spinner");
			window.location.href = "decentFe.jsp?id=" + id;
		}

		function deletez(id) {
			$("body").addClass("show-spinner");

			var txt;
			if (confirm("Are you sure want to delete? Can't restore record once deleted")) {
				$
						.ajax({
							url : getUrl()
									+ '/service/knowledge-center/delete?id='
									+ id,
							beforeSend : function(request) {
								request.setRequestHeader("user-id",
										getUserName());
							},
							success : function(result) {
								$("body").removeClass("show-spinner");
								window.location.href = "decentlistFe.jsp";
							}
						});
			} else {
				$("body").removeClass("show-spinner");
			}

		}

		function detail(id) {
			$("body").addClass("show-spinner");
			window.location.href = "decentdetail.jsp?id=" + id;
		}
	</script>

</body>

</html>