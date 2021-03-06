package com.nerotomato.jdbc;

import com.nerotomato.utils.HikariUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * 使用HikariCP连接池进行JDBC操作
 * Created by nero on 2021/4/21.
 */
public class HikariCPDemo {
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
            conn = HikariUtil.getConnection();
            //开启事务
            HikariUtil.beginTransaction(conn);

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
            pstat.setObject(1, "test689%");
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
            HikariUtil.commitTransaction(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            //回滚事务
            HikariUtil.rollBackTransaction(conn);
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

    private static String getRandomTelNum() {
        String[] startNums = {"133", "149", "153", "173", "177",
                "180", "181", "189", "199", "130", "131", "132",
                "145", "155", "156", "166", "171", "175", "176", "185", "186", "166", "134", "135",
                "136", "137", "138", "139", "147", "150", "151", "152", "157", "158", "159", "172",
                "178", "182", "183", "184", "187", "188", "198", "170", "171"};
        //随机出真实号段   使用数组的length属性，获得数组长度，
        //通过Math.random（）*数组长度获得数组下标，从而随机出前三位的号段
        int firstIndex = (int) (Math.random() * startNums.length);
        String telFirstThreeNum = startNums[firstIndex];
        //随机出剩下的8位数
        String telLastNum = "";
        //定义尾号，尾号是8位
        final int telNumLength = 8;
        //循环剩下的位数
        for (int i = 0; i < telNumLength; i++) {
            //每次循环都从0~9挑选一个随机数
            telLastNum += (int) (Math.random() * 10);
        }
        //最终将号段和尾数连接起来
        String phoneNum = telFirstThreeNum + telLastNum;

        return phoneNum;
    }
}
