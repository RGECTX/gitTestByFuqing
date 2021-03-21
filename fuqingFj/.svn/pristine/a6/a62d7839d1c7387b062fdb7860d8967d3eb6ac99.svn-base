layui.use(['form', 'table', "upload", "laydate"], function () {
    var $ = layui.jquery;
    var table = layui.table;
    var form = layui.form;
    var upload = layui.upload;
    var laydate = layui.laydate;

    var url = servicePath + "/auxZljd/searchAproveOrg?charset=utf-8&loginCode=" + adminInfo.loginCode;
    auxZljdTable = table.render({
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
        title: '招录建档',
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
                field: 'orgName',
                title: '上报单位',
                width: 120,
                align: "center"
            }, {
                field: 'newReceiveOrgId',
                title: '接收单位(分配)',
                width: 180,
                align: "center",
                templet: function (d) {
                    return getOrgName(d.newReceiveOrgId);
                }
            }, {
                field: 'state',
                title: '状态',
                width: 120,
                align: "center",
                templet: function (d) {
                    if (d.state == '2') {
                        return '<font color=blue >审批中</font>'
                    } else if (d.state == '4') {
                        return '<font color=green >审批通过</font>'
                    } else if (d.state == '8') {
                        return '<font color=red >审批不通过</font>'
                    } else {
                        return '未上报';
                    }
                }
            },{
                field: 'ryfpState',
                title: '单位接收状态',
                width: 120,
                align: "center",
                templet: function (d) {
                    if (d.ryfpState == '2') {
                        return '<font color=blue >分配中</font>'
                    } else if (d.ryfpState == '4') {
                        return '<font color=green >分配通过</font>'
                    } else if (d.ryfpState == '8') {
                        return '<font color=red >分配不通过</font>'
                    } else if (d.ryfpState == '1') {
                        return '未上报';
                    }
                }
            },/* {
                field: 'receiveOrgId',
                title: '接收单位(分配)',
                width: 180,
                align: "center"
            },*/ {
                field: 'remark',
                title: '备注',
                minWidth: 200
            }, {
                title: '操作',
                toolbar: '#barDemo',
                fixed: 'right',
                width: 150
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
            dataName: 'auxZljdList' //规定数据列表的字段名称，默认：data
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
                $("#add_uploadList").empty();
                var formData = {};
                formData.orgId = adminInfo.amUnitId;
                formData.orgName = adminInfo.unitName;
                form.val("add", formData);
                layer.open({
                    type: 1,
                    title: '招录建档',
                    area: ['800px', '620px'],
                    content: $('#addPage'),
                    btn: ['招录建档信息提交', '取消'],
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
                    var myIds = data[i].zlId;
                    if (ids == '') {
                        ids = ids + myIds;
                    } else {
                        ids = ids + "," + myIds;
                    }
                }

                layer.confirm('确定删除选中的招录建档信息吗', function (index) {
                    var layerIndex = layer.load();
                    var url = servicePath + "/auxZljd/delete?charset=utf-8&loginCode=" + adminInfo.loginCode + "&ids=" + ids;
                    $.ajax({
                        type: "POST",
                        url: url,
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify(data),
                        dataType: "json",
                        success: function (data) {
                            layer.close(layerIndex);
                            auxZljdTable.reload();
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
        if (obj.event === 'edit') {
            getAttachmentList("AuxZljd", data.zlId);
            form.val("edit", data);
            layer.open({
                type: 1,
                title: '单位接收信息审批',
             /*   area: ['800px', '620px'],
                content: $('#editPage'),*/
                btn: ['通过', '不通过', '关闭'],
                yes: function (index, layero) {
                    $("#editSubmit").click();
                    layer.close(index);
                },
                btn2: function (index, layero) {
                    check(data, 8);
                },
                btn3: function (index, layero) {
                    layer.close(index);
                }
            });
        }

        if (obj.event === 'view') {
            getAttachmentListView("AuxZljd", data.zlId);
            form.val("view", data);
            layer.open({
                type: 1,
                title: '查看招录建档信息',
                area: ['800px', '620px'],
                content: $('#viewPage'),
                btn: ['关闭'],
                yes: function (index, layero) {
                    layer.close(index);
                }
            });
        }

        if (obj.event === 'zlAdd') {
            getAttachmentList("AuxZljd", data.zlId);
            layer.confirm('确定要开始招录吗？', function (index) {

                zlAdd(data.zlId);
                layer.close(index);
            });
        }

        if (obj.event === 'startApply') {
            if (data.progress != null && data.progress != '') {
                layer.msg("该条记录已经提交申请不能重复提交！");
                return;
            }
            layer.confirm('确定要提交申请吗？', function (index) {
                startApply(data.zlId);
                layer.close(index);
            });
        }

    });

    // 审批
    function check(data, RyfpState){
        var layerIndex = layer.load();
        var params={"zlId":data.zlId, "RyfpState":RyfpState};
        $.ajax({
            type: "POST",
            url: servicePath + "/auxZljd/check?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(params),
            dataType: "json",
            success: function(data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("审批未通过", 2000, function() {
                        layer.closeAll('page');
                        auxZljdTable.reload();

                        var iframeWindow = top.getIframeWindow("auxiliary/ryfpList.html?v=1.60");
                        if (iframeWindow != undefined) {
                            iframeWindow.auxZljdTable.reload();
                            top.topTabDelete("auxiliary/dwjsList.html?v=1.60");
                        }

                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！",2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    }

    //开始招录
    function zlAdd(zlId) {
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/auxZljd/zlAdd?charset=utf-8&zlId=" + zlId + "&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (res) {
                layer.close(layerIndex);
                if (res.errCode == 0) {
                    /*addZlUpload.reload({
                            url: servicePath + "/file/upload?charset=utf-8&instanceType=AuxDagl&instanceId=" + zlId + "&loginCode=" + adminInfo.loginCode
                        });*/
                    showTicket("保存成功！", 1000, function () {
                        layer.closeAll('page');
                        auxZljdTable.reload();
                    });
                    /*addZlUpload.upload();*/

                } else if (res.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(res.errMsg, 2000);
                }

            }
        })
    };

    //开始提交申请
    function startApply(zlId) {
        var layerIndex = layer.load();
        $.ajax({
            type: "GET",
            url: servicePath + "/zlProcess/apply?charset=utf-8&zlId=" + zlId + "&processTempleteId=111503644557312&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("提交成功", 1000);
                    auxZljdTable.reload();
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
    };

    //添加招录信息
    function addAuxZljd(data) {
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/auxZljd/add?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    if ($("#add_uploadList").find("tr").length == 0) {
                        showTicket("保存成功！", 1000, function () {
                            layer.closeAll('page');
                            document.getElementById("add").reset();
                            auxZljdTable.reload();
                        });
                    } else {
                        addUpload.reload({
                            url: servicePath + "/file/upload?charset=utf-8&instanceType=AuxZljd&instanceId=" + data.auxZljdObj.zlId + "&loginCode=" + adminInfo.loginCode
                        });
                        addUpload.upload();
                    }
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

    function editAuxZljd(data) {
        var formData = data;
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/auxZljd/updateDwjs?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    if ($("#edit_uploadList").find("tr[id]").length == 0) {
                        showTicket("保存成功！", 1000, function () {
                            layer.closeAll('page');
                            auxZljdTable.reload();

                            var iframeWindow = top.getIframeWindow("auxiliary/archiveList.html?v=1.60");
                            if (iframeWindow != undefined) {
                                iframeWindow.amArchiveTable.reload();
                                top.topTabDelete("auxiliary/dwjsList.html?v=1.60");
                            }

                        });
                    } else {
                        editUpload.reload({
                            url: servicePath + "/file/upload?charset=utf-8&instanceType=AuxZljd&instanceId=" + formData.zlId + "&loginCode=" + adminInfo.loginCode
                        });
                        editUpload.upload();
                    }
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


    //添加信息“保存”按钮触发
    form.on('submit(add)', function (data) {
        addAuxZljd(form.val("add"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    //修改信息“保存”按钮触发
    form.on('submit(edit)', function (data) {
        editAuxZljd(form.val("edit"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });


    //搜索访问民警信息
    function searchAuxZljd() {
        var formData = form.val("searchForm");
        var data = {
            receiveOrgId: formData.receiveOrgId
        }
        auxZljdTable.reload({
            where: data
        });
    }

    //搜索用户“查询”按钮触发
    form.on('submit(searchBtn)', function (data) {
        searchAuxZljd()
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
                        $(".receiveOrgId").each(function () {
                            seladditem(orgList[i].orgId, orgList[i].orgName, this);
                        })
                        $(".newReceiveOrgId").each(function () {
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

    //设置单位下拉列表框
    setAmUnitDropList();


    //字典下拉框
    $(".state").each(function () {
        addDictDataToSelect(this, "AUX_STATE");
    })
    $(".fjType").each(function () {
        addDictDataToSelect(this, "AUX_FJLB");
    })

    $(".newFjType").each(function () {
        addDictDataToSelect(this, "AUX_FJLB");
    })

    $(".RyfpState").each(function () {
        addDictDataToSelect(this, "RYFP_STATE");
    })

    form.render('select');


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

    //===============================上传附件开始==================================================
    //container：容器的JQ对象
    //instanceType：主体类型
    //instanceId：主体ID
    function fileUpload(container, elem, instanceType, instanceId) {
        //多文件列表上传
        var uploadListIns = upload.render({
            elem: elem
            ,
            url: servicePath + "/file/upload?charset=utf-8&instanceType=" + instanceType + "&instanceId=" + instanceId + "&loginCode=" + adminInfo.loginCode
            ,
            accept: 'file'
            ,
            multiple: true
            ,
            auto: false
            //,bindAction: '#edit_uploadAction'
            ,
            choose: function (obj) {
                var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                //读取本地文件
                obj.preview(function (index, file, result) {
                    var tr = $(['<tr id="upload-' + index + '">'
                        , '<td>' + file.name + '</td>'
                        , '<td>' + (file.size / 1014).toFixed(1) + 'kb</td>'
                        , '<td>等待上传</td>'
                        , '<td>'
                        , '<button class="layui-btn layui-btn-xs reupload layui-hide">重传</button>'
                        , '<button class="layui-btn layui-btn-xs layui-btn-danger deleteTr">删除</button>'
                        , '</td>'
                        , '</tr>'].join(''));

                    //单个重传
                    tr.find('.reupload').on('click', function () {
                        obj.upload(index, file);
                    });

                    //删除
                    tr.find('.deleteTr').on('click', function () {
                        delete files[index]; //删除对应的文件
                        tr.remove();
                        uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                    });
                    container.append(tr);
                });
            }
            ,
            done: function (res, index, upload) {
                if (res.errCode == 0) { //上传成功
                    var tr = container.find('tr#upload-' + index)
                        , tds = tr.children();
                    tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                    tds.eq(3).html('<button class="layui-btn layui-btn-xs viewFile layui-hide" attachmentId="' + res.attachmentList[0].id + '">查看</button><button class="layui-btn layui-btn-xs layui-btn-danger deleteTr" attachmentId="' + res.attachmentList[0].id + '">删除</button>'); //操作变成查看
                    return delete this.files[index]; //删除文件队列已经上传成功的文件
                }
                this.error(index, upload);
            }
            ,
            error: function (index, upload) {
                var tr = container.find('tr#upload-' + index)
                    , tds = tr.children();
                tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                tds.eq(3).find('.reupload').removeClass('layui-hide'); //显示重传
            }
            ,
            allDone: function (obj) { //当文件全部被提交后，才触发
                showTicket("保存成功！", 1000, function () {
                    layer.closeAll('page');
                    document.getElementById("add").reset();
                    auxZljdTable.reload();
                });
            }
        });
        return uploadListIns;
    }


    var addUpload = fileUpload($("#add_uploadList"), '#add_selectFileBtn', "AuxZljd", 1);

    var editUpload = fileUpload($("#edit_uploadList"), '#edit_selectFileBtn', "AuxZljd", 1);


    //获取附件列表
    function getAttachmentList(instanceType, instanceId) {
        var layerIndex = layer.load();
        $.ajax({
            type: "GET",
            url: servicePath + "/file/getAttachmentList?charset=utf-8&instanceType=" + instanceType + "&instanceId=" + instanceId + "&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    $("#edit_uploadList").html("");
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
                        $("#edit_uploadList").append(tr);
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


    //删除附件
    function delAttachment(attachmentId) {
        var layerIndex = layer.load();
        $.ajax({
            type: "GET",
            url: servicePath + "/file/delAttachment?charset=utf-8&attachmentId=" + attachmentId + "&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
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
    };


    //删除附件
    $("#edit_uploadList").delegate('.deleteFile', 'click', function () {
        delAttachment($(this).attr("attachmentId"));
        $(this).parents("tr").remove();
    });

    //删除附件
    $("#add_uploadList").delegate('.deleteFile', 'click', function () {
        delAttachment($(this).attr("attachmentId"));
        $(this).parents("tr").remove();
    });
    //===============================上传附件结束==================================================

    //选择慰问人员
    $(document).on('click', '#addUser', function () {
        top.openSelectUser({
                url: 'selectUser.html',
                title: '选择民警',
                resource: '/user/selectUser',
                where: {orgId: adminInfo.amUnitId},
                treeWhere: {orgId: adminInfo.amUnitId},
                area: ['800px', '620px'],
                page: true,
                limit: 12,
                limits: [12, 30, 50, 100],
                selectType: 'checkbox',//radio,checkbox
                checkedData: [],//默认选中的数据，只对selectType为radio起作用
                disabledData: [],//默认禁用的数据
                compareField: 'id'//用于识别选中或禁用的字段
            }, window.name, function (returnData, index) {
                /*document.getElementById("add").reset();*/
                if (returnData[0] != undefined) {
                    var formData = {};
                    var selectUserList = [];
                    for (var i = 0; i < returnData.length; i++) {
                        selectUserList.push(returnData[i].name);
                    }
                    $("#userName").val(selectUserList);
                    form.val("add", formData);
                }
                top.layer.close(index);
            }
        );
    });

    //编辑慰问人员
    $(document).on('click', '#editUser', function () {
        top.openSelectUser({
                url: 'selectUser.html',
                title: '选择民警',
                resource: '/user/selectUser',
                where: {orgId: adminInfo.amUnitId},
                treeWhere: {orgId: adminInfo.amUnitId},
                area: ['800px', '620px'],
                page: true,
                limit: 12,
                limits: [12, 30, 50, 100],
                selectType: 'checkbox',//radio,checkbox
                checkedData: [],//默认选中的数据，只对selectType为radio起作用
                disabledData: [],//默认禁用的数据
                compareField: 'id'//用于识别选中或禁用的字段
            }, window.name, function (returnData, index) {
                /*document.getElementById("add").reset();*/
                if (returnData[0] != undefined) {
                    var formData = {};
                    var selectUserList = [];
                    for (var i = 0; i < returnData.length; i++) {
                        selectUserList.push(returnData[i].name);
                    }
                    $("#edituserName").val(selectUserList);
                    form.val("edit", formData);
                }
                top.layer.close(index);
            }
        );
    });


    //选择领导
    $(document).on('click', '#addLds', function () {
        top.openSelectUser({
                url: 'selectUser.html',
                title: '选择领导',
                resource: '/user/selectUser',
                where: {orgId: adminInfo.amUnitId, state: 1},
                treeWhere: {orgId: adminInfo.amUnitId},
                area: ['800px', '620px'],
                page: true,
                limit: 12,
                limits: [12, 30, 50, 100],
                selectType: 'checkbox',//radio,checkbox
                checkedData: [],//默认选中的数据，只对selectType为radio起作用
                disabledData: [],//默认禁用的数据
                compareField: 'id'//用于识别选中或禁用的字段
            }, window.name, function (returnData, index) {
                /*document.getElementById("add").reset();*/
                if (returnData[0] != undefined) {
                    var formData = {};
                    var selectUserList = [];
                    for (var i = 0; i < returnData.length; i++) {
                        selectUserList.push(returnData[i].name);
                    }
                    $("#lds").val(selectUserList);
                    form.val("add", formData);
                }
                top.layer.close(index);
            }
        );
    });

    //编辑领导
    $(document).on('click', '#editLds', function () {
        top.openSelectUser({
                url: 'selectUser.html',
                title: '选择领导',
                resource: '/user/selectUser',
                where: {orgId: adminInfo.amUnitId, state: 1},
                treeWhere: {orgId: adminInfo.amUnitId},
                area: ['800px', '620px'],
                page: true,
                limit: 12,
                limits: [12, 30, 50, 100],
                selectType: 'checkbox',//radio,checkbox
                checkedData: [],//默认选中的数据，只对selectType为radio起作用
                disabledData: [],//默认禁用的数据
                compareField: 'id'//用于识别选中或禁用的字段
            }, window.name, function (returnData, index) {
                /*document.getElementById("add").reset();*/
                if (returnData[0] != undefined) {
                    var formData = {};
                    var selectUserList = [];
                    for (var i = 0; i < returnData.length; i++) {
                        selectUserList.push(returnData[i].name);
                    }
                    $("#editld").val(selectUserList);
                    form.val("edit", formData);
                }
                top.layer.close(index);
            }
        );
    });

})
