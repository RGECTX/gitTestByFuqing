layui.use(['tree', 'util', 'element', 'form', 'table', 'layer', 'laydate'], function () {
    var $ = layui.jquery;
    var tree = layui.tree;
    var util = layui.util;
    var element = layui.element;
    var table = layui.table;
    var form = layui.form;
    var layer = layui.layer;
    var laydate = layui.laydate;


    var scheduleTaskTable = table.render({
        elem: '#scheduleTaskList'
        , url: servicePath + "/scheduletask/search?charset=utf-8&loginCode=" + adminInfo.loginCode
        , method: 'POST'
        , contentType: "application/json; charset=utf-8"
        , limit: 10
		, limits: [10, 50, 100, 1000, 100000]
        , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        , defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
            title: '提示'
            , layEvent: 'LAYTABLE_TIPS'
            , icon: 'layui-icon-tips'
        }]
        , title: '定时任务列表'
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'taskName', title: '任务名称', width: 200}
            , {field: 'taskCode', title: '任务编码', width: 160}
            /* , {field: 'taskType', title: '任务类别', width: 160} */
            , {
                field: 'startTime', title: '开始时间', width: 148, templet: function (d) {
                    return new Date(d.startTime).format("yyyy-MM-dd hh:mm");
                }
            }
            , {
                field: 'endTime', title: '结束时间', width: 148, templet: function (d) {
                    return new Date(d.endTime).format("yyyy-MM-dd hh:mm");
                }
            }
            , {field: 'times', title: '执行次数', width: 90}
            , {field: 'interval1', title: '执行间隔（ms）', width: 140}
            , {field: 'taskClass', title: '任务类', width: 360}
            , {field: 'remark', title: '备注', minwidth: 120}
            , {fixed: 'right', field: 'state', title: '状态', toolbar: '#barState', width: 90, unresize: true}
            , {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 150}
        ]]
        , page: true
        , parseData: function (res) { //res 即为原始返回的数据
            return res;
        }
        , height: 'full-105'
        , response: {
            statusName: 'errCode' //规定数据状态的字段名称，默认：code
            , statusCode: 0 //规定成功的状态码，默认：0
            , msgName: 'errMsg' //规定状态信息的字段名称，默认：msg
            , countName: 'recordCount' //规定数据总数的字段名称，默认：count
            , dataName: 'scheduleTaskList' //规定数据列表的字段名称，默认：data
        }
        , request: {
            pageName: 'page' //页码的参数名称，默认：page
            , limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
    });

    //头工具栏事件
    table.on('toolbar(scheduleTaskList)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            //添加定时任务
            case 'addScheduleTask':
                form.render('select', 'addScheduleTask')
                layer.open({
                    type: 1
                    , title: '添加定时任务'
                    , area: ['600px', '500px']
                    , content: $('#addScheduleTaskPage')
                    , btn: ['保存', '关闭']
                    , yes: function (index, layero) {
                        $("#addScheduleTaskSubmit").click();
                    }
                    , btn2: function (index, layero) {
                        layer.close(index);
                    }
                });
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

    //监听行工具事件
    table.on('tool(scheduleTaskList)', function (obj) {
        var data = obj.data;
        //console.log(obj)
        if (obj.event === 'del') {
            layer.confirm('真的要删除该任务吗？', function (index) {
                obj.del();
                deleteScheduleTask(data.id);
                layer.close(index);
            });
        }
        //修改定时任务
        if (obj.event === 'edit') {
			document.getElementById("editScheduleTask").reset();
			var formData=data;
			formData.startTime=new Date(data.startTime).format("yyyy-MM-dd hh:mm:ss");
			formData.endTime=new Date(data.endTime).format("yyyy-MM-dd hh:mm:ss");
            form.val("editScheduleTask", formData);
            layer.open({
                type: 1
                , title: '修改定时任务'
                , area: ['600px', '500px']
                , content: $('#editScheduleTaskPage')
                , btn: ['保存', '关闭']
                , yes: function (index, layero) {
                    $("#editScheduleTaskSubmit").click();
                }
                , btn2: function (index, layero) {
                    layer.close(index);
                }
            });
        }
    });


    //添加定时任务
    function addScheduleTask(data) {
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/scheduletask/add?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("添加成功！", 2000, function () {
                        layer.closeAll('page');
                        document.getElementById("addScheduleTask").reset();
                        scheduleTaskTable.reload();
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

    //修改定时任务
    function editScheduleTask(data) {
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/scheduletask/update?charset=utf-8&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("修改成功！", 2000, function () {
                        layer.closeAll('page');
                        scheduleTaskTable.reload();
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
    };


    //删除定时任务
    function deleteScheduleTask(id) {
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/scheduletask/delete?charset=utf-8&id=" + id + "&loginCode=" + adminInfo.loginCode,
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

    //搜索
    function search() {
        var formData = form.val("searchForm");
        var data = {
            taskName: formData.taskName
        }
        scheduleTaskTable.reload({
            where: data
        });
    }

    //修改状态
    function changeState(id,state) {
        var layerIndex = layer.load();
        $.ajax({
            type: "POST",
            url: servicePath + "/scheduletask/changeState?charset=utf-8&id=" + id + "&state=" + state + "&loginCode=" + adminInfo.loginCode,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                layer.close(layerIndex);
                if (data.errCode == 0) {
                    showTicket("操作成功", 1000);
                } else if (data.errCode == 200061 || data.errCode == 200062) {
                    showTicket("请先登录！", 2000, function () {
                        parent.location.href = "index.html?v=1.60";
                    });
                } else if (data.errCode == 200063) {
                    showTicket("没有权限！", 2000);
                } else {
                    showTicket(data.errMsg, 2000);
                }				
				scheduleTaskTable.reload();
            }
        })
    }


    //添加定时器“保存”按钮触发
    form.on('submit(addScheduleTask)', function (data) {
		var formData=form.val("addScheduleTask");
		formData.startTime=new Date(formData.startTime).getTime();
		formData.endTime=new Date(formData.endTime).getTime();
        addScheduleTask(formData);
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    //修改定时器“保存”按钮触发
    form.on('submit(editScheduleTask)', function (data) {
		var formData=form.val("editScheduleTask");
		formData.startTime=new Date(formData.startTime).getTime();
		formData.endTime=new Date(formData.endTime).getTime();
        editScheduleTask(formData);
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    //搜索定时器“查询”按钮触发
    form.on('submit(searchBtn)', function (data) {
        search();
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    //监听状态操作
    form.on('switch(stateDemo)', function(obj){
        //layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
        changeState(this.value,obj.elem.checked);
        return false;
    });

    //日期判断
    function dateCheck() {
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        if (startDate != '' && endDate != '') {
            if (startDate > endDate) {
                layer.alert('开始时间不能大于结束时间');
                $("#endDate").val("");
                $("#startDate").val("");
                return;
            }
        }
    }
	
	$(".date-time").each(function(){
		laydate.render({
		    elem: this, //指定元素
			type: 'datetime'
		});
	})

    //执行次数、间隔验证
    form.verify({
        positiveNumber: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (!new RegExp("^[0-9]*[1-9][0-9]*$").test(value)) {
                return '只能输入正整数';
            }
        }
    });

});