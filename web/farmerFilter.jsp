<script src="js/vendor/jquery-3.3.1.min.js"></script>
<script src="js/vendor/bootstrap.bundle.min.js"></script>
<script src="js/vendor/perfect-scrollbar.min.js"></script>
<script src="js/vendor/datatables.min.js"></script>
<script src="js/dore.script.js"></script>
<script src="js/scripts.js?v=1.1"></script>

<div class="col-md-4">
	<div class="form-group">
		<label>Country <sup>*</sup></label> <select id="country" name="country" onchange="handleCountryOnChange()"
			required class="form-control"></select>
	</div>
</div>

<div class="col-md-4">
	<div class="form-group">
		<label>State <sup>*</sup></label> <select id="state" name="state" class="form-control"
			onchange="handleStateOnChange()" required></select>
	</div>
</div>

<div class="col-md-4">
	<div class="form-group">
		<label>District <sup>*</sup></label> <select id="district" name="district" class="form-control"
			onchange="handleDistrictOnChange()" required></select>
	</div>
</div>

<div class="col-md-4">
	<div class="form-group">
		<label>Taluk <sup>*</sup></label> <select id="taluk" name="taluk" class="form-control"
			onchange="handleTalukOnChange()" required></select>
	</div>
</div>

<div class="col-md-4">
	<div class="form-group">
		<label>Village <sup>*</sup></label> <select id="village" name="village" class="form-control" required></select>
	</div>
</div>

<div class="col-md-4">
	<div class="form-group">
		<label>Brand</label> <select id="brand" class="form-control select2" name="brand"
			onchange="populateProgram()" required></select>
	</div>
</div>
<div class="col-md-4">
	<div class="form-group">
		<label>Program <sup>*</sup></label> <select id="program" name="program" class="form-control"
			onchange="populatePartners()" required></select>
	</div>
</div>

<div class="col-md-4 local-partner">
	<div class="form-group">
		<label>Local Partner <sup>*</sup></label> <select id="partner" 
		onchange="populateFarmGroup()"
		required
			name="localPartner" class="form-control select2-multiple"></select>
	</div>
</div>

<div class="col-md-4 farm-group">
	<div class="form-group">
		<label>Farm Group <sup>*</sup></label> <select id="farmGroup" onchange="populateLearners()" name="farmGroup"
		required
			class="form-control select2-multiple"></select>
	</div>
</div>

<div class="col-md-4 learner-group">
	<div class="form-group">
		<label>Learner Group <sup>*</sup></label> <select id="learners" name="learnerGroup"
			class="form-control select2-multiple" required></select>
	</div>
</div>

<script type='text/javascript'>
	$(document).ready(
		function () {
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
		$.ajax({
			url: getUrl() + '/master/brand/countries?id=' + country,
			beforeSend: function (request) {
				request.setRequestHeader("user-id", getUserName());
			},
			type: 'post',
			dataType: 'json',
			contentType: 'application/json',
			success: function (result) {
				$("#brand").html('');
				$("#brand").append("<option value=''>Select</option>");
				$(result).each(
					function (k, v) {
						$("#brand").append(
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