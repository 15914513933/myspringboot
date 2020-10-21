package com.chenkj.myspringboot.netty;

import lombok.Data;

/**
 * @Author
 * @Description
 * @Date 2020-09-29 14:49
 */
@Data
public class Session {
    private String userId;
    private String userName;

    public Session(){}
    public Session(String userId,String userName){
        this.userId = userId;
        this.userName = userName;
    }
}
