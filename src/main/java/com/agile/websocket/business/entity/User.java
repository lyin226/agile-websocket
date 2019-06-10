package com.agile.websocket.business.entity;

import com.agile.websocket.business.dto.GroupDTO;
import com.agile.websocket.business.dto.UserDTO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author liuyi
 * @since 2019-05-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User extends Model<User> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 头像
     */
    @TableField(exist = false)
    private String avatarUrl;

    /**
     * 好友列表
     */
    @TableField(exist = false)
    private List<UserDTO> friendList;

    /**
     * 群组列表
     */
    @TableField(exist = false)
    private List<GroupDTO> groupList;


}
