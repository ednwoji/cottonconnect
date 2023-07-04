
<script src="js/vendor/jquery-3.3.1.min.js"></script>
<script src="js/vendor/bootstrap.bundle.min.js"></script>
<script src="js/vendor/perfect-scrollbar.min.js"></script>
<script src="js/vendor/datatables.min.js"></script>
<script src="js/dore.script.js"></script>
<script src="js/scripts.js?v=1.1"></script>

<div class="col-md-4">
	<div class="form-group">
		<label>Country <sup>*</sup></label> <select id="country"
			required="required" name="country" onchange="populateBrand()"
			class="form-control"></select>
	</div>
</div>

<div class="col-md-4">
	<div class="form-group">
		<label>Brand</label> <select id="brand" class="form-control"
			name="brand" required onchange="populateProgram()"></select>
	</div>
</div>
<div class="col-md-4">
	<div class="form-group">
		<label>Program <sup>*</sup></label> <select id="program"
			name="program" required class="form-control select2-multiple"
			onchange="populatePartners()"></select>
	</div>
</div>

<div class="col-md-4">
	<div class="form-group">
		<label>Local Partner <sup>*</sup></label> <select id="partner"
			onchange="populateFarmGroup()" name="partner" required
			class="form-control"></select>
	</div>
</div>
<div class="col-md-4">
	<div class="form-group">
		<label>Farm Group <sup>*</sup></label> <select id="farmGroup"
			name="farmGroup" required class="form-control"></select>
	</div>
</div>


<script type='text/javascript'>
	$(document).ready(
			function() {
				$.ajax({
					url : getUrl() + '/location/country/getCountries',
					success : function(result) {
						$("#country").html('');
						$("#country")
								.append("<option value=''>Select</option>");
						$(result).each(
								function(k, v) {
									$("#country").append(
											"<option value=" + v.id + ">"
													+ v.name + "</option>");
								});

					}
				});

			});

	function populateBrand() {
		var country = $("#country").val();
		$.ajax({
			url : getUrl() + '/master/brand/country/' + country,
			beforeSend : function(request) {
				request.setRequestHeader("user-id", getUserName());
			},
			type : 'post',
			dataType : 'json',
			contentType : 'application/json',
			success : function(result) {
				$("#brand").html('');
				$("#brand").append("<option value=''>Select</option>");
				$(result).each(
						function(k, v) {
							$("#brand").append(
									"<option value=" + v.id + ">" + v.name
											+ "</option>");
						});
			},
			error : function(err) {
				console.log(err);
			}
		});
	}

	function populateProgram() {
		var brand = $("#brand").val();
		$
				.ajax({
					url : getUrl() + '/master/programme/by-brand?brandId='
							+ brand + '',
					beforeSend : function(request) {
						request.setRequestHeader("user-id", getUserName());
					},
					success : function(result) {
						$("#program").html('');
						$("#program")
								.append("<option value=''>Select</option>");
						$(result).each(
								function(k, v) {
									$("#program").append(
											"<option value=" + v.id + ">"
													+ v.name + "</option>");
								});
					}
				});
	}

	function populatePartners() {
		var program = $("#program").val();
		$.ajax({
			url : getUrl() + '/master/partner/by-program?program=' + program
					+ '',
			beforeSend : function(request) {
				request.setRequestHeader("user-id", getUserName());
			},
			success : function(result) {
				$("#partner").html('');
				$("#partner").append("<option value=''>Select</option>");
				$(result).each(
						function(k, v) {
							$("#partner").append(
									"<option value=" + v.id + ">" + v.name
											+ "</option>");
						});
			}
		});
	}

	function populateFarmGroup() {
		var partner = $("#partner").val();
		$.ajax({
			url : getUrl() + '/master/farm-group/partner?partner=' + partner
					+ '',
			beforeSend : function(request) {
				request.setRequestHeader("user-id", getUserName());
			},
			success : function(result) {
				$("#farmGroup").html('');
				$("#farmGroup").append("<option value=''>Select</option>");
				$(result).each(
						function(k, v) {
							$("#farmGroup").append(
									"<option value=" + v.id + ">" + v.name
											+ "</option>");
						});
			}
		});
	}

	function listBrand(countrId, brandId) {
		$.ajax({
			url : getUrl() + '/master/brand/country/' + countrId,
			success : function(result) {
				$("#brand").html('');
				$("#brand").append("<option value=''>Select</option>");
				$(result).each(
						function(k, v) {
							$("#brand").append(
									"<option value=" + v.id + ">" + v.name
											+ "</option>");
						});
				$("#brand").val(brandId);
			}
		});
	}

	function listProgram(brandId, program) {
		$.ajax({
			url : getUrl() + '/master/programme/by-brand?brandId=' + brandId
					+ '',
			beforeSend : function(request) {
				request.setRequestHeader("user-id", getUserName());
			},
			success : function(result) {
				$("#program").html('');
				$("#program").append("<option value=''>Select</option>");
				$(result).each(
						function(k, v) {
							$("#program").append(
									"<option value=" + v.id + ">" + v.name
											+ "</option>");
						});
				$("#program").val(program);
			}
		});
	}

	function listPartner(program, partner) {
		$.ajax({
			url : getUrl() + '/master/partner/by-program?program=' + program
					+ '',
			beforeSend : function(request) {
				request.setRequestHeader("user-id", getUserName());
			},
			success : function(result) {
				$("#partner").html('');
				$("#partner").append("<option value=''>Select</option>");
				$(result).each(
						function(k, v) {
							$("#partner").append(
									"<option value=" + v.id + ">" + v.name
											+ "</option>");
						});
				$("#partner").val(partner);
			}
		});
	}

	function listFarmGroup(partner, fg) {
		$.ajax({
			url : getUrl() + '/master/farm-group/partner?partner=' + partner
					+ '',
			beforeSend : function(request) {
				request.setRequestHeader("user-id", getUserName());
			},
			success : function(result) {
				$("#farmGroup").html('');
				$("#farmGroup").append("<option value=''>Select</option>");
				$(result).each(
						function(k, v) {
							$("#farmGroup").append(
									"<option value=" + v.id + ">" + v.name
											+ "</option>");
						});
				$("#farmGroup").val(fg);
			}
		});
	}

	function listLearnerGroup(fg, lg) {
		$.ajax({
			url : getUrl() + '/master/learner/by-fg?fg=' + fg + '',
			beforeSend : function(request) {
				request.setRequestHeader("user-id", getUserName());
			},
			success : function(result) {
				$("#learnerGroup").html('');
				$("#learnerGroup").append("<option value=''>Select</option>");
				$(result).each(
						function(k, v) {
							$("#learnerGroup").append(
									"<option value=" + v.id + ">" + v.name
											+ "</option>");
						});
				$("#learnerGroup").val(lg);
			}
		});
	}

	function setUpdate(result) {
		listBrand(result.country, result.brand);
		listProgram(result.brand, result.program);
		listPartner(result.program, result.partner);
		listFarmGroup(result.partner, result.farmGroup);
	}
</script>