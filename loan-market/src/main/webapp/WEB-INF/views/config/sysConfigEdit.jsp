<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#configEditForm').form({
            url : '${path }/manager/config/edit',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
        var state = '${config.state}';
        if(state == 'true'){
            $("#configEditStatus").val('1');
        }else{
            $("#configEditStatus").val('0');
        }
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="configEditForm" method="post">
            <input name="id" type="hidden"  value="${config.id}">
            <table class="grid">
                <tr>
                    <td>配置key</td>
                    <td><input name="sysKey" type="text" class="easyui-validatebox span2" style="width: 360px;height:20px" data-options="editable:false" value="${config.sysKey}"></td>
                </tr>
                <tr>
                    <td>配置值</td>
                    <td colspan="3"><textarea name="sysValue" type="text" class="easyui-validatebox span2" style="width: 360px;height:150px" required="required" data-options="required:true">${config.sysValue}</textarea></td>
                </tr>
                <tr>
                    <td>状态</td>
                    <td >
                        <select id="configEditStatus" name="state" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'" value="${config.state}">
                            <option value="0">禁用</option>
                            <option value="1">启用</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>描述</td>
                    <td colspan="3"><textarea name="description" placeholder="请输入配置描述" class="easyui-validatebox span2" style="width: 360px;height:100px">${config.description}</textarea></td>
                </tr>
            </table>
        </form>
    </div>
</div>