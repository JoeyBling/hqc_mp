package com.hqc.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 项目类型表
 *
 * @author cxw
 * @date 2017年5月13日
 */
@Data
@Table(name = "mp_project_category")
public class MpProjectCategoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 项目类型
     */
    @Column
    private String categoryName;

    /**
     * 父级分类id
     */
    @Column
    private Long parentId;

    /**
     * 是否系统内置分类1、是 0、否（内置分类不允许删除）
     */
    @Column
    private Integer isSystem;

    /**
     * 上级菜单名字
     */
    @Transient
    private String parentName;

}
