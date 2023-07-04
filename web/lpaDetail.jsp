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
						<h1>Local Partner</h1>
						<nav
							class="breadcrumb-container d-none d-sm-block d-lg-inline-block"
							aria-label="breadcrumb">
							<ol class="breadcrumb pt-0">
								<li class="breadcrumb-item"><a href="#">Home</a></li>
								<li class="breadcrumb-item"><a href="lpalist.jsp">Local
										Partner</a></li>
								<li class="breadcrumb-item active" aria-current="page">Detail</li>
							</ol>
						</nav>
					</div>
				</div>
			</div>

			<div class="row mb-4">
				<div class="col-12 mb-4">
					<div class="card">
						<div class="card-body">
							<div class="table-responsive">
								<table class="table">
									<tr>
										<td>Name</td>
										<td id="name"></td>

										<td>Address</td>
										<td id="address"></td>

										<td>Country</td>
										<td id="countries"></td>

										<td>Brand</td>
										<td id="brands"></td>
									</tr>

									<tr>
										<td>Programs</td>
										<td id="programs"></td>

										<td>Status</td>
										<td id="status"></td>

										<td></td>
										<td id=""></td>

										<td></td>
										<td id=""></td>
									</tr>
								</table>

							</div>
							<div class="row">
								<div class="col-md-3">
									<button onclick="redirect()" class="btn btn-primary">Edit</button>

									<a class="btn btn-outline-primary" href="lpalist.jsp">Cancel</a>
								</div>
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
		$.urlParam = function(name) {
			var results = new RegExp('[\?&]' + name + '=([^&#]*)')
					.exec(window.location.href);
			return results[1] || 0;
		}
	
	   $(document).ready(function () {
           $("#menu-div").load("menu.html");
           $("#menu-header").load("nav.html");
           $("#page-footer").load("footer.html");

           $("body").addClass("show-spinner");
           var id = $.urlParam('id');
           if (id != null) {
               $.ajax({
            	   url : getUrl()+'/master/partner/by-id/'+ id, 
            	   beforeSend : function(request) {
    					request.setRequestHeader(
    							"user-id",
    							getUserName());
    				},
    				success : function(result) {
    					$.each(result, function(key, element) {
        					$("#"+key).css("color","#0b265d").css("font-weight","bold").text(element);
    					})
    				}
               });  
           }         
       });

       function redirect(){
    	   var id = $.urlParam('id');
           window.location.href="lpa.html?id="+id;
       }
    </script>

</body>

</html>