package com.hqc.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 景点表
 *
 * @author cxw
 * @date 2017年5月13日
 */
@Data
@Table(name = "mp_scenery_spot")
public class MpScenerySpotEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 景点名称
     */
    @Column
    private String sceneryName;

    /**
     * 所属景区id（关联表mp_scenic_spot表字段Id）
     */
    @Column
    private Long scenicId;

    /**
     * 景点缩略图地址
     */
    @Column
    private String thumbUrl;

    /**
     * 景点简介
     */
    @Column
    private String about;

    /**
     * 景点详细介绍
     */
    @Column
    private String sceneryContent;

    /**
     * 创建时间
     */
    @Column
    private Long createTime;

    /**
     * 更新时间
     */
    @Column
    private Long updateTime;

    /**
     * 所属景区
     */
    @Transient
    private MpScenicSpotEntity mpScenicSpotEntity;

}
