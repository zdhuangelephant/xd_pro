package com.xiaodou.oms.statemachine.instance.trainSelfPay;

import org.junit.Test;

/**
 * Date: 2015/1/7
 * Time: 11:44
 *
 * @author Tian.Dong
 */
public class StateMachineTest {
  String transitionsStr =
      "fromInitToInQueueOnEnterQueueSuccess\n" +
          "fromInitToClosedOnEnterQueueFailure\n" +
          "fromInQueueToHoldingSeatOnHoldingSeatSuccess\n" +
          "fromInQueueToClosedOnHoldingSeatFailure\n" +
          "fromHoldingSeatToPaySuccessOnPaySuccess\n" +
          "fromHoldingSeatToPayFailureOnPayFailure\n" +
          "fromHoldingSeatToClosedOnCancelByUser\n" +
          "fromHoldingSeatToClosedOnPay12306Timeout\n" +
          "fromPaySuccessToDeliveringOnEnterDeliveringQueue\n" +
          "fromDeliveringToDeliveredOnDeliveredSuccess\n" +
          "fromPayFailureToPaySuccessOnPaySuccess\n" +
          "fromPayFailureToClosedOnCancelByUser\n" +
          "fromPayFailureToClosedOnPay12306Timeout\n" +
          "fromPaySuccessToClosedOnCancelByUser\n" +
          "fromPaySuccessToClosedOnPay12306Timeout\n" +
          "fromDeliveringToClosedOnDeliveredFailure\n" +
          "fromDeliveringToClosedOnPay12306Timeout\n" +
          "fromPayFailureToPayFailureOnPayFailure\n" +
          "fromClosedToClosedOnRefundSuccess\n" +
          "fromClosedToClosedOnRefundFailure\n" +
          "fromDeliveredToDeliveredOnTicketRefundRequest\n" +
          "fromDeliveredToDeliveredOnTicketRefundSuccess\n" +
          "fromDeliveredToDeliveredOnTicketRefundFailure\n" +
          "fromDeliveredToDeliveredOnRefundRequest\n" +
          "fromDeliveredToDeliveredOnRefundSuccess\n" +
          "fromDeliveredToDeliveredOnRefundFailure";

  String[] transitions = transitionsStr.split("\n");

  //@Test
  public void testGenTrans() {
    for (String transition : transitions) {
      //@Transit(from = "Init", to = "InQueue", on = "EnterQueueSuccess", callMethod = "fromInitToInQueueOnEnterQueueSuccess"),
      String format = "@Transit(from = \"%s\", to = \"%s\", on = \"%s\", callMethod = \"%s\"),";
      String from = transition.substring(4, transition.indexOf("To"));
      String to = transition.substring(transition.indexOf("To") + 2, transition.indexOf("On"));
      String on = transition.substring(transition.indexOf("On") + 2);
      String trans = String.format(format, from, to, on, transition);
      System.out.println(trans);
    }
  }

  //@Test
  public void testGenMethod() {
    for (String transition : transitions) {
      String format = "public void %s(TrainState from, TrainState to, TrainEvent event,\n" +
          "      Context context) throws Exception {\n" +
          "    flowExecutor.doFlow(context.getCoreParams().getProductLine(),\n" +
          "        \"%s\", context, to.getName());\n" +
          "\n" +
          "  }";

      String trans = String.format(format, transition, transition);
      System.out.println(trans);
    }
  }

  //@Test
  public void testGenXml() {
    for (int i = 1; i <= transitions.length; i++) {
      String xml =
          "<Transition name=\"%s\" id=\"%s\">\n" +
          "      <Do>\n" +

          "      </Do>\n" +
          "    </Transition>\n\n";
      xml = String.format(xml,transitions[i-1],i);
      System.out.println(xml);
    }
  }
}
