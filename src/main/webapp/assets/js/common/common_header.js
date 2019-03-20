/**
 * 页面加载时获取请假记录(填充顶部通知)
 * */
$(function () {
    $.ajax({
        type: "post",
        url: "getNoExamineLeave.do",
        data: {"pageNo": 0, pageSize: 6},
        dataType: "json",
        success: function (data) {
            //如果没查到数据
            if (data.success) {
                /**
                 * 内容自定义填充
                 */
                //分页返回的页面数据
                dataList = data.data.list;
                if (dataList.length === 0) {
                    $("#notification_header").text('暂无待审批的请假记录哦');
                } else {
                    $("#notification_count").text(data.data.total);
                    $("#notification_header").text('您有' + data.data.total + '条请假记录待待审批哦');
                    dataList.forEach(function (item, index) {
                        var time = new Date(item.applicationTime).toLocaleDateString();
                        $("#notification_content").after('<li class="text-dark media py-2">\n' +
                            '                                <div class="mr-2 ml-3">\n' +
                            '                                    <i class="fa fa-fw fa-plus-circle text-info"></i>\n' +
                            '                                </div>\n' +
                            '                                <div class="media-body pr-2">\n' +
                            '                                    <div class="font-w400">' + item.sNo + ' ' + item.sName + ' 申请请假</div>\n' +
                            '                                    <small class="text-muted float-right">' + time + '</small>\n' +
                            '                                </div>\n' +
                            '                            </li>')
                    });
                }
            } else
                Swal.mixin({
                    toast: true, position: 'top', showConfirmButton: false, timer: 5000
                }).fire({type: 'error', title: data.errMsg});
        },
        error: function () {
            Swal.mixin({
                toast: true, position: 'top', showConfirmButton: false, timer: 5000
            }).fire({type: 'error', title: '待审批请假记录获取失败！'});
        }
    })
})