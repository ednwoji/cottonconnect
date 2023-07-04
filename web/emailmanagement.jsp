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
						<h1>Email Management</h1>
						<nav
							class="breadcrumb-container d-none d-sm-block d-lg-inline-block"
							aria-label="breadcrumb">
							<ol class="breadcrumb pt-0">
								<li class="breadcrumb-item"><a href="#">Service</a></li>
								<li class="breadcrumb-item"><a href="#">Email</a></li>
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
							<!-- <% if (request.getParameter("error") != null && !request.getParameter("error").isEmpty()) { %>
								<div class="alert alert-danger">
								  <%= request.getParameter("error") %>
								</div>
							  <% } %> -->

				
							  <% if (request.getAttribute("error") != null) { %>
								<div class="alert alert-danger">
								  <%= request.getAttribute("error") %>
								</div>
							  <% } %>

							<!-- <form id="formInput" action="https://server.cottonconnectelearning.com/email/save" method="post"> -->
								<form id="formInput" action="http://cottonconnectelearning.in:10000/app/email/save" method="post">
								<!-- <form id="formInput" action="http://localhost:5000/app/email/save" method="post"> -->

								<input type="hidden" value="0" name="id" id="id"> 
								<input type="hidden" name="redirectUrl" id="redirectUrl">

								<div class="row">
									<jsp:include page="ccfilter.jsp" />
									<div class="col-md-4">
										<label>Email 1</label> 
										<input type="email" name="emails1" class="form-control" required id="e0">
									</div>
									<div class="col-md-4">
										<label>Email 2</label> 
										<input type="email" name="emails2" class="form-control" id="e1" >
									</div>
									<div class="col-md-4">
										<label>Email 3</label> 
										<input type="email" name="emails3" class="form-control" id="e2">
									</div>

									<div class="col-md-4">
										<label>Email 4</label> 
										<input type="email" name="emails4" class="form-control" id="e3">
									</div>
									<div class="col-md-4">
										<label>Email 5</label> 
										<input type="email" name="emails5" class="form-control" id="e4">
									</div>
									<div class="col-md-4">
										<label>Email 6</label> 
										<input type="email" name="emails6" class="form-control" id="e5">
									</div>
								</div>

								<div class="modal-footer">
									<a href="emailManagementList.jsp" class="btn btn-outline-primary">Cancel</a>
									<button class="btn btn-primary" id="submit-button">Submit</button>
								</div>
							</form>
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
	// $.urlParam = function(name) {
	// 	try {
	// 		var results = new RegExp('[\?&]' + name + '=([^&#]*)')
	// 				.exec(window.location.href);
	// 		console.log('URL:', window.location.href);
    // 		console.log('Results:', results);
	// 		return results[1] || 0;
	// 	} catch (e) {
	// 		console.log('Error:', e);
	// 	}
	// }


	$.urlParam = function(name) {
  try {
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results && results.length > 1) {
      return results[1];
    } else {
      return 0;
    }
  } catch (e) {
    console.log('Error:', e);
  }
}

	$(document).ready(function() {

		var currentUrl = window.location.href;
 	 	console.log(currentUrl);

		  if (currentUrl.indexOf('failed') !== -1) {
			$('#formInput').prepend('<div class="alert alert-danger text-center border border-info">Error submitting form. Please re-try</div>')
				.find('.alert')
				.fadeIn(300)
				.delay(3000)
				.fadeOut(300, function() {$(this).remove();});
  			}

			else if (currentUrl.indexOf('success') !== -1){

				$('#formInput').prepend('<div class="alert alert-info text-center border border-info">Email IDs '+data.emailings+ ' are saved successfully</div>')
				.find('.alert')
				.fadeIn(300)
				.delay(3000)
				.fadeOut(300, function() {$(this).remove();});
  			}
			

		$("#img-div").hide();
		$("#menu-div").load("menu.html");
		$("#menu-header").load("nav.html");
		$("#page-footer").load("footer.html");
		$("#redirectUrl").val("https://cottonconnectelearning.com/emailManagementList.jsp");


		$("#formInput").submit(function(event) {
        event.preventDefault(); // Prevent the form from submitting        // Get the input field values

		var id = $.urlParam('id');
		console.log('This is an ID '+id);
		if (id != null) {
			// $("body").addClass("show-spinner");
			$.ajax({
				url : getUrl()+ '/email/by-id?id='+ id,
				beforeSend : function(request) {
					request.setRequestHeader(
							"user-id",
							getUserName());
				},
				success : function(result) {
					$("#id").val(id);
					console.log('The generated ID is '+$("#id").val());
					$("#country").val(result.country);
					setUpdate(result);
					$(result.emails).each(function(i,v){
						$("#e"+i).val(v);
					});
				}
			});
		}


		function validateEmails() {
        var emailFields = document.querySelectorAll('input[type="email"]');
        var values = Array.from(emailFields).map(function(field) {
            return field.value.trim();
        });

        var uniqueValues = new Set();
        var hasBlankValue = false;

        for (var i = 0; i < values.length; i++) {
            var value = values[i];

            // Skip blank values
            if (value === '') {
                hasBlankValue = true;
                continue;
            }

            // Check for duplicate values
            if (uniqueValues.has(value)) {
				$('#formInput').prepend('<div class="alert alert-danger text-center border border-info">Email IDs should be unique</div>')
				.find('.alert')
				.fadeIn(300)
				.delay(3000)
				.fadeOut(300, function() {$(this).remove();});


                // alert("Please make sure all email fields have unique values (except when blank).");
                return false; // Prevent form submission
            }

            uniqueValues.add(value);
        }

        return true; // Prevent form submission
    }


	if (!validateEmails()) {
            event.preventDefault(); // Prevent form submission if validation fails
        }

	else {
		this.submit();
	}


	 });
	});

	 
	</script>
</body>