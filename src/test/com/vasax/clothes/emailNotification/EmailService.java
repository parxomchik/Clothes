package com.vasax.clothes.emailNotification;

import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.DocumentType;
import com.hp.gagawa.java.elements.*;
import com.vasax.clothes.entities.Order;
import com.vasax.clothes.entities.OrderItem;
import com.vasax.clothes.entities.User;
import com.vasax.clothes.service.OrderItemService;
import com.vasax.clothes.service.OrderService;
import com.vasax.clothes.service.UserService;
import org.apache.commons.mail.HtmlEmail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vasax on 06.05.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/webapp/WEB-INF/app-context.xml")
public class EmailService {

    @Inject
    OrderService orderService;
    @Inject
    UserService userService;
    @Inject
    OrderItemService orderItemService;

    private String GMAIL_LOGIN = "vasax32zermonmailservice@gmail.com";
    private String GMAIL_PASS = "yoyoyoyoBoom";

    @Test
    public void simpleTest(){
        int orderId = 28;

        String content = generateManagerNotificationByOrderId(orderId);
//        sendEmail(content, Collections.singletonList("h7k5@yandex.ua"));
//
        try {
            File output = new File("test.html");
            PrintWriter out = new PrintWriter(new FileOutputStream(output));
            out.println(content);
            out.close();
        } catch (FileNotFoundException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void sendEmail(String content, List<String> sendTos){
        try {
            // Create the email message
            HtmlEmail email = new HtmlEmail();
            email.setAuthentication(GMAIL_LOGIN, GMAIL_PASS);
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setStartTLSEnabled(true);
            for (String sendTo : sendTos) {
                email.addTo(sendTo, "");
            }
            email.setFrom(GMAIL_LOGIN, "Zermon");
            email.setSubject("Zermon notification");

            // set the html message
            email.setHtmlMsg(content);

            // set the alternative message
            email.setTextMsg("Your email client does not support HTML messages");

            // send the email
            email.send();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private String generateManagerNotificationByOrderId(int orderId){
        Order order = orderService.getOrderById(orderId);
        User user = order.getUser();
        List<OrderItem> orderItems = orderItemService.getOrderItemByOrderId(orderId);

        Document document = new Document(DocumentType.HTMLTransitional);
        document.head.appendChild(new Meta("text/html; charset=utf-8").setHttpEquiv("Content-Type"));

        document.body.appendChild(new Div().appendChild(new H3().appendChild(new Text("У вас новый заказ: #" + orderId)))).
                appendChild(new Div().appendChild(new Text("ФИО: " + user.getName()))).
                appendChild(new Div().appendChild(new Text("Страна: " + user.getCountry()))).
                appendChild(new Div().appendChild(new Text(
                        "Адрес : " + user.getCity() + "; "
                                + user.getStreet() + "; "
                                + user.getHouse() + "; "
                                + user.getApartment()))).
                appendChild(new Div().appendChild(new Text("Телефон: " + user.getPhone()))).
                appendChild(new Div().appendChild(new Text("Email: " + user.getEmail()))).
                appendChild(new Div().appendChild(new Text("Способ доставки: " + order.getDeliveryType()))).
                appendChild(new Div().appendChild(new Text("Способ оплаты: " + order.getPaymentType()))).
                appendChild(new Div().appendChild(new H3().appendChild(new Text("Описание заказа")))).
                appendChild(new Div().appendChild(new Text("Количество предметов: " + orderItems.size()))).
                appendChild(new Div().appendChild(new Text("Сумма заказа: $" + order.getTotal()))).
                appendChild(new Div().appendChild(new H3().appendChild(new Text("Составляющие заказа"))));

        String styleForTableV = "border: 1px solid black; border-collapse: collapse;";
        Table table = new Table().setStyle(styleForTableV);

        for(int row = 0; row < orderItems.size(); row++){
            OrderItem orderItem = orderItems.get(row);
            Tr tr = new Tr().setTitle("Название").setStyle(styleForTableV);
            table.appendChild(tr);
            Td td = new Td().setStyle(styleForTableV);
            tr.appendChild(td);
            td.appendChild(new A("http://zermon-cj5.rhcloud.com/app/item.xhtml?id=" + orderItem.getItem().getId(), new Text(orderItem.getItem().getTitle())));
            td = new Td().setStyle(styleForTableV);
            tr.appendChild(td);
            td.appendChild(new Div()
                    .appendChild(new Text("Цена: " + orderItem.getPrice())).appendChild(new Br())
                    .appendChild(new Text("Количество:" + orderItem.getCount())).appendChild(new Br())
                    .appendChild(new Text("Размер: " + orderItem.getItem().getPackSize())));
        }
        document.body.appendChild(table);

        return document.write();
    }
}
