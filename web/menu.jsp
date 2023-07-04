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
						<h1>Role Menu</h1>

					</div>
				</div>
			</div>

			<div class="row mb-4">
				<div class="col-12 mb-4">
					<div class="card">
						<div class="card-body">
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label>Role</label> <select class="form-control" id="role"></select>
									</div>
								</div>
								<div class="col-md-8">
									<div class="form-group">
										<label>Parent Menu</label> <select
											class="form-control select2-multiple" multiple="multiple"
											onchange="getMenu(this.value)" id="parent_menu"></select>
									</div>
								</div>
							<!--  	<div class="col-md-12">
									<div class="form-group">
										<label>Menu</label> <select
											class="form-control select2-multiple" multiple="multiple"
											id="child_menu"></select>
									</div>
								</div>
							</div> -->
							<div class="row" style="margin-left: 2%">
								<div class="float-left">
								<button class="btn btn-info" onclick="selectAll()"
									style="margin-bottom: 4%">Select All</button>
									
									<button class="btn btn-info" onclick="removeAll()"
									style="margin-bottom: 4%">Remove All</button>
							</div>
							</div>
							<div class="row" id="chkBox">
							</div>
							
							<div class="row" style="margin-left: 5%">
								<button class="btn btn-success" onclick="submitForm()">Save</button>
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
	<script src="js/dore.script.js"></script>
	<script src="js/scripts.js?v=1.1"></script>
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

		$(document).ready(
				function() {
					$("#menu-div").load("menu.html");
					$("#menu-header").load("nav.html");
					$("#page-footer").load("footer.html");

					var id = $.urlParam('id');
					if (id != null) {
						$("body").addClass("show-spinner");
						$.ajax({
							url : getUrl()
									+ '/service/knowledge-center/by-id?id='
									+ id,
							beforeSend : function(request) {
								request.setRequestHeader("user-id",
										getUserName());
							},
							success : function(result) {
								$("#id").val(id);
							}
						});
					}

					$.ajax({
						url : getUrl() + '/role/getRoles',
						success : function(result) {
							$("#role").html('');
							$("#role").append(
									"<option value=''>Select</option>");
							$(result)
									.each(
											function(k, v) {
												$("#role").append(
														"<option value=" + v.id + ">"
																+ v.name
																+ "</option>");
											});

						}
					});

					$.ajax({
						url : getUrl() + '/user/menu/parent-menu',
						success : function(result) {
							$("#parent_menu").html('');
							$("#parent_menu").append(
									"<option value=''>Select</option>");
							$(result)
									.each(
											function(k, v) {
												$("#parent_menu").append(
														"<option value=" + v.id + ">"
																+ v.name
																+ "</option>");
											});

						}
					});

				});

		function submitForm() {
			if ($("#role").val() == "") {
				alert("Please enter role");
				return;
			} else if ($("#parent_menu").val() == "") {
				alert("Please select Role Menu");
				return;
			} 
			
			var entitlements = [];
			$("input:checkbox[name=ents]:checked").each(function() {
				entitlements.push($(this).val());
			});

			var countryData = {
				"roleId" : $("#role").val(),
				"parentMenuId" : $("#parent_menu").val(),
				"menuId" : entitlements,
			};
			$.ajax({
				url : getUrl() + '/role/save-role-menu',
				type : 'post',
				dataType : 'json',
				contentType : 'application/json',
				data : JSON.stringify(countryData),
				success : function(data) {
					window.location.href = "menu.jsp";
				},
				error : function(err) {
					console.log(err);
				}
			});
		}

		function getMenu(menus) {
			var parentId = $("#parent_menu").val();
			var persisted = [];
			$("input:checkbox[name=ents]:checked").each(function() {
				persisted.push($(this).val());
			});
			
			$.ajax({
						url : getUrl() + '/user/menu/by-parents?parentId='
								+ parentId,
						beforeSend : function(request) {
							request.setRequestHeader("user-id", getUserName());
						},
						success : function(result) {
							$("#chkBox").html('');
							$(result)
									.each(
											function(k, v) {
												$("#chkBox").append(
														"<div class='col-md-2'><input type='checkbox' name='ents' class='rowchk' value="+v.id+">"
																+ v.name
																+ "</div>");
											});

						}						
					});
		}

		function selectAll() {
			$("input:checkbox.rowchk").attr('checked', true);
		}

		function removeAll(){
			$("input:checkbox.rowchk").attr('checked', false);
		}
	</script>

</body>

</html>