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
						<h1>Insect</h1>
						<nav
							class="breadcrumb-container d-none d-sm-block d-lg-inline-block"
							aria-label="breadcrumb">
							<ol class="breadcrumb pt-0">
								<li class="breadcrumb-item"><a href="#">Service</a></li>
								<li class="breadcrumb-item"><a href="inspectList.jsp">Insect</a></li>
								<li class="breadcrumb-item active" aria-current="page">Add</li>
							</ol>
						</nav>
						<div class="top-right-button-container">
							<a href="insect.jsp" class="btn btn-primary">ADD NEW</a>
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
										<th data-sortable="true">Name</th>
										<th data-sortable="true">Country</th>
										<th data-sortable="true">Type</th>
										<th data-sortable="true">Brand</th>
										<th data-sortable="true">Program</th>
										<th data-sortable="true">Farmer Group</th>
										<th data-sortable="true">Status</th>
										<th data-sortable="true">Action</th>
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
								if (v.name == 'Insects') {
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
						"ajax" : getUrl() + '/service/knowledge-center/getAllRecords/'+ id + '?type=1',
						// "ajax" : 'http://localhost:5000/app/service/knowledge-center/getAllRecords/'+ id + '?type=1',
						"ordering": true,
						"searching": true
					});
		}


// 		$('#table-full').dataTable({
//     "paging": true, // enable pagination
//     "pageLength": 10, // number of rows to show per page
//     "lengthChange": true, // hide the option to change the page length
//     "searching": true, // hide the search box
//     "ordering": false , //enable sorting
//     "info": true, // hide the "Showing X to Y of Z entries" info
//     "language": {
//       "paginate": {
//         "first": "<<",
//         "last": ">>",
//         "next": ">",
//         "previous": "<"
//       }
//     } // customize the pagination button labels
//   });




 		function edit(id) {
			$("body").addClass("show-spinner");
			window.location.href = "insect.jsp?id=" + id;
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
								window.location.href = "inspectList.jsp";
							}
						});
			} else {
				$("body").removeClass("show-spinner");
			}

		}

		function detail(id) {
			$("body").addClass("show-spinner");
			window.location.href = "inspectDetail.jsp?id=" + id;
		}
	</script>

</body>

</html>