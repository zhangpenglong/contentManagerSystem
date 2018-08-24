$(function(){
    // 默认皮肤orange
    if(JXJY.getCookie("skinC") == '' || JXJY.getCookie("skinC") == null){
        var css=$("#cssSrc"),jbox_src=$("link#jbox"),exp=0;
        css.attr("href",$("#allPageJs").attr('contextPath')+"/css/skin/blue.css");
        JXJY.setCookie("skinC","blue",{path:"/",expires:"d300"});
    }
});

(function(w){
	/*公用方法*/
    var JXJY,
        Dcontainer=$(".container"),
        Dmain=$("#main"),
        win=$(w),
        ww=win.width(),
        MINWIDTH=1132,
        _path = $("#allPageJs").attr('contextPath')+"/";
    JXJY={
        getCookie:function(str){
            var arr=document.cookie.match(RegExp(str+"=([^;]*)"));
            return arr!=null?unescape(arr[1]):"";
        },
        setCookie:function(cname,cvalue,options){
            function isNonEmptyString(str){
                return typeof str==="string"&&str!=='';
            }
            if(!isNonEmptyString(cname)){
                throw new TypeError("cookie名必须是一个非空字符串。");
            }
            options=options||{};
            var expires=options["expires"];
            var domain=".dongaoacc.com";
            var path=options["path"];
            var secure=options["secure"];
            cvalue=escape(cvalue);
            var str=cname+"="+cvalue;
            function getSec(time){
                var timeValue;
                if(time instanceof Date){
                    timeValue=time.getTime();
                }else{
                    var type=String(time).substring(0,1);
                    timeValue=parseInt(String(time).substring(1,String(time).length));
                    switch(type){
                        case "s": timeValue*=1000; break;
                        case "m": timeValue*=60*1000; break;
                        case "h": timeValue*=60*60*1000; break;
                        case "d": timeValue*=24*60*60*1000; break;
                        default: timeValue=0; break;
                    }
                }
                return timeValue;
            }
            var date=new Date();
            date.setTime(date.getTime()+getSec(expires));
            str+=";expires="+date.toGMTString();
            if(isNonEmptyString(domain)) str+=";domain="+domain;
            if(isNonEmptyString(path)) str+=";path="+path;
            if(isNonEmptyString(secure)) str+=";secure="+secure;
            document.cookie=str;
        },
        delCookie:function(cname){
            var date=new Date();
            date.setTime(date.getTime()-1000);
            document.cookie=cname+"=a;expires="+date.toUTCString();
        },
        showSkinBtn:function(obj){//显示或隐藏皮肤按钮
            var div=obj.siblings("div").eq(0);
            div.is(":visible")?div.hide():div.show();
        },
        changeSkin:function(obj){//切换CSS
            var css=$("#cssSrc"),jbox_src=$("link#jbox"),exp=0;
            defaultSkin = obj.attr("class");
            css.attr("href",_path+"css/skin/"+obj.attr("class")+".css");
            jbox_src.attr("href",_path+"css/jbox/jbox_"+obj.attr("class")+".css")
            JXJY.setCookie("skinC",obj.attr("class"),{path:"/",expires:"d300"})
            obj.parent().hide();
        },
        getSkin:function(){
            if(JXJY.getCookie("skinC")){

                var css=$("#cssSrc"),jbox_src=$("link#jbox");
                css.attr("href",_path+"css/skin/"+JXJY.getCookie("skinC")+".css");
                jbox_src.attr("href",_path+"css/jbox/jbox_"+JXJY.getCookie("skinC")+".css");
            }
        },
        sideBarHeight:function(){//左侧导航高度设置
            var resultHeight=win.height()-3;
            Array.prototype.max=function(){
                return Math.max.apply(Math,this);
            }
            var hArr=[$("#sideCont").height(),$("#main").height()]
            if(resultHeight>=[$("#sideCont").height(),$("#main").height()].max()){
                $(".container").height(resultHeight)
            }else{
                $(".container").height(hArr.max())
            }
        },

        fullWidth:function(){//头，中，尾100%宽

            //ww=win.width();
            if(win.width()<=MINWIDTH){
                win.width(MINWIDTH)
                //ww=MINWIDTH;

            }else{
                win.width(ww)

                //ww=win.width()
            }
            Dcontainer.width(win.width()+win.scrollLeft());
            Dmain.width(Dcontainer.width()-30);
        },
        sideMenuShow:function(icon,obj){//左侧导航二级栏目显示控制
            if(obj.length){
                if(obj.is(":visible")){
                    obj.hide();
                    icon.removeClass("sel");
                }else{
                    obj.show();
                    icon.addClass("sel");
                }
            }
        },
        valign:function(obj,h){//垂直居中
            obj.css("top",(win.height()-obj.height())/2-h+"px")
        },
        align:function(obj,width){//水平居中
            var _width=parseInt(Dmain.siblings().css("marginLeft"));
            var _left=_width<0?(Dmain.width()-width)/2:(Dmain.width()-width)/2+Dmain.siblings().width()
            obj.css("left",_left+"px");
        },
        iframeHeight:function(){//获取弹窗的高度
            $(".ifm").load(function(){
                var _height=ifm.contents().find("body").height();
                if(_height>win.height()-71){
                    _height=win.height()-71

                }else{

                }
                ifm.height(_height)
            })
            //ifm.contents().find(".box_content").height(_height-71)
        },
        showBox:function(title,width,h,url){//显示弹窗，设置标题，居中
            var box=$(".box_container");
            $(".mask").show();
            box.find(".ifm").attr("src",url)
            box.find(".box_title span").text(title)
            box.show();
            box.width(width)
            JXJY.iframeHeight()
            JXJY.valign(box,h)
            JXJY.align(box,width)
        },

        menuShow:function(icon,obj){
            if(obj.length){
                if(obj.is(":visible")){
                    obj.hide();
                    //icon.removeClass("sel");
                }
                else{
                    obj.show();
                    //icon.addClass("sel");
                }
            }
        },
        iconClick:function(obj){//展开下拉列表
            $(".selList").hide();
            var lis=obj.siblings("ul")
            JXJY.menuShow(obj,lis);

        },
        liClick:function(obj){//选中下拉列表
            var ul=obj.parent();
            var txt=ul.siblings(".selectTxt");
            var status=ul.siblings(".status");
            obj.addClass("sel").siblings().removeClass("sel");
            txt.text(obj.text());
            status.val(obj.attr("val"));
            ul.hide();
            if(ul.hasClass("tempList")){
                JXJY.bankTemplate();
            }
            if(ul.hasClass("bankGroup")){
                JXJY.showBox("温馨提示",500,0,"温馨提示.html")
            }
            return false;
        },
        selectAll:function(obj,tag){//全选
            obj.live("click",function(){
                if(!$(this).is(":checked"))
                    tag.removeAttr("checked")
                else
                    tag.attr("checked","checked")
            })
            tag.live("click",function(){
                if(!$(this).is(":checked"))
                    $(this).removeAttr("checked")
                else
                    $(this).attr("checked","checked")
            })
        },
        //file:function(obj){
        //obj.live("click",function(){
        //var _this=$(this)
        //var _file=_this.siblings(":file")
        //_file.click();
        //_file.change(function(){
        //	$(this).siblings(".selectTxt").val($(this).val())
        //})
        //})
//	},
        changeBorderColor:function(obj,cls){//鼠标经过文本框效果
            obj.live("mouseover",function(){
                $(this).addClass(cls)
            }).live("mouseout",function(){
                $(this).removeClass(cls)
            })
        },
        clearData:function(){
            $(".word,.cword,.selectTxt,.status").val("")
            $(".selectTxt").text("请选择")
            $(".selList li").removeClass("sel")
        },
        desc:function(obj){//排序提示框
            obj.hover(function(){
                $(this).children("div").show();
            },function(){
                $(this).children("div").hide();
            })
            obj.find("li").mouseover(function(){
                $(this).addClass("sel").siblings().removeClass("sel")
            }).click(function(){
                JXJY.descResult($(this).parent().parent());
            })
        },
        descResult:function(obj){//排序功能
            obj.hide();
            //ajax
        },

        hideBox:function(box,mask){
            box.hide();
        },
        hideMask:function(mask){
            mask.hide();
        },
        deleteBox:function(exit){
            exit.click(function(){
                var box=$(".box_container")
                var mask=$(".mask")
                JXJY.hideBox(box)
                JXJY.hideMask(mask)
            })
        },
        addSpace:function(){
            $(".data_Table td").each(function(){
                if($(this).html()=="") $(this).html("&nbsp;");
            })
        },
        dataTableCallback:function(){
            layui.use(['element','form','layer'], function() {
                var form = layui.form;
                form.render('');
            });
            JXJY.fullWidth();
        }
    }
    w.JXJY=JXJY
})(window)

JXJY.getSkin()
$(window).scroll(function(){
    JXJY.fullWidth();//宽度自适应
    $(".sideBtn").css("left",$(this).scrollLeft()>=204?0:204-$(this).scrollLeft()+"px")
    JXJY.valign($(".box_container,.result"),0)//弹窗垂直居中
    JXJY.align($(".box_container"),500)//弹窗相对于右侧内容水平居中
    JXJY.align($(".result"),320)

}).resize(function(){
    JXJY.sideBarHeight();
    JXJY.fullWidth();
    JXJY.valign($(".box_container,.result"),0)//弹窗垂直居中
    JXJY.align($(".box_container"),500)//弹窗相对于右侧内容水平居中
    JXJY.align($(".result"),320)

})

JXJY.sideBarHeight();
JXJY.fullWidth();
$(".skinBtn").click(function(){
    JXJY.showSkinBtn($(this));
})
$(".skinCont i").click(function(){
    JXJY.changeSkin($(this))
})

$(".open").live("click",function(){
    JXJY.iconClick($(this))
    return false;
})
$(document).click(function(){
    $(".selList").hide();
})
$(".selList li").live("click",function(){
    JXJY.liClick($(this))
})
$(".picBtn").click(function(){
    //JXJY.file($(this))
})
var html=$(".addForm").html();
$(".dis").live("click",function(){
    $(".addForm").html(html)
})
//$(".reset_btn").click(function(){
//	cleanAll();
//})
//$(".search_btn").click(function(){
//	search();
//})

//JXJY.file($(".picBtn"))
JXJY.valign($(".result"),0);
JXJY.align($(".result"),320);
JXJY.changeBorderColor($(".cword"),"over")
JXJY.deleteBox($(".box_close,.cancel"))
JXJY.selectAll($(".selAll"),$(".data_Table :checkbox"))
JXJY.desc($(".desc"))