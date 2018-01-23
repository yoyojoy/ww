<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#redisEditForm').form({
            url : '${path }/manager/redis/edit',
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
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="redisEditForm" method="post">
            <input type="hidden" name="key" value="${config.key}" />
            <table class="grid">
                <tr>
                    <td>缓存key</td>
                    <td>${config.key}</td>
                </tr>
                <tr>
                    <td>缓存值</td>
                    <td colspan="3"><textarea name="value" type="text" class="easyui-validatebox span2" style="width: 400px;height:350px" required="required" data-options="required:true">${config.value}</textarea></td>
                </tr>
            </table>
        </form>
    </div>
</div>