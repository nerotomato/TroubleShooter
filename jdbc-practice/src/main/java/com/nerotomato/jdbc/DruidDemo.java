package com.nerotomato.jdbc;

import com.nerotomato.utils.DruidUtil;
import com.nerotomato.utils.HikariUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 使用Druid连接池进行JDBC操作
 * Created by nero on 2021/4/27.
 */
public class DruidDemo {
    private static String insertSql = "INSERT INTO ums_member\n" +
            "(username, password, nickname, telephone, status, create_time, gender, birthday, city, job)\n" +
            "VALUES(?, ?, ?, ?, 1, NOW(), 1, NOW(), 'Shanghai', 'Java Developer')";
    /*private static String insertSql = "INSERT INTO oms_order\n" +
            "(member_id, order_sn, create_time, member_username, total_amount, pay_type, status, order_type, receiver_name, receiver_phone, receiver_post_code, receiver_province, receiver_city, receiver_region, receiver_detail_address, note, confirm_status, delete_status, payment_time, comment_time, modify_time)\n" +
            "VALUES(?, ?, NOW(), ?, ?, 1, 1, 1, '', '', '', '', '', '', '', '', 0, 0, '', '', NOW())";*/

    private static String deleteSql = "delete from ums_member where username like ?";
    private static String updateSql = "update ums_member set password=? where username like ?";
    private static String querySql = "select * from ums_member where username like ?";

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet resultSet = null;
        long start = System.currentTimeMillis();
        System.out.println("开始时间：" + start);
        try {
            conn = DruidUtil.getConnection();
            //开启事务
            DruidUtil.beginTransaction(conn);

            //批处理
            /*pstat = conn.prepareStatement(insertSql);
            for (int i = 0; i < 1000000; i++) {
                pstat.setObject(1, "test" + i);
                pstat.setObject(2, "123456789");
                pstat.setObject(3, "测试用户" + i);
                pstat.setObject(4, "17621504249" + i);
                pstat.addBatch();
            }
            int[] ints = pstat.executeBatch();
            System.out.println(ints.length);*/

            //单条数据插入
            /*pstat.setObject(1, "nero");
            pstat.setObject(2, "nero@123");
            pstat.setObject(3, "尼禄");
            pstat.setObject(4, "17621504249");
            int result = pstat.executeUpdate();
            System.out.println(result);*/

            //删除操作
            pstat = conn.prepareStatement(deleteSql);
            pstat.setObject(1, "test%");
            int result = pstat.executeUpdate();
            System.out.println(result);

            //查询操作
            /*pstat = conn.prepareStatement(querySql);
            pstat.setObject(1, "%test689%");
            resultSet = pstat.executeQuery();
            int count = 0;
            while (resultSet.next()) {
                //System.out.println("username: " + resultSet.getString(2));
                //System.out.println("password: " + resultSet.getString(3));
                count++;
            }
            System.out.println("count:" + count);*/

            //更新操作
            /*pstat = conn.prepareStatement(updateSql);
            pstat.setObject(1, "123456789");
            pstat.setObject(2, "test689%");
            int result = pstat.executeUpdate();
            System.out.println(result);*/

            //提交事务
            DruidUtil.commitTransaction(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            //回滚事务
            DruidUtil.rollBackTransaction(conn);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (pstat != null) {
                    pstat.clearBatch();
                    pstat.close();
                }
                if (conn != null) {
                    conn.close();

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("结束时间：" + end);
        System.out.println("总耗时：" + (end - start));
    }
}
