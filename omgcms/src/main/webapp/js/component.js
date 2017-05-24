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




