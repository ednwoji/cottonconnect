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
						<h1>Farmer Upload</h1>
						<nav class="breadcrumb-container d-none d-sm-block d-lg-inline-block" aria-label="breadcrumb">
							<ol class="breadcrumb pt-0">
								<li class="breadcrumb-item"><a href="#">Service</a></li>
								<li class="breadcrumb-item"><a href="farmer_upload.jsp">Farmer
										Upload</a></li>
								<li class="breadcrumb-item active" aria-current="page">Upload</li>
							</ol>
						</nav>
					</div>
				</div>
			</div>
			<div class="card">
				<div class="card-body">
					<div class="row mb-4">
						<div class="col-12 mb-4">

							<div class="float-right">
								<a target="_blank"
									href="http://cottonconnectelearning.in:10000/templates/elearning_farmer.xlsx"
									style="font-weight: bold;">Download template</a>
							</div>

							<div class="float-left">
								<form id="formInput" action="http://cottonconnectelearning.in:10000/app/upload/farmer"
									method="post" enctype="multipart/form-data">

									<!-- <form id="formInput" action="http://localhost:5000/app/upload/farmer"
									method="post" enctype="multipart/form-data"> -->



									<input type="hidden" name="redirectUrl" id="redirectUrl">
									<div class="row">
										<jsp:include page="filter.jsp" />

										<div class="col-md-4">
											<div class="form-group">
												<label>State</label> <select id="state" name="state" class="form-control"
													onchange="populateDistrict()">
													<option>Select</option>
												</select>
											</div>
										</div>


										<div class="col-md-4">
											<div class="form-group">
												<label>District</label> <select id="district" name="district" class="form-control"
													onchange="populateTaluk()">
													<option>Select</option>
												</select>
											</div>
										</div>


										<div class="col-md-4">
											<div class="form-group">
												<label>Taluk</label> <select id="block" name="taluk" class="form-control" onchange="populateVillage()">
													<option>Select</option>
												</select>
											</div>
										</div>

										<div class="col-md-4">
											<div class="form-group">
												<label>Village</label> <select id="village" name="village" class="form-control">
													<option>Select</option>
												</select>
											</div>
										</div>


									</div>
									<div class="row">
										<div class="col-md-10">
											<label>Upload Excel Document(Using the Template)</label>

											<input type="file" class="form-control" name="file" accept=".xls,.xlsx"
												required>
										</div>
										<div class="col-md-2">
											<button class="btn btn-info">Upload</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</main>

	<script src="js/vendor/jquery-3.3.1.min.js"></script>
	<script src="js/vendor/bootstrap.bundle.min.js"></script>
	<script src="js/vendor/Chart.bundle.min.js"></script>
	<script src="js/vendor/chartjs-plugin-datalabels.js"></script>
	<script src="js/vendor/moment.min.js"></script>
	<script src="js/vendor/fullcalendar.min.js"></script>
	<script src="js/vendor/datatables.min.js"></script>
	<script src="js/vendor/perfect-scrollbar.min.js"></script>
	<script src="js/vendor/progressbar.min.js"></script>
	<script src="js/vendor/jquery.barrating.min.js"></script>
	<script src="js/vendor/nouislider.min.js"></script>
	<script src="js/vendor/bootstrap-datepicker.js"></script>
	<script src="js/vendor/Sortable.js"></script>
	<script src="js/vendor/select2.full.js"></script>
	<script src="js/vendor/mousetrap.min.js"></script>
	<script src="js/dore.script.js"></script>
	<script src="js/scripts.js?v=1.1"></script>

	<script type='text/javascript'>
		$(document).ready(function () {


			var currentUrl = window.location.href;

			if (currentUrl.indexOf('failed') !== -1) {
				$('#formInput').prepend('<div class="alert alert-danger text-center border border-info">Please use the correct template and ensure there are no duplicates</div>')
					.find('.alert')
					.fadeIn(300)
					.delay(3000)
					.fadeOut(300, function () { $(this).remove(); });
			}

			else if (currentUrl.indexOf('success') !== -1) {

				$('#formInput').prepend('<div class="alert alert-info text-center border border-info">Data uploaded successfully</div>')
					.find('.alert')
					.fadeIn(300)
					.delay(3000)
					.fadeOut(300, function () { $(this).remove(); });
			}



			$("#menu-div").load("menu.html");
			$("#menu-header").load("nav.html");
			$("#page-footer").load("footer.html");
			$("#redirectUrl").val("http://cottonconnectelearning.in:10000/farmer_upload.jsp");

			$('#country-table')
				.DataTable(
					{
						"processing": true,
						"serverSide": true,
						"ajax": getUrl()
							+ '/service/knowledge-center/getAllRecords/'
							+ id,
					});


			function populateState() {
				var country = $("#country").val();
				$.ajax({
					url: getUrl() + '/location/state/getStatesByCountry?countryId=' + country + '', success: function (result) {
						$("#state").html('');
						$("#state").append("<option value=''>Select</option>");
						$(result).each(function (k, v) {
							$("#state").append("<option value=" + v.id + ">" + v.name + "</option>");
						});
					}
				});
			}




			function populateDistrict() {
				var state = $("#state").val();
				$.ajax({
					url: getUrl() + '/location/district/getDistrictsByState?stateId=' + state + '', success: function (result) {
						$("#district").html('');
						$("#district").append("<option value=''>Select</option>");
						$(result).each(function (k, v) {
							$("#district").append("<option value=" + v.id + ">" + v.name + "</option>");
						});
					}
				});
			}

			function populateTaluk() {
				var disitrict = $("#district").val();
				$.ajax({
					url: getUrl() + '/location/taluk/getTaluksByDistrict?districtId=' + disitrict + '',
					success: function (result) {
						$("#block").html('');
						$("#block").append("<option value=''>Select</option>");
						$(result).each(function (k, v) {
							$("#block").append("<option value=" + v.id + ">" + v.name + "</option>");
						});
					}
				});
			}


			function populateVillage() {
				var taluk = $("#block").val();
				$.ajax({
					url: getUrl() + '/location/village/getVillageByTaluk?talukId=' + taluk + '',
					success: function (result) {
						$("#village").html('');
						$("#village").append("<option value=''>Select</option>");
						$(result).each(function (k, v) {
							$("#village").append("<option value=" + v.id + ">" + v.name + "</option>");
						});
					}
				});
			}



		});
	</script>
</body>

</html>