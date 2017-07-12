(function($) {
    'use strict';

    Vue.component('cms-pagination', {

        props: ['totalPagesNum', 'currentPageNum', 'showPages', 'showTotalCount', 'labels'],
        
        template: '\
        		<div class="cms_custom_pagination" v-show="totalPagesNum>0">\
	        		<div class="cust_pagination_total" v-show="showTotal">{{navLabels.total}}{{parseInt(totalPagesNum)}}{{navLabels.page}}</div>\
	                <ul class="pager pagination">\
	                    <li class="previous" :class="{disabled : (currentPage - 1) < 1}"><a href="#" @click.prevent="gotoPage($event,currentPage-1)"><i class="icon icon-double-angle-left"></i></a></li>\
        				<li class="" v-show="showPrevEllipsis"><a href="#" @click.prevent="gotoPage($event, 1)">1</a></li>\
        				<li class="disabled" v-show="showPrevEllipsis"><a href="#" style="color: #999;">...</a></li>\
	                    <li v-for="pageNum in pages" :class="{active:currentPage==pageNum}"><a href="#"  @click.prevent="gotoPage($event, pageNum)">{{pageNum}}</a></li>\
        				<li class="disabled" v-show="showNextEllipsis"><a href="#" style="color: #999;">...</a></li>\
        				<li class="" v-show="showNextEllipsis"><a href="#" @click.prevent="gotoPage($event, parseInt(totalPagesNum))">{{parseInt(totalPagesNum)}}</a></li>\
	                    <li class="next" :class="{disabled : (currentPage + 1) > parseInt(totalPagesNum)}"><a href="#" @click.prevent="gotoPage($event,currentPage+1)"><i class="icon icon-double-angle-right"></i></a></li>\
	                </ul>\
        		</div>',
        		
        		
        data: function(){
        	
            return {
                showTotal: this.showTotalCount != "false",
                navLabels: {
                	total:'共',
                	page:'页'
                }
            }
        },

        computed: {
        	
        	currentPage: function(){
        		return parseInt(this.currentPageNum);
        	},
        	
            pages: function(){
                var _self = this;
                //获取当前需要显示的页数组
                var _pages = [];
                var _allPagesCount = parseInt(_self.totalPagesNum);
                var _showPages = parseInt(_self.showPages);
                var _currentPage = _self.currentPage;

                if(_currentPage < _showPages){

                    var i = Math.min(_showPages, _allPagesCount);	//取小值

                    while(i){
                        _pages.unshift(i--);
                    }

                } else {

                    //当前页数大于显示页数
                    var middle = _currentPage - Math.floor(_showPages / 2 );
                    var  i = _showPages;

                    if( middle >  (_allPagesCount - _showPages)  ){
                        middle = (_allPagesCount - _showPages) + 1
                    }
                    
                    while(i--){
                        _pages.push( middle++ );
                    }
                }
                
                return _pages;

            },
            
        	showPrevEllipsis: function(){
        		var _self = this;
        		return _self.pages[0]!=1;
        	},
        	
        	showNextEllipsis: function(){
        		var _self = this;
        		return _self.pages[_self.pages.length-1] != _self.totalPagesNum;
        	}
        },

        methods: {
            gotoPage: function (event, pageIndex) {
                var _self = this;
                
                event.target.blur();

                if (pageIndex <= 0){
                    pageIndex = 1;
                }else if(pageIndex > parseInt(_self.totalPagesNum)){
                    pageIndex = parseInt(_self.totalPagesNum);
                }

                if(_self.currentPage==pageIndex){
                    return;
                }
                
                this.$emit('change', pageIndex);
            }

        }
    });
    
    

}(jQuery));

/**
 * 扩展JQuery，添加自定义datatable
 * 
 */
(function ($) {
	
	var CustomDatatable = function($target){
		var that     = this;
		that.$target = $target;
	};
	
	CustomDatatable.prototype.getSelectRows = function() {
		var that     = this;
		return that.$target.find("td.check-btn.checked");
    };
    
    CustomDatatable.prototype.getSelectIds = function() {
		var that     = this;
		
		var dataArray = [];
		that.getSelectRows().each(function(i, row){
			
			//直接使用data有缓存  data("id")-
			var _id = $(this).attr("data-id");
			dataArray.push(_id);
		});
		
		return dataArray;
    };
    
    CustomDatatable.prototype.clear = function() {
		var that     = this;
		that.$target.find("th.check-all").removeClass("checked");
		that.$target.find("td.check-btn.checked").removeClass("checked");
    };
    
    
	
    $.fn.extend({
    	
        "customDatatable": function (options) {
        	
        	var $table = this;
        	var _customDatatable = new CustomDatatable($table);
        	
        	if(!options || !options.create){
        		return _customDatatable;
        	}
        	
        	$table.find("th.check-all").off().on("click", function(){
        		
        		if($(this).hasClass("checked")){
        			$(this).removeClass("checked");
        			$(this).closest("table.datatable").find("th.check-btn, td.check-btn").removeClass("checked");
        		}else{
        			$(this).addClass("checked");
        			$(this).closest("table.datatable").find("th.check-btn, td.check-btn").removeClass("checked").addClass("checked");
        		}
        		
        		if(options.onChange){
            		options.onChange();
            	}
        		
        	});
        	
        	$table.find("td.check-btn").off().on("click", function(){
        		
        		if($(this).hasClass("checked")){
        			$(this).removeClass("checked");
        			$table.find("th.check-all.checked").removeClass("checked");
        		}else{
        			$(this).addClass("checked");
        			var notCheckedSize = $table.find("td.check-btn").not(".checked").size();
        			if(notCheckedSize==0){
        				$table.find("th.check-all").addClass("checked");
        			}
        		}
        		
        		if(options.onChange){
            		options.onChange();
            	}
        		
        	});
        	
        	return _customDatatable;
        }
    
    });
})(jQuery);


/**
 * CMS.Util tool
 * @param $
 */
(function($) {
    'use strict';
    
    window.CMS = {
		Util: {
			
			showNoticeMessage: function(type, message){
				new $.zui.Messager(message, {
				    type: type // 定义颜色主题
				    //,time: 0
				}).show();
			},
			
			showErrorMessage: function(msg, $container, prepend){
				
				var msgCode = '<div class="alert alert-danger custom-error-msg"><div class="content">'+msg+'</div></div>'
				// var msgCode = '<div class="alert">'+msg+'</div>';
				
				if($container){
					
					$container.find("div.alert.custom-error-msg").remove();
					
					if(prepend==true){
						$container.prepend(msgCode);
					}else{
						$container.html(msgCode);
					}
					
				}else{
					$('body').find("div.alert.custom-error-msg").remove();
					$('body').prepend(msgCode);
				}
			},
			
			formatDate: function(dateTimeStamp, formatString){
				if(dateTimeStamp==null){
					return '';
				}
				var mDate = moment(dateTimeStamp);
				var _formatString = formatString || 'YYYY-MM-DD HH:mm:ss';
			    return mDate.format(_formatString);
			},
			
			sendJsonRequest: function(options){
				
				var instance = this;
				
				$.ajax({
				   type: options.method,
				   dataType: "json",
				   contentType: "application/json",
				   url: options.url,
				   data: options.params,
				   success: function(data, status){
					   options.success(data, status);
				   },
				   error : function(XMLHttpRequest, textStatus, errorThrown) {
						
						// 通常 textStatus 和 errorThrown 之中
						// 只有一个会包含信息
						// 调用本次AJAX请求时传递的options参数
						if (XMLHttpRequest.responseJSON) {
							var errorMsg = XMLHttpRequest.responseJSON.message;
							if(options.prependError==true){
								instance.showErrorMessage(errorMsg, options.errorMsgContainer, options.prependError);
							}else{
								instance.showErrorMessage(errorMsg, options.errorMsgContainer);
							}
							
						}

					},
				   complete: function (XMLHttpRequest, status) {
					   if(options.complete){
						   options.complete(XMLHttpRequest, status);
					   }
					   
				   }
				});
				
			}
			
		},
		
		Dialog: {
			trigger: '',
			
			show: function(options){
				var instance = this;
				var footerHtmlCode = '<div class="dialog-modal-footer"></div>';
				var buttonHtmlCode = '<button type="button" class="btn btn-default">关闭</button>';
				
				//在$.zui对象上已默认绑定了一个对话框触发器对象，可以直接使用方法并传递不同的参数来随时启动对话框。
				var newTrigger = new $.zui.ModalTrigger();
				instance.trigger = newTrigger;
				
				var $footer = $(footerHtmlCode);
				
				if(options.toolbar){
					$.each(options.toolbar, function(i, button){
						var $newBtn = $(buttonHtmlCode);
						if(button.label){
							$newBtn.text(button.label);
						}
						if(button.callback){
							$newBtn.off().on("click", function(){
								button.callback();
							});
						}
						if(button.cssClass){
							$newBtn.removeClass("btn-default");
							$newBtn.addClass(button.cssClass);
						}
						
						$footer.append($newBtn);
						
					});
				}
				
				if($footer.find("button").size() > 0){
					options.onShow = function(){
						$(newTrigger.$modal).find(".modal-body").append($footer);
					};
					
				}
				
				newTrigger.show(options);
				return instance;
				
			},
			
			close: function(){
				var instance = this;
				instance.trigger.close();
			}
		}
    }
    
}(jQuery));





