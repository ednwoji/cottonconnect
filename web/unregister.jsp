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
						<h1>Unregister</h1>
						<nav
							class="breadcrumb-container d-none d-sm-block d-lg-inline-block"
							aria-label="breadcrumb">
							<ol class="breadcrumb pt-0">
								<li class="breadcrumb-item"><a href="#">Settings</a></li>
								<li class="breadcrumb-item"><a href="inspectList.jsp">Unregister</a></li>
								<li class="breadcrumb-item active" aria-current="page">Edit</li>
							</ol>
						</nav>
					</div>
				</div>
			</div>

			<div class="row mb-4">
				<div class="col-12 mb-4">
					<div class="card">
						<div class="card-body">
							<div class="table-responsive">
								<table class="table">
									<thead>
										<tr>
											<th colspan="4" style="text-align: center;background-color: #0b265d;color: white;"><p>Personal Info</p></th>
										</tr>
										<tr>
											<td>Name</td>
											<td>Organization</td>
											<td>Country</td>
											<td>Mobile number</td>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td id="name"></td>
											<td id="organisation"></td>
											<td id="countries"></td>
											<td id="mobile"></td>
										</tr>
									</tbody>
								</table>
								<table class="table">
									<thead>
										<tr>
											<th colspan="4" style="text-align: center;background-color: #0b265d;color: white;"><p>Device Info</p></th>
										</tr>
										<tr>
											<td>Brand</td>
											<td>IMEI</td>
											<td>Latitude</td>
											<td>Longitude</td>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td id="manufacturer"></td>
											<td id="imei"></td>
											<td id="lat"></td>
											<td id="lon"></td>
										</tr>
									</tbody>
								</table>
							</div>
							<hr />
							<div class="row">
								<jsp:include page="ccfilter.jsp" />
							</div>
							<div class="modal-footer">
								<a href="unregisterList.jsp" class="btn btn-outline-primary">Cancel</a>
								<button class="btn btn-primary" onclick="submitData()">Submit</button>
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
	<script src="js/vendor/select2.full.js"></script>

	<script type='text/javascript'>
		$.urlParam = function(name) {
			try {
				var results = new RegExp('[\?&]' + name + '=([^&#]*)')
						.exec(window.location.href);
				return results[1] || 0;
			} catch (e) {
			}
		}

		$(document).ready(function() {
			$("#menu-div").load("menu.html");
			$("#menu-header").load("nav.html");
			$("#page-footer").load("footer.html");

			var id = $.urlParam('id');
			if(id!=null){
				$.ajax({
					url : getUrl()+ '/device/register/by-id?id='+ id,
					beforeSend : function(request) {
						request.setRequestHeader(
								"user-id",
								getUserName());
					},
					success : function(result) {
						$("#id").val(id);
						$("#name").html("<b>"+result.name+"</b>");
						$("#organisation").html("<b>"+result.organisation+"</b>");
						$("#countries").html("<b>"+result.countryName+"</b>");
						$("#mobile").html("<b>"+result.mobileNumber+"</b>");
						$("#manufacturer").html("<b>"+result.manufacturer+"</b>");
						$("#imei").html("<b>"+result.imei+"</b>");
						$("#lat").html("<b>"+result.lat+"</b>");
						$("#lon").html("<b>"+result.lon+"</b>");

						setUpdate(result);
					}
				});
			}
		
		});

		function submitData(){
			$("#redirectUrl").val(getHomeUrl() +'/inspectList.jsp');
			var id = $.urlParam('id');
			var countryData = {
				"id" : id,
				"countries":$("#country").val(),
				"brands":$("#brand").val(),
				"programs":$("#program").val(),
				"localPartners":$("#localPartners").val(),
				"farmGroups":$("#farmGroup").val(),
				"learners":$("#learners").val(),
			}

			$.ajax({ 
				url : getUrl()+ '/device/approve/',
				beforeSend : function(request) {
					request.setRequestHeader("user-id", getUserName());
				},
				type : 'post',
				dataType : 'json',
				contentType : 'application/json',
				data : JSON.stringify(countryData),
				success : function(data) {
					window.location.href = "unregisterList.jsp";
				},
				error : function(err) {
					window.location.href = "unregisterList.jsp";
					console.log(err);
				}
				
			});
			
		}
	</script>
</body>