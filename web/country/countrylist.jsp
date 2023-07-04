<!DOCTYPE html>
<html lang="en">

<jsp:include page="../common/header.jsp" />

<body id="app-container" class="menu-sub-hidden show-spinner">
	<jsp:include page="../common/nav.html" />
	<jsp:include page="../common/menu.html" />
	<main>
		<div class="container-fluid">
			<div class="row ">
				<div class="col-12">
					<div class="mb-2">
						<h1>Country</h1>
						<nav
							class="breadcrumb-container d-none d-sm-block d-lg-inline-block"
							aria-label="breadcrumb">
							<ol class="breadcrumb pt-0">
								<li class="breadcrumb-item"><a href="#">Master</a></li>
								<li class="breadcrumb-item"><a href="countrylist.jsp">Country</a></li>
								<li class="breadcrumb-item active" aria-current="page">Add</li>
							</ol>
						</nav>
						<div class="top-right-button-container">
							<a href="country.jsp" class="btn btn-primary">ADD NEW</a>
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

	<jsp:include page="../common/include.jsp" />

	<script type='text/javascript'>
        $(document).ready(function () {
            $("#menu-div").load("../common/menu.html");
            $("#menu-header").load("../common/nav.html");
            $("#page-footer").load("../common/footer.html");
			$("body").removeClass("show-spinner");
            
           var table =  $('#country-table').DataTable({
                "processing": true,
                "serverSide": true,
                "ajax": getUrl() + '/location/country/getAllCountries',
            });
           
        });

        function submitForm() {
            if ($("#countryCode").val() == "") {
                alert("Please enter Country Code");
                return;
            }
            else if ($("#countryName").val() == "") {
                alert("Please enter Country name");
                return;
            }
            var countryData = { "code": $("#countryCode").val(), "name": $("#countryName").val(), "id" : $("#id").val() };
            $.ajax({
                url: getUrl() + '/location/country/saveCountry',
                type: 'post',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(countryData),
                success: function (data) {
                	 table.ajax.reload();
                	 $("#cbtn").click();
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
							window.location.href = "country.html";
						}
					});
			  } else {
				  $("body").removeClass("show-spinner");
			  }
			
		}

    </script>

</body>

</html>