package com.chenkj.myspringboot.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @Author
 * @Description
 * @Date 2020-09-15 09:57
 */
public class JdbcTest {
    public static void main(String[] args) throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://47.113.122.122:3306/test", "chenkj", "chenkj");
        System.out.println(connection);
    }
}
