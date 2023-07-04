 function listState(countrId, stateId) {
    var country = $("#country").val();
    $.ajax({
        url: getUrl() + '/location/state/getStatesByCountry?countryId=' + countrId + '', success: function (result) {
            $("#state").html('');
            $("#state").append("<option value=''>Select</option>");
            $(result).each(function (k, v) {
                $("#state").append("<option value=" + v.id + ">" + v.name + "</option>");
            });
            $("#state").val(stateId);
        }
    });
}
 
 
 function listDistrict(stateId,districtId) {
	    var state = $("#state").val();
	    $.ajax({
	        url: getUrl() + '/location/district/getDistrictsByState?stateId=' + stateId + '', success: function (result) {
	            $("#district").html('');
	            $("#district").append("<option value=''>Select</option>");
	            $(result).each(function (k, v) {
	                $("#district").append("<option value=" + v.id + ">" + v.name + "</option>");
	            });
	            $("#state").val(stateId);
	            $("#district").val(districtId);
	        }
	    });	    
 }
 
 function listBlock(districtId, blockId){
	 var state = $("#district").val();
	    $.ajax({
	        url: getUrl() + '/location/taluk/getTaluksByDistrict?districtId=' + districtId + '', success: function (result) {
	            $("#block").html('');
	            $("#block").append("<option value=''>Select</option>");
	            $(result).each(function (k, v) {
	                $("#block").append("<option value=" + v.id + ">" + v.name + "</option>");
	            });
	            $("#district").val(districtId);
	            $("#block").val(blockId);
	        }
	    });	    
 }