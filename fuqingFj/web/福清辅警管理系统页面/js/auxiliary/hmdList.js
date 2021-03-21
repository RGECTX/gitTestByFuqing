layui.use(['form', 'table', "upload", "laydate"], function () {
    var $ = layui.jquery;
    var table = layui.table;
    var form = layui.form;
    var url = servicePath + "/auxHmd/search?charset=utf-8&loginCode=" + adminInfo.loginCode;
    var auxHmdTable = table.render({
        elem: '#test',
        url: url,
        method: 'POST',
        contentType: "application/json; charset=utf-8",
        where: {
            "kwFields": 3,
            "keyword": ""
        },
        toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        ,
        defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        title: '黑名单管理列表',
        cols: [
            [{
                type: 'checkbox',
                fixed: 'center'
            }, {
                field: 'xm',
                title: '姓名',
                width: 150,
                align: "center"
            }, {
                field: 'idcard',
                title: '身份证号',
                width: 200,
                align: "center"
            }, {
                field: 'reason',
                title: '加入黑名单原因',
                width: 500,
                align: "center"
            }, {
                field: 'remark',
                title: '备注',
                minWidth: 120,
                align: "center"
            }, {
                title: '操作',
                toolbar: '#barDemo',
                fixed: 'right',
                width: 120
            }
            ]
        ],
        page: true,
        parseData: function (res) { //res 即为原始返回的数据
            return res;
        },
        height: 'full-105',
        response: {
            statusName: 'errCode', //规定数据状态的字段名称，默认：code
            statusCode: 0, //规定成功的状态码，默认：0
            msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
            countName: 'recordCount', //规定数据总数的字段名称，默认：count
            dataName: 'auxHmdList' //规定数据列表的字段名称，默认：data
        },
        request: {
            pageName: 'page', //页码的参数名称，默认：page
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
    });

    //头工具栏事件
    table.on('toolbar(test)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'addData':
                document.getElementById("add").reset();
                var formData = {};
                form.val("add", formData);
                layer.open({
                    type: 1,
                    title: '黑名单',
                    area: ['800px', '620px'],
                    content: $('#addPage'),
                    btn: ['黑名单信息提交', '取消'],
                    yes: function (index, layero) {
                        $("#addSubmit").click();
                    },
                    btn2: function (index, layero) {
                        layer.close(index);
                    }
                });
                break;

            case 'del':
                var data = checkStatus.data;
                if (data.length == 0) {
                    layer.msg('请先选择数据');
                    return;
                }
                var ids = "";
                for (var i = 0; i < data.length; i++) {
                    var myIds = data[i].hmdId;
                    if (ids == '') {
                        ids = ids + myIds;
                    } else {
                        ids = ids + "," + myIds;
                    }
                }

                layer.confirm('确定删除选中的黑名单信息吗', function (index) {
                    var layerIndex = layer.load();
                    var url = servicePath + "/auxHmd/delete?charset=utf-8&loginCode=" + adminInfo.loginCode + "&ids=" + ids;
                    $.ajax({
                        type: "POST",
                        url: url,
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify(data),
                        dataType: "json",
                        success: function (data) {
                            layer.close(layerIndex);
                            auxHmdTable.reload();
                            if (data.errCode == 0) {
                                showTicket("删除成功", 1000);
                            } else if (data.errCode == 200061 || data.errCode == 200062) {
                                showTicket("请先登录！", 2000, function () {
                                    parent.location.href = "../index.html?v=1.60";
                                });
                            } else if (data.errCode == 200063) {
                                showTicket("没有权限！", 2000);
                            } else {
                                showTicket(data.errMsg, 2000);
                            }
                        }
                    })
                });
                break;
        }
        ;
    });

    //监听行工具事件
    table.on('tool(test)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('确定删除家访民警吗', function (index) {
                obj.del();
                var layerIndex = layer.load();
                var url = servicePath + "/axedgJfmj/delete?charset=utf-8&loginCode=" + adminInfo.loginCode + "&id=" + obj.data.jfmjId;
                $.ajax({
                    type: "POST",
                    url: url,
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(data),
                    dataType: "json",
                    success: function (data) {
                        layer.close(layerIndex);
                        if (data.errCode == 0) {
                            showTicket("删除成功", 1000);
                        } else if (data.errCode == 200061 || data.errCode == 200062 || data.errCode == 200063) {
                            showTicket("请先登录！", 2000, function () {
                                parent.location.href = "index.html?v=1.60";
                            });
                        }
                    }
                })
            });
        }

        if (obj.event === 'edit') {
            form.val("edit", data);
            layer.open({
                type: 1,
                title: '修改黑名单信息',
                area: ['800px', '620px'],
                content: $('#editPage'),
                btn: ['保存', '关闭'],
                yes: function (index, layero) {
                    $("#editSubmit").click();
                },
                btn2: function (index, layero) {
                    layer.close(index);
                }
            });
        }

        if (obj.event === 'view') {
            form.val("view", data);
            layer.open({
                type: 1,
                title: '查看黑名单信息',
                area: ['800px', '620px'],
                content: $('#viewPage'),
                btn: ['关闭'],
                yes: function (index, layero) {
                    layer.close(index);
                }
            });
        }
    });


    function addAuxHmd(data) {
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/auxHmd/add?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("保存成功！", 1000, function () {
                        layer.closeAll('page');
                        document.getElementById("add").reset();
                        auxHmdTable.reload();
                    });
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "../index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }

            }
        })
    }

    function editAuxHmd(data) {
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/auxHmd/update?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("保存成功！", 1000, function () {
                        layer.closeAll('page');
                        auxHmdTable.reload();
                    });
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "../index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    }

    //添加黑名单信息“保存”按钮触发
    form.on('submit(add)', function (data) {
        addAuxHmd(form.val("add"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    //修改黑名单信息“保存”按钮触发
    form.on('submit(edit)', function (data) {
        editAuxHmd(form.val("edit"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    //搜索黑名单信息
    function searchAuxHmd() {
        var formData = form.val("searchForm");
        var data = {
            orgName: formData.orgName,
            xm:formData.xm
        }
        auxHmdTable.reload({
            where: data
        });
    }

    //搜索“查询”按钮触发
    form.on('submit(searchBtn)', function (data) {
        searchAuxHmd()
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    //设置单位下拉列表框
    function setAmUnitDropList() {
        $.ajax({
            type: "POST",
            url: servicePath + "/amUnit/search?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                "page": 1,
                "pageSize": 1000
            }),
            dataType: "json",
            success: function (data) {
                if (data.errCode == 0) {
                    var orgList = data.orgList;
                    for (var i = 0; i < orgList.length; i++) {
                        $(".orgId").each(function () {
                            seladditem(orgList[i].orgId, orgList[i].orgName, this);
                        })
                    }
                    form.render('select');
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.59";
                    });
                }
            }
        })
    }

    //身份证校验
    form.verify({
        numbermsg: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value != "") {  //值不是空的时候再去走验证
                if (!/^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|30|31)|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}([0-9]|x|X)$/.test(value)) {
                    return '身份证格式有误，请重新输入！';
                }
            }
        }
    });
    //设置单位下拉列表框
    setAmUnitDropList();
})
