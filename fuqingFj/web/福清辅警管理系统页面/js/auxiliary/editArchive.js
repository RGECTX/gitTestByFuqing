layui.use(['form', 'table', 'laydate', 'element', 'upload'], function () {
    var $ = layui.jquery;
    var laydate = layui.laydate;
    var table = layui.table;
    var form = layui.form;
    var element = layui.element;
    var upload = layui.upload;

    var iframeName = getParameter("iframeName");
    var id = getParameter('id');
    var daId = getParameter("daId");
    //权限控制禁止修改一些字段
    var disClassList = ['AM_JBXX_MDFY_STATE', 'AM_JBXX_MDFY_GWJB', 'AM_JBXX_MDFY_XJ', 'AM_JBXX_MDFY_CJGARQ',
        // 'AM_JBXX_MDFY_JX',
        'AM_JBXX_MDFY_RYLB', 'AM_JBXX_MDFY_XL', 'AM_JBXX_MDFY_BYRQ', 'AM_JBXX_MDFY_BYYS',
        'AM_JBXX_MDFY_CJGZRQ', 'AM_JBXX_MDFY_JH', 'AM_JBXX_MDFY_ZWMC', 'AM_JBXX_MDFY_RZRQ',
        'AM_JBXX_MDFY_ZJQSRQ', 'AM_JBXX_MDFY_DABH', 'AM_JBXX_MDFY_GWTZJL', 'AM_JBXX_MDFY_GZKH', 'AM_JBXX_MDFY_KHHMC',
        'AM_JBXX_MDFY_SBJF', 'AM_JBXX_MDFY_ZFGJJJF', 'AM_JBXX_MDFY_SBJF'
    ];
    for (var i = 0; i < disClassList.length; i++) {
        if (adminInfo.powers[disClassList[i]] == 2) {
            $(".DISABLE_" + disClassList[i]).removeAttr("disabled");
        }
    }

    //=========基本信息start===================

    $(".fjType").each(function () {
        addDictDataToSelect(this, "AUX_FJLB");
    })
    $(".xb").each(function () {
        addDictDataToSelect(this, "SEX");
    })
    $(".state").each(function () {
        addDictDataToSelect(this, "AUX_DASTATE");
    })


    /*$(".xb").each(function() {
        addDictDataToSelect(this, "SEX");
    })*/
    $(".mz").each(function () {
        addDictDataToSelect(this, "MZ");
    })
    $(".jz").each(function () {
        addDictDataToSelect(this, "DRIVER_CARD");
    })
    $(".fby").each(function () {
        addDictDataToSelect(this, "YES_NO");
    })
    $(".isdy").each(function () {
        addDictDataToSelect(this, "YES_NO");
    })
    $(".isbx").each(function () {
        addDictDataToSelect(this, "YES_NO");
    })
    $(".hyzk").each(function () {
        addDictDataToSelect(this, "HYZK");
    })
    $(".zzmm").each(function () {
        addDictDataToSelect(this, "AM_ZZMM");
    })
    $(".xl").each(function () {
        addDictDataToSelect(this, "AM_XL");
    })
    $(".hjdlx").each(function () {
        addDictDataToSelect(this, "AM_HJDLX");
    })
    $(".rylb").each(function () {
        addDictDataToSelect(this, "AM_RYLB");
    })
    /*$(".state").each(function() {
        addDictDataToSelect(this, "AM_STATE", ["16"]);
    })*/
    $(".zj").each(function () {
        addDictDataToSelect(this, "AM_ZJ");
    })
    $(".zwmc").each(function () {
        addDictDataToSelect(this, "AM_ZFZ");
    })
    $(".gwlb").each(function () {
        addDictDataToSelect(this, "AM_GWLB");
    })
    $(".gwjb").each(function () {
        addDictDataToSelect(this, "AM_GWJB");
    })
    $(".xj").each(function () {
        addDictDataToSelect(this, "AM_XJ");
    })
    $(".zpfs").each(function () {
        addDictDataToSelect(this, "AM_ZPFS");
    })
    $(".jfly").each(function () {
        addDictDataToSelect(this, "AM_JFLY");
    })
    /*$(".jx").each(function() {
        addDictDataToSelect(this, "AM_JX");
    })*/
    $(".xx").each(function () {
        addDictDataToSelect(this, "BLOOD_TYPE");
    })

    //亲属关系
    $(".kinshipTerm").each(function () {
        addDictDataToSelect(this, "APPELLATION");
    })
    $(".eduLevel").each(function () {
        addDictDataToSelect(this, "AM_XL");
    })
    $(".nowStatus").each(function () {
        addDictDataToSelect(this, "AM_REALTION_NOW_STATUS");
    })
    $(".healthState").each(function () {
        addDictDataToSelect(this, "AM_HEALTH_STATE");
    })
    $(".kjysgbFlag").each(function () {
        addDictDataToSelect(this, "KJYSGB_FLAG");
    })
    $(".cgjFlag").each(function () {
        addDictDataToSelect(this, "CGJ_FLAG");
    })
    $(".fzFlag").each(function () {
        addDictDataToSelect(this, "FZ_FLAG");
    })
    $(".sysjFlag").each(function () {
        addDictDataToSelect(this, "SYSJ_FLAG");
    })
    $(".rhflFlag").each(function () {
        addDictDataToSelect(this, "RHFL_FLAG");
    })
    $(".bjgzFlag").each(function () {
        addDictDataToSelect(this, "BJGZ_FLAG");
    })
    $(".gzddssfw").each(function () {
        addDictDataToSelect(this, "DDSSFW");
    })
    $(".gzdwxz").each(function () {
        addDictDataToSelect(this, "GZDWXZ");
    })
    $(".jzddssfw").each(function () {
        addDictDataToSelect(this, "DDSSFW");
    })

    //受奖情况
    $(".sjlb").each(function () {
        addDictDataToSelect(this, "AUX_SJLB");
    })

    // 学历简历
    $(".degree").each(function () {
        addDictDataToSelect(this, "AM_XW");
    })

    // 合同信息
    $(".contractCategory").each(function () {
        addDictDataToSelect(this, "CONTRACT_CATEGORY");
    })
    $(".contractState").each(function () {
        addDictDataToSelect(this, "CONTRACT_STATE");
    })

    //被装信息
    $(".commodityType").each(function () {
        addDictDataToSelect(this, "COMMODITY_TYPE");
    })

    //年度考核
    $(".khjl").each(function () {
        addDictDataToSelect(this, "AUX_NDKH");
    })
    form.render('select');

    //日期选择器
    lay('.date').each(function () {
        laydate.render({
            elem: this,
            format: 'yyyy-MM-dd',
            trigger: 'click'
        });
    });
    lay('.month').each(function () {
        laydate.render({
            elem: this,
            type: 'month',
            format: 'yyyyMM',
            trigger: 'click'
        });
    });
    lay('.year').each(function () {
        laydate.render({
            elem: this,
            type: 'year',
            trigger: 'click'
        });
    });

    //设置单位下拉列表框
    setAmUnitDropList();

    /*getJbxxObject();*/

    /*getGzxxObject();*/
    getAttachmentList("AuxDagl", daId);

    // 修改基本信息
    form.on('submit(addAdmin)', function (data) {
        $("#updateJbxx").click();
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
    form.on('submit(updateJbxx)', function (data) {
        $("#updateGzxx").click();
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
    form.on('submit(updateGzxx)', function (data) {
        addAdmin(form.val("addAdmin"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    //修改个人政治学习申请信息“保存”按钮触发
    form.on('submit(edit)', function (data) {
        editAuxDagl(form.val("edit"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    function editAuxDagl(data) {
        var formData = data;
        var layerIndex = layer.load();
        formData.daId = daId;
        $.ajax({
            type: "POST",
            url: servicePath + "/auxDagl/update?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    if ($("#edit_uploadList").find("tr[id]").length == 0) {
                        showTicket("保存成功！", 1000, function () {
                            layer.closeAll('page');
                            closeTab();
                        });
                    } else {
                        editUpload.reload({
                            url: servicePath + "/file/upload?charset=utf-8&instanceType=AuxDagl&instanceId=" + formData.daId + "&loginCode=" + adminInfo.loginCode
                        });
                        editUpload.upload();

                        var iframeWindow = top.getIframeWindow("auxiliary/archiveList.html?v=1.74");
                        iframeWindow.amArchiveTable.reload();
                        top.topTabDelete("auxiliary/editArchive.html?v=1.74&daId=" + da_id);
                        alert(999);
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

    //修改基本信息
    function addAdmin(data) {
        data.csrq = formatDate10To8(data.csrq);
        data.byrq = formatDate10To8(data.byrq);
        data.zrrq = formatDate10To8(data.zrrq);
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/amJbxx/update?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            async: false,
            success: function (json) {
                layer.close(layerIndex);
                if (json.errCode == 0) {
                    addGzxx(form.val("updateGzxx"));
                    if ($("#edit_uploadList").find("tr[id]").length > 0) {
                        editUpload.reload({
                            url: servicePath + "/file/upload?charset=utf-8&instanceType=AmJbxx&instanceId=" + json.amJbxx.id +
                                "&loginCode=" + adminInfo.loginCode
                        });
                        editUpload.upload();
                    }
                } else if (json.errCode == 200061 || json.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (json.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(json.errMsg, 2000);
                }
            }
        })
    }

    //修改工作信息
    function addGzxx(data) {
        data.jbxxId = $("#id").val();
        data.idcard = $("#idcard").val();
        data.jrdtrq = formatDate10To8(data.jrdtrq);
        data.cjgzrq = formatDate10To8(data.cjgzrq);
        data.cjgarq = formatDate10To8(data.cjgarq);
        data.rzrq = formatDate10To8(data.rzrq);
        data.zjqsrq = formatDate10To8(data.zjqsrq);
        var layerIndex = layer.load();
        var url = "";
        if (data.id) {
            url = servicePath + "/amGzxx/update?charset=utf-8&loginCode=" + adminInfo.loginCode;
        } else {
            url = servicePath + "/amGzxx/add?charset=utf-8&loginCode=" + adminInfo.loginCode;
        }
        $.ajax({
            type: "POST",
            url: url,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            async: false,
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("操作成功！", 1000, function () {
                        var iframeWindow = top.getIframeWindow(iframeName);
                        if (iframeWindow != undefined) {
                            iframeWindow.amArchiveTable.reload();
                            top.topTabDelete("am/editArchive.html?v=1.60&id=" + id);
                        }
                    });

                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    }

    function getJbxxObject() {
        var url = servicePath + "/auxDagl/getDagl?charset=utf-8&loginCode=" + adminInfo.loginCode + "&daId=" + daId;
        $.ajax({
            type: "POST",
            url: url,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                "daId": daId
            }),
            dataType: "json",
            success: function (data) {
                form.val("edit", data.auxDagl);
            }
        })
    }

    //查看工作信息
    /*function getGzxxObject() {
        $.ajax({
            type: "POST",
            url: servicePath + "/amGzxx/findByJbxxId?charset=utf-8&loginCode=" + adminInfo.loginCode + "&jbxxId=" + id,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(data) {
                form.val("updateGzxx", {
                    id: data.amGzxx.id,
                    zwmc: data.amGzxx.zwmc,
                    zj: data.amGzxx.zj,
                    gwlb: data.amGzxx.gwlb,
                    gwjb: data.amGzxx.gwjb,
                    xj: data.amGzxx.xj,
                    jh: data.amGzxx.jh,
                    jx: data.amGzxx.jx,
                    jrdtrq: formatDate8To10(data.amGzxx.jrdtrq + ""),
                    cjgarq: formatDate8To10(data.amGzxx.cjgarq + ""),
                    cjgzrq: formatDate8To10(data.amGzxx.cjgzrq + ""),
                    rzrq: formatDate8To10(data.amGzxx.rzrq + ""),
                    zjqsrq: formatDate8To10(data.amGzxx.zjqsrq + ""),
                    gwtzjl: data.amGzxx.gwtzjl,
                    gzkh: data.amGzxx.gzkh,
                    khhmc: data.amGzxx.khhmc,
                    sbjf: data.amGzxx.sbjf,
                    zfgjjjf: data.amGzxx.zfgjjjf,
                    zpfs: data.amGzxx.zpfs,
                    jfly: data.amGzxx.jfly
                })
                form.render("select");
            }
        })
    }*/

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
                    getJbxxObject();

                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.59";
                    });
                }
            }
        })
    }

    //设置单位下拉列表框
    /*function setAmUnitDropList() {
        $.ajax({
            type: "POST",
            url: servicePath + "/amUnit/search?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                "orgLevel": 2,
                "page": 1,
                "pageSize": 1000
            }),
            dataType: "json",
            success: function(data) {
                if (data.errCode == 0) {
                    var orgList = data.orgList;
                    for (var i = 0; i < orgList.length; i++) {
                        seladditem(orgList[i].orgId, orgList[i].orgName, document.getElementById("jdOrgId"));
                    }
                    form.render('select');
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function() {
                        parent.location.href = "index.html?v=1.60";
                    });
                }
            }
        })
    }*/


    //=========基本信息end===================

    //=========亲属关系start===================
    var relationTable = table.render({
        elem: '#relationElem',
        /*url: servicePath + "/relation/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,*/
        url: '',
        method: 'POST',
        contentType: "application/json; charset=utf-8",
        where: {},
        toolbar: '#toolbarRelation',//开启头部工具栏，并为其绑定左侧模板
        defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        title: '亲属关系信息数据表',
        cols: [
            [{
                /*type: 'checkbox',
                fixed: 'center'*/
                type: 'numbers',
                fixed: 'center',
                title: '序号'
            }, {
                field: 'qsname',
                title: '姓名',
                minWidth: 150
            }, {
                field: 'kinshipTerm',
                title: '称谓',
                width: 80,
                sort: true,
                templet: function (d) {
                    return getDictDataLabel("APPELLATION", d.kinshipTerm);
                }
            }
                , {
                field: 'ownIdcard',
                title: '身份证号',
                width: 180,
                sort: true
            }, {
                field: 'birthdayStr',
                title: '出生日期',
                minWidth: "120",
                align: 'center'/*,
					templet: function(d) {
						var birthday=d.birthday+"";
						return formatDate8To10(birthday)
					}*/
            }
                , {
                field: 'workUnit',
                title: '工作单位',
                minWidth: "150",
                sort: true
            }
                , {
                field: 'post',
                minWidth: "150",
                title: '职务',
            }, {
                field: 'eduLevel',
                title: '学历',
                minWidth: 120,
                templet: function (d) {
                    return getDictDataLabel("AM_XL", d.eduLevel);
                }
            }, {
                field: 'nowStatus',
                title: '现状',
                minWidth: 120,
                templet: function (d) {
                    return getDictDataLabel("AM_REALTION_NOW_STATUS", d.nowStatus);
                }
            }
                , {
                field: 'healthState',
                title: '健康状况',
                minWidth: 120,
                templet: function (d) {
                    return getDictDataLabel("AM_HEALTH_STATE", d.healthState);
                }
            }
                , {
                field: 'others',
                title: '其他',
                minWidth: 150
            }
                , {
                fixed: 'right',
                title: '操作',
                toolbar: '#barRelation',
                minWidth: "130"
            }
            ]
        ],
        page: false,
        parseData: function (res) { //res 即为原始返回的数据
            return res;
        },
        data: [],
        height: 'full-205',
        response: {
            statusName: 'errCode', //规定数据状态的字段名称，默认：code
            statusCode: 0, //规定成功的状态码，默认：0
            msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
            countName: 'recordCount',  //规定数据总数的字段名称，默认：count
            dataName: 'auxQsgxList' //规定数据列表的字段名称，默认：data
        },
        request: {
            pageName: 'page', //页码的参数名称，默认：page
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
    });

    //亲属关系头工具栏事件
    table.on('toolbar(relationElem)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'addRelation':
                layer.open({
                    type: 1,
                    title: '添加亲属关系信息',
                    area: ['800px', '500px'],
                    content: $('#addRelationPage'),
                    btn: ['保存', '取消'],
                    yes: function (index, layero) {
                        $("#addRelationSubmit").click();
                    },
                    btn2: function (index, layero) {
                        layer.close(index);
                    }
                });
                break;
            case 'getCheckData':
                var data = checkStatus.data;
                layer.alert(JSON.stringify(data));
                break;
            case 'getCheckLength':
                var data = checkStatus.data;
                layer.msg('选中了：' + data.length + ' 个');
                break;
            case 'isAll':
                layer.msg(checkStatus.isAll ? '全选' : '未全选');
                break;

            //自定义头工具栏右侧图标 - 提示
            case 'LAYTABLE_TIPS':
                layer.alert('这是工具栏右侧自定义的一个图标按钮');
                break;
        }
        ;
    });

    //亲属关系、监听行工具事件
    table.on('tool(relationElem)', function (obj) {
        //console.log(obj.event);
        var data = obj.data;
        if (obj.event === 'view') {
            alert(data.email);
        }
        if (obj.event === 'delRelation') {
            layer.confirm('确定删除亲属关系信息吗？', function (index) {
                obj.del();
                delRelation(data.id);
                layer.close(index);
            });
        }
        //修改亲属关系
        if (obj.event === 'editRelation') {
            document.getElementById("editRelation").reset();

            //出生日期转化
            /*var birthday=data.birthday+"";
            if (birthday != null && birthday != ''&&birthday.length==8) {
                //return birthday.length;
                birthday= birthday.substring(0,4)+"-"+birthday.substring(4,6)+"-"+birthday.substring(6,8)
            }else{
                birthday="";
            }
            data.birthdayStr=birthday;*/
            data.bjOrgName = getOrgName(data.bjOrgId)
            form.val("editRelation", data);
            change(1, editGzdd);
            change(1, editJzdd);
            // checkDropListItem(document.getElementById("edit_gzddShi"),data.gzddShi);
            // checkDropListItem(document.getElementById("edit_jzddShi"),data.jzddShi);
            form.val("editRelation", data);
            change(2, editGzdd);
            change(2, editJzdd);
            // checkDropListItem(document.getElementById("edit_gzddXian"),data.gzddXian);
            // checkDropListItem(document.getElementById("edit_jzddXian"),data.jzddXian);


            form.val("editRelation", data);
            layer.open({
                type: 1,
                title: '修改亲属关系信息',
                area: ['800px', '500px'],
                content: $('#editRelationPage'),
                btn: ['保存', '关闭'],
                yes: function (index, layero) {
                    $("#editRelationSubmit").click();
                },
                btn2: function (index, layero) {
                    layer.close(index);
                }
            });
        }
        if (obj.event === 'edit_kszl') {
            layer.confirm('开始招录行么', function (index) {
                editState(data.id, 2);
                layer.close(index);
            });
        }
        if (obj.event === 'edit_wczl') {
            layer.confirm('完成招录行么', function (index) {
                editState(data.id, 4);
                layer.close(index);
            });
        }
    });

    //添加亲属关系
    function addRelation(data) {
        /*data.instanceId=$("#id").val();*/
        data.instanceId = daId;
        data.idcard = $("#idcard").val();
        //console.log(data)
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/auxQsgx/add?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("添加成功！", 2000, function () {
                        layer.closeAll('page');
                        document.getElementById("addRelation").reset();
                        relationTable.reload();
                    });
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }

            }
        })
    }

    //修改亲属关系
    function editRelation(data) {
        /*data.instanceId=$("#id").val();*/
        data.instanceId = daId;
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/auxQsgx/update?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("修改成功！", 2000, function () {
                        layer.closeAll('page');
                        relationTable.reload();
                    });
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    }

    //删除亲属关系
    function delRelation(id) {
        var layerIndex = layer.load();
        $.ajax({
            type: "GET",
            url: servicePath + "/auxQsgx/delete?charset=utf-8&id=" + id + "&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("删除成功", 1000);
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    };
    //添加亲属关系“保存”按钮触发
    form.on('submit(addRelation)', function (data) {
        addRelation(form.val("addRelation"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    //修改亲属关系“保存”按钮触发
    form.on('submit(editRelation)', function (data) {
        editRelation(form.val("editRelation"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
    //=========亲属关系end===================


    //=========受奖情况start===================
    var auxSjqkTable = table.render({
        elem: '#sjqkElem',
        /*url: servicePath + "/relation/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,*/
        url: '',
        method: 'POST',
        contentType: "application/json; charset=utf-8",
        where: {},
        toolbar: '#toolbarSjqk',//开启头部工具栏，并为其绑定左侧模板
        defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        title: '受奖情况信息数据表',
        cols: [
            [{
                /*type: 'checkbox',
                fixed: 'center'*/
                type: 'numbers',
                fixed: 'center',
                title: '序号'
            }, {
                field: 'sjrq',
                title: '受奖日期',
                width: 120,
                align: 'center',
                templet: function (d) {
                    return formatDate8To10(d.sjrq + "");
                }
            }, {
                field: 'sjlb',
                title: '受奖类别',
                width: 120,
                align: 'center',
                templet: function (d) {
                    return getDictDataLabel("AUX_SJLB", d.sjlb);
                }
            }, {
                field: 'sjmc',
                title: '受奖名称',
                width: 120,
                align: 'center'
            }, {
                field: 'sjyy',
                title: '受奖原因',
                width: 120,
                align: 'center'
            }, {
                field: 'pzdw',
                title: '批准单位',
                width: 120,
                align: 'center'
            }, {
                fixed: 'right',
                title: '操作',
                toolbar: '#barSjqk',
                minWidth: "130"
            }
            ]
        ],
        page: false,
        parseData: function (res) { //res 即为原始返回的数据
            return res;
        },
        data: [],
        height: 'full-205',
        response: {
            statusName: 'errCode', //规定数据状态的字段名称，默认：code
            statusCode: 0, //规定成功的状态码，默认：0
            msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
            countName: 'recordCount',  //规定数据总数的字段名称，默认：count
            dataName: 'auxSjqkList' //规定数据列表的字段名称，默认：data
        },
        request: {
            pageName: 'page', //页码的参数名称，默认：page
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
    });

    //受奖情况头工具栏事件
    table.on('toolbar(sjqkElem)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'addSjqk':
                layer.open({
                    type: 1,
                    title: '添加受奖情况信息',
                    area: ['800px', '500px'],
                    content: $('#addSjqkPage'),
                    btn: ['保存', '取消'],
                    yes: function (index, layero) {
                        $("#addSjqkSubmit").click();
                    },
                    btn2: function (index, layero) {
                        layer.close(index);
                    }
                });
                break;
            case 'getCheckData':
                var data = checkStatus.data;
                layer.alert(JSON.stringify(data));
                break;
            case 'getCheckLength':
                var data = checkStatus.data;
                layer.msg('选中了：' + data.length + ' 个');
                break;
            case 'isAll':
                layer.msg(checkStatus.isAll ? '全选' : '未全选');
                break;

            //自定义头工具栏右侧图标 - 提示
            case 'LAYTABLE_TIPS':
                layer.alert('这是工具栏右侧自定义的一个图标按钮');
                break;
        }
        ;
    });


    //受奖情况、监听行工具事件
    table.on('tool(sjqkElem)', function (obj) {
        //console.log(obj.event);
        var data = obj.data;
        /*if (obj.event === 'view') {
            alert(data.email);
        }*/
        if (obj.event === 'delSjqk') {
            layer.confirm('确定删除受奖情况信息吗？', function (index) {
                obj.del();
                delSjqk(data.id);
                layer.close(index);
            });
        }
        //修改受奖情况
        if (obj.event === 'editSjqk') {
            document.getElementById("editSjqk").reset();
            var sjrq=formatDate8To10(data.sjrq+"");
            data.sjrq=sjrq;
            form.val("editSjqk", data);
            getAttachmentListEdit(data.id);
            layer.open({
                type: 1,
                title: '修改受奖情况信息',
                area: ['800px', '500px'],
                content: $('#editSjqkPage'),
                btn: ['保存', '关闭'],
                yes: function (index, layero) {
                    $("#editSjqkSubmit").click();
                },
                btn2: function (index, layero) {
                    layer.close(index);
                }
            });
        }
        /*if (obj.event === 'edit_kszl') {
            layer.confirm('开始招录行么', function (index) {
                editState(data.id, 2);
                layer.close(index);
            });
        }
        if (obj.event === 'edit_wczl') {
            layer.confirm('完成招录行么', function (index) {
                editState(data.id, 4);
                layer.close(index);
            });
        }*/
    });

    //添加受奖情况
    function addSjqk(data) {
        /*data.instanceId=$("#id").val();*/
        data.instanceId = daId;
        data.idcard = $("#idcard").val();
        data.sjrq = formatDate10To8(data.sjrq);
        //console.log(data)
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/auxSjqk/add?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
               /* if (data.errCode == 0) {
                    showTicket("添加成功！", 2000, function () {
                        layer.closeAll('page');
                        document.getElementById("addSjqk").reset();
                        auxSjqkTable.reload();
                    });
                } */if (data.errCode == 0) {
                    if ($("#add_uploadList").find("tr").length == 0) {
                        showTicket("保存成功！", 2000, function () {
                            layer.closeAll('page');
                            document.getElementById("addSjqk").reset();
                            auxSjqkTable.reload();
                        });
                    } else {
                        addUpload.reload({
                            url: servicePath + "/file/upload?charset=utf-8&instanceType=AuxSjqk&instanceId=" + data.auxSjqkObj.id + "&loginCode=" + adminInfo.loginCode
                        });
                        addUpload.upload();

                        layer.closeAll('page');
                        document.getElementById("addSjqk").reset();
                        auxSjqkTable.reload();
                    }
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }

            }
        })
    }

    //修改受奖情况
    function editSjqk(data) {
        /*data.instanceId=$("#id").val();*/
        var sjrq=formatDate10To8(data.sjrq);
        data.idcard = $("#idcard").val();
        data.sjrq=sjrq;
        data.instanceId = daId;
        var layerIndex = layer.load();
        var formData = data;
        $.ajax({
            type: "POST",
            url: servicePath + "/auxSjqk/update?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
             /*   if (data.errCode == 0) {
                    showTicket("修改成功！", 2000, function () {
                        layer.closeAll('page');
                        auxSjqkTable.reload();
                    });
                } */if (data.errCode == 0) {
                    if ($("#edit_sjqk_uploadList").find("tr[id]").length == 0) {
                        showTicket("保存成功！", 1000, function () {
                            layer.closeAll('page');
                            auxSjqkTable.reload();
                        });
                    } else {
                        editSjqkUpload.reload({
                            url: servicePath + "/file/upload?charset=utf-8&instanceType=AuxSjqk&instanceId=" + formData.id + "&loginCode=" + adminInfo.loginCode
                        });
                        editSjqkUpload.upload();
                    }
                }else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    }

    //删除受奖情况
    function delSjqk(id) {
        var layerIndex = layer.load();
        $.ajax({
            type: "GET",
            url: servicePath + "/auxSjqk/delete?charset=utf-8&id=" + id + "&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("删除成功", 1000);
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    };

    //添加受奖情况“保存”按钮触发
    form.on('submit(addSjqk)', function (data) {
        addSjqk(form.val("addSjqk"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
    //修改受奖情况“保存”按钮触发
    form.on('submit(editSjqk)', function (data) {
        editSjqk(form.val("editSjqk"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });


    //=========工作简历start===================
    var jobResumeTable = table.render({
        elem: '#jobResumeElem',
        url: '',
        method: 'POST',
        contentType: "application/json; charset=utf-8",
        where: {},
        toolbar: '#toolbarJobResume', //开启头部工具栏，并为其绑定左侧模板
        defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        title: '工作简历数据表',
        cols: [
            [{
                type: 'numbers',
                fixed: 'center',
                title: '序号'
            }, {
                field: 'workUnit',
                title: '工作单位',
                width: 200,
                sort: true
            }, {
                field: 'post',
                title: '职务',
                width: 120
            }, {
                field: 'startDate',
                title: '开始日期',
                width: 120,
                align: 'center',
                templet: function (d) {
                    return formatDate8To10(d.startDate + "");
                }
            }, {
                field: 'endDate',
                title: '结束日期',
                align: 'center',
                width: 120,
                templet: function (d) {
                    return formatDate8To10(d.endDate + "");
                }
            }, {
                field: 'remark',
                title: '备注',
                minWidth: 150
            }, {
                fixed: 'right',
                title: '操作',
                toolbar: '#barJobResume',
                width: 130
            }]
        ],
        page: false,
        parseData: function (res) { //res 即为原始返回的数据
            return res;
        },
        data: [],
        height: 'full-205',
        response: {
            statusName: 'errCode', //规定数据状态的字段名称，默认：code
            statusCode: 0, //规定成功的状态码，默认：0
            msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
            countName: 'recordCount', //规定数据总数的字段名称，默认：count
            dataName: 'jobResumeList' //规定数据列表的字段名称，默认：data
        },
        request: {
            pageName: 'page', //页码的参数名称，默认：page
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
    });
    //工作简历头工具栏事件
    table.on('toolbar(jobResumeElem)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'addJobResume':
                document.getElementById("addJobResumeForm").reset();
                layer.open({
                    type: 1,
                    title: '添加工作简历',
                    area: ['800px', '500px'],
                    content: $('#addJobResumePage'),
                    btn: ['保存', '取消'],
                    yes: function (index, layero) {
                        $("#addJobResumeSubmit").click();
                    },
                    btn2: function (index, layero) {
                        layer.close(index);
                    }
                });
                break;

        }
        ;
    });
    //工作简历、监听行工具事件
    table.on('tool(jobResumeElem)', function (obj) {
        var data = obj.data;
        if (obj.event === 'delJobResume') {
            layer.confirm('是否删除', function (index) {
                obj.del();
                delJobResume(data.id);
                layer.close(index);
            });
        }
        //修改工作简历
        if (obj.event === 'editJobResume') {
            data.startDate = formatDate8To10(data.startDate + "");
            data.endDate = formatDate8To10(data.endDate + "");
            form.val("editJobResumeForm", data);
            layer.open({
                type: 1,
                title: '修改工作简历',
                area: ['800px', '500px'],
                content: $('#editJobResumePage'),
                btn: ['保存', '关闭'],
                yes: function (index, layero) {
                    $("#editJobResumeSubmit").click();
                },
                btn2: function (index, layero) {
                    layer.close(index);
                }
            });
        }
    });
    form.on('submit(addJobResumeSubmit)', function (data) {
        addJobResume(form.val("addJobResumeForm"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
    form.on('submit(editJobResumeSubmit)', function (data) {
        editJobResume(form.val("editJobResumeForm"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    //添加工作简历
    function addJobResume(data) {

        data.instanceId = daId;
        data.idcard = $("#idcard").val();
        data.startDate = formatDate10To8(data.startDate);
        data.endDate = formatDate10To8(data.endDate);
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/jobResume/add?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("添加成功！", 1000, function () {
                        layer.closeAll('page');
                        jobResumeTable.reload();
                    });
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }

            }
        })
    }

    //修改工作简历
    function editJobResume(data) {
        data.startDate = formatDate10To8(data.startDate);
        data.endDate = formatDate10To8(data.endDate);
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/jobResume/update?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("修改成功！", 1000, function () {
                        layer.closeAll('page');
                        jobResumeTable.reload();
                    });
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    }

    //删除工作简历
    function delJobResume(id) {
        var layerIndex = layer.load();
        $.ajax({
            type: "GET",
            url: servicePath + "/jobResume/delete?charset=utf-8&loginCode=" + adminInfo.loginCode + "&id=" + id,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("删除成功", 1000);
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    };
    //=========工作简历end===================

    //=========学历简历start===================
    var eduResumeTable = table.render({
        elem: '#eduResumeElem',
        url: '',
        method: 'POST',
        contentType: "application/json; charset=utf-8",
        where: {},
        toolbar: '#toolbarEduResume', //开启头部工具栏，并为其绑定左侧模板
        defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        title: '学历简历数据表',
        cols: [
            [{
                type: 'numbers',
                fixed: 'center',
                title: '序号'
            }, {
                field: 'university',
                title: '毕业院校',
                width: 150,
                sort: true
            }, {
                field: 'specialitie',
                title: '专业',
                width: 120
            }, {
                field: 'startDate',
                title: '开始日期',
                align: 'center',
                width: 120,
                templet: function (d) {
                    return formatDate8To10(d.startDate + "")
                }
            }, {
                field: 'endDate',
                title: '结束日期',
                align: 'center',
                width: 120,
                templet: function (d) {
                    return formatDate8To10(d.endDate + "")
                }
            }, {
                field: 'eduLevel',
                title: '学历',
                align: 'center',
                width: 120,
                sort: true,
                templet: function (d) {
                    return getDictDataLabel("AM_XL", d.eduLevel);
                }
            }, {
                field: 'degree',
                title: '学位',
                align: 'center',
                width: 120,
                sort: true,
                templet: function (d) {
                    return getDictDataLabel("AM_XW", d.degree);
                }
            }, {
                field: 'remark',
                title: '备注',
                minWidth: 150
            }, {
                fixed: 'right',
                title: '操作',
                toolbar: '#barEduResume',
                width: 130
            }]
        ],
        page: false,
        parseData: function (res) { //res 即为原始返回的数据
            return res;
        },
        data: [],
        height: 'full-205',
        response: {
            statusName: 'errCode', //规定数据状态的字段名称，默认：code
            statusCode: 0, //规定成功的状态码，默认：0
            msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
            countName: 'recordCount', //规定数据总数的字段名称，默认：count
            dataName: 'eduResumeList' //规定数据列表的字段名称，默认：data
        },
        request: {
            pageName: 'page', //页码的参数名称，默认：page
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
    });
    //学历简历头工具栏事件
    table.on('toolbar(eduResumeElem)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'addEduResume':
                document.getElementById("addEduResumeForm").reset();
                layer.open({
                    type: 1,
                    title: '添加学历简历',
                    area: ['800px', '500px'],
                    content: $('#addEduResumePage'),
                    btn: ['保存', '取消'],
                    yes: function (index, layero) {
                        $("#addEduResumeSubmit").click();
                    },
                    btn2: function (index, layero) {
                        layer.close(index);
                    }
                });
                break;
        }
        ;
    });
    //学历简历、监听行工具事件
    table.on('tool(eduResumeElem)', function (obj) {
        var data = obj.data;
        if (obj.event === 'delEduResume') {
            layer.confirm('确定删除学历简历吗？', function (index) {
                obj.del();
                delEduResume(data.id);
                layer.close(index);
            });
        }
        //修改学历简历
        if (obj.event === 'editEduResume') {
            data.startDate = formatDate8To10(data.startDate + "");
            data.endDate = formatDate8To10(data.endDate + "");
            form.val("editEduResumeForm", data);
            getAttachmentList("EduResume",data.id);
            layer.open({
                type: 1,
                title: '修改学历简历',
                area: ['800px', '500px'],
                content: $('#editEduResumePage'),
                btn: ['保存', '关闭'],
                yes: function (index, layero) {
                    $("#editEduResumeSubmit").click();
                },
                btn2: function (index, layero) {
                    layer.close(index);
                }
            });
        }
    });
    form.on('submit(addEduResumeSubmit)', function (data) {
        addEduResume(form.val("addEduResumeForm"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
    form.on('submit(editEduResumeSubmit)', function (data) {
        editEduResume(form.val("editEduResumeForm"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    //添加学历简历
    function addEduResume(data) {
        /*data.instanceId = $("#id").val();*/
        data.instanceId = daId;
        data.idcard = $("#idcard").val();
        data.startDate = formatDate10To8(data.startDate);
        data.endDate = formatDate10To8(data.endDate);
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/eduResume/add?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
              /*  if (data.errCode == 0) {
                    showTicket("添加成功！", 1000, function () {
                        layer.closeAll('page');
                        eduResumeTable.reload();
                    });
                } */if (data.errCode == 0) {
                    if ($("#add_edu_uploadList").find("tr").length == 0) {
                        showTicket("保存成功！", 2000, function () {
                            layer.closeAll('page');
                            document.getElementById("addEduResumeForm").reset();
                            eduResumeTable.reload();
                        });
                    } else {
                        addEduUpload.reload({
                            url: servicePath + "/file/upload?charset=utf-8&instanceType=EduResume&instanceId=" + data.eduResume.id + "&loginCode=" + adminInfo.loginCode
                        });
                        addEduUpload.upload();

                        layer.closeAll('page');
                        document.getElementById("addEduResumeForm").reset();
                        eduResumeTable.reload();
                    }
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }

            }
        })
    }

    //修改学历简历
    function editEduResume(data) {
        data.startDate = formatDate10To8(data.startDate);
        data.endDate = formatDate10To8(data.endDate);
        var layerIndex = layer.load();
        var formData = data;
        $.ajax({
            type: "POST",
            url: servicePath + "/eduResume/update?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
               /* if (data.errCode == 0) {
                    showTicket("修改成功！", 1000, function () {
                        layer.closeAll('page');
                        eduResumeTable.reload();
                    });
                } */if (data.errCode == 0) {
                    if ($("#edit_edu_uploadList").find("tr[id]").length == 0) {
                        showTicket("保存成功！", 1000, function () {
                            layer.closeAll('page');
                            eduResumeTable.reload();
                        });
                    } else {
                        editEduUpload.reload({
                            url: servicePath + "/file/upload?charset=utf-8&instanceType=EduResume&instanceId=" + formData.id + "&loginCode=" + adminInfo.loginCode
                        });
                        editEduUpload.upload();
                    }
                }else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    }

    //删除学历简历
    function delEduResume(id) {
        var layerIndex = layer.load();
        $.ajax({
            type: "GET",
            url: servicePath + "/eduResume/delete?charset=utf-8&loginCode=" + adminInfo.loginCode + "&id=" + id,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("删除成功", 1000);
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    };
    //=========学历简历end===================

    //=========年度考核start===================
    var amNdkhTable = table.render({
        elem: '#amNdkhElem',
        url: '',
        method: 'POST',
        contentType: "application/json; charset=utf-8",
        where: {},
        toolbar: '#toolbarAmNdkh', //开启头部工具栏，并为其绑定左侧模板
        defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        title: '年度考核数据表',
        cols: [
            [{
                type: 'numbers',
                fixed: 'center',
                title: '序号'
            }, {
                field: 'khnd',
                title: '考核年度',
                width: 120
            }, {
                field: 'khjl',
                title: '考核结论',
                width: 120,
                sort: true,
                templet: function (d) {
                    return getDictDataLabel("AUX_NDKH", d.khjl);
                }
            }, {
                field: 'remark',
                title: '备注',
                minWidth: 200
            }, {
                fixed: 'right',
                title: '操作',
                toolbar: '#barAmNdkh',
                width: 130
            }]
        ],
        page: false,
        parseData: function (res) { //res 即为原始返回的数据
            return res;
        },
        data: [],
        height: 'full-205',
        response: {
            statusName: 'errCode', //规定数据状态的字段名称，默认：code
            statusCode: 0, //规定成功的状态码，默认：0
            msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
            countName: 'recordCount', //规定数据总数的字段名称，默认：count
            dataName: 'amNdkhList' //规定数据列表的字段名称，默认：data
        },
        request: {
            pageName: 'page', //页码的参数名称，默认：page
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
    });
    //年度考核头工具栏事件
    table.on('toolbar(amNdkhElem)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'addAmNdkh':
                document.getElementById('addAmNdkhForm').reset();
                layer.open({
                    type: 1,
                    title: '添加年度考核',
                    area: ['800px', '500px'],
                    content: $('#addAmNdkhPage'),
                    btn: ['保存', '取消'],
                    yes: function (index, layero) {
                        $("#addAmNdkhSubmit").click();
                    },
                    btn2: function (index, layero) {
                        layer.close(index);
                    }
                });
                break;
        }
        ;
    });
    //年度考核、监听行工具事件
    table.on('tool(amNdkhElem)', function (obj) {
        var data = obj.data;
        if (obj.event === 'delAmNdkh') {
            layer.confirm('确定删除年度考核吗？', function (index) {
                obj.del();
                delAmNdkh(data.id);
                layer.close(index);
            });
        }
        //修改年度考核
        if (obj.event === 'editAmNdkh') {
            form.val("editAmNdkhForm", data);
            layer.open({
                type: 1,
                title: '修改年度考核',
                area: ['800px', '500px'],
                content: $('#editAmNdkhPage'),
                btn: ['保存', '关闭'],
                yes: function (index, layero) {
                    $("#editAmNdkhSubmit").click();
                },
                btn2: function (index, layero) {
                    layer.close(index);
                }
            });
        }
    });
    form.on('submit(addAmNdkhSubmit)', function (data) {
        addAmNdkh(form.val("addAmNdkhForm"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
    form.on('submit(editAmNdkhSubmit)', function (data) {
        editAmNdkh(form.val("editAmNdkhForm"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    //添加年度考核
    function addAmNdkh(data) {
        /*data.instanceId = $("#id").val();*/
        data.instanceId = daId;
        data.idcard = $("#idcard").val();
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/auxNdkh/add?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("添加成功！", 1000, function () {
                        layer.closeAll('page');
                        amNdkhTable.reload();
                    });
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }

            }
        })
    }

    //修改年度考核
    function editAmNdkh(data) {
        var layerIndex = layer.load();

        $.ajax({
            type: "POST",
            url: servicePath + "/auxNdkh/update?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("修改成功！", 1000, function () {
                        layer.closeAll('page');
                        amNdkhTable.reload();
                    });
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    }

    //删除年度考核
    function delAmNdkh(id) {
        var layerIndex = layer.load();
        $.ajax({
            type: "GET",
            url: servicePath + "/auxNdkh/delete?charset=utf-8&loginCode=" + adminInfo.loginCode + "&id=" + id,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("删除成功", 1000);
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    };
    //=========年度考核end===================

    //=========月度考核start===================
    var amYdkhTable = table.render({
        elem: '#amYdkhElem',
        url: '',
        method: 'POST',
        contentType: "application/json; charset=utf-8",
        where: {},
        toolbar: '#toolbarAmYdkh', //开启头部工具栏，并为其绑定左侧模板
        defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        title: '月度考核数据表',
        cols: [
            [{
                type: 'numbers',
                fixed: 'center',
                title: '序号'
            }, {
                field: 'khyd',
                title: '考核月度',
                width: 120
            }, {
                field: 'khjl',
                title: '考核结论',
                width: 120,
                sort: true,
                templet: function (d) {
                    return getDictDataLabel("AUX_NDKH", d.khjl);
                }
            }, {
                field: 'remark',
                title: '备注',
                minWidth: 200
            }, {
                fixed: 'right',
                title: '操作',
                toolbar: '#barAmYdkh',
                width: 130
            }]
        ],
        page: false,
        parseData: function (res) { //res 即为原始返回的数据
            return res;
        },
        data: [],
        height: 'full-205',
        response: {
            statusName: 'errCode', //规定数据状态的字段名称，默认：code
            statusCode: 0, //规定成功的状态码，默认：0
            msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
            countName: 'recordCount', //规定数据总数的字段名称，默认：count
            dataName: 'amYdkhList' //规定数据列表的字段名称，默认：data
        },
        request: {
            pageName: 'page', //页码的参数名称，默认：page
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
    });
    //月度考核头工具栏事件
    table.on('toolbar(amYdkhElem)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'addAmYdkh':
                document.getElementById('addAmYdkhForm').reset();
                layer.open({
                    type: 1,
                    title: '添加月度考核',
                    area: ['800px', '500px'],
                    content: $('#addAmYdkhPage'),
                    btn: ['保存', '取消'],
                    yes: function (index, layero) {
                        $("#addAmYdkhSubmit").click();
                    },
                    btn2: function (index, layero) {
                        layer.close(index);
                    }
                });
                break;
        }
        ;
    });
    //月度考核、监听行工具事件
    table.on('tool(amYdkhElem)', function (obj) {
        var data = obj.data;
        if (obj.event === 'delAmYdkh') {
            layer.confirm('确定删除月度考核吗？', function (index) {
                obj.del();
                delAmYdkh(data.id);
                layer.close(index);
            });
        }
        //修改月度考核
        if (obj.event === 'editAmYdkh') {
            form.val("editAmYdkhForm", data);
            layer.open({
                type: 1,
                title: '修改月度考核',
                area: ['800px', '500px'],
                content: $('#editAmYdkhPage'),
                btn: ['保存', '关闭'],
                yes: function (index, layero) {
                    $("#editAmYdkhSubmit").click();
                },
                btn2: function (index, layero) {
                    layer.close(index);
                }
            });
        }
    });
    form.on('submit(addAmYdkhSubmit)', function (data) {
        addAmYdkh(form.val("addAmYdkhForm"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
    form.on('submit(editAmYdkhSubmit)', function (data) {
        editAmYdkh(form.val("editAmYdkhForm"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    //添加月度考核
    function addAmYdkh(data) {
        /*data.instanceId = $("#id").val();*/
        data.instanceId = daId;
        data.idcard = $("#idcard").val();
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/auxYdkh/add?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("添加成功！", 1000, function () {
                        layer.closeAll('page');
                        amYdkhTable.reload();
                    });
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }

            }
        })
    }

    //修改月度考核
    function editAmYdkh(data) {
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/auxYdkh/update?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("修改成功！", 1000, function () {
                        layer.closeAll('page');
                        amYdkhTable.reload();
                    });
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    }

    //删除月度考核
    function delAmYdkh(id) {
        var layerIndex = layer.load();
        $.ajax({
            type: "GET",
            url: servicePath + "/auxYdkh/delete?charset=utf-8&loginCode=" + adminInfo.loginCode + "&id=" + id,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("删除成功", 1000);
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    };
    //=========月度考核end===================

    /* //=========合同信息start===================
    var contractTable = table.render({
        elem: '#contractElem',
        url: '',
        method: 'POST',
        contentType: "application/json; charset=utf-8",
        where: {},
        toolbar: '#toolbarContract', //开启头部工具栏，并为其绑定左侧模板
        defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        title: '合同信息数据表',
        cols: [
            [{
                type: 'numbers',
                fixed: 'center',
                title: '序号'
            }, {
                field: 'category',
                title: '合同类别',
                width: 150,
                sort: true,
                templet: function(d) {
                    return getDictDataLabel("CONTRACT_CATEGORY", d.category);
                }
            }, {
                field: 'contractNo',
                title: '合同编号',
                width: 120
            },
            {
                field: 'contractName',
                title: '合同名称',
                width: 160,
                sort: true
            }, {
                field: 'state',
                title: '合同状态',
                width: 120,
                templet: function(d) {
                    return getDictDataLabel("CONTRACT_STATE", d.state);
                }
            }, {
                field: 'signDate',
                title: '签订日期',
                align: 'center',
                width: 120,
                templet: function(d) {
                    return formatDate8To10(d.signDate + "")
                }
            }, {
                field: 'effectiveDate',
                title: '生效日期',
                align: 'center',
                width: 120,
                templet: function(d) {
                    return formatDate8To10(d.effectiveDate + "")
                }
            }, {
                field: 'expirationDate',
                title: '失效日期',
                align: 'center',
                width: 120,
                templet: function(d) {
                    if (d.expirationDate == null || d.expirationDate == '') {
                        return '';
                    }
                    //当前日期
                    var currentDate = new Date();
                    currentDate = currentDate.format("yyyyMMdd");
                    //失效日期
                    var expirationDate = d.expirationDate + "";
                    var expirationDate = formatDate8To10(expirationDate)
                    //失效日期减去一个月预警
                    var expDate = new Date(expirationDate);
                    expDate.setMonth(expDate.getMonth() - 1);
                    expDate = expDate.format("yyyyMMdd");
                    //当前日期大于如果当前日期减去一个月，红色预警
                    if (currentDate > expDate) {
                        return '<font color=red >' + expirationDate + '</font>'
                    } else {
                        return expirationDate;
                    }

                }
            }, {
                field: 'terminationDate',
                title: '终止日期',
                align: 'center',
                width: 120,
                templet: function(d) {
                    return formatDate8To10(d.terminationDate + "")
                }
            },
            {
                field: 'reason',
                title: '终止原因',
                width: 150,
                sort: true
            }, {
                field: 'remark',
                title: '备注',
                minWidth: 150
            }, {
                fixed: 'right',
                title: '操作',
                toolbar: '#barContract',
                width: 130
            }]
        ],
        page: false,
        parseData: function(res) { //res 即为原始返回的数据
            return res;
        },
        data: [],
        height: 'full-205',
        response: {
            statusName: 'errCode', //规定数据状态的字段名称，默认：code
            statusCode: 0, //规定成功的状态码，默认：0
            msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
            countName: 'recordCount', //规定数据总数的字段名称，默认：count
            dataName: 'contractList' //规定数据列表的字段名称，默认：data
        },
        request: {
            pageName: 'page', //页码的参数名称，默认：page
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
    });
    //合同信息头工具栏事件
    table.on('toolbar(contractElem)', function(obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'addContract':
                layer.open({
                    type: 1,
                    title: '添加合同信息',
                    area: ['800px', '600px'],
                    content: $('#addContractPage'),
                    btn: ['保存', '取消'],
                    yes: function(index, layero) {
                        $("#addContractSubmit").click();
                    },
                    btn2: function(index, layero) {
                        layer.close(index);
                    }
                });
                break;
        };
    });
    //合同信息、监听行工具事件
    table.on('tool(contractElem)', function(obj) {
        var data = obj.data;
        if (obj.event === 'delContract') {
            layer.confirm('确定删除合同信息吗？', function(index) {
                obj.del();
                delContract(data.contractId);
                layer.close(index);
            });
        }
        if (obj.event === 'editContract') {
            data.startDate = formatDate8To10(data.startDate + "");
            data.endDate = formatDate8To10(data.endDate + "");
            form.val("editContract", data);
            layer.open({
                type: 1,
                title: '修改合同信息',
                area: ['800px', '600px'],
                content: $('#editContractPage'),
                btn: ['保存', '关闭'],
                yes: function(index, layero) {
                    $("#editContractSubmit").click();
                },
                btn2: function(index, layero) {
                    layer.close(index);
                }
            });
        }
    });
    laydate.render({
        elem: '#signDateAdd', //指定元素
        done: function(value, date, endDate) {
            var date1 = new Date(value);
            date1.setFullYear(date1.getFullYear() + 1);
            date1.setDate(date1.getDate() - 1);
            date1 = date1.format("yyyy-MM-dd");
            form.val("addContract", {
                "effectiveDate": value, //生效日期等于签订日期，立即生效
                "expirationDate": date1 //失效日期为签订日期增加一年
            });
        }
    });
    //添加合同信息
    function addContract(data) {
        data.instanceId = $("#id").val();
        data.idcard = $("#idcard").val();
        data.signDate = formatDate10To8(data.signDate);
        data.effectiveDate = formatDate10To8(data.effectiveDate);
        data.terminationDate = formatDate10To8(data.terminationDate);
        data.expirationDate = formatDate10To8(data.expirationDate);
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/contract/add?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function(data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("添加成功！", 1000, function() {
                        layer.closeAll('page');
                        document.getElementById("addContract").reset();
                        contractTable.reload();
                    });
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function() {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }

            }
        })
    }
    //修改合同信息
    function editContract(data) {
        data.instanceId = $("#id").val();
        var signDate = formatDate10To8(data.signDate);
        data.signDate = signDate;
        var effectiveDate = formatDate10To8(data.effectiveDate);
        data.effectiveDate = effectiveDate;
        var terminationDate = formatDate10To8(data.terminationDate);
        data.terminationDate = terminationDate;
        var expirationDate = formatDate10To8(data.expirationDate);
        data.expirationDate = expirationDate;
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/contract/update?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function(data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("修改成功！", 1000, function() {
                        layer.closeAll('page');
                        contractTable.reload();
                    });
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function() {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    }
    //删除合同信息
    function delContract(id) {
        var layerIndex = layer.load();
        $.ajax({
            type: "GET",
            url: servicePath + "/contract/delete?charset=utf-8&id=" + id + "&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("删除成功", 1000);
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function() {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    };
    //添加合同信息“保存”按钮触发
    form.on('submit(addContract)', function(data) {
        addContract(form.val("addContract"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
    //修改合同信息“保存”按钮触发
    form.on('submit(editContract)', function(data) {
        editContract(form.val("editContract"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
    //=========合同信息end===================*/

    /* //=========被装信息start===================
    var amBzxxTable = table.render({
        elem: '#amBzxxElem',
        url: '',
        method: 'POST',
        contentType: "application/json; charset=utf-8",
        where: JSON.stringify({
            "kwFields": 3,
            "keyword": ""
        }),
        toolbar: '#toolbarAmBzxx', //开启头部工具栏，并为其绑定左侧模板
        defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        title: '被装信息数据表',
        cols: [
            [{
                field: 'commodityName',
                title: '被装名称',
                minWidth: "150",
                sort: true
            }, {
                field: 'commodityType',
                title: '装备类别',
                width: 150,
                sort: true,
                templet: function(d) {
                    return getDictDataLabel("COMMODITY_TYPE", d.commodityType);
                }
            }, {
                field: 'skuCode',
                title: '规格',
                minWidth: "120"
            }, {
                field: 'unit',
                title: '单价',
                minWidth: "120"
            }, {
                fixed: 'right',
                title: '操作',
                toolbar: '#barAmBzxx',
                minWidth: "100"
            }]
        ],
        page: false,
        parseData: function(res) { //res 即为原始返回的数据
            return res;
        },
        data: [],
        height: 'full-205',
        response: {
            statusName: 'errCode', //规定数据状态的字段名称，默认：code
            statusCode: 0, //规定成功的状态码，默认：0
            msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
            countName: 'recordCount', //规定数据总数的字段名称，默认：count
            dataName: 'amBzxxList' //规定数据列表的字段名称，默认：data
        },
        request: {
            pageName: 'page', //页码的参数名称，默认：page
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
    });

    //被装信息工具栏事件
    table.on('toolbar(amBzxxElem)', function(obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'addAmBzxx':
                openCommodity(1, 2, 3)
                break;
        };
    });

    //被装信息、监听行工具事件
    table.on('tool(amBzxxElem)', function(obj) {
        var data = obj.data;
        if (obj.event === 'view') {
            alert(data.email);
        }
        if (obj.event === 'delAmBzxx') {
            layer.confirm('是否删除', function(index) {
                obj.del();
                delAmBzxx(data.id);
                layer.close(index);
            });
        }
        //修改被装信息
        if (obj.event === 'editAmBzxx') {
            form.val("editAmBzxx", data);
            layer.open({
                type: 1,
                title: '修改被装信息',
                area: ['800px', '500px'],
                content: $('#editAmBzxxPage'),
                btn: ['保存', '关闭'],
                yes: function(index, layero) {
                    $("#editAmBzxxSubmit").click();
                },
                btn2: function(index, layero) {
                    layer.close(index);
                }
            });
        }

    });

    //添加被装信息
    function addAmBzxx(data) {
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/amBzxx/add?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function(data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("添加成功！", 1000, function() {
                        layer.closeAll('page');
                        amBzxxTable.reload();
                    });
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function() {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }

            }
        })
    }
    //修改被装信息
    function editAmBzxx(data) {
        data.instanceId = $("#id").val();
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/amBzxx/update?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function(data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("修改成功！", 1000, function() {
                        layer.closeAll('page');
                        amBzxxTable.reload();
                    });
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function() {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    }

    //删除被装信息
    function delAmBzxx(id) {
        var layerIndex = layer.load();
        $.ajax({
            type: "GET",
            url: servicePath + "/amBzxx/delete?charset=utf-8&id=" + id + "&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("删除成功", 1000);
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function() {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    };

    //添加被装信息“保存”按钮触发
    form.on('submit(addAmBzxx)', function(data) {
        addAmBzxx(form.val("addAmBzxx"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    //修改被装信息“保存”按钮触发
    form.on('submit(editAmBzxx)', function(data) {
        editAmBzxx(form.val("editAmBzxx"));
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    //===============装备选择框start================
    // 装备信息弹出框
    function openCommodity() {
        layer.open({
            type: 1,
            title: '选择装备',
            area: ['1024px', '768px'],
            content: $('#hidden1'),
            btn: ['确定', '关闭'],
            yes: function(index, layero) {
                apendCommodity();
                layer.close(index);
            },
            btn2: function(index, layero) {
                layer.close(index);
            },
            success: function() {
                $('#hidden1').css('display', 'block');
                getCommodityList(); //加载装备信息
            }
        });

    }

    //选择装备信息后加载装备列表
    function apendCommodity() {
        var amBzxxList = []
        $(".checkBoxClass:checked").each(function() {
            var instanceId = $("#id").val();
            var idcard = $("#idcard").val();

            var commodityId = this.getAttribute("data-commodityId");
            var commodityName = this.getAttribute("data-commodityName");
            var skuId = this.getAttribute("data-skuId");
            var skuCode = this.getAttribute("data-skuCode");
            var price = this.getAttribute("data-price");

            var arrStr = {
                instanceId: instanceId,
                idcard: idcard,
                commodityId: commodityId,
                commodityName: commodityName,
                skuId: skuId,
                skuCode: skuCode,
                price: price
            };
            amBzxxList.push(arrStr);
        })

        var amBzxx = {
            id: "111"
        };
        amBzxx.amBzxxList = amBzxxList;
        addAmBzxx(amBzxx);

    }
    //搜索装备信息
    function search() {
        getCommodityList(); //加载装备信息
    }

    //加载装备信息
    function getCommodityList() {
        var commodityName = $("#commodityName").val();
        var commodityType = $("#commodityType").val();
        $.ajax({
            type: "POST",
            url: servicePath + "/commodity/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                "page": 1,
                "pageSize": 1000,
                "commodityName": commodityName,
                "commodityType": commodityType,
                "commodityState": 1
            }),
            dataType: "json",
            success: function(data) {
                if (data.errCode == 0) {
                    setCommodityList(data.commodityList)
                    //console.log(data);
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function() {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    }

    //设置被装table信息
    function setCommodityList(commodityList) {
        $("#zhuangbeiId").empty();
        for (var i = 0; i < commodityList.length; i++) {
            var j = i + 1;
            var commodity = commodityList[i];
            $("#zhuangbeiId").append('<div class=layui-form-item>装备信息' + j + '</div>');

            var divStr = "";
            var skuList = commodity.skuList;
            var commodityType = getDictDataLabel("COMMODITY_TYPE", commodity.commodityType);

            divStr = '<div class=borderCss>' +
                '<table class=layui-table>' +
                '<tr class=theadClass>' +
                '<th>装备名称</th>' +
                '<th>装备类别</th>' +
                '<th>售价</th>' +
                '<th>单位</th>' +
                '</tr>' +

                '<tr>' +
                '<td>' + commodity.commodityName + '</td>' +
                '<td>' + commodityType + '</td>' +
                '<td>' + commodity.sellPrice + '</td>' +
                '<td>' + commodity.unit + '</td>' +
                '</tr>' +
                '</table>';

            divStr = divStr + '<table class=layui-table>' +
                '<tr class=theadClass>' +
                '<th>选择</th>' +
                '<th>规格</th>' +
                '<th>售价</th>' +
                '</tr>';

            for (var k = 0; k < skuList.length; k++) {
                var sku = skuList[k];
                divStr = divStr + '<tr>' +
                    '<td><input type="radio" class=checkBoxClass data-skuId=' + sku.skuId + ' data-commodityId=' + commodity.commodityId +
                    ' data-skuCode=' + sku.skuCode + ' data-price=' + sku.price + ' data-commodityName=' + commodity.commodityName +
                    ' name=' + commodity.commodityId + ' value="" title=" "></td>' +
                    // '<td><input type=checkbox class=checkBoxClass name=states title=实习 value=2 lay-skin=primary></td>'+
                    '<td>' + sku.skuCode + '</td>' +
                    '<td>' + sku.price + '</td>' +
                    '</tr>';
            }

            divStr = divStr +
                '</table>' +
                '</div>';

            $("#zhuangbeiId").append(divStr)
        }
    }
    //搜索用户“查询”按钮触发
    form.on('submit(searchBzxxBtn)', function(data) {
        search()
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
    //===============装备选择框end================

    //=========被装信息end===================*/

    //Tab选项卡点击切换时触发
    element.on('tab(tabFilter)', function (data) {
        if (data.index == 1) { //工作简历
            jobResumeTable.reload({
                url: servicePath + "/jobResume/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,
                where: {
                    /*instanceId: $("#daId").val()*/
                    instanceId: daId

                }
            });
        } else if (data.index == 2) { //学历简历
            eduResumeTable.reload({
                url: servicePath + "/eduResume/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,
                where: {
                    /*instanceId: $("#id").val()*/
                    instanceId: daId
                }
            });
        } else if (data.index == 3) { //年度考核
            amNdkhTable.reload({
                url: servicePath + "/auxNdkh/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,
                where: {
                    /*instanceId: $("#id").val()*/
                    instanceId: daId
                }
            });
        } else if (data.index == 4) { //月度考核
            amYdkhTable.reload({
                url: servicePath + "/auxYdkh/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,
                where: {
                    /*instanceId: $("#id").val()*/
                    instanceId: daId
                }
            });
        } else if (data.index == 5) { //亲属关系
            relationTable.reload({
                url: servicePath + "/auxQsgx/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,
                where: {
                    /*instanceId: $("#id").val()*/
                    instanceId: daId
                }
            });
        } else if (data.index == 6) { //受奖情况
            auxSjqkTable.reload({
                url: servicePath + "/auxSjqk/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,
                where: {
                    /*instanceId: $("#id").val()*/
                    instanceId: daId
                }
            });
        }


        /* else if(data.index==1){//亲属关系
            relationTable.reload({
                url:relationUrl,
                where:{
                  instanceId:$("#id").val()
                }
            });
        } else if(data.index==4){//合同信息
            contractTable.reload({
                url:servicePath + "/contract/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,
                where:{
                    instanceId:$("#id").val()
                }
            });
        }else if(data.index==5){//被装信息
            amBzxxTable.reload({
                url:servicePath + "/amBzxx/getList?charset=utf-8&loginCode=" + adminInfo.loginCode,
                where:{
                  instanceId:$("#id").val()
                }
            });
        } */
    });

    var addGzdd = ["add_gzddSheng", "add_gzddShi", "add_gzddXian"]; //三个select的id
    var addJzdd = ["add_jzddSheng", "add_jzddShi", "add_jzddXian"]; //三个select的id
    var editGzdd = ["edit_gzddSheng", "edit_gzddShi", "edit_gzddXian"]; //三个select的id
    var editJzdd = ["edit_jzddSheng", "edit_jzddShi", "edit_jzddXian"]; //三个select的id
    initArea(addGzdd, 'addGzdd');
    initArea(addJzdd, 'addJzdd');
    initArea(editGzdd, 'editGzdd');
    initArea(editJzdd, 'editJzdd');

    $("#orgName").click(function () {
        if (adminInfo.powers["AM_JBXX_SELECT_ALL_UNIT"] == 2) {
            top.openSelectOrg({
                title: '选择单位',
                where: {
                    state: 1
                },
                treeWhere: {
                    state: 1
                },
                area: ['1024px', '768px'],
                page: true,
                limit: 10,
                limits: [10, 50, 100, 1000, 10000],
                selectType: 'radio', //radio,checkbox
                checkedData: [], //默认选中的数据，只对selectType为radio起作用
                disabledData: [], //默认禁用的数据
                compareField: 'id' //用于识别选中或禁用的字段
            }, window.name, function (returnData, layerIndex) {
                $("#orgName").val(returnData[0].orgName);
                $("#orgId").val(returnData[0].orgId);
                top.layer.close(layerIndex);
            });
        }
    });

    $("#addBjOrgName").click(function () {
        top.openSelectOrg({
            title: '选择单位',
            where: {
                state: 1
            },
            treeWhere: {
                state: 1
            },
            area: ['1024px', '768px'],
            page: true,
            limit: 10,
            limits: [10, 50, 100, 1000, 10000],
            selectType: 'radio', //radio,checkbox
            checkedData: [], //默认选中的数据，只对selectType为radio起作用
            disabledData: [], //默认禁用的数据
            compareField: 'id' //用于识别选中或禁用的字段
        }, window.name, function (returnData, layerIndex) {
            $("#addBjOrgName").val(returnData[0].orgName);
            $("#addBjOrgId").val(returnData[0].orgId);
            top.layer.close(layerIndex);
        });
    });

    $("#editBjOrgName").click(function () {
        top.openSelectOrg({
            title: '选择单位',
            where: {
                state: 1
            },
            treeWhere: {
                state: 1
            },
            area: ['1024px', '768px'],
            page: true,
            limit: 10,
            limits: [10, 50, 100, 1000, 10000],
            selectType: 'radio', //radio,checkbox
            checkedData: [], //默认选中的数据，只对selectType为radio起作用
            disabledData: [], //默认禁用的数据
            compareField: 'id' //用于识别选中或禁用的字段
        }, window.name, function (returnData, layerIndex) {
            $("#editBjOrgName").val(returnData[0].orgName);
            $("#editBjOrgId").val(returnData[0].orgId);
            top.layer.close(layerIndex);
        });
    });

    //删除附件
    $("#edit_uploadList").delegate('.deleteFile', 'click', function () {
        delAttachment($(this).attr("attachmentId"));
        $(this).parents("tr").remove();
    })

    //删除附件
    $("#edit_sjqk_uploadList").delegate('.deleteFile', 'click', function () {
        delAttachment($(this).attr("attachmentId"));
        $(this).parents("tr").remove();
    })

    //学历简历删除附件
    $("#edit_edu_uploadList").delegate('.deleteFile', 'click', function () {
        delAttachment($(this).attr("attachmentId"));
        $(this).parents("tr").remove();
    })

    var editUpload = fileUpload($("#edit_uploadList"), '#edit_selectFileBtn', "AuxDagl", 1);

    var addUpload=fileUpload($("#add_uploadList"),'#add_selectFileBtn',"AuxSjqk",1);
    var editSjqkUpload=fileUpload($("#edit_sjqk_uploadList"),'#edit_sjqk_selectFileBtn',"AuxSjqk",1);

    var addEduUpload=fileUpload($("#add_edu_uploadList"),'#add_edu_selectFileBtn',"EduResume",1);
    var editEduUpload=fileUpload($("#edit_edu_uploadList"),'#edit_edu_selectFileBtn',"EduResume",1);

    //container：容器的JQ对象
    //instanceType：主体类型
    //instanceId：主体ID
    function fileUpload(container, elem, instanceType, instanceId) {
        //多文件列表上传
        var uploadListIns = upload.render({
            elem: elem,
            url: servicePath + "/file/upload?charset=utf-8&instanceType=" + instanceType + "&instanceId=" + instanceId +
                "&loginCode=" + adminInfo.loginCode,
            accept: 'images',
            acceptMime: 'image/*',
            multiple: true,
            auto: false
            //,bindAction: '#edit_uploadAction'
            ,
            choose: function (obj) {
                var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                //读取本地文件
                obj.preview(function (index, file, result) {
                    var tr = $(['<tr id="upload-' + index + '">', '<td>' + file.name + '</td>', '<td>' + (file.size / 1014).toFixed(
                        1) + 'kb</td>', '<td>等待上传</td>', '<td>',
                        '<button class="layui-btn layui-btn-xs reupload layui-hide">重传</button>',
                        '<button class="layui-btn layui-btn-xs layui-btn-danger deleteTr">删除</button>', '</td>', '</tr>'
                    ].join(''));

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
            },
            done: function (res, index, upload) {
                if (res.errCode == 0) { //上传成功
                    var tr = container.find('tr#upload-' + index),
                        tds = tr.children();
                    tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                    tds.eq(3).html('<button class="layui-btn layui-btn-xs viewFile layui-hide" attachmentId="' + res.attachmentList[
                            0].id + '">查看</button><button class="layui-btn layui-btn-xs layui-btn-danger deleteTr" attachmentId="' +
                        res.attachmentList[0].id + '">删除</button>'); //操作变成查看
                    return delete this.files[index]; //删除文件队列已经上传成功的文件
                }
                this.error(index, upload);
            },
            error: function (index, upload) {
                var tr = container.find('tr#upload-' + index),
                    tds = tr.children();
                tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                tds.eq(3).find('.reupload').removeClass('layui-hide'); //显示重传
            },
            allDone: function (obj) { //当文件全部被提交后，才触发
                showTicket("保存成功！", 1000, function () {
                    layer.closeAll('page');
                });
            }
        });
        return uploadListIns;
    }

    //获取附件列表编辑
    function getAttachmentListEdit(instanceId) {
        var layerIndex = layer.load();
        $.ajax({
            type: "GET",
            url: servicePath + "/file/getAttachmentList?charset=utf-8&instanceType=AuxSjqk&instanceId="+instanceId+"&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    $("#edit_sjqk_uploadList").html("");
                    var attachmentList=data.attachmentList;
                    for(var i=0;i<attachmentList.length;i++){
                        var tr = $(['<tr>'
                            ,'<td><a href="'+servicePath+'/'+attachmentList[i].filePath+'" target="_blank">'+ attachmentList[i].fileName +'</a></td>'
                            ,'<td>'+ (attachmentList[i].fileSize/1014).toFixed(1) +'kb</td>'
                            ,'<td>已存在</td>'
                            ,'<td>'
                            ,'<button class="layui-btn layui-btn-xs viewFile layui-hide" attachmentId="'+attachmentList[i].id+'">查看</button><button class="layui-btn layui-btn-xs layui-btn-danger deleteFile" attachmentId="'+attachmentList[i].id+'">删除</button>'
                            ,'</td>'
                            ,'</tr>'].join(''));
                        $("#edit_sjqk_uploadList").append(tr);
                    }
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！",2000,function(){
                        parent.location.href="../index.html?v=1.60";
                    });
                } else {
                    showTicket(data.errMsg, 2000);
                }
            }
        })
    };

    //获取附件列表
    function getAttachmentList(instanceType, instanceId) {
        var layerIndex = layer.load();
        $.ajax({
            type: "GET",
            url: servicePath + "/file/getAttachmentList?charset=utf-8&instanceType=" + instanceType + "&instanceId=" +
                instanceId + "&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    $("#edit_uploadList").html("");
                    var attachmentList = data.attachmentList;
                    if (attachmentList.length > 0) {
                        // 人员照片
                        $('.imgRen').attr('src', servicePath + '/' + attachmentList[0].filePath)
                    }
                    for (var i = 0; i < attachmentList.length; i++) {
                        var tr = $(['<tr>', '<td><a href="' + servicePath + '/' + attachmentList[i].filePath + '" target="_blank">' +
                        attachmentList[i].fileName + '</a></td>', '<td>' + (attachmentList[i].fileSize / 1014).toFixed(1) +
                        'kb</td>', '<td>已存在</td>', '<td>',
                            '<button class="layui-btn layui-btn-xs viewFile layui-hide" attachmentId="' + attachmentList[i].id +
                            '">查看</button><button class="layui-btn layui-btn-xs layui-btn-danger deleteFile" attachmentId="' +
                            attachmentList[i].id + '">删除</button>', '</td>', '</tr>'
                        ].join(''));
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

    //学历简历获取附件列表
    function getAttachmentList(instanceType, instanceId) {
        var layerIndex = layer.load();
        $.ajax({
            type: "GET",
            url: servicePath + "/file/getAttachmentList?charset=utf-8&instanceType=" + instanceType + "&instanceId=" +
                instanceId + "&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    $("#edit_edu_uploadList").html("");
                    var attachmentList = data.attachmentList;
                    if (attachmentList.length > 0) {
                        // 人员照片
                        $('.imgRen').attr('src', servicePath + '/' + attachmentList[0].filePath)
                    }
                    for (var i = 0; i < attachmentList.length; i++) {
                        var tr = $(['<tr>', '<td><a href="' + servicePath + '/' + attachmentList[i].filePath + '" target="_blank">' +
                        attachmentList[i].fileName + '</a></td>', '<td>' + (attachmentList[i].fileSize / 1014).toFixed(1) +
                        'kb</td>', '<td>已存在</td>', '<td>',
                            '<button class="layui-btn layui-btn-xs viewFile layui-hide" attachmentId="' + attachmentList[i].id +
                            '">查看</button><button class="layui-btn layui-btn-xs layui-btn-danger deleteFile" attachmentId="' +
                            attachmentList[i].id + '">删除</button>', '</td>', '</tr>'
                        ].join(''));
                        $("#edit_edu_uploadList").append(tr);
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
    $("#add_uploadList").delegate('.deleteFile','click', function(){
        delAttachment($(this).attr("attachmentId"));
        $(this).parents("tr").remove();
    });

    /*//自定义验证规则
    form.verify({
        numbermsg2: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value != "") {  //值不是空的时候再去走验证
                if (!/^(\-|\+)?\d+(\.\d+)?$/.test(value)) {
                    return '只能是数字';
                }
            }
        }
    });*/


});

//关闭当前tab页面
function closeTab() {
    top.topTabDelete("auxiliary/editArchive.html?v=1.74&daId=" + getParameter('daId'));
}
