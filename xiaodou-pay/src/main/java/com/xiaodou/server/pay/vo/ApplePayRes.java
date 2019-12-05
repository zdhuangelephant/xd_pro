package com.xiaodou.server.pay.vo;

/**
 * 苹果支付返回参数反序列化bean
 */
public class ApplePayRes {
  private String status;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}


/*
{
  environment = Sandbox;
  receipt =     {
      "adam_id" = 0;
      "app_item_id" = 0;
      "application_version" = 1;
      "bundle_id" = "com.xiaodou.xdquestionBank";
      "download_id" = 0;
      "in_app" =         (
                      {
              "is_trial_period" = false;
              "original_purchase_date" = "2016-03-23 02:56:16 Etc/GMT";
              "original_purchase_date_ms" = 1458701776000;
              "original_purchase_date_pst" = "2016-03-22 19:56:16 America/Los_Angeles";
              "original_transaction_id" = 1000000200954513;
              "product_id" = "com.buy.first";
              "purchase_date" = "2016-03-23 02:56:16 Etc/GMT";
              "purchase_date_ms" = 1458701776000;
              "purchase_date_pst" = "2016-03-22 19:56:16 America/Los_Angeles";
              quantity = 1;
              "transaction_id" = 1000000200954513;
          }
      );
      "original_application_version" = "1.0";
      "original_purchase_date" = "2013-08-01 07:00:00 Etc/GMT";
      "original_purchase_date_ms" = 1375340400000;
      "original_purchase_date_pst" = "2013-08-01 00:00:00 America/Los_Angeles";
      "receipt_creation_date" = "2016-03-23 03:19:10 Etc/GMT";
      "receipt_creation_date_ms" = 1458703150000;
      "receipt_creation_date_pst" = "2016-03-22 20:19:10 America/Los_Angeles";
      "receipt_type" = ProductionSandbox;
      "request_date" = "2016-03-23 03:24:59 Etc/GMT";
      "request_date_ms" = 1458703499862;
      "request_date_pst" = "2016-03-22 20:24:59 America/Los_Angeles";
      "version_external_identifier" = 0;
  };
  status = 0;
}*/