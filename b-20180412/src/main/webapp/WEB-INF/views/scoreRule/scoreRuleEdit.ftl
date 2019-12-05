<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">


<table class="table table-striped table-bordered table-hover" id="">
<thead>
<tr>
    <th style="width: 75px;">计分点</th>
    <th style="width: 75px;">成绩占比</th>
    <th style="width: 50px;">排序</th>
    <th style="width: 300px;">计分点规则说明</th>
</tr>
</thead>
 <tbody>
    <#list rules?keys as key>
    <#assign item = rules[key]>   
    <tr>
        <#list scoreRuleType as val>  
            <#if val.getCode()==item.code>  
                <td>${val.getAbtractInfo()}</td>
            </#if>
        </#list> 
        <td>
            <input style="padding:0px;width:100px" name="weight" prevalue="${item.weight}" id="${key}" class="orderWeight" type="text" value="${item.weight}" 
            onblur="checkWeightIsPositiveNumber(value,id);checkTotalWeight()">
        </td>
        <td>
            <input style="padding:0px;width:100px" name="listOrder" prevalue="${item.order}" id="${key}" class="orderList" type="text" value="${item.order}">
        </td>
        <td>写死</td>
    </tr>
    </#list>
</tbody>
</table>

 <#if scoreRule.ruleDesc??>  
		<div class="form-group">
			<label class="col-sm-3 col-xs-3 control-label">展示说明</label>
			<div class="col-sm-9 col-xs-9">
			    <textarea id="description" class="form-control" style="width: 60%;overflow-y:visible;height: 80px;">${scoreRule.ruleDesc}</textarea>
			</div>
			<label class="col-sm-3 col-xs-3 control-label" id="totalWeightLabel"></label>
		</div>	
</#if>

<script>
    $("#addForm").Validform({
        tiptype: 2,
        postonce: true
    });
    /**
     * 判断权重是否为正
     */
     function checkWeightIsPositiveNumber(weight,id){
       if(Number(weight) < 0){
         alert("权重不能为负");
         var element = document.getElementById(id);
         element.value="";
       }
     }
      /**
     * 判断权重和是否为1
     */
     function checkTotalWeight(){
       var weightArr = document.getElementsByName("weight");
       var totalWeight = 0;
       var element = document.getElementById("totalWeightLabel");
       for(var i = 0;i < weightArr.length-1;i++){
         totalWeight += Number(weightArr[i].value);
       }
       if(totalWeight != 1){
          element.innerHTML = "权重和不为1";
          element.style.color = "red";
       }else{
          element.innerHTML = "";
       }
     }
</script>
</@htmlBody>
