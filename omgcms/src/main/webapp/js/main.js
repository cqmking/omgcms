$(function(){
	
	window.CMS = {
		Util: {
			
			showErrorMessage: function(msg, $container){
				
				var msgCode = '<div class="alert alert-danger"><div class="content">'+msg+'</div></div>'
				// var msgCode = '<div class="alert">'+msg+'</div>';
				
				if($container){
					$container.html(msgCode);
				}else{
					$('body').prepend(msgCode);
				}
			}
			
		}
	}
	
});