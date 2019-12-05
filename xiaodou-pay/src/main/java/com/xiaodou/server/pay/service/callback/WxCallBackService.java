package com.xiaodou.server.pay.service.callback;

import java.io.BufferedOutputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.server.pay.enums.PayStatus;
import com.xiaodou.server.pay.model.PayRecord;
import com.xiaodou.server.pay.payplatform.dc.wxpay.WxPayResult;
import com.xiaodou.server.pay.response.PayResponse;
import com.xiaodou.server.pay.response.resultType.PaymentResType;
import com.xiaodou.server.pay.service.facade.IPayServiceFacade;
import com.xiaodou.server.pay.service.queue.QueueService;
import com.xiaodou.server.pay.vo.CallbackBusinessPojo;
import com.xiaodou.summer.vo.out.ResultType;

@Service("wxCallBackService")
public class WxCallBackService {

  @Resource(name = "payServiceFacade")
  IPayServiceFacade payServiceFacade;
  @Resource(name = "queueService")
  QueueService queueService;

  public PayResponse  callback(HttpServletRequest request,HttpServletResponse response)  throws Exception
  {
	    PayResponse payResponse = new PayResponse(ResultType.SUCCESS);
	  try{
		//把如下代码贴到的你的处理回调的servlet 或者.do 中即可明白回调操作
		System.out.print("微信支付回调数据开始");
		//示例报文
//		String xml = "<xml><appid><![CDATA[wxb4dc385f953b356e]]></appid><bank_type><![CDATA[CCB_CREDIT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1228442802]]></mch_id><nonce_str><![CDATA[1002477130]]></nonce_str><openid><![CDATA[o-HREuJzRr3moMvv990VdfnQ8x4k]]></openid><out_trade_no><![CDATA[1000000000051249]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[1269E03E43F2B8C388A414EDAE185CEE]]></sign><time_end><![CDATA[20150324100405]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1009530574201503240036299496]]></transaction_id></xml>";
		String inputLine;
		String notityXml = "";
		String resXml = "";

		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("接收到的报文：" + notityXml);
		
		Map m = parseXmlToList2(notityXml);
		WxPayResult wpr = new WxPayResult();
		wpr.setAppid(m.get("appid").toString());
		wpr.setBankType(m.get("bank_type").toString());
		wpr.setCashFee(m.get("cash_fee").toString());
		wpr.setFeeType(m.get("fee_type").toString());
		wpr.setIsSubscribe(m.get("is_subscribe").toString());
		wpr.setMchId(m.get("mch_id").toString());
		wpr.setNonceStr(m.get("nonce_str").toString());
		wpr.setOpenid(m.get("openid").toString());
		wpr.setOutTradeNo(m.get("out_trade_no").toString());
		wpr.setResultCode(m.get("result_code").toString());
		wpr.setReturnCode(m.get("return_code").toString());
		wpr.setSign(m.get("sign").toString());
		wpr.setTimeEnd(m.get("time_end").toString());
		wpr.setTotalFee(m.get("total_fee").toString());
		wpr.setTradeType(m.get("trade_type").toString());
		wpr.setTransactionId(m.get("transaction_id").toString());
		
		if("SUCCESS".equals(wpr.getResultCode())){
			//支付成功
	        // 2、改库
	        PayRecord payRecord =
	            payServiceFacade.queryPayRecordByTradeNo4Update(wpr.getOutTradeNo());
	        if (null == payRecord) {
	        	  return new PayResponse(PaymentResType.FindPayFailed);
	        } else {
	          payRecord.setPayStatus(PayStatus.SUCCESS.getCode());
	          payRecord.setPayResult(PayStatus.SUCCESS.getStatus());
	          if (!payServiceFacade.updatePayRecord(payRecord))
	              return new PayResponse(PaymentResType.FindPayFailed);
	            // 处理业务操作
	            queueService.callBackBusiness(new CallbackBusinessPojo(payRecord));
	            System.out.println("业务逻辑完成");
	          }
	        payResponse.setPayStatus(PayStatus.SUCCESS.getCode().toString());
			resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
			+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
		}else{
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
			+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
		}
		System.out.println("微信支付回调数据结束");
		BufferedOutputStream out = new BufferedOutputStream(
				response.getOutputStream());
		out.write(resXml.getBytes());
		out.flush();
		out.close();
	  }catch(Exception e) {
		  LoggerUtil.error("微信回调通知失败！", e);
	  }
	  return payResponse;
  }
	/**
	 * description: 解析微信通知xml
	 * 
	 * @param xml
	 * @return
	 * @author zhouhuan
	 * @see
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map parseXmlToList2(String xml) {
		Map retMap = new HashMap();
		try {
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = (Document) sb.build(source);
			Element root = doc.getRootElement();// 指向根节点
			List<Element> es = root.getChildren();
			if (es != null && es.size() != 0) {
				for (Element element : es) {
					retMap.put(element.getName(), element.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}
}
