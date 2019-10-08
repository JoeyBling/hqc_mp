package com.hqc.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 景区表
 *
 * @author cxw
 * @date 2017年5月13日
 */
@Data
@Table(name = "mp_scenic_spot")
public class MpScenicSpotEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 景区名称
     */
    @Column
    private String scenicName;

    /**
     * 景区简介
     */
    @Column
    private String about;

    /**
     * 景区缩略图地址
     */
    @Column
    private String thumbUrl;

    /**
     * 景区详细介绍
     */
    @Column
    private String content;

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

}
