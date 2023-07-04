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
						<h1>User</h1>
						<nav
							class="breadcrumb-container d-none d-sm-block d-lg-inline-block"
							aria-label="breadcrumb">
							<ol class="breadcrumb pt-0">
								<li class="breadcrumb-item"><a href="#">Home</a></li>
								<li class="breadcrumb-item"><a href="userList.jsp">User</a></li>
								<li class="breadcrumb-item active" aria-current="page">Add</li>
							</ol>
						</nav>
					</div>
				</div>
			</div>

			<div class="row mb-4">
				<div class="col-12 mb-4">
					<div class="card">
						<div class="card-body" id="form-body">
							<div class="row">
								<input type="hidden" id="id">
							</div>

							<div class="row">
								<div class="col-md-3">
									<div class="form-group">
										<label>User name</label> <input type="text"
											class="form-control" required id="userName">
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label>Name</label> <input type="text" class="form-control"
											required id="name">
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label>Mobile number</label> <input type="text"
											class="form-control" required id="mobile">
									</div>


								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label>Email</label> <input type="text" class="form-control"
											required id="email">
									</div>

								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label>Acess Level</label> <br />
										<div class="form-check-inline">
											<label class="form-check-label" for="radio1"> <input
												type="radio" class="form-check-input" id="radio1"
												name="accessType" value="0" checked>Mobile
											</label> <label class="form-check-label" for="radio1"> <input
												type="radio" class="form-check-input" id="radio2"
												name="accessType" value="1">web
											</label> <label class="form-check-label" for="radio3"> <input
												type="radio" class="form-check-input" id="radio3"
												name="accessType" value="2">Both
											</label>
										</div>
									</div>

								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label>Password</label> <input type="password"
											class="form-control" required id="password">
									</div>

								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label>Confirm password</label> <input type="password"
											class="form-control" required id="cpassword">
									</div>

								</div>

								<div class="col-md-3">
									<div class="form-group">
										<label>Role</label> <select id="role" class="form-control">
											<option value="">Select</option>
										</select>
									</div>
								</div>
							</div>

							<div class="row">
								<div class="col-md-3">
									<button onclick="submitForm()" class="btn btn-primary">Submit</button>

									<a class="btn btn-outline-primary" href="userList.jsp">Cancel</a>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>

		<footer id="page-footer" class="page-footer"> </footer>
	</main>
	<script src="js/vendor/jquery-3.3.1.min.js"></script>
	<script src="js/vendor/bootstrap.bundle.min.js"></script>
	<script src="js/vendor/perfect-scrollbar.min.js"></script>
	<script src="js/vendor/datatables.min.js"></script>
	<script src="js/dore.script.js"></script>
	<script src="js/scripts.js"></script>
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

          $('#country-table').DataTable({
              "processing": true,
              "serverSide": true,
              "ajax": getUrl() + '/service/user/getAllRecords',
          });

          $.ajax({
              url: getUrl() + '/role/getRoles', success: function (result) {
                  $("#role").html('');
                  $("#role").append("<option value=''>Select</option>");
                  $(result).each(function (k, v) {
                      $("#role").append("<option value=" + v.id + ">" + v.name + "</option>");
                  });

                  setUser();
              }
          });
   
	});

	function setUser(){
		var id = $.urlParam('id');
		if (id != null) {
			$("body").addClass("show-spinner");
			$("#userName").attr("disabled",true);
			$("#password").attr("disabled",true);
			$("#cpassword").attr("disabled",true);
			$.ajax({
				url : getUrl()
				+ '/service/user/by-id?id='
				+ id,
				beforeSend : function(request) {
					request.setRequestHeader("user-id",getUserName());
					
				},
				success : function(result) {
					$("#id").val(id);
					$("#email").val(result.emailId);
					$("#mobile").val(result.mobileNo);
					$("#userName").val(result.userId);
					$("#name").val(result.name);
					$("#password").val(result.password);
					$("#cpassword").val(result.password);
					$('input:radio[name=accessType]').val([ result.accessLevel ]);
					$("#role").val(result.roleId);
					$("body").removeClass("show-spinner");
				}
			});
		}
	}

	function submitForm() {
        if ($("#userName").val() == "") {
            alert("Please enter User name");
            return;
        }
        else if ($("#name").val() == "") {
            alert("Please enter Name");
            return;
        } else if ($("#password").val() == "") {
            alert("Please enter Password");
            return;
        } else if ($("#password").val() != $("#cpassword").val()) {
            alert("Password should match with confirm password");
            return;
        }

        var accessLevel = $('input[name="accessType"]:checked').val();
        var countryData = {
            "id" : $("#id").val(),    
            "userId": $("#userName").val(),
            "name": $("#name").val(),
            "mobileNo": $("#mobile").val(),
            "emailId": $("#email").val(),
            "password": $("#password").val(),
            "roleId": $("#role").val(),
            "accessLevel": accessLevel
        };
        
        $.ajax({
            url: getUrl() + '/service/user/saveUser',
			// url: 'http://localhost:5000/app/service/user/saveUser',

            type: 'post',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(countryData),
            success: function (data) {
				console.log(data);
				$('#form-body').prepend('<div class="alert alert-info text-center border border-info">'+data.message+'</div>')
				.find('.alert')
				.fadeIn(300)
				.delay(5000)
				.fadeOut(300, function() {$(this).remove();});
	
            },
            error: function (xhr, status, error) {
				var errorMessage = xhr.responseJSON ? xhr.responseJSON.message : 'An error occurred';

				$('#form-body').prepend('<div class="alert alert-info text-center border border-info">'+errorMessage+'</div>')
				.find('.alert')
				.fadeIn(300)
				.delay(3000)
				.fadeOut(300, function() {$(this).remove();});
                // console.log(err);
            }
        });
    }
	</script>
</body>