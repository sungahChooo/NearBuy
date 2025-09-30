
function connectSummonAddressSearchAreaEvent() {
	$("#memberJoinAddr1, #memberJoinAddr2").click(function() {
		new daum.Postcode({
	        oncomplete: function(data) {
	            $("#memberJoinAddr1").val(data.zonecode);
	            $("#memberJoinAddr2").val(data.address);
	        }
	    }).open();
	});
}



$(function(){
	connectSummonAddressSearchAreaEvent();
});