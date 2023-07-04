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
						<h1>Farmer</h1>
						<nav
							class="breadcrumb-container d-none d-sm-block d-lg-inline-block"
							aria-label="breadcrumb">
							<ol class="breadcrumb pt-0">
								<li class="breadcrumb-item"><a href="#">Service</a></li>
								<li class="breadcrumb-item"><a href="farmerList.jsp">Farmer</a></li>
								<li class="breadcrumb-item active" aria-current="page">Add</li>
							</ol>
						</nav>
					</div>
				</div>
			</div>

			<div class="row mb-4">
				<div class="col-12 mb-4">
					<div class="card">
						<div class="card-body">
							<div class="row">
								<div class="col-md-4">
									<input type="hidden" id="id">
									<div class="form-group">
										<label>Farmer Code<sup>*</sup></label> <input type="text"
											class="form-control" required id="farmerCode">
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label>Farmer Name<sup>*</sup></label> <input type="text"
											class="form-control" required id="farmerName">
									</div>
								</div>
								<jsp:include page="country_filter.jsp" />
								<div class="col-md-4">
									<div class="form-group">
										<label>Learner Group <sup>*</sup></label> <select
											id="learners" name="learners" required class="form-control"></select>
									</div>
								</div>
								<div class="col-md-4" style="display: none;">
									<div class="form-group">
										<label>Field Executive Name<sup>*</sup></label> <input
											type="text" class="form-control" required value="-"
											id="fieldExecutiveName">
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label>Country Code<sup>*</sup></label> <input type="text"
											class="form-control" required id="countryCode">
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label>Mobile Number<sup>*</sup></label> <input type="text"
											class="form-control" required id="mobileNo">
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label>Farmer Type</label><br /> <label> <input
											type="radio" value="0" name="status">Demo
										</label> <label> <input type="radio" value="1" name="status"
											checked="checked">Lead
										</label>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<a href="farmerList.jsp" class="btn btn-outline-primary">Cancel</a>
								<button class="btn btn-primary" onclick="submitForm()">Submit</button>
							</div>
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
		$(document)
				.ready(
						function() {
							$("#menu-div").load("menu.html");
							$("#menu-header").load("nav.html");
							$("#page-footer").load("footer.html");

							$("#redirectUrl").val( getHomeUrl() + '/farmerList.jsp');
						});

		$("#farmGroup").change(function() {
			populateLearnerGroup();
		});

		function populateLearnerGroup() {
			var fg = $("#farmGroup").val();
			$.ajax({
				url : getUrl() + '/master//learner/by-fg?fg=' + fg,
				beforeSend : function(request) {
					request.setRequestHeader("user-id", getUserName());
				},
				type : 'post',
				dataType : 'json',
				contentType : 'application/json',
				success : function(result) {
					$("#learners").html('');
					$("#learners").append("<option value=''>Select</option>");
					$(result).each(
							function(k, v) {
								$("#learners").append(
										"<option value=" + v.id + ">" + v.name
												+ "</option>");
							});
				},
				error : function(err) {
					console.log(err);
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
				"id" : $("#id").val(),
				"name" : $("#farmerName").val(),
				"countryId" : $("#country").val(),
				"brandId" : $("#brand").val(),
				"programmeId" : $("#program").val(),
				"farmGroupId" : $("#farmGroup").val(),
				"learnerGroupId" : $("#learners").val(),
				"countryCode" : $("#countryCode").val(),
				"mobileNumber" : $("#mobileNo").val(),
				"farmerCode" : $("#farmerCode").val(),
				"typez" : $('input[name="status"]:checked').val(),
				"fieldExecutive" : $("#fieldExecutiveName").val(),
				"learnerGroup" : $("#learners").val(),
				"brand" : $("#brand").val()
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
					window.location.href = "farmerList.jsp";
				},
				error : function(err) {
					console.log(err);
				}
			});
		}
	</script>
</body>