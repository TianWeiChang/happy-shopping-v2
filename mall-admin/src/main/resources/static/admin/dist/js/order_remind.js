$(function () {
    $("#jqGrid").jqGrid({
        url: '/remind/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'orderId', index: 'orderId', width: 120, key: true, hidden: true},
            {label: 'orderId', name: 'orderId', index: 'orderId', width: 120},
            {label: '用户登录名', name: 'userName', index: 'userName', width: 180},
            {label: '处理状态', name: 'status', index: 'status', width: 80, formatter: orderRemindStatusFormatter},
            {label: '提醒时间', name: 'createTime', index: 'createTime', width: 120},
            {label: '更新时间', name: 'updateTime', index: 'updateTime', width: 120}

        ],
        height: 560,
        rowNum: 10,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            remind: "remind",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    function orderRemindStatusFormatter(cellvalue) {
        //订单提醒是否已处理
        if (cellvalue == 0) {
            return "待处理";
        }
        if (cellvalue == 1) {
            return "已处理";
        }
    }

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });


});

/**
 * jqGrid重新加载
 */
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

/**
 * 下架
 */
function doRemind() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    console.log(ids);
    var orderIds = '';
    for (i = 0; i < ids.length; i++) {
        var rowData = $("#jqGrid").jqGrid("getRowData", ids[i]);
        if (rowData.status != '已处理') {
            orderIds += rowData.orderId + " ";
        }
    }
    swal({
        title: "确认弹框",
        text: "确认要已发货?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if (flag) {
            $.ajax({
                type: "POST",
                url: "/remind/status/1",
                contentType: "application/json",
                data: JSON.stringify(ids),
                success: function (r) {
                    if (r.resultCode == 200) {
                        swal("处理成功", {
                            icon: "success",
                        });
                        $("#jqGrid").trigger("reloadGrid");
                    } else {
                        swal(r.message, {
                            icon: "error",
                        });
                    }
                }
            });
        }
    }
)
    ;
}