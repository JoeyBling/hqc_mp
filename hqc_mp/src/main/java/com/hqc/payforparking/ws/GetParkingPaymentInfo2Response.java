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
 *         &lt;element name="GetParkingPaymentInfo2Result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getParkingPaymentInfo2Result"
})
@XmlRootElement(name = "GetParkingPaymentInfo2Response")
public class GetParkingPaymentInfo2Response {

    @XmlElement(name = "GetParkingPaymentInfo2Result")
    protected String getParkingPaymentInfo2Result;

    /**
     * 获取getParkingPaymentInfo2Result属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetParkingPaymentInfo2Result() {
        return getParkingPaymentInfo2Result;
    }

    /**
     * 设置getParkingPaymentInfo2Result属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetParkingPaymentInfo2Result(String value) {
        this.getParkingPaymentInfo2Result = value;
    }

}
