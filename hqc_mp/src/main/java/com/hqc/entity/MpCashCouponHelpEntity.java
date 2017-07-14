package com.hqc.entity;

import lombok.Data;

/**
 * 代金卷(账单)显示帮助类（微信端）
 * 
 * @author cxw
 * @date 2017年6月12日
 */
@Data
public class MpCashCouponHelpEntity {
	
	/**
	 * 账单ID（用于标记）
	 */
	private Long id;
	
	/**
	 * 代金卷名称
	 */
	private String cashCouponName;
	
	/**
	 * 代金卷价值
	 */
	private Double faceValue;
	
	/**
	 * 创建时间（加上2592000就是过期时间）
	 */
	private Long createTime;
	
	
}
