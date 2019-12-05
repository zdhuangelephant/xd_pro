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
                        <li><a href="${item.url}"
                               <#if item.url==rc.requestUri>class="selected"</#if> >${item.name}</a>
                        </li>
                    </#list>
                </@sibling>
            </ul>
        </div>
        <div id="tab1" class="tabson">
            <form method="post" id="addForm" action="/config/base">
                <table class="formTable">
                    <tr>
                        <td class="formLabel">站点名称</td>
                        <td><input name="siteName" type="text"
                                   class="dfinput" value="${config.siteName}"
                                   style="width:300px;" datatype="s2-16" sucmsg=" " nullmsg="请填写站点名称" /></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">站点域名</td>
                        <td><input name="siteUrl" type="text" class="dfinput"
                                   value="${config.siteUrl}"
                                   style="width:300px;" datatype="url" sucmsg=" " nullmsg="请填写站点url"/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">站点标题</td>
                        <td><input name="siteTitle" type="text"
                                   class="dfinput"
                                   value="${config.siteTitle}"
                                   style="width:300px;" datatype="s2-100" sucmsg=" " nullmsg="请填写站点标题"/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">关键词</td>
                        <td><input name="siteKeywords" type="text"
                                   class="dfinput"
                                   value="${config.siteKeywords}"
                                   style="width:300px;" datatype="s2-100" sucmsg=" " nullmsg="请填写站点关键词"/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">描述</td>
                        <td><input name="siteDescription" type="text"
                                   class="dfinput"
                                   value="${config.siteDescription}"
                                   style="width:300px;" datatype="s2-100" sucmsg=" " nullmsg="请填写站点描述"/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">联系人</td>
                        <td><input name="contactMan" type="text"
                                   class="dfinput"
                                   value="${config.contactMan}"
                                   style="width:300px;" datatype="s2-100" ignore="ignore" sucmsg=" " /></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">联系人邮箱</td>
                        <td><input name="contactEmail" type="text"
                                   class="dfinput"
                                   value="${config.contactEmail}"
                                   style="width:300px;" datatype="e" ignore="ignore" sucmsg=" " /></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">联系人手机</td>
                        <td><input name="contactMobile" type="text"
                                   class="dfinput"
                                   value="${config.contactMobile}"
                                   style="width:300px;" datatype="m" ignore="ignore" sucmsg=" "/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">联系人电话</td>
                        <td><input name="contactTel" type="text"
                                   class="dfinput"
                                   value="${config.contactTel}"
                                   style="width:300px;" datatype="s2-100" ignore="ignore" sucmsg=" "/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">联系人QQ</td>
                        <td><input name="contactQQ" type="text"
                                   class="dfinput"
                                   value="${config.contactQQ}"
                                   style="width:300px;" datatype="s2-100" ignore="ignore" sucmsg=" "/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">是否启用页面压缩</td>
                        <td><input type="radio" name="gzip" <#if config.gzip==0>checked</#if> value="0" /><label style="margin-right: 50px; padding-left: 10px;">否</label><input type="radio" name="gzip" <#if config.gzip==1>checked</#if> value="1" /><label style="padding-left: 10px;">是</label>
                            </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">备案号</td>
                        <td><input name="icp" type="text" class="dfinput"
                                   value="${config.icp}"
                                   style="width:300px;" datatype="s2-100" sucmsg=" " nullmsg="请填写备案号"/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">&nbsp;</label><input name="" type="submit" class="btn"
                                                                   value="提交"/>
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

