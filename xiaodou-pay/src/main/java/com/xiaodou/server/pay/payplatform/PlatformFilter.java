package com.xiaodou.server.pay.payplatform;

import com.xiaodou.server.pay.payplatform.ca.CaPayPlatform;
import com.xiaodou.server.pay.payplatform.dc.IDcPayPlatform;


public class PlatformFilter {

  private CaPayPlatform caPlatform;

  private IDcPayPlatform dcPlatform;

  public CaPayPlatform getCaPlatform() {
    return caPlatform;
  }

  public void setCaPlatform(CaPayPlatform caPlatform) {
    this.caPlatform = caPlatform;
  }

  public IDcPayPlatform getDcPlatform() {
    return dcPlatform;
  }

  public void setDcPlatform(IDcPayPlatform dcPlatform) {
    this.dcPlatform = dcPlatform;
  }

}
