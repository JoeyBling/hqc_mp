package com.hqc.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户与角色对应关系
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:28:39
 */
@Table(name = "sys_user_role")
@Data
public class SysAdminRoleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column
    private Long userId;

    /**
     * 角色ID
     */
    @Column
    private Long roleId;

}
