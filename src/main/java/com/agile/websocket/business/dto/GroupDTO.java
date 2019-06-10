package com.agile.websocket.business.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liuyi
 * @date 2019/6/3
 */

@Data
public class GroupDTO implements Serializable {

    private Long groupId;

    private String groupName;

}
