
package com.hqc.payforparking.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="parkId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="plateNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="etcNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ValidFrom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ValidTo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "parkId",
    "plateNo",
    "etcNo",
    "carType",
    "validFrom",
    "validTo"
})
@XmlRootElement(name = "AddCarCardNo")
public class AddCarCardNo {

    protected int appId;
    protected String key;
    protected int parkId;
    protected String plateNo;
    protected String etcNo;
    protected int carType;
    @XmlElement(name = "ValidFrom")
    protected String validFrom;
    @XmlElement(name = "ValidTo")
    protected String validTo;

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
     * 获取parkId属性的值。
     * 
     */
    public int getParkId() {
        return parkId;
    }

    /**
     * 设置parkId属性的值。
     * 
     */
    public void setParkId(int value) {
        this.parkId = value;
    }

    /**
     * 获取plateNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlateNo() {
        return plateNo;
    }

    /**
     * 设置plateNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlateNo(String value) {
        this.plateNo = value;
    }

    /**
     * 获取etcNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEtcNo() {
        return etcNo;
    }

    /**
     * 设置etcNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEtcNo(String value) {
        this.etcNo = value;
    }

    /**
     * 获取carType属性的值。
     * 
     */
    public int getCarType() {
        return carType;
    }

    /**
     * 设置carType属性的值。
     * 
     */
    public void setCarType(int value) {
        this.carType = value;
    }

    /**
     * 获取validFrom属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidFrom() {
        return validFrom;
    }

    /**
     * 设置validFrom属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidFrom(String value) {
        this.validFrom = value;
    }

    /**
     * 获取validTo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidTo() {
        return validTo;
    }

    /**
     * 设置validTo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidTo(String value) {
        this.validTo = value;
    }

}
