package com.test.demo.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestA {
    public static void main(String[] args) throws Exception {   //下面方法有不同的异常，我直接抛出一个大的异常

        //1、导入驱动jar包
        //2、注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        //3、获取数据库的连接对象
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123456");

        //4、定义sql语句
        String sql = "select * from book where id=1";

        //5、获取执行sql语句的对象
        Statement stat = con.createStatement();

        //6、执行sql并接收返回结果
        ResultSet resultSet = stat.executeQuery(sql);

        //7、处理结果
        System.out.println(resultSet);
        while (resultSet.next()){  //循环一次，游标移动一行
            System.out.println("id：" + resultSet.getString(1)); //  获取第一列的数据
            System.out.println("name：" + resultSet.getString("name"));  //获取字段为name的数据
            System.out.println("-------------------");
        }

        //8、释放资源
        stat.close();
        con.close();
    }

}
