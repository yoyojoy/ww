<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var requestRedisInfoGrid;

    $(function () {
        requestRedisInfoGrid = $('#requestRedisInfoGrid').datagrid({
            url: '${path }/manager/redis/dataGrid',
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
                field: 'key',
                title: '缓存key',
                width: 380
            }, {
                field: 'value',
                title: '缓存值',
                width: 1250
            },{
                field : 'action',
                title : '操作',
                width : 80,
                formatter : function(value, row, index) {
                    var str = '';
                        <shiro:hasPermission name="/manager/redis/edit">
                            str += $.formatString('<a href="javascript:void(0)" class="config-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="redisInfoEdit(\'{0}\');" >编辑</a>', row.key);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/manager/redis/delete">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="easyui-linkbutton platformUser-detail" data-options="plain:true, iconCls:\'icon-list\'" onclick="redisInfoDelete(\'{0}\');" >删除</a>', row.key);
                        </shiro:hasPermission>
                    return str;
                }
            }
              ]],
			onLoadSuccess:function(data){
                $('.config-easyui-linkbutton-del').linkbutton({text:'详情'});
            },
            toolbar: '#loanConfigToolBar'
        });
    });

    function redisInfoEdit(id) {
        if (id == undefined) {
            var rows = requestRedisInfoGrid.datagrid('getSelections');
            id = rows[0].key;
        } else {
            requestRedisInfoGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '编辑',
            width : 550,
            height : 500,
            href : '${path }/manager/redis/editPage?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = requestRedisInfoGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#redisEditForm');
                    f.submit();
                }
            } ]
        });
    }

    function redisInfoDelete(key) {
         if (key == undefined) {
            parent.$.messager.alert('提示', '数据有误', 'info');
         }
         parent.$.messager.confirm('询问', '您是否要删除该缓存值？', function(b) {
             if (b) {
                 progressLoad();
                 $.post('${path }/manager/redis/delete', {
                     key : key
                 }, function(result) {
                     if (result.success) {
                         parent.$.messager.alert('提示', result.msg, 'info');
                         requestRedisInfoGrid.datagrid('reload');
                     }
                     progressClose();
                 }, 'JSON');
             }
         });
    }

    function searchConfigForm() {
        requestRedisInfoGrid.datagrid('load', $.serializeObject($('#sysRedisInfoForm')));
    }

    function resetConfigForm() {
        $('#sysRedisInfoForm input').val('');
        requestRedisInfoGrid.datagrid('load', {});
    }

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false,collapsible:true" style="overflow: hidden;">
        <table id="requestRedisInfoGrid"></table>
    </div>
    <div id="loanConfigToolBar" style="display: none;">
        <form id="sysRedisInfoForm">
            <table>
                <tr>
                    <td>
                        用户手机号 <input id="account" name="account" type="text" style="width:220px"/>
                    </td>
                    <td>
                        身份证号码 <input id="certNo" name="certNo" type="text" style="width:220px"/>
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
                </tr>
            </table>
        </form>
    </div>
</div>
