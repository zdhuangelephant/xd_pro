<#include "/common/layout.ftl" />
<@htmlHead title="登陆页">
<link href="${CSS_PATH}select.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${JS_PATH}select-ui.min.js"></script>
<script type="text/javascript" src="${JS_PATH}validform.js"></script>
<script type="text/javascript" src="${JS_PATH}jquery.idTabs.min.js"></script>
<script type="text/javascript">
    $(document).ready(function (e) {
        $(".select1").uedSelect({
            width: 345
        });
        $(".select2").uedSelect({
            width: 167
        });
        $(".select3").uedSelect({
            width: 100
        });
    });
</script>
<style>
    .formLabel{
        width: 130px;;
    }
</style>
</@htmlHead>

<@htmlBody>
<#--面包屑-->
    <@bread></@bread>
<div class="formbody">
    <div id="usual1" class="usual">
        <div class="itab">
            <ul>
                <@sibling return="lists" path="${rc.requestUri}" >
                    <#list lists as item>
                        <li><a href="${item.url}"
                               <#if item.url==rc.requestUri>class="selected"</#if> >${item.name}</a>
                        </li>
                    </#list>
                </@sibling>
            </ul>
        </div>
        <div id="tab1" class="tabson">
            <form id="addForm" action="/config/security" method="post">
                <table class="formTable">
                    <tr>
                        <td class="formLabel">后台操作日志</td>
                        <td>
                            <input type="radio" name="openOperLog" <#if config.openOperLog==0>checked</#if> value="0" /><span style="display: inline;padding-left: 5px; margin-right: 20px;">不开启</span>
                            <input type="radio" name="openOperLog" <#if config.openOperLog==1>checked</#if> value="1" /><span style="display: inline;padding-left: 5px; margin-right: 20px;">开启</span>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel" style="width: 130px;">后台最大登录失败次数</td>
                    <td><input name="loginFailTime" type="text"
                                                                class="dfinput"
                                                                value="${config.loginFailTime}"
                                                                style="width:518px;" datatype="n" sucmsg=" " nullmsg="请填写后台最大登录失败次数"/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">&nbsp;</td>
                    <td><input name="" type="submit" class="btn" value="提交"/>
                    </td>
                        <td></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<script>
    $("#addForm").Validform({
        tiptype: 2,
        postonce: true
    });
</script>
</@htmlBody>

