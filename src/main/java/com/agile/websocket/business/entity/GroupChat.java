package com.agile.websocket.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 群聊表
 * </p>
 *
 * @author liuyi
 * @since 2019-05-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GroupChat extends Model<GroupChat> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 群聊名称
     */
    private String groupName;

    /**
     * 群主id
     */
    private Long groupUserId;

    /**
     * 群内用户
     */
    private String otherId;

    /**
     * 用户是否删除
     */
    private Integer isDeleteUser;

    /**
     * 群内用户是否删除
     */
    private Integer isDeleteOther;

    /**
     * 创建时间毫秒用于排序
     */
    private Long createTime;


}
