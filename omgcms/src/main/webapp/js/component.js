(function($) {
    'use strict';

    Vue.component('cms-pagination', {

        props: ['totalPagesNum', 'currentPageNum', 'showPages', 'showTotalCount', 'labels'],
        
        template: '\
        		<div class="cms_custom_pagination">\
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
                currentPage: parseInt(this.currentPageNum),
                showTotal: this.showTotalCount != "false",
                navLabels: {
                	total:'共',
                	page:'页'
                }
            }
        },

        computed: {
        	
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

                _self.currentPage = pageIndex;
                this.$emit('change', _self.currentPage)
            }

        }
    });
    
    

}(jQuery));

(function($) {
    'use strict';
    
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
			},
			
			formatDate: function(dateTimeStamp, formatString){
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
				   error: function (XMLHttpRequest, textStatus, errorThrown) {
					    // 通常 textStatus 和 errorThrown 之中
					    // 只有一个会包含信息
					    // 调用本次AJAX请求时传递的options参数
					    if(XMLHttpRequest.responseJSON){
					    	var errorMsg = XMLHttpRequest.responseJSON.message;
					    	instance.showErrorMessage(errorMsg, options.errorMsgContainer);
					    }
					    
				   },
				   complete: function (XMLHttpRequest, status) {
					   options.complete(XMLHttpRequest, status);
				   }
				});
				
			}
			
		}
	}
    
}(jQuery));


