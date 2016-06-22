package com.xiuman.xinjiankang.pay.alipay;

import java.io.Serializable;

/**
 * @author danding 2014-9-19
 * 名称：AliPayStatus.java
 * 描述：传递在线参数支付结果实体类
 */
public class AliPayStatus implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -9116750101656714056L;
    // 订单状态
    private String OrderStatus;
    // 支付状态
    private String paymentStatus;
    // 订单id
    private String orderId;

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
