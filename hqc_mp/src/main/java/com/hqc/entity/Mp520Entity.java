package com.hqc.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 东部华侨城520活动记录表
 *
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月17日
 */
@Data
@Table(name = "mp_520")
public class Mp520Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 公众号标识openid 女
     */
    @Column
    private String womanOpenId;

    /**
     * 公众号标识openid 男
     */
    @Column
    private String manOpenId;

    /**
     * 生成的唯一数字标识
     */
    @Column
    private Long randomNumber;

    /**
     * 昵称男
     */
    @Column
    private String manNickName;
    /**
     * 昵称女
     */
    @Column
    private String womanNickName;

    /**
     * 创建时间
     */
    @Column
    private Long createTime;

}
