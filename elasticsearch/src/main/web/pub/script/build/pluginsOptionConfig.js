define(function() {

	return {
		columnDefs: {
			number: {
	        	orderable: false,
	            className: 'center',
	            data: "",
	            targets: 0,
	            width: "38px",
	            render: function(data, type, row, meta){
	            	return $.isArray(meta.row)?meta.row[0]+1:meta.row+1;
	            }
	        },
	        checkbox: function(key){
		        return {
		        	orderable: false,
		            className: 'select-checkbox center',
		            data: "",
		            width: "20px",
		            targets: 1,
		            render: function(data, type, row, meta){
		            	var str= "<label><input type='checkbox' id='checkbox_"+ (key?row[key]:row.id) +
		            			 "' data-key='"+ (key?row[key]:row.id) +
		            			 "' class='ace' /><span class='lbl'></span></label>";
		            	return str;
		            }
		        }
	        }
		},
		dataTables: {
			dom: "<tr><'row datatable-bootstrap-bottom'<'col-sm-7'li><'col-sm-5'p>>",
			aLengthMenu: [10, 20, 30]
		},
		subDataTables: {
			pageLength: 5,
			dom: "<tr><'row datatable-bootstrap-bottom'<'col-sm-6'i><'col-sm-6'p>>"
		},
		jqPaginator: {
			first: '<li class="first"><a href="javascript:void(0);">首页<\/a><\/li>',
            prev: '<li class="prev"><a href="javascript:void(0);"><i class="arrow arrow2"><\/i>上一页<\/a><\/li>',
            next: '<li class="next"><a href="javascript:void(0);">下一页<i class="arrow arrow3"><\/i><\/a><\/li>',
            last: '<li class="last"><a href="javascript:void(0);">末页<\/a><\/li>',
            page: '<li class="page"><a href="javascript:void(0);">{{page}}<\/a><\/li>',
            currentPage: 1,
            pageSize: 15,
            visiblePages: 10
		},
		multiselect:{
			inheritClass: true,
			buttonWidth:'100px',
			maxHeight:260
			//enableCollapsibleOptGroups: true  多选时才可用
		}
		
	}

});