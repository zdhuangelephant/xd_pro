package com.xiaodou.wallet.agent.test;

import com.google.common.collect.Lists;
import com.xiaodou.wallet.agent.service.WalletService;

public class WalletServiceTest {

  // @Test
  public void testCreateAccount() {
    assert WalletService.createAccount("02", "39", Lists.newArrayList(11020001)).isRetOk();
  }

}
