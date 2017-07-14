
package com.hqc.payforparking.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="appId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="payType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="payMethod" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="freeMoney" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="freeTime" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="freeDetail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "appId",
    "key",
    "orderNo",
    "amount",
    "payType",
    "payMethod",
    "freeMoney",
    "freeTime",
    "freeDetail"
})
@XmlRootElement(name = "PayParkingFee2")
public class PayParkingFee2 {

    protected int appId;
    protected String key;
    protected String orderNo;
    protected int amount;
    protected int payType;
    protected int payMethod;
    protected int freeMoney;
    protected int freeTime;
    protected String freeDetail;

    /**
     * 获取appId属性的值。
     * 
     */
    public int getAppId() {
        return appId;
    }

    /**
     * 设置appId属性的值。
     * 
     */
    public void setAppId(int value) {
        this.appId = value;
    }

    /**
     * 获取key属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置key属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * 获取orderNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置orderNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderNo(String value) {
        this.orderNo = value;
    }

    /**
     * 获取amount属性的值。
     * 
     */
    public int getAmount() {
        return amount;
    }

    /**
     * 设置amount属性的值。
     * 
     */
    public void setAmount(int value) {
        this.amount = value;
    }

    /**
     * 获取payType属性的值。
     * 
     */
    public int getPayType() {
        return payType;
    }

    /**
     * 设置payType属性的值。
     * 
     */
    public void setPayType(int value) {
        this.payType = value;
    }

    /**
     * 获取payMethod属性的值。
     * 
     */
    public int getPayMethod() {
        return payMethod;
    }

    /**
     * 设置payMethod属性的值。
     * 
     */
    public void setPayMethod(int value) {
        this.payMethod = value;
    }

    /**
     * 获取freeMoney属性的值。
     * 
     */
    public int getFreeMoney() {
        return freeMoney;
    }

    /**
     * 设置freeMoney属性的值。
     * 
     */
    public void setFreeMoney(int value) {
        this.freeMoney = value;
    }

    /**
     * 获取freeTime属性的值。
     * 
     */
    public int getFreeTime() {
        return freeTime;
    }

    /**
     * 设置freeTime属性的值。
     * 
     */
    public void setFreeTime(int value) {
        this.freeTime = value;
    }

    /**
     * 获取freeDetail属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFreeDetail() {
        return freeDetail;
    }

    /**
     * 设置freeDetail属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFreeDetail(String value) {
        this.freeDetail = value;
    }

}
