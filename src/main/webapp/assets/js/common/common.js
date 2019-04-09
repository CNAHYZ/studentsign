/**
 * 填充用户名、用户角色
 */
$(function () {
    $.post('/getCurrentUser.do', {}, function (data) {
        $("#header_username").text(data.currentUser);
        $("#header_userRole").text(data.typeString);
    }, "json")
});


function toast(type, title) {
    Swal.mixin(
        {
            toast: true, position: 'top', timer: 5000
        }
    ).fire({type: type, title: title});

}
