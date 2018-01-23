<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var requestConfigGrid;

    $(function () {
        requestConfigGrid = $('#requestConfigGrid').datagrid({
            url: '${path }/manager/config/dataGrid',
            idField: 'id',
            pagination: true,
            fit: true,
            striped: true,
            rownumbers:true,
            fitColumns: false,
            singleSelect : true,
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50, 100, 200],
            frozenColumns: [[{
                field: 'id',
                checkbox: false,
                hidden:true
            }, {
                field: 'sysKey',
                title: '配置key',
                width: 240
            }, {
                field: 'sysValue',
                title: '配置值',
                width: 420
            }, {
                field: 'state',
                title: '状态',
                width: 60,
                formatter: function (value, row, index) {
                    var va = new String(value);
                    if(va == 'false'){
                        return '禁用';
                    }else{
                        return '启用';
                    }
                }
            }, {
                 field: 'description',
                 title: '描述',
                 width: 800
             },{
                field : 'action',
                title : '操作',
                width : 130,
                formatter : function(value, row, index) {
                    var str = '';
                        <shiro:hasPermission name="/manager/config/edit">
                            str += $.formatString('<a href="javascript:void(0)" class="config-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editConfig(\'{0}\');" >编辑</a>', row.id);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/manager/config/delete">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="config-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteConfig(\'{0}\');" >删除</a>', row.id);
                        </shiro:hasPermission>
                    return str;
                }
            }
              ]],
			onLoadSuccess:function(data){
                $('.config-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.config-easyui-linkbutton-del').linkbutton({text:'删除'});
            },
            toolbar: '#loanConfigToolBar'
        });
    });

    function addConfig() {
        parent.$.modalDialog({
            title : '添加',
            width : 550,
            height : 480,
            href : '${path }/manager/config/addPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = requestConfigGrid;
                    var f = parent.$.modalDialog.handler.find('#configAddForm');
                    f.submit();
                }
            } ]
        });
    }

    function editConfig(id) {
        if (id == undefined) {
            var rows = requestConfigGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            requestConfigGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '编辑',
            width : 550,
            height : 480,
            href : '${path }/manager/config/editPage?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = requestConfigGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#configEditForm');
                    f.submit();
                }
            } ]
        });
    }

    function deleteConfig(id) {
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = requestConfigGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            requestConfigGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除该配置？', function(b) {
            if (b) {
                progressLoad();
                $.post('${path }/manager/config/delete', {
                    id : id
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        requestConfigGrid.datagrid('reload');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }

    $('#systemConfigForm').submit(function () {
        var form = $(this);
        var formData = new FormData(this);

        $.ajax({
            type: form.attr('method'),
            url: form.attr('action'),
            data: formData,
            //mimeType: "multipart/form-data",
            contentType: false,
            dataType: 'json',
            cache: false,
            processData: false
        }).success(function (result) {
            var htmlValue = result.msg;
            if (result.data != null && result.data.length > 0) {
                htmlValue = htmlValue + "<br/>";
                htmlValue = htmlValue + result.data.join("; ");
            }
            reset();
            fileUpload.filebox('setValue', '');
        }).error(function () {
            alert('上传异常');
        });

        return false;
    });

    function searchConfigForm() {
        requestConfigGrid.datagrid('load', $.serializeObject($('#systemConfigForm')));
    }

    function resetConfigForm() {
        $('#systemConfigForm input').val('');
        $('#state option:first').prop('selected', 'selected');
        requestConfigGrid.datagrid('load', {});
    }

    function refreshRedisAll(){
        $.post('${path }/manager/config/redisRefresh', function(result) {
            console.log(result);
            if (result.success) {
                parent.$.messager.alert('提示', result.msg, 'info');
            }
            progressClose();
        }, 'JSON');
    }

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false,collapsible:true" style="overflow: hidden;">
        <table id="requestConfigGrid"></table>
    </div>
    <div id="loanConfigToolBar" style="display: none;">
        <form id="systemConfigForm">
            <table>
                <tr>
                    <td>
                        配置key <input id="sysKey" name="sysKey" type="text" style="width:200px"/>
                    </td>
                    <td>
                        配置值 <input id="sysValue" name="sysValue" type="text" style="width:200px"/>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'icon-search'"
                           onclick="searchConfigForm();">搜索</a>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'icon-refresh'"
                           onclick="resetConfigForm();">重置</a>
                    </td>
                    <td>
                        <shiro:hasPermission name="/manager/config/add">
                            <a onclick="addConfig();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加配置</a>
                        </shiro:hasPermission>
                    </td>
                    <td>
                        <shiro:hasPermission name="/manager/config/redisRefresh">
                            <a onclick="refreshRedisAll();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-refresh icon-green'">刷新redis所有数据</a>
                        </shiro:hasPermission>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
