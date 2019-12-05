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
    span{
        display: inline;
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
            <form id="addForm" method="post" action="/config/attachment">
                <table class="formTable">
                    <tr>
                        <td class="formLabel">允许上传附件大小</td>
                        <td><input name="attachmentSize" type="text"
                                   class="dfinput"
                                   value="${config.attachmentSize}"
                                   style="width:518px;" datatype="n" sucmsg=" " nullmsg="请填写允许上传附件大小" />k</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">允许上传附件类型</td>
                        <td><input name="attachmentType" type="text"
                                   class="dfinput"
                                   value="${config.attachmentType}"
                                   style="width:518px;" datatype="*2-100" sucmsg=" " nullmsg="请填写允许上传附件类型" /></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">是否开启水印</td>
                        <td>
                            <input type="radio" <#if config.openWatermark==0>checked</#if> name="openWatermark" value="0" />
                            <span style="padding-left: 10px; margin-right: 20px; display: inline">不开启</span>
                            <input type="radio" <#if config.openWatermark==1>checked</#if> name="openWatermark" value="1" />
                            <span style="padding-left: 10px;display: inline">开启</span>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">水印添加条件</td>
                        <td><input name="watermarkCondition" type="text"
                                   class="dfinput"
                                   value="${config.watermarkCondition}"
                                   style="width:518px;"/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">水印图片</td>
                        <td><input name="watermarkPic" type="text"
                                   class="dfinput"
                                   value="${config.watermarkPic}"
                                   style="width:518px;" datatype="*2-10" sucmsg=" " nullmsg="请上传水印图片"/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">水印文本</td>
                        <td><input name="watermarkText" type="text"
                                   class="dfinput"
                                   value="${config.watermarkText}"
                                   style="width:518px;" datatype="s2-10" sucmsg=" " nullmsg="请填写水印文本"/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">水印透明度</td>
                        <td><input name="watermarkTransparency" type="text"
                                   class="dfinput"
                                   value="${config.watermarkTransparency}"
                                   style="width:518px;" datatype="n" sucmsg=" " nullmsg="请填写水印透明度" />%</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">jpg水印质量</td>
                        <td>


                            <input name="picWeight" type="text"
                                   class="dfinput"
                                   value="${config.picWeight}"
                                   style="width:518px;" datatype="n" sucmsg=" " nullmsg="请填写jpg水印质量"/>%</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">水印位置</td>
                        <td>
                            <table style="width: 100%">
                                <tr>
                                    <td rowspan="3"><input type="radio" <#if config.watermarkPosition==0>checked</#if> name="watermarkPosition" value="0" /><span style="padding-left: 5px;">随机位置</span></td>
                                    <td><input type="radio" name="watermarkPosition" <#if config.watermarkPosition==1>checked</#if> value="1" /><span style="padding-left: 5px;">顶部居左</span></td>
                                    <td><input type="radio" name="watermarkPosition" <#if config.watermarkPosition==2>checked</#if> value="2" /><span style="padding-left: 5px;">顶部居中</span></td>
                                    <td><input type="radio" name="watermarkPosition" <#if config.watermarkPosition==3>checked</#if> value="3" /><span style="padding-left: 5px;">顶部居右</span></td>
                                </tr>
                                <tr>
                                    <td><input type="radio" name="watermarkPosition" <#if config.watermarkPosition==4>checked</#if> value="4" /><span style="padding-left: 5px;">中部居左</span></td>
                                    <td><input type="radio" name="watermarkPosition" <#if config.watermarkPosition==5>checked</#if> value="5" /><span style="padding-left: 5px;">中部居中</span></td>
                                    <td><input type="radio" name="watermarkPosition" <#if config.watermarkPosition==6>checked</#if> value="6" /><span style="padding-left: 5px;">中部居右</span></td>
                                </tr>
                                <tr>
                                    <td><input type="radio" name="watermarkPosition" <#if config.watermarkPosition==7>checked</#if> value="7" /><span style="padding-left: 5px;">下部居左</span></td>
                                    <td><input type="radio" name="watermarkPosition" <#if config.watermarkPosition==8>checked</#if> value="8" /><span style="padding-left: 5px;">下部居中</span></td>
                                    <td><input type="radio" name="watermarkPosition" <#if config.watermarkPosition==9>checked</#if> value="9" /><span style="padding-left: 5px;">下部居右</span></td>
                                </tr>
                            </table>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="formLabel">&nbsp;</td>
                        <td><input name="" type="submit" class="btn"
                                                                   value="提交"/>
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

