package com.hqc.payforparking.ws;

/**
 * 停车付费查询返回结果
 * 
 * @author cxw
 * @date 2017年6月1日
 */
public class ParkingInfo {

	/**
	 * 车场Id
	 */
	private Integer parkId;

	/**
	 * 车场名
	 */
	private String parkName;

	/**
	 * 账单号
	 */
	private String orderNo;

	/**
	 * 入场时间
	 */
	private String entryTime;

	/**
	 * 停车时长（分钟）
	 */
	private Integer elapsedTime;

	/**
	 * 停车入场图片
	 */
	private String imgName;

	/**
	 * 付款(查询费用)时间
	 */
	private String payTime;

	/**
	 * 应付金额（减去优惠金额）,单位为分
	 */
	private Integer payable;

	/**
	 * 收费后允许延时出场的时间限制（分钟），默认：10分钟
	 */
	private Integer delayTime;

	public Integer getParkId() {
		return parkId;
	}

	public void setParkId(Integer parkId) {
		this.parkId = parkId;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

	public Integer getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Integer elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public Integer getPayable() {
		return payable;
	}

	public void setPayable(Integer payable) {
		this.payable = payable;
	}

	public Integer getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(Integer delayTime) {
		this.delayTime = delayTime;
	}

}
