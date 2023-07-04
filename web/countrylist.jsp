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
						<h1>Country</h1>
						<div class="top-right-button-container">
							<button type="button"
								class="btn btn-primary btn-lg top-right-button mr-1" id="mbtn"
								data-toggle="modal" data-backdrop="static"
								data-target="#exampleModalRight">ADD NEW</button>

							<div class="modal fade modal-right" id="exampleModalRight"
								tabindex="-1" role="dialog" aria-labelledby="exampleModalRight"
								aria-hidden="true">
								<div class="modal-dialog" role="document">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title">Add Country</h5>
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
										</div>
										<div class="modal-body">
											<input type="hidden" id="id">
											<div class="form-group">
												<label>Code</label> <input type="text" class="form-control"
													id="countryCode" maxlength="4">
											</div>

											<div class="form-group">
												<label>Name</label> <input type="text" class="form-control"
													required id="countryName" maxlength="15">
											</div>

											<div class="modal-footer">
												<button type="button" id="cbtn"
													class="btn btn-outline-primary" data-dismiss="modal">Cancel</button>
												<button onclick="submitForm()" class="btn btn-primary">Submit</button>
											</div>
										</div>
									</div>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>

			<div class="row mb-4">
				<div class="col-12 mb-4">
					<div class="card">
						<div class="card-body">
							<table
								class="data-table data-table-feature data-tables-pagination"
								id="country-table">
								<thead>
									<tr>
										<th>Code</th>
										<th>Name</th>
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
        $(document).ready(function () {
            $("#menu-div").load("menu.html");
            $("#menu-header").load("nav.html");
            $("#page-footer").load("footer.html");

           var table =  $('#country-table').DataTable({
                "processing": true,
                "serverSide": true,
                "ajax": getUrl() + '/location/country/getAllCountries',
            });

/*            console.log("document.URL : "+document.URL);
           console.log("document.location.href : "+document.location.href);
           console.log("document.location.origin : "+document.location.origin);
           console.log("document.location.hostname : "+document.location.hostname);
           console.log("document.location.host : "+document.location.host);
           console.log("document.location.pathname : "+document.location.pathname); */
        });

        function submitForm() {
             if ($("#countryName").val() == "") {
                alert("Please enter Country name");
                return;
            }
            var countryData = { "code": $("#countryCode").val(), "name": $("#countryName").val(), "id" : $("#id").val() };
            console.log(countryData);
            $.ajax({
                url: getUrl() + '/location/country/saveCountry',
                type: 'post',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(countryData),
                success: function (data) {
                	 table.ajax.reload();
                	 //$("#cbtn").click();
                },
                error: function (err) {
                    console.log(err);
                }
            });
        }
        
		function edit(id) {
			$("body").addClass("show-spinner");
			$.ajax({
				url : getUrl() + '/location/country/by-id/'+id,
				beforeSend : function(request) {
					request.setRequestHeader("user-id", getUserName());
				},
				success : function(result) {
					$("#id").val(id);
					$("#countryCode").val(result.code);
					$("#countryName").val(result.name);
					$("body").removeClass("show-spinner");
					$("#mbtn").click();
				}
			});
		}
		

		function deletez(id){
			$("body").addClass("show-spinner");

			  var txt;
			  if (confirm("Are you sure want to delete? Can't restore record once deleted")) {
					$.ajax({
						url : getUrl() + '/location/country/delete?id='+id,
						beforeSend : function(request) {
							request.setRequestHeader("user-id", getUserName());
						},
						success : function(result) {
							$("body").removeClass("show-spinner");
							window.location.href = "countrylist.jsp";
						}
					});
			  } else {
				  $("body").removeClass("show-spinner");
			  }
			
		}

    </script>

</body>

</html>