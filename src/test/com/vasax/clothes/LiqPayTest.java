package com.vasax.clothes;

import com.liqpay.LiqPay;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vasax32 on 31.03.15.
 */
public class LiqPayTest {

    private String PUBLIC_KEY = "i28696871532";
    private String PRIVATE_KEY = "y18Gbz5yF7c0QHbgug70sLka10tx5mAfxIUVw3Ec";

    @Test
    public void simpleTest(){
        Map params = new HashMap();
        params.put("amount", "1.50");
        params.put("currency", "USD");
        params.put("description", "description text");
        params.put("order_id", "156");
        params.put("sandbox", "1"); // enable the testing environment and card will NOT charged. If not set will be used property isCnbSandbox()
        LiqPay liqpay = new LiqPay(PUBLIC_KEY, PRIVATE_KEY);
        String html = liqpay.cnb_form(params);
        System.out.println(html);
    }

    @Test
    public void statusCheck() throws Exception {
        HashMap params = new HashMap();
        params.put("version", "3");
        params.put("order_id", "156");

        LiqPay liqpay = new LiqPay(PUBLIC_KEY, PRIVATE_KEY);
        Map<String, Object> res = liqpay.api("payment/status", params);
        System.out.println(res.get("result"));
    }
}
