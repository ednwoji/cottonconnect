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
			<div class="row mb-4">
				<div class="col-12 mb-4">
					<h1>FAQ Info</h1>
					<nav
						class="breadcrumb-container d-none d-sm-block d-lg-inline-block"
						aria-label="breadcrumb">
						<ol class="breadcrumb pt-0">
							<li class="breadcrumb-item"><a href="#">Service</a></li>
							<li class="breadcrumb-item"><a href="#">Faq Info</a></li>
						</ol>
					</nav>
					<div class="card">
						<div class="card-body">
							<div class="row ">
								<div class="col-12">
									<div class="mb-2">
										<div class="top-right-button-container">
											<a href="faq.jsp" class="btn btn-primary">ADD NEW</a>
										</div>
									</div>
								</div>
							</div>
							<table class="data-table data-table-feature" id="faq-table">
								<thead>
									<tr>
										<th>Question</th>
										<th>Answer</th>
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
		$(document).ready(function() {
			$("#menu-div").load("menu.html");
			$("#menu-header").load("nav.html");
			$("#page-footer").load("footer.html");
			fillDataTable();
		});

		function fillDataTable(id) {
			$('#faq-table').DataTable({
				"processing" : true,
				"serverSide" : true,
				"ajax" : getUrl() + '/faq/question/list'
			});
		}

		function deletez(id) {
			$("body").addClass("show-spinner");

			var txt;
			if (confirm("Are you sure want to delete? Can't restore record once deleted")) {
				$.ajax({
					url : getUrl() + '/response/delete?id='
							+ id,
					beforeSend : function(request) {
						request.setRequestHeader("user-id", getUserName());
					},
					success : function(result) {
						$("body").removeClass("show-spinner");
						window.location.href = "faqList.jsp";
					}
				});
			} else {
				$("body").removeClass("show-spinner");
			}

		}

		function detail(id) {
			$("body").addClass("show-spinner");
			window.location.href = "faqResponse.jsp?id=" + id;
		}

		function edit(id) {
			$("body").addClass("show-spinner");
			window.location.href = "faq.jsp?id=" + id;
		}
	</script>

</body>

</html>