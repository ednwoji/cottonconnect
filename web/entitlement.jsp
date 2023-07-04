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
						<h1>Role Entitlements</h1>
						<nav class="breadcrumb-container d-none d-sm-block d-lg-inline-block" aria-label="breadcrumb">
							<ol class="breadcrumb pt-0">
								<li class="breadcrumb-item"><a href="#">Settings</a></li>
								<li class="breadcrumb-item"><a href="#">Role</a></li>
								<li class="breadcrumb-item active" aria-current="page">Entitlement</li>
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
								<div class="col">
									<label> Role </label> <select class="form-control" id="role" onchange="loadMenu()">
									</select>
								</div>

								<div class="col">
									<label> Role Menu </label> <select class="form-control" id="parent_menu"
										onchange="loadEntitlements()">
									</select>
								</div>
							</div>
						</div>
					</div>

					<div class="card" style="margin-top: 2%">
						<div class="card-body">
							<div class="float-right">
								<button class="btn btn-info" onclick="selectAll()" style="margin-bottom: 4%">Select
									All</button>

								<button class="btn btn-info" onclick="removeAll()" style="margin-bottom: 4%">Remove
									All</button>
							</div>
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>Menu</th>
											<th>List</th>
											<th>Create</th>
											<th>Update</th>
											<th>Delete</th>
										</tr>
									</thead>
									<tbody id="tbody-ent"></tbody>
								</table>
							</div>

							<div class="float-right">
								<button class="btn btn-success" onclick="save()">Save</button>
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
		$.urlParam = function (name) {
			try {
				var results = new RegExp('[\?&]' + name + '=([^&#]*)')
					.exec(window.location.href);
				return results[1] || 0;
			} catch (e) {
			}
		};

		$(document)
			.ready(
				function () {

					$("#img-div").hide();
					$("#menu-div").load("menu.html");
					$("#menu-header").load("nav.html");
					$("#page-footer").load("footer.html");
					$("#redirectUrl").val(getHomeUrl() + "/emailManagementList.jsp");

					var id = $.urlParam('id');
					if (id != null) {
						$("body").addClass("show-spinner");
						$.ajax({
							url: getUrl() + '/email/by-id?id=' + id,
							beforeSend: function (request) {
								request.setRequestHeader("user-id",
									getUserName());
							},
							success: function (result) {
								$("#id").val(id);
								$("#country").val(result.country);
								setUpdate(result);
								$(result.emails).each(function (i, v) {
									$("#e" + i).val(v);
								});
							}
						});
					}

					$
						.ajax({
							url: getUrl() + '/role/getRoles',
							success: function (result) {
								$("#role").html('');
								$("#role")
									.append(
										"<option value=''>Select</option>");
								$(result)
									.each(
										function (k, v) {
											$("#role")
												.append(
													"<option value=" + v.id + ">"
													+ v.name
													+ "</option>");
										});

							}
						});

				});

		function loadMenu () {
			$.ajax({
				url: getUrl() + '/role/menu/by-role?role=' + $("#role").val(),
				success: function (result) {
					$("#parent_menu").html('');
					$("#parent_menu")
						.append("<option value=''>Select</option>");
					$(result).each(
						function (k, v) {
							$("#parent_menu").append(
								"<option value=" + v.id + ">" + v.name
								+ "</option>");
						});

				}
			});
		}

		function loadEntitlements () {
			$
				.ajax({
					url: getUrl() + '/role/entitlements/by-menu?menus='
						+ $("#parent_menu").val(),
					success: function (result) {
						$("#tbody-ent").html("");
						$(result)
							.each(
								function (i, v) {
									Object
										.keys(v)
										.forEach(
											function (key) {
												var tr = $("<tr/>");
												var value = v[key];
												var menuTd = $(
													"<td/>")
													.text(
														key);
												tr
													.append(menuTd);
												for (idx = 0; idx < value.length; idx++) {
													var chk = $(
														"<td/>")
														.html(
															"<input type='checkbox' name='ents' class='rowchk' value=" + value[idx][0] + ">");
													tr
														.append(chk);
												}
												$(
													"#tbody-ent")
													.append(
														tr);
											});

								});

						updateValues();
					}
				});
		}

		function updateValues () {
			$.ajax({
				url: getUrl() + '/role/entitlements/by-role?role='
					+ $("#role").val(),
				success: function (result) {
					$(result.ents).each(function (i, v) {
						$("input:checkbox[value='" + v + "']").each(function () {
							$(this).attr('checked', true);
						});
					});
				}
			});
		}

		function selectAll () {
			$("input:checkbox.rowchk").attr('checked', true);
		}

		function removeAll () {
			$("input:checkbox.rowchk").attr('checked', false);
		}

		function save () {
			var entitlements = [];
			$("input:checkbox[name=ents]:checked").each(function () {
				entitlements.push($(this).val());
			});

			//save-role-entitlement
			var countryData = {
				"roleId": $("#role").val(),
				"ents": entitlements
			};

			$.ajax({
				url: getUrl() + '/role/save-role-entitlement',
				type: 'post',
				dataType: 'json',
				contentType: 'application/json',
				data: JSON.stringify(countryData),
				success: function (result) {
					$("#tbody-ent").html("");
				}
			});
		}
	</script>
</body>