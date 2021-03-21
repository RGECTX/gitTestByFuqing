layui.use(['form', 'table', 'laydate'], function () {
    var $ = layui.jquery;
    var laydate = layui.laydate;
    var table = layui.table;
    var form = layui.form;

    var processId = getParameter('processId');
    var url = servicePath + "/jyglProcess/getApprovalItemList?charset=utf-8&loginCode=" + adminInfo.loginCode + "&processId=" + processId;
    var jyglTable = table.render({
        elem: '#approvalElem',
        url: url,
        method: 'POST',
        contentType: "application/json; charset=utf-8",
        where: {},
        defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        title: '减员管理数据表',
        cols: [
            [
                {
                    type: 'numbers',
                    fixed: 'center'
                }, {
                field: 'nodeName',
                title: '审批节点',
                minwidth: 200
            }
                , {
                field: 'name',
                title: '审批人',
                minwidth: 180,
                sort: true
            }
                , {
                field: 'remark',
                title: '审批意见',
                minwidth: 180,
                sort: true
            }
                , {
                field: 'itemState',
                title: '审批状态',
                minwidth: 250,
                templet: function (d) {
                    if (d.itemState == '1') {
                        return '未到达';
                    } else if (d.itemState == '2') {
                        return '等待审批';
                    } else if (d.itemState == '4') {
                        return '审批通过';
                    } else if (d.itemState == '8') {
                        return '审批不通过';
                    } else {
                        return '';
                    }
                }
            }
                , {
                field: 'updateTime',
                title: '审批时间',
                minwidth: 250
            }
            ]
        ],
        page: false,
        parseData: function (res) { //res 即为原始返回的数据
            return res;
        },
        height: 400,
        response: {
            statusName: 'errCode', //规定数据状态的字段名称，默认：code
            statusCode: 0, //规定成功的状态码，默认：0
            msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
            countName: 'recordCount', //规定数据总数的字段名称，默认：count
            dataName: 'approvalItemList' //规定数据列表的字段名称，默认：data
        },
        request: {
            pageName: 'page', //页码的参数名称，默认：page
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },
    });

    //获取实体数据回显
    function getObject() {


        var jyId = getParameter('jyId');

        getAttachmentListView("AuxJygl", jyId);

        var url = servicePath + "/auxJygl/getObject?charset=utf-8&loginCode=" + adminInfo.loginCode + "&jyId=" + jyId;
        $.ajax({
            type: "POST",
            url: url,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({"jyId": jyId}),
            dataType: "json",
            success: function (data) {
                form.val("addAdmin", data.auxJygl);
                form.render('select');
            }
        })
    }

    getObject();

    //审批
    $("#approvalBtn").on('click', function () {
        var processId = getParameter('processId');
        var jyId = getParameter('jyId');
        document.getElementById("approval").reset();
        layer.open({
            type: 1,
            title: '审批',
            area: ['600px', '500px'],
            content: $('#approvalPage'),
            btn: ['通过', '不通过', '关闭'],
            yes: function (index, layero) {
                form.val("approval", {
                    "jyId": jyId,
                    "processId": processId,
                    "itemState": 4
                });
                $("#approvalSubmit").click();
            },
            btn2: function (index, layero) {
                form.val("approval", {
                    "jyId": jyId,
                    "processId": processId,
                    "itemState": 8
                });
                $("#approvalSubmit").click();
            },
            btn3: function (index, layero) {
                layer.close(index);
            }
        });
    });

    //单个审批
    function approval(data) {
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/process/approval?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("本环节完成，进入下一个环节！", 2000, function () {
                        layer.closeAll('page');
                        location.reload();

                        var iframeWindow = top.getIframeWindow("auxiliary/dclJyglList.html?v=1.60");
                        if (iframeWindow != undefined) {
                            iframeWindow.auxJyglTable.reload();
                        }

                    });
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.74";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    }

    //单个审核“保存”按钮触发
    form.on('submit(approval)', function (data) {
        approval(form.val("approval"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    //打印
    $("#printExcel").on('click', function () {
        var headStr = window.document.head.innerHTML;
        var printStr = "<html><head>" + headStr + "</head><body>";
        var content = "";

        var str = document.body.innerHTML;     //获取需要打印的页面元素 ，page1元素设置样式page-break-after:always，意思是从下一行开始分割。
        content = content + str;

        printStr = printStr + content + "</body></html>";
        console.log(printStr);
        printStr = printStr.replace('<div style="text-align: center;">\n' +
            '                                <a class="layui-btn layui-btn-warn" id="approvalBtn">审批</a>\n' +
            '                                <a class="layui-btn layui-btn-danger" onclick="closeTab();">关闭</a>\n' +
            '                                <a class="layui-btn layui-btn-warm" id="printExcel">打印</a>\n' +
            '                            </div>', "");
        var pwin = window.open("Print.html", "print"); //如果是本地测试，需要先新建Print.htm，如果是在域中使用，则不需要
        pwin.document.write(printStr);
        pwin.document.close();                   //这句很重要，没有就无法实现
        setTimeout(function () {
            pwin.print()
        }, 700);
    })

});

//获取附件列表用于查看
function getAttachmentListView(instanceType, instanceId) {
    var layerIndex = layer.load();
    $.ajax({
        type: "GET",
        url: servicePath + "/file/getAttachmentList?charset=utf-8&instanceType=" + instanceType + "&instanceId=" + instanceId + "&loginCode=" + adminInfo.loginCode,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            layer.close(layerIndex);
            if (data.errCode == 0) {
                $("#view_uploadList").html("");
                var attachmentList = data.attachmentList;
                for (var i = 0; i < attachmentList.length; i++) {
                    var tr = $(['<tr>'
                        , '<td><a href="' + servicePath + '/' + attachmentList[i].filePath + '" target="_blank">' + attachmentList[i].fileName + '</a></td>'
                        , '<td>' + (attachmentList[i].fileSize / 1014).toFixed(1) + 'kb</td>'
                        , '<td>已存在</td>'
                        /*, '<td>'
                        , '<button class="layui-btn layui-btn-xs viewFile layui-hide" attachmentId="' + attachmentList[i].id + '">查看</button><button class="layui-btn layui-btn-xs layui-btn-danger deleteFile" attachmentId="' + attachmentList[i].id + '">删除</button>'
                        , '</td>'*/
                        , '</tr>'].join(''));
                    $("#view_uploadList").append(tr);
                }
            } else if (data.errCode == 200061 || data.errCode == 200062) {
                showTicket("请先登录！", 2000, function () {
                    parent.location.href = "../index.html?v=1.60";
                });
            } else {
                showTicket(data.errMsg, 2000);
            }
        }
    })
};

//关闭当前tab页面
function closeTab() {
    var jyId = getParameter('jyId');
    top.topTabDelete("auxiliary/viewJygl.html?v=1.74&jyId=" + jyId);
}

