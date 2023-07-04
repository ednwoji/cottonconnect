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
						<h1>Farmer</h1>
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
							<a href="farmer.jsp" class="btn btn-primary">ADD NEW</a>
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
										<th>Farmer Code</th>
										<th>Farmer Name</th>
										<th>Program</th>
										<th>Country</th>
										<th>Farm Group</th>
										<th>Field Executive Name</th>
										<th>Country Code</th>
										<th>Mobile Number</th>
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

					$('#country-table').DataTable({
						"processing" : true,
						"serverSide" : true,
						"ajax" : getUrl() + '/service/famer/getAllFarmers',
						"searching": false,
					});

					$.ajax({
						url : getUrl() + '/location/country/getCountries',
						success : function(result) {
							$("#country").html('');
							$("#country").append(
									"<option value=''>Select</option>");
							$(result)
									.each(
											function(k, v) {
												$("#country").append(
														"<option value=" + v.id + ">"
																+ v.name
																+ "</option>");
											});
						}
					});

					$.ajax({
						url : getUrl() + '/master/programme/getPrograms',
						beforeSend : function(request) {
							request.setRequestHeader("user-id", getUserName());
						},
						success : function(result) {
							$("#program").html('');
							$("#program").append(
									"<option value=''>Select</option>");
							$(result)
									.each(
											function(k, v) {
												$("#program").append(
														"<option value=" + v.id + ">"
																+ v.name
																+ "</option>");
											});
							
						}
					});

					$.ajax({
						url : getUrl() + '/master/farm-group/getFarmGroup',
						beforeSend : function(request) {
							request.setRequestHeader("user-id", getUserName());
						},
						success : function(result) {
							$("#farmGroup").html('');
							$("#farmGroup").append(
									"<option value=''>Select</option>");
							$(result)
									.each(
											function(k, v) {
												$("#farmGroup").append(
														"<option value=" + v.id + ">"
																+ v.name
																+ "</option>");
											});
							
						}
					});

				});

		function edit(id) {
			$("body").addClass("show-spinner");
			$.ajax({
				url : getUrl() + '/service/famer/by-id?id='+id,
				beforeSend : function(request) {
					request.setRequestHeader("user-id", getUserName());
				},
				success : function(result) {
					$("#id").val(id);
					$("#farmerCode").val(result.farmerCode);
					$("#farmerName").val(result.name);
					$("#program").val(result.programmeId);
					$("#country").val(result.countryId);
					$("#farmGroup").val(result.farmGroupId);
					$("#fieldExecutiveName").val(result.fieldExecutive);
					$("#country").val(result.countryCode);
					$("#mobileNo").val(result.mobileNumber);
					$('input:radio[name=status]').val([result.typez]);

					$("body").removeClass("show-spinner");
					$("#mbtn").click();
				}
			});
		}


		function submitForm() {
			if ($("#farmerCode").val() == "") {
				alert("Please enter Farmer Code");
				return;
			} else if ($("#farmerName").val() == "") {
				alert("Please enter Farmer name");
				return;
			} else if ($("#program").val() == "") {
				alert("Please enter Program");
				return;
			} else if ($("#country").val() == "") {
				alert("Please enter Country");
				return;
			} else if ($("#farmGroup").val() == "") {
				alert("Please enter Farm Group");
				return;
			} else if ($("#countryCode").val() == "") {
				alert("Please enter Country Code");
				return;
			} else if ($("#mobileNo").val() == "") {
				alert("Please enter Mobile number");
				return;
			}

			var countryData = {
				"name" : $("#farmerName").val(),
				"mobileNumber" : $("#mobileNo").val(),
				"farmerCode" : $("#farmerCode").val(),
				"typez" : $('input[name="status"]:checked').val(),
				"countryCode" : $("#countryCode").val(),
				"programmeId" : $("#program").val(),
				"countryId" : $("#country").val(),
				"farmGroupId" : $("#farmGroup").val(),
				"fieldExecutive" : $("#fieldExecutiveName").val(),
				"id":$("#id").val()
			};

			$.ajax({
				url : getUrl() + '/service/famer/save',
				beforeSend : function(request) {
					request.setRequestHeader("user-id", getUserName());
				},
				type : 'post',
				dataType : 'json',
				contentType : 'application/json',
				data : JSON.stringify(countryData),
				success : function(data) {
					window.location.href = "farmer.jsp";
				},
				error : function(err) {
					console.log(err);
				}
			});
		}

		function deletez(id){
			$("body").addClass("show-spinner");

			  var txt;
			  if (confirm("Are you sure want to delete? Can't restore record once deleted")) {
					$.ajax({
						url : getUrl() + '/service/famer/delete?id='+id,
						beforeSend : function(request) {
							request.setRequestHeader("user-id", getUserName());
						},
						success : function(result) {
							$("body").removeClass("show-spinner");
							window.location.href = "farmer.jsp";
						}
					});
			  } else {
				  $("body").removeClass("show-spinner");
			  }
			
		}
	</script>

</body>

</html>