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

<link href="https://unpkg.com/video.js/dist/video-js.min.css"
	rel="stylesheet">
<script src="https://unpkg.com/video.js/dist/video.min.js"></script>

<!-- unpkg : use a specific version of Video.js (change the version numbers as necessary) -->
<link href="https://unpkg.com/video.js@7.8.2/dist/video-js.min.css"
	rel="stylesheet">
<script src="https://unpkg.com/video.js@7.8.2/dist/video.min.js"></script>

<!-- cdnjs : use a specific version of Video.js (change the version numbers as necessary) -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/video.js/7.8.1/video-js.min.css"
	rel="stylesheet">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/video.js/7.8.1/video.min.js"></script>

</head>

<body id="app-container" class="menu-sub-hidden show-spinner">
	<div id="menu-header"></div>
	<div id="menu-div"></div>
	<main>
		<div class="container-fluid">
			<div class="row ">
				<div class="col-12">
					<div class="mb-2">
						<div class="card">
							<div class="card-body">
								<h1>Video Detail</h1>
								<div class="row">
									<div class="col-md-2">
										<p>Video Title</p>
										<b id="name"></b>
									</div>

									<div class="col-md-4">
										<p>Description</p>
										<b id="desc"></b>
									</div>

									<div class="col-md-4">
										<p>External Link</p>
										<a id="link" target="_blank"><p id="linkVal"></p></a>
										<a id="exlink" target="_blank"><p id="exlinkVal"></p></a>
									</div>
								</div>

								<div class="modal-footer" style="margin-top: 10%">
									<a href="videolist.jsp" class="btn btn-outline-primary">Cancel</a>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</main>

	<footer class="page-footer"> </footer>

	<!-- unpkg : use the latest version of Video.js -->

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/video.js/7.8.1/video.min.js"></script>
	<script src="js/vendor/jquery-3.3.1.min.js"></script>
	<script src="js/vendor/bootstrap.bundle.min.js"></script>
	<script src="js/vendor/perfect-scrollbar.min.js"></script>
	<script src="js/vendor/datatables.min.js"></script>
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

		$(document).ready(function() {
			$("#menu-div").load("menu.html");
			$("#menu-header").load("nav.html");
			$("#page-footer").load("footer.html");

			var id = $.urlParam('id');
			if (id != null) {
				$("body").addClass("show-spinner");
				$("#img-div").show();
				$.ajax({
					url : getUrl() + '/video/by-id?id=' + id,
					beforeSend : function(request) {
						request.setRequestHeader("user-id", getUserName());
					},
					success : function(result) {
						$("#id").val(id);
						$("#name").html(result.name);
						$("#desc").html(result.description);
						$("#exlink").attr("href", result.link);
						$("#exlinkVal").html(result.link);
						console.log(result.sourceUrl);
						if(result.sourceUrl!=''){
							$("#link").attr("href", result.sourceUrl);
							$("#linkVal").html(result.sourceUrl);
						}else{
							console.log('ELSE');
							$("#link").attr("href", result.link);
							$("#linkVal").html(result.link);
						}

					}
				});
			}
		});
	</script>

</body>

</html>