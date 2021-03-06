var setting = JSON.parse(unescape(getParameter('setting')));
var compareField = setting.compareField;
var checkedDataObj = {};
var checkedDataList = setting.checkedData;
if (checkedDataList != undefined && checkedDataList.length > 0) {
    for (var i = 0; i < checkedDataList.length; i++) {
        checkedDataObj[checkedDataList[i]] = true;
    }
}
var disabledDataObj = {};
var disabledData = setting.disabledData;
if (disabledData != undefined && disabledData.length > 0) {
    for (var i = 0; i < disabledData.length; i++) {
        disabledDataObj[disabledData[i]] = true;
    }
}
var table;
var checkedData = [];//回调返回值
var list = [];

function getCheckedData() {
    return table.checkStatus('ryTable').data;
}

layui.use(['tree', 'util', 'form', 'table', 'layer', 'upload'], function () {
    var $ = layui.jquery;
    table = layui.table;
    var form = layui.form;
    var tree = layui.tree;
    var util = layui.util;
    var upload = layui.upload;

    var ryTable = table.render({
        id: 'ryTable',
        elem: '#ryTable',
        url: servicePath + setting.resource + "?charset=utf-8&loginCode=" + adminInfo.loginCode,
        method: 'POST',
        contentType: "application/json; charset=utf-8",
        where: setting.where,
        limit: setting.limit,
        limits: setting.limits,
        title: '选择减员人员表',
        cols: [
            [{
                type: setting.selectType,
                fixed: 'center'
            },/* {
                field: 'rpsId',
                title: '收文处理单ID',
                sort: true,
                with: 150
            }, {
                field: 'documentId',
                title: '文件ID',
                sort: true,
                with: 150
            }, */{
                field: 'xm',
                title: '姓名',
                sort: true,
                with: 150
            }, {
                field: 'idcard',
                title: '身份证号',
                sort: true,
                with: 150
            }, {
                field: 'orgName',
                title: '工作单位',
                sort: true,
                with: 150,
                templet:function (d) {
                    return getOrgName(d.orgId);
                }
            }, {
                field: 'state',
                title: '状态',
                sort: true,
                with: 150,
                templet: function (d) {
                    if (d.state == '1') {
                        return '<font color=green >在岗</font>'
                    } else if (d.state == '2') {
                        return '<font color=red >离职</font>'
                    }else {
                        return '<font color=blue >未上报</font>';
                    }
                }/*,
                templet: function (d) {
                    return getDictDataLabel("AUX_DASTATE", d.state);
                }*/
            },{
                field: 'remark',
                title: '备注',
                sort: true,
                with: 150
            },{
                field: 'xb',
                title: '性别',
                sort: true,
                with: 150,
                templet: function (d) {
                    return getDictDataLabel("SEX", d.xb);
                }
            }/*,{
                field: 'rpsId',
                title: '收文文件来源',
                sort:true,
                minWidth: 200
            }*/]
        ],
        page: setting.page,
        parseData: function (res) { //res 即为原始返回的数据
            return res;
        },
        height: 'full-151',
        response: {
            statusName: 'errCode', //规定数据状态的字段名称，默认：code
            statusCode: 0, //规定成功的状态码，默认：0
            msgName: 'errMsg', //规定状态信息的字段名称，默认：msg
            countName: 'recordCount', //规定数据总数的字段名称，默认：count
            dataName: 'auxDaglList' //规定数据列表的字段名称，默认：data
        },
        request: {
            pageName: 'page', //页码的参数名称，默认：page
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }/*,
        done: function (res, curr, count) {
            list = res.list;
            if (setting.selectType == 'checkbox') {
                for (var i = 0; i < list.length; i++) {
                    var index = list[i]["LAY_TABLE_INDEX"];
                    if (checkedDataObj[list[i][compareField]]) {
                        $(".layui-table-body [class!='layui-table-body layui-table-main']").find('tr[data-index=' + index + '] .layui-form-checkbox').click();
                    }
                    if (disabledDataObj[list[i][compareField]]) {
                        $('tr[data-index=' + index + '] input[type="checkbox"]').prop('disabled', true);
                        $('tr[data-index=' + index + '] input[type="checkbox"]').next().addClass('layui-btn-disabled');
                    }
                }
            }
            if (setting.selectType == 'radio') {
                for (var i = 0; i < list.length; i++) {
                    var index = list[i]["LAY_TABLE_INDEX"];
                    if (disabledDataObj[list[i][compareField]]) {
                        $('tr[data-index=' + index + '] input[type="radio"]').prop('disabled', true);
                        $('tr[data-index=' + index + '] input[type="radio"]').next().addClass('layui-btn-disabled');
                    }
                }
            }
            form.render('checkbox');
        }*/
    });

    //监听选择事件
    table.on('checkbox(ryTable)', function (obj) {
        if (obj.type === 'all') {
            if (obj.checked == true) {
                for (var i = 0; i < list.length; i++) {
                    var index = list[i]["LAY_TABLE_INDEX"];
                    //console.log(checkedDataObj[list[i][compareField]],disabledDataObj[list[i][compareField]]);
                    //不在选中里面，而且是禁用状态的
                    if (checkedDataObj[list[i][compareField]] == undefined && disabledDataObj[list[i][compareField]]) {
                        //先解除禁用
                        //$('tr[data-index=' + index + '] input[type="checkbox"]').prop('disabled', false);
                        //再点击
                        //2:选中值改变
                        $(".layui-table-body [class!='layui-table-body layui-table-main']").find('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', false);
                        $('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', false);
                        table.cache.ryTable[index].LAY_CHECKED = false;
                        //$(".layui-table-body [class!='layui-table-body layui-table-main']").find('tr[data-index=' + index + '] .layui-form-checkbox').click();
                        //1:选中外观改变
                        //$('tr[data-index=' + index + '] input[type="checkbox"]').click();
                        //再禁用
                        //$('tr[data-index=' + index + '] input[type="checkbox"]').prop('disabled', true);
                        //$('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', false);
                    }
                }
            }
            if (obj.checked == false) {
                for (var i = 0; i < list.length; i++) {
                    var index = list[i]["LAY_TABLE_INDEX"];
                    //在选中里面，而且是禁用状态的
                    if (checkedDataObj[list[i][compareField]] && disabledDataObj[list[i][compareField]]) {
                        //$(".layui-table-body [class!='layui-table-body layui-table-main']").find('tr[data-index=' + index + '] .layui-form-checkbox').click();
                        //$('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
                        $(".layui-table-body [class!='layui-table-body layui-table-main']").find('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
                        $('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
                        table.cache.ryTable[index].LAY_CHECKED = true;
                    }
                }
            }
            form.render('checkbox');
        }

    });

    //头工具栏事件
    table.on('toolbar(ryTable)', function (obj) {
        var checkStatus = table.checkStatus('ryTable');
        switch (obj.event) {
            case 'addArchive':
                top.topTabAdd("添加来源",
                    '<div class="layui-body"><iframe name="kqlrList.html?v=1.60" src="kqlrList.html?v=1.60" frameborder="0" class="layadmin-iframe"></iframe></div>',
                    'kqlrList.html?v=1.60')
                break;
            case 'getCheckLength':
                var data = checkStatus.data;
                layer.msg('选中了：' + data.length + ' 个');
                break;
            case 'isAll':
                layer.msg(checkStatus.isAll ? '全选' : '未全选');
                break;
        }
        ;
    });

    //监听行工具事件
    table.on('tool(ryTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
        }

    });

    $(".assignType").each(function () {
        addDictDataToSelect(this, "DOC_ASSIGNTYPE");
    })

    /*$(".secretLevel").each(function () {
        addDictDataToSelect(this, "DOC_SECRET_LEVEL");
    })
    $(".assignType").each(function () {
        addDictDataToSelect(this, "DOC_ASSIGNTYPE");
    })*/

    form.render('select');

    //搜索文件信息
    function search() {
        var formData = form.val("searchForm");
        var data = {
            xm: formData.xm,
            idcard: formData.idcard
        }
        ryTable.reload({
            where: data
        });
    }

    //搜索人员“查询”按钮触发
    form.on('submit(searchBtn)', function (data) {
        search()
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

});

window.onload = function () {

}
