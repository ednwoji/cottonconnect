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

<link rel="stylesheet" href="css/vendor/select2.min.css" />
<link rel="stylesheet" href="css/vendor/select2-bootstrap.min.css" />
</head>

<body id="app-container" class="menu-sub-hidden show-spinner">
	<div id="menu-header"></div>
	<div id="menu-div"></div>
	<main>
		<div class="container-fluid">
			<div class="row ">
				<div class="col-12">
					<div class="mb-2">
						<h1>Local Partner</h1>
						<nav
							class="breadcrumb-container d-none d-sm-block d-lg-inline-block"
							aria-label="breadcrumb">
							<ol class="breadcrumb pt-0">
								<li class="breadcrumb-item"><a href="#">Home</a></li>
								<li class="breadcrumb-item"><a href="lpalist.jsp">Local Partner</a></li>
								<li class="breadcrumb-item active" aria-current="page">List</li>
							</ol>
						</nav>
						<div class="top-right-button-container">
							<a href="lpa.html" class="btn btn-primary">ADD NEW</a>
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
										<th>Address</th>
										<th>Program</th>
										<th>Country</th>
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
	<script src="js/vendor/select2.full.js"></script>

	<script type='text/javascript'>
        $(document).ready(function () {
            $("#menu-div").load("menu.html");
            $("#menu-header").load("nav.html");
            $("#page-footer").load("footer.html");

            $('#country-table').DataTable({
                "processing": true,
                "serverSide": true,
                "ajax": getUrl() + '/master/partner/getAllPartners',
            });


            $.ajax({
                url: getUrl() + '/location/country/getCountries', success: function (result) {
                    $("#country").html('');
                    $("#country").append("<option value=''>Select</option>");
                    $(result).each(function (k, v) {
                        $("#country").append("<option value=" + v.id + ">" + v.name + "</option>");
                    });
                   
                }
            });

            $.ajax({
                url: getUrl() + '/master/brand/getBrands',
                beforeSend: function(request) {
                    request.setRequestHeader("user-id", getUserName());
                }
                , success: function (result) {
                    $("#brand").html('');
                    $("#brand").append("<option value=''>Select</option>");
                    $(result).each(function (k, v) {
                        $("#brand").append("<option value=" + v.id + ">" + v.name + "</option>");
                    });
                   
                }
            });

        });

        
        function edit(id){
			window.location.href = "lpa.html?id="+id;
		}

		function deletez(id){
			$("body").addClass("show-spinner");

			  var txt;
			  if (confirm("Are you sure want to delete? Can't restore record once deleted")) {
					$.ajax({
						url : getUrl() + '/master/partner/delete?id='+id,
						beforeSend : function(request) {
							request.setRequestHeader("user-id", getUserName());
						},
						success : function(result) {
							$("body").removeClass("show-spinner");
							window.location.href = "lpalist.jsp";
						}
					});
			  } else {
				  $("body").removeClass("show-spinner");
			  }
			
		}
		
    </script>

</body>

</html>