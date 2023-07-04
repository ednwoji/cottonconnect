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
						<h1>Mobile Users</h1>
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
										<th>Organization</th>
										<th>Country</th>
										<th>Mobile No</th>
										<th>Latitude</th>
										<th>Longitude</th>
										<th>Serial No</th>
										<th>Mobile Model</th>
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
	<script src="js/scripts.js?v=1.8"></script>

	<script type='text/javascript'>
        $(document).ready(function () {
            $("#menu-div").load("menu.html");
            $("#menu-header").load("nav.html");
            $("#page-footer").load("footer.html");

            $('#country-table').DataTable({
                "processing": true,
                "serverSide": true,
                "ajax": getUrl() + '/device/getAllUnRegister/',
            });
        });


		function edit(id){
			if(confirm('Do you want to approve?')){
				$.ajax({
					url : getUrl()+'/device/register/delete?id='+ id,
					beforeSend : function(request) {
						request.setRequestHeader(
								"user-id",
								getUserName());
					},
					success : function(result) {
						window.location.href = window.location.href;
					}
				});
			}else{
			}
		}


    </script>

</body>

</html>