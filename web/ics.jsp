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
						<h1>Learner Group / ICS</h1>
						<nav
							class="breadcrumb-container d-none d-sm-block d-lg-inline-block"
							aria-label="breadcrumb">
							<ol class="breadcrumb pt-0">
								<li class="breadcrumb-item"><a href="#">Home</a></li>
								<li class="breadcrumb-item"><a href="icsList.jsp">Learner
										Group / ICS</a></li>
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
								<jsp:include page="country_filter.jsp" />
							</div>
							<div class="row">
								<input type="hidden" id="id">
								<div class="col-md-4">
									<div class="form-group">
										<label>Name<sup>*</sup></label> <input type="text"
											class="form-control" id="name">
									</div>
								</div>
								<div class="col-md-4 latlon">
									<div class="form-group">
										<label>Latitude<sup>*</sup></label> <input type="text"
											class="form-control" required id="latitude">
									</div>
								</div>
								<div class="col-md-4 latlon">
									<div class="form-group">
										<label>Longitude<sup>*</sup></label> <input type="text"
											class="form-control" required id="longitude">
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label>Number of Farmers<sup>*</sup></label> <input
											type="text" class="form-control" required id="noOfFarmers">
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label>Acreage<sup>*</sup></label> <input type="text"
											class="form-control" required id="acreage">
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label>Estimated Yield<sup>*</sup></label> <input type="text"
											class="form-control" required id="yield">
									</div>
								</div>

							</div>
							<div class="modal-footer">
								<a href="icsList.jsp" class="btn btn-outline-primary">Cancel</a>
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
	<script src="js/vendor/select2.full.js"></script>

	<script type='text/javascript'>
	
	jQuery.fn.ForceNumericOnly = function() {
		return this
				.each(function() {
					$(this)
							.keydown(
									function(e) {
										var key = e.charCode || e.keyCode
												|| 0;
										return (key == 8 || key == 9
												|| key == 13 || key == 46
												|| key == 110 || key == 190
												|| (key >= 35 && key <= 40)
												|| (key >= 48 && key <= 57) || (key >= 96 && key <= 105));
									});
				});
	};

	$.urlParam = function(name) {
		try {
			var results = new RegExp('[\?&]' + name + '=([^&#]*)')
					.exec(window.location.href);
			return results[1] || 0;
		} catch (e) {
		}
	}
	
	 $(document).ready(function () {
         $("#menu-div").load("menu.html");
         $("#menu-header").load("nav.html");
         $("#page-footer").load("footer.html");
         $(".latlon").hide();
         
         $("#noOfFarmers").ForceNumericOnly();
         $("#yield").ForceNumericOnly();
         $("#acreage").ForceNumericOnly();

         $('#country-table').DataTable({
             "processing": true,
             "serverSide": true,
             "ajax": getUrl() + '/master/farm-group/getAllFarmGroup',
         });


         $.ajax({
             url: getUrl() + '/location/country/getCountries', success: function (result) {
                 $("#country").html('');
                 $("#country").append("<option value=''>Select</option>");
                 $(result).each(function (k, v) {
                     $("#country").append("<option value=" + v.id + ">" + v.name + "</option>");
                 });

                 getBrand();
             }
         });


         

     });

	 function edit(id){
			$("body").addClass("show-spinner");
			$.ajax({
				url : getUrl() + '/master/learner/by-id/'+id,
				beforeSend : function(request) {
					request.setRequestHeader("user-id", getUserName());
				},
				success : function(result) {
					console.log(result);
					$("#id").val(id);
					$("#name").val(result.name);
					$("#typez").val(result.typez);
					$("#brand").val(result.brandId);
					$("#country").val(result.countryId);
					$("#latitude").val(result.latitude);
					$("#longitude").val(result.longitude);
					$("#noOfFarmers").val(result.noOfFarmers);
					$("#acreage").val(result.acreage);
					$("#yield").val(result.estYield);
					listBrand(result.countryId, result.brandId);
					listProgram(result.brandId, result.programId);
					listPartner(result.programId, result.partnerId);
					listFarmGroup(result.partnerId, result.farmGroupId);
					$('input:radio[name=status]').val([ result.statusz ]);
					$("body").removeClass("show-spinner");
				}
			});
	 }

	 function getBrand(){
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

                var id = $.urlParam('id');
				if (id != null) {
					edit(id);
				}
             }	
         });
      }
	 
	 
	 function populateProgram() {
         var brand = $("#brand").val();
         $.ajax({
             url: getUrl() + '/master/programme/by-brand?brandId=' + brand + '',
             beforeSend: function(request) {
                 request.setRequestHeader("user-id", getUserName());
             },
              success: function (result) {
                 $("#program").html('');
                 $("#program").append("<option value=''>Select</option>");
                 $(result).each(function (k, v) {
                     $("#program").append("<option value=" + v.id + ">" + v.name + "</option>");
                 });
             }
         });
     }

     function populatePartners() {
         var program = $("#program").val();
         $.ajax({
             url: getUrl() + '/master/partner/by-program?program=' + program + '',
             beforeSend: function(request) {
                 request.setRequestHeader("user-id", getUserName());
             },
              success: function (result) {
                 $("#partner").html('');
                 $("#partner").append("<option value=''>Select</option>");
                 $(result).each(function (k, v) {
                     $("#partner").append("<option value=" + v.id + ">" + v.name + "</option>");
                 });
             }
         });
     }
     

     function submitForm() {
         if ($("#name").val() == "") {
             alert("Please enter Name");
             return;
         }
         else if ($("#typez").val() == "") {
             alert("Please select Type");
             return;
         }
         else if ($("#brand").val() == "") {
             alert("Please select Brand");
             return;
         } 
         else if ($("#program").val() == "") {
             alert("Please select Program");
             return;
         }
         else if ($("#country").val() == "") {
             alert("Please select Country");
             return;
         }else if ($("#partner").val() == "") {
             alert("Please select Local Partner");
             return;
         }

         var lpaData = {
        		  "id": $("#id").val(),
        		  "name": $("#name").val(),
        		  "countryId": $("#country").val(),
        		  "partnerId": $("#partner").val(),
        		  "countryId": $("#country").val(),
        		  "latitude": $("#latitude").val(),
        		  "longitude": $("#longitude").val(),
        		  "noOfFarmers": $("#noOfFarmers").val(),
        		  "acreage": $("#acreage").val(),
        		  "estYield": $("#yield").val(),
        		  "brandId": $("#brand").val(),
        		  "programId": $("#program").val(),
        		  "farmGroupId": $("#farmGroup").val()
        		};
          console.log(lpaData);

         $.ajax({
             url: getUrl() + '/master/learner-group/save',
             beforeSend: function(request) {
                 request.setRequestHeader("user-id", getUserName());
             },
             type: 'post',
             dataType: 'json',
             contentType: 'application/json',
             data: JSON.stringify(lpaData),
             success: function (data) {
                 window.location.href = "icsList.jsp";
             },
             error: function (err) {
                 console.log(err);
             }
         }); 
     }

     function showHideLatLon(typez){
         if(typez==1){
             $(".latlon").show();
         }else{
             $(".latlon").hide();
         }
     }
	</script>
</body>