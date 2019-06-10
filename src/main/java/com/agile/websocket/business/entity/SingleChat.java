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
 * 单聊表
 * </p>
 *
 * @author liuyi
 * @since 2019-05-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SingleChat extends Model<SingleChat> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 对方用户id
     */
    private Long otherUserId;

    /**
     * 用户是否删除
     */
    private Integer isDeleteUser;

    /**
     * 对方用户是否删除
     */
    private Integer isDeleteOther;

    /**
     * 创建时间
     */
    private Long createTime;


}
