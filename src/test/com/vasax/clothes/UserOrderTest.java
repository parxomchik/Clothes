//import AbstractGenericDao;
//import Dish;
//import Order;
//import OrderDish;
//import junit.framework.Assert;
//import org.junit.Test;
//
//import java.util.List;
//
///**
// * Created by root on 07.10.14.
// */
//public class UserOrderTest {
//
//    @Test
//    public void getDishesFromOrder() {
//        AbstractGenericDao<Order> dao = new AbstractGenericDao<>();
//        Order order = dao.selectById(Order.class,1);
//        List<Dish> dishes = order.getDishes();
//        for (Dish dish : dishes)
//            System.out.println(dish);
//        Assert.assertTrue(dishes.size() > 0);
//    }
//
//    @Test
//    public void getOrderDishesFromOrder() {
//        AbstractGenericDao<Order> dao = new AbstractGenericDao<>();
//        Order order = dao.selectById(Order.class,1);
//        List<OrderDish> orderDishes = order.getOrderDishes();
//        for (OrderDish orderDish : orderDishes)
//            System.out.println(orderDish);
//        Assert.assertTrue(orderDishes.size() > 0);
//    }
//
//    @Test
//    public void getDishesFromOrderThrowOrderDishes() {
//        AbstractGenericDao<Order> dao = new AbstractGenericDao<>();
//        Order order = dao.selectById(Order.class, 1);
//        List<OrderDish> orderDishes = order.getOrderDishes();
//        for (OrderDish orderDish : orderDishes)
//            System.out.println(orderDish.getDish());
//        Assert.assertTrue(orderDishes.size() > 0);
//    }
//}
