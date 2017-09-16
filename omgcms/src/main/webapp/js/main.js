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


window.loadLanages = function(lang){
	
	const defaultMessage = getDefaultMessage();
	var langMessage = {};
	
	var _messages = {};
	_messages[defaultMessage.message.lang] = defaultMessage;
	
	if(typeof(getMessage) === "function"){
		langMessage = getMessage();
		_messages[langMessage.message.lang] = langMessage;
	}
	
	if($.isEmptyObject(langMessage)){
		// 设置默认语言为当前语言
		lang = defaultMessage.message.lang;
		
	}
	
	const _i18n = new VueI18n({
		locale: lang, // 语言标识
		messages: _messages
	});
	
	return _i18n;
	
}








