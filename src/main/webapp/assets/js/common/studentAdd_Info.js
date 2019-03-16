/**
 * 初始化班级下拉列表
 */
function initClassList() {
    var select2 = $('#select_class').select2({
        placeholder: '请选择该生班级',
        delay: 250,
        language: "zh-CN",
        ajax: {
            url: getClassListUrl,
            dataType: 'json',
            type: 'post',
            processResults: function (data) {
                console.log(data.data);
                var realData = $.map(data.data, function (obj) {
                    //自定义对应关系，id-->cNo，text-->grade、cName
                    obj.id = obj.id || obj.cNo;
                    obj.text = obj.text || obj.grade + "  " + obj.cName;
                    return obj;
                });
                return {
                    results: realData
                };
            },
            success: function (data) {
                if (!data.success) {
                    Swal.mixin({
                        toast: true, position: 'top', showConfirmButton: false, timer: 5000
                    }).fire({type: 'error', title: data.errMsg});
                }
            },
            error: function () {
                Swal.mixin({
                    toast: true, position: 'top', showConfirmButton: false, timer: 5000
                }).fire({type: 'error', title: "获取班级列表失败，请重试！"});
            }
        }
    })
}


/**
 * 初始化datepicker
 */
function initDatepicker() {
    $('#birthday').datepicker({
        endDate: '0d',//最大只能选择到今天
        language: "zh-CN",
        // defaultViewDate:'2000-01-01',
        startView: 'century',
        weekStart: "1",
        autoclose: "true",
        format: "yyyy-mm-dd",
        maxViewMode: "years"
    });
}