<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#configAddForm').form({
            url : '${path }/manager/config/add',
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
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="configAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>配置key</td>
                    <td><input name="sysKey" type="text" placeholder="请输入配置key" class="easyui-validatebox span2" style="width: 360px;height:20px" data-options="required:true" value=""></td>
                </tr>
                <tr>
                    <td>配置值</td>
                    <td colspan="3"><textarea name="sysValue" type="text" placeholder="请输入配置value" class="easyui-validatebox span2" style="width: 360px;height:150px" required="required" data-options="required:true"></textarea></td>
                </tr>
                <tr>
                    <td>状态</td>
                    <td >
                        <select name="state" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="0">禁用</option>
                            <option value="1" selected="selected">启用</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>描述</td>
                    <td colspan="3"><textarea name="description" placeholder="请输入配置描述" class="easyui-validatebox span2" style="width: 360px;height:100px"></textarea></td>
                </tr>
            </table>
        </form>
    </div>
</div>