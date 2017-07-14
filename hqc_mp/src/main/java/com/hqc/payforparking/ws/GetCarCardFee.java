
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
 *         &lt;element name="parkId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cardId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ruleId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="chargeFrom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="chargeCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "cardId",
    "ruleId",
    "chargeFrom",
    "chargeCount"
})
@XmlRootElement(name = "GetCarCardFee")
public class GetCarCardFee {

    protected int appId;
    protected String key;
    protected int parkId;
    protected int cardId;
    protected int ruleId;
    protected String chargeFrom;
    protected int chargeCount;

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
     * 获取cardId属性的值。
     * 
     */
    public int getCardId() {
        return cardId;
    }

    /**
     * 设置cardId属性的值。
     * 
     */
    public void setCardId(int value) {
        this.cardId = value;
    }

    /**
     * 获取ruleId属性的值。
     * 
     */
    public int getRuleId() {
        return ruleId;
    }

    /**
     * 设置ruleId属性的值。
     * 
     */
    public void setRuleId(int value) {
        this.ruleId = value;
    }

    /**
     * 获取chargeFrom属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeFrom() {
        return chargeFrom;
    }

    /**
     * 设置chargeFrom属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeFrom(String value) {
        this.chargeFrom = value;
    }

    /**
     * 获取chargeCount属性的值。
     * 
     */
    public int getChargeCount() {
        return chargeCount;
    }

    /**
     * 设置chargeCount属性的值。
     * 
     */
    public void setChargeCount(int value) {
        this.chargeCount = value;
    }

}
