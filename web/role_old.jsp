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
					<div class="mb-2" id="formInput">
						<h1>Role</h1>
						<div class="top-right-button-container">
							<button type="button"
								class="btn btn-primary btn-lg top-right-button mr-1"
								data-toggle="modal" data-backdrop="static" id="mbtn"
								data-target="#exampleModalRight">ADD NEW</button>

							<div class="modal fade modal-right" id="exampleModalRight"
								tabindex="-1" role="dialog" aria-labelledby="exampleModalRight"
								aria-hidden="true">
								<div class="modal-dialog" role="document">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title">Add Role</h5>
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
										</div>
										<div class="modal-body">
											<form id="role-form">
											<input type="hidden" id="id">
												<div class="form-group">
													<label>Name</label> <br>
													<span id="name-error" style="background-color: red;"></span>
													<input type="text" pattern="[A-Za-z0-9]+" class="form-control" required id="roleName">
												</div>

												<div class="modal-footer">
													<button type="button" class="btn btn-outline-primary"
														data-dismiss="modal">Cancel</button>
													<button onclick="submitForm()" class="btn btn-primary">Submit</button>
												</div>
											</form>
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
							<table class="data-table data-table-feature" id="Role-table">
								<thead>
									<tr>
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

            $('#Role-table').DataTable({
                "processing": true,
                "serverSide": true,
                "ajax": getUrl() + '/role/getAllRoles',
            });
        });

        function submitForm() {
          if ($("#roleName").val() == "") {
                alert("Please enter Role name");
                return;
            }

			var roleNameInput = document.getElementById('roleName');
    			if (!roleNameInput.checkValidity()) {

			  $('#name-error').html('Invalid input. Please enter only alphabets and numbers.');
      			return false; // Prevent form submission
    		}
    		// return true; // Allow form submission

            var RoleData = {
				"id":$("#id").val(), 
				"name": $("#roleName").val() 
				};

			console.log('Sending request for role' +RoleData);


            $.ajax({
                // url: getUrl() + '/role/save',
				url: 'http://localhost:5000/app/role/save',
                type: 'post',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(RoleData),
                success: function (data) {
					alert('Role created successfully');
                    // window.location.href = "role.html";

                },
                error: function (jqXHR, textStatus, errorThrown) {
					console.log('I got an error');
					alert('Duplicate Roles. Please create a unique role');
                }
            });
        }

        function edit(id) {
        	$("body").addClass("show-spinner");
        	$.ajax({
				url : getUrl() + '/role/by-id?id='+id,
				beforeSend : function(request) {
					request.setRequestHeader("user-id", getUserName());
				},
				success : function(result) {
					$("#id").val(id);
					$("#roleName").val(result.name);
			
					$("body").removeClass("show-spinner");
					$("#mbtn").click();
				}
			});
        }

    </script>

</body>

</html>