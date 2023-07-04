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
						<h1>Farm Group Upload</h1>
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
									href="https://cotton-connect-images-dev.s3.ap-south-1.amazonaws.com/template/Farm_group_upload_template.xlsx"
									style="font-weight: bold;">Download template</a>
							</div>

							<div class="float-left">
								<form action=""
									id="add-form" method="post" enctype="multipart/form-data">
									<input type="hidden" name="redirectUrl" id="redirectUrl">
									<div class="row">
										<jsp:include page="filter.jsp" />
									</div>
									<div class="row">
										<div class="col-md-10">
											<input type="file" class="form-control" name="file" accept=".xls,.xlsx">
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
		$(document)
			.ready(
				function () {
					$("#menu-div").load("menu.html");
					$("#menu-header").load("nav.html");
					$("#page-footer").load("footer.html");
					$("#redirectUrl").val(getHomeUrl() + "/farmer_upload.jsp");
					$("#add-form").attr("action", getVideoUploadUrl() + "/upload/farm-group/");
					$(".farm-group").hide();
					$(".learner-group").hide();
					$('#country-table')
						.DataTable(
							{
								"processing": true,
								"serverSide": true,
								"ajax": getUrl()
									+ '/service/knowledge-center/getAllRecords/'
									+ id,
							});

				});
	</script>
</body>

</html>