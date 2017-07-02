$(function() {
	loadCustomVueFormValidator();
});

function loadCustomVueFormValidator() {
	var options = {
		validators : {
			'confirm-password' : function(value, attrValue, vnode) {
				// return true to set input as $valid, false to set as $invalid
				return value === attrValue;
			}
		}
	}
	
	Vue.use(VueForm, options);
}









