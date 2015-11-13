//import AbstractGenericDao;
//import Role;
//import User;
//import junit.framework.Assert;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import java.sql.Timestamp;
//import java.util.List;
//
///**
// * Created by root on 07.10.14.
// */
//public class SimpleTests {
//
//    @Test
//    public void simpleRolesSelectAll() {
//        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/app-context.xml");
//        AbstractGenericDao<Role> dao = context.getBean(AbstractGenericDao.class);
//        List<Role> roles = dao.selectAll(Role.class);
//        for (Role role : roles)
//            System.out.println(role);
//        Assert.assertTrue(roles.size() > 0);
//    }
//
//    @Test
//    public void simpleUserSelectAll() {
//        AbstractGenericDao<User> dao = new AbstractGenericDao<>();
//        List<User> users = dao.selectAll(User.class);
//        for (User user : users)
//            System.out.println(user);
//        Assert.assertTrue(users.size() > 0);
//    }
//
//    @Test
//    public void simpleUserUpdate() {
//        AbstractGenericDao<User> dao = new AbstractGenericDao<>();
//        User user = dao.selectById(User.class, 1);
//        String name = user.getName() + 2;
//        user.setName(name);
//        dao.update(user);
//        user = null;
//        user = dao.selectById(User.class, 1);
//        Assert.assertEquals(user.getName(), name);
//    }
//
//    @Test
//    public void simpleUserInsert() {
//        AbstractGenericDao<User> dao = new AbstractGenericDao<>();
//        User user = new User();
//        String name = "Node";
//        user.setName(name);
//        user.setEmail("node@email.com");
//        user.setPhone("7894562");
//        user.setAddress("Some small address");
//        user.setPass("joke");
//        user.setRegDate(new Timestamp(123456));
//        user.setRole(new AbstractGenericDao<Role>().selectById(Role.class, 1));
//        dao.insert(user);
//        User user2 = dao.selectById(User.class, user.getId());
//        Assert.assertEquals(user.getName(), user2.getName());
//    }
//
//    @Test
//    public void AnotherSimpleTest() {
//        AbstractGenericDao<User> dao = new AbstractGenericDao<>();
//        User user = dao.selectById(User.class, 1);
//        Role role = user.getRole();
//        System.out.println(role);
//        Assert.assertNotNull(role);
//    }
//}
