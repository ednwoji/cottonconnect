<!DOCTYPE html>
<html lang="en">
<jsp:include page="header.jsp" />
<body id="app-container" class="menu-sub-hidden show-spinner">
	<div id="menu-header"></div>
	<div id="menu-div"></div>
	<main>
		<div class="container-fluid">
			<div class="row mb-4">
				<div class="col-12 mb-4">
					<div class="card">
						<div class="card-body">
							<h3>FAQ Response</h3>
							<div class="col-md-12">
								<div class="table-responsive">
									<table class="table" style="width: 100%">
										<thead>
											<tr>
												<th>Farmer Name</th>
												<th>Mobile Number</th>
												<th>Farmer Group</th>
												<th>Country Name</th>
												<th>Created Date</th>
												<th>Device IMEI</th>
												<th>Device</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td id="farmerName"></td>
												<td id="mobile"></td>
												<td id="fgroup"></td>
												<td id="country"></td>
												<td id="createdDate"></td>
												<td id="imei"></td>
												<td id="device"></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<div class="col-md-12">
								<h4>Query</h4>
							</div>
							<div class="col-md-12">
								<p id="query"></p>
							</div>
							<div class="col-md-12">
								<h4>Images</h4>
							</div>
							<div class="col-md-12">
								<div id="img-container"></div>
							</div>
							<div class="col-md-12">
								<h4>Audio</h4>
							</div>
							<div class="col-md-12">
								<div id="audio_player" style="margin-top: 5%; padding-right: 1%"></div>
							</div>

							<hr />
							<div class="row">
								<input type="hidden" name="id" id="id"> <input
									type="hidden" name="redirectUrl" id="redirectUrl"> <input
									type="hidden" name="subCategory" id="subCategory">
								
								<div class="col-md-12"> <p id="recordTxt" style='color:blue;text-align: center;'><b>Recording is processing. press "Record / Stop" button to end</b></p></div>
								<div class="table-responsive">
									<table class="table" style="width: 100%">
										<tr>
											<th style="width: 80%">Response to farmer</th>
											<th style="width: 20%">Recording</th>
										</tr>
										<tr>
											<td>
												<div class="form-group">
													<textarea maxlength="250" class="form-control" required
														name="queryz" id="queryz"></textarea>
												</div>
											</td>
											<td>
												<div class="form-group">
													<button id="recordButton" class='btn btn-success'
														type="button">Record/Stop</button>
													<div id="avRecorder-fallback-ajax-wrapper"></div>
													<div id="avRecorder" class=""></div>
												</div>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<div class="row" id="aPreview"></div>
											</td>
										</tr>
									</table>
									<div class="row">
										<div class="col-md-4" id="modalVal"></div>
										<div class="col-md-4" id="audioVal"></div>
										<div class="col-md-2" id="createdVal"></div>
										<div class="col-md-2" id="dateVal"></div>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<a href="faqQueryList.jsp" class="btn btn-outline-primary">Cancel</a>
								<button class="btn btn-primary" type="button"
									onclick="submitData()">Submit</button>
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
	<script src="js/recorderJs/recorder.js"></script>
	<!-- jQuery AV Recorder JS -->
	<script src="js/AVRecorder/av-recorder-api.js"></script>
	<script src="js/AVRecorder/av-recorder-html5.js"></script>
	<script src="js/AVRecorder/av-recorder.js"></script>

	<script type='text/javascript'>
	var blob;
		$.urlParam = function(name) {
			var results = new RegExp('[\?&]' + name + '=([^&#]*)')
					.exec(window.location.href);
			return results[1] || 0;
		}

		var recorder, gumStream;
		var recordButton = document.getElementById("recordButton");
		recordButton.addEventListener("click", toggleRecording);

		function toggleRecording() {
			if (recorder && recorder.state == "recording") {
				recorder.stop();
				gumStream.getAudioTracks()[0].stop();
				$("#recordTxt").hide();
			} else {
				$("#recordTxt").show();
				navigator.mediaDevices.getUserMedia({
					audio : true
				}).then(
						function(stream) {
							gumStream = stream;
							recorder = new MediaRecorder(stream);
							recorder.ondataavailable = function(e) {
								blob  = e.data;
								var url = URL.createObjectURL(e.data);
								var preview = document.createElement('audio');
								preview.name="audio";
								preview.id="aFile";
								preview.controls = true;
								preview.src = url;
								document.getElementById("aPreview")
										.appendChild(preview);
							};
							recorder.start();
						});
			}
		}

		function submitData(){
			console.log($("#queryz").val());
			var preview = document.getElementById("aFile");
			var id = $.urlParam('id');
			var fd = new FormData();
			if(blob!=null){
				fd.append('fname', 'test.wav');
				fd.append('file', blobToFile(blob, 'test'));
			}else{
				fd.append('file', null);
			}
			fd.append("queryId", id);
			fd.append("query", $("#queryz").val());
			fd.append("user", getUserName());
			$.ajax({
			    type: 'POST',
			    url:  getUrl()+'/faq/saveResp',
			    data: fd,
			    processData: false,
			    contentType: false,
			    success : function(result) {
			    	window.location.href = "faqQueryList.jsp";
				},
				error : function(result){
					console.log(result);
				}
			});
			 
		}

		function blobToFile(theBlob, fileName){
		    //A Blob() is almost a File() - it's just missing the two properties below which we will add
		    
		    theBlob.lastModifiedDate = new Date();
		    theBlob.name = fileName;
		    return theBlob;
		}

		$(document).ready(function() {
			$("#menu-div").load("menu.html");
			$("#menu-header").load("nav.html");
			$("#page-footer").load("footer.html");
			$("#recordTxt").hide();

			var id = $.urlParam('id');
			if (id != null) {
				$.ajax({
					url : getUrl() + '/faq/query/by-id?id=' + id,
					beforeSend : function(request) {
						request.setRequestHeader("user-id", getUserName());
					},
					success : function(result) {
						$("body").removeClass("show-spinner");
						$("#query").text(result.name);
						$("#farmerName").text(result.farmer.name);
						$("#mobile").text(result.farmer.mobileNumber);
						$("#fgroup").text(result.farmer.farmGroupName);
						$("#country").text(result.farmer.countryName);
						$("#createdDate").text(result.createdDate);
						$("#imei").text(result.devie.imei);
						$("#device").text(result.devie.manufacturer);
						$(result.documents).each(function(i, v) {
							if (v.rul != '') {
								if (v.docType == 'image') {
									$("<img/>", {
										class : "zoom mleft1",
										src : v.url,
										height : '200',
										width : '200'
									}).appendTo("#img-container");
								} else if (v.docType == 'voice') {
									var audio = $("<audio controls/>");
									$("<source/>", {
										src : v.url
									}).appendTo(audio);
									audio.appendTo("#audio_player");
								}
							}
						});

						$(result.faqResponse).each(function(i,v){
							if (v.url != '') {
								$("#queryz").attr("disabled",true);
								$("#recordButton").attr("disabled",true);
								$("#modalVal").append("<p style='margin-top:5%;margin-bottom:5%;'>"+v.query+"</p>");

								var preview = document.createElement('audio');
								preview.name="audio";
								preview.id="aFile";
								preview.controls = true;
								preview.src = v.url;
								document.getElementById("audioVal")
										.appendChild(preview);

								$("#createdVal").html("Created by : "+"<b>"+v.createdUser+"</b>");

								$("#dateVal").html("<button class='btn btn-danger' onclick='deleteResp("+v.id+")'>Delete</button>");
							}
						});
					}
				});
			} else {
				window.location.href = "faqQueryList.jsp";
			}
			
		});

		function deleteResp(id){
			if (confirm("Are you sure want to delete? Can't restore record once deleted")) {
			$.ajax({
				url : getUrl() + '/faq/response/delete?id=' + id,
				beforeSend : function(request) {
					request.setRequestHeader("user-id", getUserName());
				},
				success : function(result) {
					$("#queryz").removeAttr("disabled");
					$("#recordButton").removeAttr("disabled");
					$("#modalVal").html("");
					$("#createdVal").html("");
					$("#dateVal").html("");
					$("#audioVal").html("");
				}
			});
		}
	}

	</script>
</body>
</html>