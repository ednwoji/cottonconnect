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
						<h1>CICR Advisory Broadcast Upload</h1>
					</div>
				</div>
			</div>
			<div class="card">
				<div class="card-body">
					<div class="row mb-4">
						<div class="col-12 mb-4">
							<form id="add-form" method="post" enctype="multipart/form-data" onsubmit=" 
							return validateForm('add-form')" onchange="validateForm('add-form')">
								<input type="hidden" name="redirectUrl" id="redirectUrl">
								<input type="hidden" name="id" id="id">
								<div class="row">
									<script src="js/vendor/jquery-3.3.1.min.js"></script>
									<script src="js/vendor/bootstrap.bundle.min.js"></script>
									<script src="js/vendor/perfect-scrollbar.min.js"></script>
									<script src="js/vendor/datatables.min.js"></script>
									<script src="js/dore.script.js"></script>
									<script src="js/scripts.js?v=1.1"></script>

									<div class="col-md-4">
										<div class="form-group">
											<label>Country <sup>*</sup></label> <select id="country" name="country"
												onchange="handleCountryOnChange()" class="form-control req"></select>
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label>State <sup>*</sup></label> <select id="state" name="state"
												class="form-control req" onchange="handleStateOnChange()"></select>
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label>District <sup>*</sup></label> <select id="district" name="district"
												class="form-control req" onchange="handleDistrictOnChange()"></select>
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label>Taluk <sup>*</sup></label> <select id="taluk" name="taluk"
												class="form-control req" onchange="handleTalukOnChange()"></select>
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label>Village <sup>*</sup></label> <select id="village" name="village"
												class="form-control req"></select>
										</div>
									</div>
								</div>
								<div class="row">

									<div class="col-md-4">
										<div class="form-group">
											<label>Upload Video [Max size : Upto to
												100MB]</label>
											<input type="file" class="form-control req req-file" name="video"
												accept=".mp4" id="video-file" style="margin-bottom: 10px;">
											<a class='detail' target='_blank' id="video-file-view"
												style="margin-left: 10px;">View </a>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label>Upload Audio [Max size : Upto to
												6MB]</label>
											<input type="file" class="form-control req req-file" name="audio"
												accept=".mp3" id="audio-file" style="margin-bottom: 10px;">
											<a class='detail' style="margin-left: 10px;" target='_blank'
												id="audio-file-view">View </a>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label>Upload PDF [Max size : Upto to
												7MB]</label>
											<input type="file" class="form-control req req-file" name="document"
												accept=".pdf" id="doc-file" style="margin-bottom: 10px;">
											<a class='detail' target='_blank' id="doc-file-view"
												style="margin-left: 10px;">View </a>
										</div>
									</div>
								</div>

								<div class="modal-footer">
									<a href="cicrBroadcast.jsp" class="btn btn-outline-primary">Cancel</a>
									<button class="btn btn-primary">Submit</button>
								</div>

							</form>
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
					$("#video-file-view").hide();
					$("#audio-file-view").hide();
					$("#doc-file-view").hide();
					$("#redirectUrl").val(getHomeUrl() + "/cicrBroadcast.jsp");
					$("#add-form").attr("action", getUrl() + "/cicrBroadcast/save/");
					$.ajax({
						url: getUrl() + '/location/country/getCountries',
						success: function (result) {
							$("#country").html('');
							$("#country")
								.append("<option value=''>Select</option>");
							$(result).each(
								function (k, v) {
									$("#country").append(
										"<option value=" + v.id + ">"
										+ v.name + "</option>");
								});

						}
					});
					const urlParams = new URLSearchParams(window.location.search);
					const id = urlParams.get('id');
					if (id) {
						edit(id);
					}


				});


		const edit = async (id) => {
			try {
				if (id) {
					$("#id").val(id);
					$("#video-file").removeClass("req req-file");
					$("#audio-file").removeClass("req req-file");
					$("#doc-file").removeClass("req req-file");
					const res = await fetch(
						getUrl() + '/cicrBroadcast/get?id=' + id, {
						headers: {
							"user-id": getUserName()
						},
					});
					const broadcast = await res.json();

					$("#country").val(broadcast.country);
					await handleCountryOnChange();
					$("#state").val(broadcast.state);
					await handleStateOnChange();
					$("#district").val(broadcast.district);
					await handleDistrictOnChange();
					$("#taluk").val(broadcast.taluk);
					await handleTalukOnChange();
					$("#village").val(broadcast.village);
					$("#village").val(broadcast.village);
					$("#video-file-view").show();
					$("#video-file-view").attr('href', broadcast.videoUrl);
					$("#audio-file-view").show();
					$("#audio-file-view").attr('href', broadcast.audioUrl);
					$("#doc-file-view").show();
					$("#doc-file-view").attr('href', broadcast.documentUrl);


				}
			} catch (error) {
				console.log(error);
			}

		};



		async function handleCountryOnChange () {
			try {
				const country = $("#country").val();
				if (country) {
					const res = await fetch(
						getUrl() + '/location/state/getStatesByCountry?countryId=' + country, {
						headers: {
							"user-id": getUserName()
						},
					});
					const stateList = await res.json();
					$("#state").html('');
					$("#state").append("<option value=''>Select</option>");
					$(stateList).each((k, v) => {
						$("#state").append(
							"<option value=" + v.id + ">" + v.name
							+ "</option>");
					});
				}

			} catch (error) {
				console.log(error);
			}
		}

		async function handleStateOnChange () {
			try {
				const state = $("#state").val();
				if (state) {
					const res = await fetch(
						getUrl() + '/location/district/getDistrictsByState?stateId=' + state, {
						headers: {
							"user-id": getUserName()
						},
					});
					const districtList = await res.json();
					$("#district").html('');
					$("#district").append("<option value=''>Select</option>");
					$(districtList).each(
						function (k, v) {
							$("#district").append(
								"<option value=" + v.id + ">" + v.name
								+ "</option>");
						});
				}
			} catch (error) {
				console.log(error);
			}
		}

		async function handleDistrictOnChange () {
			try {
				const district = $("#district").val();
				if (district) {
					const res = await fetch(
						getUrl() + '/location/taluk/getTaluksByDistrict?districtId=' + district, {
						headers: {
							"user-id": getUserName()
						},
					});
					const talukList = await res.json();
					$("#taluk").html('');
					$("#taluk").append("<option value=''>Select</option>");
					$(talukList).each(
						function (k, v) {
							$("#taluk").append(
								"<option value=" + v.id + ">" + v.name
								+ "</option>");
						});
				}
			} catch (error) {
				console.log(error);
			}


		}

		async function handleTalukOnChange () {
			try {
				const taluk = $("#taluk").val();
				if (taluk) {
					const res = await fetch(
						getUrl() + '/location/village/getVillageByTaluk?talukId=' + taluk, {
						headers: {
							"user-id": getUserName()
						},
					});
					const villageList = await res.json();
					$("#village").html('');
					$("#village").append("<option value=''>Select</option>");
					$(villageList).each(
						function (k, v) {
							$("#village").append(
								"<option value=" + v.id + ">" + v.name
								+ "</option>");
						});
				}

			} catch (error) {
				console.log(error);
			}

		}



	</script>
</body>

</html>