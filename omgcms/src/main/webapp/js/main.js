$(function(){
	
	window.CMS = {
		Util: {
			
			loadPage: function(url, contentId){
				var instance = this;
				
				$.ajax({
				   type: "GET",
				   dataType: "html",
				   url: url,
				   success: function(data, status){
					   instance.refreshPage(data);
				   }
				});
			},
			
			refreshPage: function(htmlCode){
				
				var $title = $(htmlCode).find("title");
				var $header = $(htmlCode).find("head");
				var $body = $(htmlCode).find("body");
				
				if($title.length > 0){
					$("title").html($title.html());
				}
				if($header.length > 0){
					$("head").html($header.html());
				}
				if($body.length > 0){
					$("#contentId").html($body.html());
				}
				
			}
		}
	}
	
});