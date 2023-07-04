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
						<h1>Nutrient</h1>
												<nav
							class="breadcrumb-container d-none d-sm-block d-lg-inline-block"
							aria-label="breadcrumb">
							<ol class="breadcrumb pt-0">
								<li class="breadcrumb-item"><a href="#">Service</a></li>
								<li class="breadcrumb-item"><a href="nutrientList.jsp">Nutrient</a></li>
								<li class="breadcrumb-item active" aria-current="page">List</li>
							</ol>
						</nav>
					</div>
				</div>
			</div>

			<div class="row mb-4">
				<div class="col-12 mb-4">
					<div class="card">
						<div class="card-body">
							<form>
								<input type="hidden" name="id" id="id"> <input
									type="hidden" name="redirectUrl" id="redirectUrl"> <input
									type="hidden" name="subCategory" id="subCategory">
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label>Name of Nutrient</label> <!--  input type="text"
												class="form-control" required id="name" name="name"> -->
											    <div id="name_nutrient"></div>	
										</div>
									</div>
									<div class="col-md-12">
										<div class="form-group">
											<label>Type</label><br /> <label> <input type="radio"
												value="1" name="typez" checked="checked">Macro
											</label> <label> <input type="radio" value="0" name="typez">Micro
											</label>
										</div>
									</div>
									<div class="col-md-12">
										<div class="form-group">
											<label>Uses</label>
								<!-- 			<textarea maxlength="250" class="form-control" required
												name="identification" id="identification"></textarea>  -->
												<div id="identification_nutrient"></div>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
									<!-- 	<label>Difficiency symptoms</label>   -->
											<label>Deficiency symptoms</label>
									<!-- 	<textarea maxlength="350" class="form-control" required
												name="description" id="description"></textarea>  -->
										    <div id="description_nutrient"></div>
										</div>
									</div>
								</div>

								<div class="row" id="img-container">
									<div class="form-group">
									</div>
								</div>
							</form>
						</div>
						<div class="modal-footer">
						<!--  	<a class="btn btn-primary" id="edit">Edit</a>  -->
						 		<a href="nutrientList.jsp" class="btn btn-outline-primary">Cancel</a>
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
		$.urlParam = function(name) {
			var results = new RegExp('[\?&]' + name + '=([^&#]*)')
					.exec(window.location.href);
			return results[1] || 0;
		}

		$(document)
				.ready(
						function() {
							$("#menu-div").load("menu.html");
							$("#menu-header").load("nav.html");
							$("#page-footer").load("footer.html");
							$("#redirectUrl").val(window.location.href);

							var id = $.urlParam('id');
							if (id != null) {

								$("#edit").attr("href", "nutrients.jsp?id=" + id);

								$("body").addClass("show-spinner");
								$
										.ajax({
											url : getUrl()
													+ '/service/knowledge-center/by-id?id='
													+ id,
											beforeSend : function(request) {
												request.setRequestHeader(
														"user-id",
														getUserName());
											},
											success : function(result) {
												$("#id").val(id);
					//							$("#name").val(result.name);
												$("#name_nutrient").html(result.name);
					//							$("#identification").val(
					//									result.identification);
												$("#identification_nutrient").html(result.identification);
					//							$("#notes").val(result.notes);
												$("#notes_nutrient").html(result.notes);
					//							$("#description").val(
					//									result.description);
												$("#description_nutrient").html(result.description);
												$('input:radio[name=status]')
														.val([ result.typez ]);

												$(result.images).each(function(i,v){
													var ext = v.imageUrl.substr(v.imageUrl.lastIndexOf('.') + 1);
													if(ext == "jpg" || ext == "png"){
														$("<img />", {
															class : "zoom",
															src : v.imageUrl,
															height : '200',
															width : '200'
														}).appendTo("#img-container");
													}else{
														 var link = $("<a target='_blank'>")
														 link.attr("href", v.imageUrl);
														 link.attr("title",v.imageUrl);
											             link.text("Click to open");
											             link.addClass("link");

											             $("#img-container").append(link);
													}
												});
												
												$("body").removeClass(
														"show-spinner");
											}
										});
							}

							$("form :input").prop("disabled", true);
						});
	</script>

</body>

</html>