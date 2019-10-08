package com.hqc.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 商品分类表
 *
 * @author cxw
 * @date 2017年5月13日
 */
@Data
@Table(name = "mp_goods_category")
public class MpGoodsCategoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品分类名称
     */
    @Column
    private String categoryName;

    /**
     * 父ID，暂时不使用
     */
    @Column
    private Long parentId;

    /**
     * 描述
     */
    @Column
    private String about;

}
