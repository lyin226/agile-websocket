package com.agile.websocket.business.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liuyi
 * @date 2019/6/3
 */

@Data
public class UserDTO implements Serializable {

    private String userId;

    private String username;

}
