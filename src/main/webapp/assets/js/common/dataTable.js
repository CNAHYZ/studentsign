var language = {
    "sProcessing": "正在加载数据，请稍后...",
    "sLengthMenu": "显示 _MENU_ 项结果",
    "sZeroRecords": "没有匹配结果",
    "sInfo": "显示第 _START_ 至 _END_ 条记录，共 _TOTAL_ 项",
    "sInfoEmpty": "显示第 0 至 0 条记录，共 0 项",
    "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
    "sSearch": "搜索:",
    "sEmptyTable": "表中数据为空",
    "sLoadingRecords": "载入中...",
    "sInfoThousands": ",",
    "oPaginate": {
        "sFirst": "首页",
        "sPrevious": "上页",
        "sNext": "下页",
        "sLast": "末页"
    }
};

var generalOptions = {
    language: language,
    //每页显示n条数据
    pageLength: 10,
    autoWidth: false,
    "processing": true,
    "retrieve": true,
    "bStateSave": true
};

var serverSideOptions = {
    "serverSide": true,
    "pagingType": "full_numbers",//显示完整的首页、末页、页码等按钮
    "lengthMenu": [10, 25, 50, 75, 100],
    "ordering": false,
    "autoWidth": false,//自动调整宽度
    "processing": true,
    language: language
};
