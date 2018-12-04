package cn.wsq;

import java.sql.Connection;
import java.sql.DriverManager;

public class HelloTest {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection root = DriverManager.getConnection("jdbc:mysql://148.70.20.2:3306/db_netty?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", "root", "@WSQadmin501");
        System.out.println(root);
        root.close();
    }
}
