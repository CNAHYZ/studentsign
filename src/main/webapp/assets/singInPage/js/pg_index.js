/*/!*Popup弹窗*!/
        //回调函数,关闭弹窗后停止获取视频流
        callbacks: {
            close: function () {
                //获取获取到视频流
                /!*     if (stream != undefined)
                     //停止播放轨道对应的源，源与轨道将脱离关联，同时轨道状态将设为“ended”。
                         stream.getVideoTracks()[0].stop();*!/
            }
        }
    });
});*/

var stream;
/**
 * 超时未操作，自动关闭摄像头
 * @type {number}
 */
var lastTime = new Date().getTime();
var currentTime = new Date().getTime();
//设置超时时间： 5分
var timeOut = 1000 * 60 * 5;
var quitTime;
window.onload = function () {
    /* 检测鼠标点击事件 */
    document.addEventListener('click', function () {
        // 更新最后的操作时间
        console.log('鼠标点击了');
        lastTime = new Date().getTime();
        /* 定时器  间隔2分钟，检测是否长时间未操作页面  */
        quitTime = window.setInterval(testTime, 120000);
    })
};

// 超时函数
function testTime() {
    //更新当前时间
    currentTime = new Date().getTime();
    console.log('currentTime', currentTime);
    //判断是否超时
    if (currentTime - lastTime > timeOut) {
        // 超时操作
        console.log('超时操作');
        //获取获取到视频流
        if (stream !== undefined) {
            //停止播放轨道对应的源，源与轨道将脱离关联，同时轨道状态将设为“ended”。
            stream.getVideoTracks()[0].stop();
            window.location.reload();
        }
        // 清除掉定时器
        window.clearInterval(quitTime)
    }
}

/*作者：1564340114@qq.com
时间：2018-12-15
描述：拍照处理*/

/**
 * 主页面签到按钮点击事件
 */
$("#signin").click(function () {
    $(".w3l-signin").hide();
    $("#form").attr("hidden", false);
    var constraints = {
        audio: false,
        video: {
            /*width: 550,
            height: 393*/
        }
    };
    navigator.mediaDevices.getUserMedia(constraints)
        .then(function (mediaStream) {
            stream = mediaStream;
            var video = document.querySelector('video');
            video.srcObject = mediaStream;
            video.onloadedmetadata = function (e) {
                video.play();
            };
        })
        .catch(function (err) {
            console.log(err.name + ": " + err.message);
        }); // 总是在最后检查错误
    $('#area1').show();
    $('#area2').hide();
});

/**
 * 拍照上传点击事件
 */
$("#takephoto").click(function () {
    /*   //停止播放轨道对应的源，源与轨道将脱离关联，同时轨道状态将设为“ended”。
       stream.getVideoTracks()[0].stop();
       //从MediaStream中删除视频轨的MediaStreamTrack对象。
       //stream.removeTrack(stream.getVideoTracks()[0]);*/
    var context = document.getElementById("canvas").getContext("2d");
    context.drawImage(video, 0, 0, 524, 393);
    $("[id^=area]").toggle(); //切换area1和area2的显示与隐藏
    upload();
});

/**
 * 图片上传方法
 */
function upload() {
    var imgData = document.getElementById("canvas").toDataURL("image/png");
    //或imgData.replace("data:image/png;base64,", "")
    var data = imgData.substr(22);
    console.log(data);
    $.ajax({
        type: "post",
        url: "student/stuSign.do",
        data: {
            "faceImage": data
        },
        //如果后台返回的数据不是json格式，那么就会进入到error:function(data){}中
        dataType: "json",
        beforeSend: function () {
            load();
        },
        complete: function () {
            //移除加载动画，切换显示区域
            removeLoad();
            $("[id^=area]").toggle();
        },
        success: function (result) {
            if (result.success) {
                swal({
                    toast: true,
                    title: result.data,
                    type: "success",
                    position: "top",
                    showConfirmButton: false,
                    timer: 5000
                })
            } else
                swal({
                    toast: true,
                    title: "签到失败",
                    text: result.errMsg,
                    position: "top",
                    type: "error",
                    showConfirmButton: false,
                    timer: 5000
                })
        },
        error: function (data) {
            swal({
                toast: true,
                title: "系统错误，请重试!",
                type: "error",
                position: "top",
                timer: 5000
            })
        }
    });
}

/**
 * 绘制加载动画
 */
function load() {
    /**
     *页面遮罩层
     */
    function overlay() {
        var docHeight = $(document).height(); //获取窗口高度
        $('body').append('<div id="overlay"></div>');
        $('#overlay')
            .height(docHeight)
            .css({
                'opacity': .9, //透明度
                'position': 'absolute',
                'top': 0,
                'left': 0,
                'background-color': 'black',
                'width': '100%',
                'z-index': 4000 //保证这个悬浮层位于其它内容之上
            });
        /*setTimeout(function(){$('#overlay').fadeOut('slow')}, 3000);*/ //设置3秒后覆盖层自动淡出
    }

    /**
     * 加载动画绘制
     * @type {{}}
     */
    var O = {};

    O.Particle = function (opt) {
        this.radius = 7;
        this.x = opt.x;
        this.y = opt.y;
        this.angle = opt.angle;
        this.speed = opt.speed;
        this.accel = opt.accel;
        this.decay = 0.01;
        this.life = 1;
    };

    O.Particle.prototype.step = function (i) {
        this.speed += this.accel;
        this.x += Math.cos(this.angle) * this.speed;
        this.y += Math.sin(this.angle) * this.speed;
        this.angle += O.PI / 64;
        this.accel *= 1.01;
        this.life -= this.decay;

        if (this.life <= 0) {
            O.particles.splice(i, 1);
        }
    };

    O.Particle.prototype.draw = function (i) {
        O.ctx.fillStyle = O.ctx.strokeStyle = 'hsla(' + (O.tick + (this.life * 120)) + ', 100%, 60%, ' + this.life + ')';
        O.ctx.beginPath();
        if (O.particles[i - 1]) {
            O.ctx.moveTo(this.x, this.y);
            O.ctx.lineTo(O.particles[i - 1].x, O.particles[i - 1].y);
        }
        O.ctx.stroke();

        O.ctx.beginPath();
        O.ctx.arc(this.x, this.y, Math.max(0.001, this.life * this.radius), 0, O.TWO_PI);
        O.ctx.fill();

        var size = Math.random() * 1.25;
        O.ctx.fillRect(~~(this.x + ((Math.random() - 0.5) * 35) * this.life), ~~(this.y + ((Math.random() - 0.5) * 35) * this.life), size, size);
    };

    O.step = function () {
        O.particles.push(new O.Particle({
            x: O.width / 2 + Math.cos(O.tick / 20) * O.min / 2,
            y: O.height / 2 + Math.sin(O.tick / 20) * O.min / 2,
            angle: O.globalRotation + O.globalAngle,
            speed: 0,
            accel: 0.01
        }));

        O.particles.forEach(function (elem, index) {
            elem.step(index);
        });

        O.globalRotation += O.PI / 6;
        O.globalAngle += O.PI / 6;
    };

    O.draw = function () {
        O.ctx.clearRect(0, 0, O.width, O.height);

        O.particles.forEach(function (elem, index) {
            elem.draw(index);
        });
    };

    O.init = function () {
        O.canvas = document.createElement('canvas');
        O.canvas.className = "loading";
        O.ctx = O.canvas.getContext('2d');
        O.width = 300;
        O.height = 300;
        O.canvas.width = O.width * window.devicePixelRatio;
        O.canvas.height = O.height * window.devicePixelRatio;
        O.canvas.style.width = O.width + 'px';
        O.canvas.style.height = O.height + 'px';
        O.ctx.scale(window.devicePixelRatio, window.devicePixelRatio);
        O.min = O.width * 0.5;
        O.particles = [];
        O.globalAngle = 0;
        O.globalRotation = 0;
        O.tick = 0;
        O.PI = Math.PI;
        O.TWO_PI = O.PI * 2;
        O.ctx.globalCompositeOperation = 'lighter';
        document.body.appendChild(O.canvas);
        // document.body.append("<div class='loading' >加载中</div>");
        O.loop();
    };

    O.loop = function () {
        requestAnimationFrame(O.loop);
        O.step();
        O.draw();
        O.tick++;
    };
    overlay();
    O.init();
}

/**
 * 移除加载动画
 */
function removeLoad() {
    $('.loading').remove();
    $('#overlay').fadeOut('slow');
}