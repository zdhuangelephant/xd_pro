package com.xiaodou.dao;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class ForumService1 {

    TransactionTemplate template;
    
    public void addForum(Integer a) {
        
        template.execute(new TransactionCallbackWithoutResult() {
            
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                // TODO Auto-generated method stub
                //需要在事务环境中执行的代码
                
            }
        });
        
        
    }
}
