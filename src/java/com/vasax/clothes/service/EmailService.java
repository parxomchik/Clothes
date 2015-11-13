package com.vasax.clothes.service;

import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.DocumentType;
import com.hp.gagawa.java.elements.*;
import com.vasax.clothes.entities.Order;
import com.vasax.clothes.entities.OrderItem;
import com.vasax.clothes.entities.User;
import com.vasax.clothes.entities.enums.EnumConverter;
import com.vasax.clothes.entities.enums.PaymentType;
import com.vasax.clothes.managed.ConstantsBean;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vasax on 06.05.2015.
 */
@Named
public class EmailService {

    @Inject
    private OrderService orderService;
    @Inject
    private UserService userService;
    @Inject
    private OrderItemService orderItemService;
    @Inject
    private ConstantsBean constantsBean;
    @Inject
    private GlobalSettingService globalSettingService;

    private String GMAIL_LOGIN = "vasax32zermonmailservice@gmail.com";
    private String GMAIL_PASS = "yoyoyoyoBoom";

    @Transactional
    public void sendManagerNotificationByOrderId(int orderId, String domain) {
        String content = generateManagerNotificationByOrderId(orderId, domain);
        List<User> activeManagers = userService.getActiveManagers();
        List<String> recipients = new ArrayList<>();
        for (User activeManager : activeManagers) {
            String email = activeManager.getEmail();
            if (isValidEmailAddress(email))
                recipients.add(email);
        }
        if (!recipients.isEmpty())
            sendEmail(content, recipients);
    }

    @Transactional
    public void sendUserNotificationAboutOrderStatusChangedToOrdered(int orderId, String domain) {
        String content = generateUserNotificationAboutOrderStatusChangedToOrderedByOrderId(orderId, domain);
        Order orderById = orderService.getOrderById(orderId);
        if (orderById != null) {
            User user = orderById.getUser();
            if (user != null && isValidEmailAddress(user.getEmail()))
                sendEmail(content, Collections.singletonList(user.getEmail()));
        }
    }

    public void sendResetPasswordEmail(String email, String resetUrl) {
        String content = generateResetPasswordMessage(resetUrl);
        if (isValidEmailAddress(email)) {
            sendEmail(content, Collections.singletonList(email));
        }
    }

    private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private void sendEmail(String content, List<String> sendTos) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateManagerNotificationByOrderId(int orderId, String domain) {
        Order order = orderService.getOrderById(orderId);
        User user = order.getUser();
        List<OrderItem> orderItems = orderItemService.getOrderItemByOrderId(orderId);
        int discount = order.getUser().getDiscount();

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
                appendChild(new Div().appendChild(new Text("Способ доставки: " + EnumConverter.convert(order.getDeliveryType())))).
                appendChild(new Div().appendChild(new Text("Способ оплаты: " + EnumConverter.convert(order.getPaymentType())))).
                appendChild(new Div().appendChild(new H3().appendChild(new Text("Описание заказа")))).
                appendChild(new Div().appendChild(new Text("Количество предметов: " + orderItems.size())));
        if (discount > 0) {
            document.body.appendChild(new Div().appendChild(new Text("Сумма заказа: " + round(order.getTotal() * globalSettingService.requireDouble("exchangeRate"), 2) + " грн "))
                    .appendChild(new Span().appendChild(new Text("(cкидка " + discount + "% - " + round(order.getTotal() * discount / (100 - discount) * globalSettingService.requireDouble("exchangeRate"), 2) + " грн)")).setStyle("color:red;")));
        } else {
            document.body.appendChild(new Div().appendChild(new Text("Сумма заказа: " + round(order.getTotal() * globalSettingService.requireDouble("exchangeRate"), 2) + " грн ")));
        }
        document.body.appendChild(new Div().appendChild(new H3().appendChild(new Text("Составляющие заказа"))));

        String styleForTableV = "border: 1px solid black; border-collapse: collapse;";
        Table table = new Table().setStyle(styleForTableV);

        for (int row = 0; row < orderItems.size(); row++) {
            OrderItem orderItem = orderItems.get(row);
            Tr tr = new Tr().setTitle("Название").setStyle(styleForTableV);
            table.appendChild(tr);
            Td td = new Td().setStyle(styleForTableV);
            tr.appendChild(td);
            td.appendChild(new A(domain + "/" + constantsBean.getPrefix() + "/item.xhtml?id=" + orderItem.getItem().getId(), new Text(orderItem.getItem().getTitle())));
            td.appendChild(new Br()).appendChild(new Text("Арт. "+orderItem.getItem().getProductCode()));
            td = new Td().setStyle(styleForTableV);
            tr.appendChild(td);
            td.appendChild(new Div()
                    .appendChild(new Text("Цена: " + round(orderItem.getPrice() * globalSettingService.requireDouble("exchangeRate"), 2))).appendChild(new Br())
                    .appendChild(new Text("Количество:" + orderItem.getCount())).appendChild(new Br())
                    .appendChild(new Text("Размер: " + orderItem.getItem().getPackSize())));
        }
        document.body.appendChild(table);

        return document.write();
    }

    private String generateUserNotificationAboutOrderStatusChangedToOrderedByOrderId(int orderId, String domain) {
        Order order = orderService.getOrderById(orderId);
        List<OrderItem> orderItems = orderItemService.getOrderItemByOrderId(orderId);

        Document document = new Document(DocumentType.HTMLTransitional);
        document.head.appendChild(new Meta("text/html; charset=utf-8").setHttpEquiv("Content-Type"));


        document.body.appendChild(new Div().appendChild(new P().appendChild(new Text("Здравствуйте," + order.getUser().getName() + "!")))
                .appendChild(new H2().appendChild(new Text("Ваш заказ: #" + orderId + " подтвержден."))));

        String boldStyle = "font-weight: bold;";
        Table infoTable = new Table().setBorder("0");
        Tr tr1 = new Tr();
        Td td11 = new Td().setStyle(boldStyle).appendChild(new Text("Имя: "));
        Td td12 = new Td().appendChild(new Text(order.getUser().getName()));
        tr1.appendChild(td11);
        tr1.appendChild(td12);
        //
        Tr tr2 = new Tr();
        Td td21 = new Td().setStyle(boldStyle).appendChild(new Text("Телефон: "));
        Td td22 = new Td().appendChild(new Text(order.getUser().getPhone()));
        tr2.appendChild(td21);
        tr2.appendChild(td22);
        //
        Tr tr3 = new Tr();
        Td td31 = new Td().setStyle(boldStyle).appendChild(new Text("Адрес: "));
        Td td32 = new Td().appendChild(new Text(order.getAddressOrStorageNum()));
        tr3.appendChild(td31);
        tr3.appendChild(td32);
        //
        Tr tr4 = new Tr();
        Td td41 = new Td().setStyle(boldStyle).appendChild(new Text("Способ доставки: "));
        Td td42 = new Td().appendChild(new Text(EnumConverter.convert(order.getDeliveryType())));
        tr4.appendChild(td41);
        tr4.appendChild(td42);
        //
        Tr tr5 = new Tr();
        Td td51 = new Td().setStyle(boldStyle).appendChild(new Text("Способ оплаты: "));
        Td td52 = new Td();
        if (order.getPaymentType() == PaymentType.privateBank) {
            td52.appendChild(new Text(EnumConverter.convert(order.getPaymentType()) + "<br/>"
                    + "Карта: <b>" + globalSettingService.require("privatBankCard") + "</b><br/>"));
            td52.appendChild(new Text(globalSettingService.require("privatBankEmailText")));
        } else {
            td52.appendChild(new Text(EnumConverter.convert(order.getPaymentType())));
        }

        tr5.appendChild(td51);
        tr5.appendChild(td52);
        //
        Tr tr6 = new Tr();
        Td td61 = new Td().setStyle(boldStyle).appendChild(new Text("Дата: "));
        Td td62 = new Td().appendChild(new Text(new SimpleDateFormat("dd.MM.yyyy hh:mm").format(Calendar.getInstance().getTime())));
        tr6.appendChild(td61);
        tr6.appendChild(td62);

        infoTable.appendChild(tr1).appendChild(tr2).appendChild(tr3).appendChild(tr4).appendChild(tr5).appendChild(tr6);
        document.body.appendChild(infoTable);

        document.body.appendChild(new Br());
        document.body.appendChild(new Div().appendChild(new H3().appendChild(new Text("Составляющие заказа"))));
        Div itemsHeaderBlock = new Div().setStyle("width:600px; font-size: 1.4em;");
        Div d0 = new Div().setStyle("width:10%; float:left;");
        d0.appendChild(new Text("Арт."));
        Div d1 = new Div().setStyle("width:30%; float:left;");
        d1.appendChild(new Text("Название"));
        Div d2 = new Div().setStyle("width:37%; float:left;");
        d2.appendChild(new Text("Размеры"));
        Div d3 = new Div().setStyle("width:8%; float:left; ");
        d3.appendChild(new Text("Кол."));
        Div d4 = new Div().setStyle("width:15%; float:right; text-align:center;");
        d4.appendChild(new Text("Цена"));
        itemsHeaderBlock.appendChild(d0);
        itemsHeaderBlock.appendChild(d1);
        itemsHeaderBlock.appendChild(d2);
        itemsHeaderBlock.appendChild(d3);
        itemsHeaderBlock.appendChild(d4);
        itemsHeaderBlock.appendChild(new Hr().setStyle("clear:both;"));
        document.body.appendChild(itemsHeaderBlock);

        for (int row = 0; row < orderItems.size(); row++) {
            OrderItem orderItem = orderItems.get(row);
            Div itemsBlock = new Div().setStyle("width:600px; font-size: 1.3em;");
            Div div0 = new Div().setStyle("width:10%; float:left; padding:10px 0px;");
            div0.appendChild(new Text(orderItem.getItem().getProductCode()));
            Div div1 = new Div().setStyle("width:30%; float:left; font-weight: bold; padding:10px 0px;");
            div1.appendChild(new A(domain + "/" + constantsBean.getPrefix() + "/item.xhtml?id=" + orderItem.getItem().getId(), new Text(orderItem.getItem().getTitle())));
            Div div2 = new Div().setStyle("width:37%; float:left; padding:10px 0px;");
            div2.appendChild(new Text(orderItem.getItem().getPackSize()));
            Div div3 = new Div().setStyle("width:8%; float:left; padding:10px 0px;");
            div3.appendChild(new Text(orderItem.getCount()));
            Div div4 = new Div().setStyle("width:15%; float:right; font-weight: bold; padding:10px 0px; text-align:right;");
            div4.appendChild(new Text(round(orderItem.getPrice() * globalSettingService.requireDouble("exchangeRate"), 2) + " грн"));

            itemsBlock.appendChild(div0);
            itemsBlock.appendChild(div1);
            itemsBlock.appendChild(div2);
            itemsBlock.appendChild(div3);
            itemsBlock.appendChild(div4);
            itemsBlock.appendChild(new Hr().setStyle("clear:both;"));
            document.body.appendChild(itemsBlock);

        }
        int discount = order.getUser().getDiscount();
        if (discount > 0) {
            document.body.appendChild(new Div().setStyle("width: 600px;").appendChild(new Span().appendChild(new Text("Скидка: " + discount + "% - " + round(order.getTotal() * discount / (100 - discount) * globalSettingService.requireDouble("exchangeRate"), 2) + " грн")).setStyle("float:right; color:red;")));
            document.body.appendChild(new Div().setStyle("clear:both"));
        }
        document.body.appendChild(new Div().setStyle("width: 600px;").appendChild(new H2().appendChild(new Text("Итого: " + round(order.getTotal() * globalSettingService.requireDouble("exchangeRate"), 2) + " грн")).setStyle("float:right;")));

        return document.write();
    }

    private String generateResetPasswordMessage(String resetUrl) {
        Document document = new Document(DocumentType.HTMLTransitional);
        document.head.appendChild(new Meta("text/html; charset=utf-8").setHttpEquiv("Content-Type"));
        document.body.appendChild(new Div().appendChild(new H4().appendChild(new Text("Уважаемый пользователь, чтобы изменить пароль перейдите по ссылке:")))
                .appendChild(new Br()).appendChild(new Text("<a href=\"" + resetUrl + "\">" + resetUrl + "</a>")).appendChild(new Br()).appendChild(new H4()
                        .appendChild(new Text("Ссылка действительна 1 час."))));
        return document.write();
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
