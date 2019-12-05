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
                        <li><a href="${item.url}" <#if item.url==rc.requestUri>class="selected"</#if> >${item.name}</a>
                        </li>
                    </#list>
                </@sibling>
            </ul>
        </div>
        <div id="tab1" class="tabson">
            <form id="addForm" method="post" action="/config/email">
                <table class="formTable">
                    <tr>
                        <td class="formLabel">邮件服务器</td>
                        <td><input name="emailServer" type="text"
                                                           class="dfinput"
                                                           value="${config.emailServer}"
                                                           style="width:518px;" datatype="s2-16" sucmsg=" " nullmsg="请填写邮件服务器地址"/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">端口号</td>
                        <td><input name="emailPort" type="text"
                                                         class="dfinput" value="${config.emailPort}"
                                                         style="width:518px;" datatype="n" sucmsg=" " nullmsg="请填写端口号"/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">发件人地址</td>
                        <td><input name="emailFrom" type="text"
                                                           class="dfinput"
                                                           value="${config.emailFrom}"
                                                           style="width:518px;" datatype="e" sucmsg=" " nullmsg="请填写发件人地址" /></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">用户名</td>
                        <td><input name="emailUser" type="text"
                                                         class="dfinput" value="${config.emailUser}"
                                                         style="width:518px;" datatype="s2-16" sucmsg=" " nullmsg="请填写用户名"/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">密码</td>
                        <td><input name="emailPassword" type="text"
                                                        class="dfinput"
                                                        value="${config.emailPassword}"
                                                        style="width:518px;" datatype="s2-16" sucmsg=" " nullmsg="请填写密码"/></td>
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
        <div id="tab2" class="tabson">
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

