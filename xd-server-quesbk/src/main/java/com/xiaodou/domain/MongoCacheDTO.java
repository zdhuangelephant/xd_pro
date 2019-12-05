package com.xiaodou.domain;

import lombok.Data;

@Data
public class MongoCacheDTO {

    private String key;
    private Object obj;
    
    public MongoCacheDTO() {}
    
    public MongoCacheDTO(String key, Object obj) {
        super();
        this.key = key;
        this.obj = obj;
    }
    
}
