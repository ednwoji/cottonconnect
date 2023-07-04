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
						<h1>Weather Advisory Broadcast Upload</h1>
					</div>
				</div>
			</div>
			<div class="card">
				<div class="card-body">
					<div class="row mb-4">
						<div class="col-12 mb-4">
							<form id="add-form" method="post" enctype="multipart/form-data">
								<input type="hidden" name="redirectUrl" id="redirectUrl">
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
												onchange="handleCountryOnChange()" required
												class="form-control"></select>
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label>State <sup>*</sup></label> <select id="state" name="state"
												class="form-control" onchange="handleStateOnChange()" required></select>
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label>District <sup>*</sup></label> <select id="district" name="district"
												class="form-control" onchange="handleDistrictOnChange()"
												required></select>
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label>Taluk </label> <select id="taluk" name="taluk"
												class="form-control" onchange="handleTalukOnChange()"></select>
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label>Village</label> <select id="village" name="village"
												class="form-control"></select>
										</div>
									</div>
								</div>
								<div class="row">

									<div class="col-md-4">
										<div class="form-group">
											<label>Upload<b style="color: red">[.mp4]</b> [Max size : Upto to
												100MB]</label>
											<input type="file" class="form-control" name="video" accept=".mp4" required>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label>Upload<b style="color: red">[.mp3]</b> [Max size : Upto to
												6MB]</label>
											<input type="file" class="form-control" name="audio" accept=".mp3" required>
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label>Upload<b style="color: red">[.pdf]</b> [Max size : Upto to
												7MB]</label>
											<input type="file" class="form-control" name="document" accept=".pdf" required>
										</div>
									</div>
								</div>

								<div class="modal-footer">
									<a href="weatherBroadcast.jsp" class="btn btn-outline-primary">Cancel</a>
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
					$("#redirectUrl").val(getHomeUrl() + "/weatherBroadcast.jsp");
					$("#add-form").attr("action", getUrl() + "/weatherBroadcast/save/");
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


				});



		function handleCountryOnChange () {
			var country = $("#country").val();
			$.ajax({
				url: getUrl() + '/location/state/getStatesByCountry?countryId=' + country,
				beforeSend: function (request) {
					request.setRequestHeader("user-id", getUserName());
				},
				type: 'post',
				dataType: 'json',
				contentType: 'application/json',
				success: function (result) {
					$("#state").html('');
					$("#state").append("<option value=''>Select</option>");
					$(result).each(
						function (k, v) {
							$("#state").append(
								"<option value=" + v.id + ">" + v.name
								+ "</option>");
						});
				},
				error: function (err) {
					console.log(err);
				}
			});

		}

		function handleStateOnChange () {
			let state = $("#state").val();
			if (state) {
				$.ajax({
					url: getUrl() + '/location/district/getDistrictsByState?stateId=' + state,
					beforeSend: function (request) {
						request.setRequestHeader("user-id", getUserName());
					},
					type: 'post',
					dataType: 'json',
					contentType: 'application/json',
					success: function (result) {
						$("#district").html('');
						$("#district").append("<option value=''>Select</option>");
						$(result).each(
							function (k, v) {
								$("#district").append(
									"<option value=" + v.id + ">" + v.name
									+ "</option>");
							});
					},
					error: function (err) {
						console.log(err);
					}
				});
			}

		}

		function handleDistrictOnChange () {
			let district = $("#district").val();
			if (district) {
				$.ajax({
					url: getUrl() + '/location/taluk/getTaluksByDistrict?districtId=' + district,
					beforeSend: function (request) {
						request.setRequestHeader("user-id", getUserName());
					},
					type: 'post',
					dataType: 'json',
					contentType: 'application/json',
					success: function (result) {
						$("#taluk").html('');
						$("#taluk").append("<option value=''>Select</option>");
						$(result).each(
							function (k, v) {
								$("#taluk").append(
									"<option value=" + v.id + ">" + v.name
									+ "</option>");
							});
					},
					error: function (err) {
						console.log(err);
					}
				});
			}

		}

		function handleTalukOnChange () {
			let taluk = $("#taluk").val();
			if (taluk) {
				$.ajax({
					url: getUrl() + '/location/village/getVillageByTaluk?talukId=' + taluk,
					beforeSend: function (request) {
						request.setRequestHeader("user-id", getUserName());
					},
					type: 'post',
					dataType: 'json',
					contentType: 'application/json',
					success: function (result) {
						$("#village").html('');
						$("#village").append("<option value=''>Select</option>");
						$(result).each(
							function (k, v) {
								$("#village").append(
									"<option value=" + v.id + ">" + v.name
									+ "</option>");
							});
					},
					error: function (err) {
						console.log(err);
					}
				});
			}

		}

		function populateProgram () {
			var brand = $("#brand").val();
			$.ajax({
				url: getUrl() + '/master/programme/by-brands?brandId=' + brand
					+ '',
				beforeSend: function (request) {
					request.setRequestHeader("user-id", getUserName());
				},
				success: function (result) {
					$("#program").html('');
					$("#program").append("<option value=''>Select</option>");
					$(result).each(
						function (k, v) {
							$("#program").append(
								"<option value=" + v.id + ">" + v.name
								+ "</option>");
						});
				}
			});
		}

		function populatePartners () {
			var program = $("#program").val();
			$.ajax({
				url: getUrl() + '/master/partner/by-programs?program=' + program
					+ '',
				beforeSend: function (request) {
					request.setRequestHeader("user-id", getUserName());
				},
				success: function (result) {
					$("#partner").html('');
					$("#partner").append("<option value=''>Select</option>");
					$(result).each(
						function (k, v) {
							$("#partner").append(
								"<option value=" + v.id + ">" + v.name
								+ "</option>");
						});
				}
			});
		}

		function populateFarmGroup () {
			var partner = $("#partner").val();
			$.ajax({
				url: getUrl() + '/master/farm-group/partners?partners=' + partner
					+ '',
				beforeSend: function (request) {
					request.setRequestHeader("user-id", getUserName());
				},
				success: function (result) {
					$("#farmGroup").html('');
					$("#farmGroup").append("<option value=''>Select</option>");
					$(result).each(
						function (k, v) {
							$("#farmGroup").append(
								"<option value=" + v.id + ">" + v.name
								+ "</option>");
						});
				}
			});
		}

		function populateLearners () {
			var farmGroups = $("#farmGroup").val();
			$.ajax({
				url: getUrl() + '/master/learner/by-fgs?fg=' + farmGroups + '',
				beforeSend: function (request) {
					request.setRequestHeader("user-id", getUserName());
				},
				success: function (result) {
					$("#learners").html('');
					$("#learners").append("<option value=''>Select</option>");
					$(result).each(
						function (k, v) {
							$("#learners").append(
								"<option value=" + v.id + ">" + v.name
								+ "</option>");
						});
				}
			});
		}

		function listBrand (countrId, brandId) {
			$.ajax({
				url: getUrl() + '/master/brand/countries?id=' + countrId,
				success: function (result) {
					$("#brand").html('');
					$("#brand").append("<option value=''>Select</option>");
					$(result).each(
						function (k, v) {
							$("#brand").append(
								"<option value=" + v.id + ">" + v.name
								+ "</option>");
						});
					$("#brand").val(brandId);
					$("#country").val(countrId);
				}
			});
		}

		function listProgram (brandId, program) {
			$.ajax({
				url: getUrl() + '/master/programme/by-brands?brandId=' + brandId
					+ '',
				beforeSend: function (request) {
					request.setRequestHeader("user-id", getUserName());
				},
				success: function (result) {
					$("#program").html('');
					$("#program").append("<option value=''>Select</option>");
					$(result).each(
						function (k, v) {
							$("#program").append(
								"<option value=" + v.id + ">" + v.name
								+ "</option>");
						});
					$("#program").val(program);
				}
			});
		}

		function listPartner (program, partner) {
			$.ajax({
				url: getUrl() + '/master/partner/by-programs?program=' + program
					+ '',
				beforeSend: function (request) {
					request.setRequestHeader("user-id", getUserName());
				},
				success: function (result) {
					$("#partner").html('');
					$("#partner").append("<option value=''>Select</option>");
					$(result).each(
						function (k, v) {
							$("#partner").append(
								"<option value=" + v.id + ">" + v.name
								+ "</option>");
						});
					$("#partner").val(partner);
				}
			});
		}

		function listFarmGroup (partner, fg) {
			$.ajax({
				url: getUrl() + '/master/farm-group/partners?partners=' + partner
					+ '',
				beforeSend: function (request) {
					request.setRequestHeader("user-id", getUserName());
				},
				success: function (result) {
					$("#farmGroup").html('');
					$("#farmGroup").append("<option value=''>Select</option>");
					$(result).each(
						function (k, v) {
							$("#farmGroup").append(
								"<option value=" + v.id + ">" + v.name
								+ "</option>");
						});
					$("#farmGroup").val(fg);
				}
			});
		}

		function listLearnerGroup (fg, lg) {
			$.ajax({
				url: getUrl() + '/master/learner/by-fgs?fg=' + fg + '',
				beforeSend: function (request) {
					request.setRequestHeader("user-id", getUserName());
				},
				success: function (result) {
					$("#learners").html('');
					$("#learners").append("<option value=''>Select</option>");
					$(result).each(
						function (k, v) {
							$("#learners").append(
								"<option value=" + v.id + ">" + v.name
								+ "</option>");
						});
					$("#learners").val(lg);
				}
			});
		}

		function listCountry (countries) {
			$("#country").val(countries);
		}

		function setUpdate (result) {
			listCountry(result.countries);
			listBrand(result.countries, result.brands);
			listProgram(result.brands, result.programs);
			listPartner(result.programs, result.localPartners);
			listFarmGroup(result.localPartners, result.farmGroups);
			listLearnerGroup(result.farmGroups, result.learners);
		}

	</script>
</body>

</html>