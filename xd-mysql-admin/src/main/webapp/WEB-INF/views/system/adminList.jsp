<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="Cache" content="no-cache">
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>

 <div id="tb3" style="padding:5px;height:auto">
                         <div>
                           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addRowButton"  onclick="addRoleForm()"> 添加 </a>
	       		           <span class="toolbar-item dialog-tool-separator"></span>
	                       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="editRowButton" onclick="editRoleForm()">修改</a>
	                       <span class="toolbar-item dialog-tool-separator"></span>
	                       <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-arrow-refresh" plain="true" onclick="refresh()">刷新</a>
	                       <span class="toolbar-item dialog-tool-separator"></span>
	                       
                         </div> 
                       
  </div>
  <div id="dlgg" ></div>  
 
 <table id="dg3"></table> 
<script type="text/javascript">
var dgg;
var config;
$(function(){  
	
    initData();
});

function initData(){
	dgg=$('#dg3').datagrid({     
	method: "get",
    url: '${ctx}/system/permission/i/adminList', 
    fit : true,
	fitColumns : true,
	border : false,
	idField : 'rid',
	striped:true,
	pagination:true,
	rownumbers:true,
	pageNumber:1,
	pageSize : 20,
	pageList : [ 10, 20, 30, 40, 50 ],
	singleSelect:false,
    columns:[[    
	  	{field:'TREESOFTPRIMARYKEY',checkbox:true}, 
	  	{field:'rid',title:'id',sortable:true,width:30},
	  	{field:'userName',title:'登录用户名',width:100 },
        {field:'name',title:'姓名',sortable:true,width:100},
        {field:'type',title:'角色',sortable:true,formatter: function(value,row,index){
				if (row.type=='1'){
					return '普通用户';
				} else if (row.type=='2'){
					return '开发';
				} else {
					return '管理员';
				}
			}}
    ] ], 
    checkOnSelect:true,
    selectOnCheck:true,
    extEditing:false,
    toolbar:'#tb3',
    autoEditing: false,     //该属性启用双击行时自定开启该行的编辑状态
    singleEditing: false,
    onDblClickRow: function (rowIndex, rowData) {
    	var id =rowData.rid  ;
	    config = $("#dlgg").dialog({   
	    title: '查看',    
	    width: 350,    
	    height: 320,    
	    href:'${ctx}/system/permission/i/editRoleForm/'+id,
	    maximizable:true,
	    modal:true,
	    buttons:[
	    	{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
					config.panel('close');
				}
		 }]
	  });
    }
   }); 
  }

 function addRoleForm(){
	 config= $("#dlgg").dialog({   
	    title: '编辑',    
	    width: 350,    
	    height: 320,    
	    href:'${ctx}/system/permission/i/addRoleForm',
	    maximizable:true,
	    modal:true,
	    buttons:[
	    	{
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
				$("#mainform").submit(); 
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
					config.panel('close');
				}
		}]
	});
 }

 function editRoleForm(){
	
	var checkedItems = $('#dg3').datagrid('getChecked');
	if(checkedItems.length == 0 ){
	      parent.$.messager.show({ title : "提示",msg: "请先选择一行数据！", position: "bottomRight" });
		  return;
	 }
	    
	 if(checkedItems.length >1 ){
	      parent.$.messager.show({ title : "提示",msg: "请选择一行数据！", position: "bottomRight" });
		  return;
	 }
	 
	 var id = checkedItems[0]['rid']  ;
	 
	 config= $("#dlgg").dialog({   
	    title: '编辑',    
	    width: 350,    
	    height: 320,    
	    href:'${ctx}/system/permission/i/editRoleForm/'+id,
	    maximizable:true,
	    modal:true,
	    buttons:[
	    	{
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
				$("#mainform").submit(); 
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
					config.panel('close');
				}
		}]
	});
 }
 
   function refresh(){
	    $('#dg3').datagrid('reload');
	    $("#dg3").datagrid('clearSelections').datagrid('clearChecked');
   }
    
</script>

</body>
</html>