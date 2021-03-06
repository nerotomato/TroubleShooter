package com.nerotomato.separate.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nerotomato.separate.entity.OmsOrder;
import com.nerotomato.separate.entity.PageRequest;
import com.nerotomato.separate.entity.PageResult;
import com.nerotomato.separate.mapper.OmsOrderMapper;
import com.nerotomato.separate.service.OmsOrderService;
import com.nerotomato.separate.service.RedisService;
import com.nerotomato.separate.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nero on 2021/5/12.
 */
@Service
public class OmsOrderServiceImpl implements OmsOrderService {

    @Value("${spring.redis.key.orderId}")
    private String REDIS_KEY_ORDER_ID;
    @Value("${spring.redis.key.database}")
    private String REDIS_DATABASE;
    @Autowired
    RedisService redisService;
    @Autowired
    OmsOrderMapper omsOrderMapper;

    @Override
    public int save(OmsOrder order) {
        //order.setOrderId(generateOrderId(order));
        return omsOrderMapper.insertOrder(order);
    }

    @Override
    public int delete(OmsOrder order) {
        return omsOrderMapper.deleteOrderByOrderId(order);
    }

    @Override
    public int update(OmsOrder order) {
        return omsOrderMapper.updateOrderByOrderId(order);
    }

    @Override
    public OmsOrder find(OmsOrder order) {
        return omsOrderMapper.queryOrderByOrderId(order);
    }

    @Override
    public List<OmsOrder> findAll() {
        return omsOrderMapper.queryAllOrders();
    }

    @Override
    public PageResult findByPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(getPageInfo(pageRequest));
    }

    /**
     * 调用分页插件完成分页
     *
     * @param pageRequest
     * @return
     */
    private PageInfo<OmsOrder> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPage();
        int pageSize = pageRequest.getSize();
        PageHelper.startPage(pageNum, pageSize);
        List<OmsOrder> omsOrderList = omsOrderMapper.queryOrdersByPage();
        return new PageInfo<OmsOrder>(omsOrderList);
    }

    /**
     * 1-for循环方式
     * 批量新增订单
     */
    @Override
    public Object saveMultiOrders(int nums, long memberId) {
        int count = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < nums; i++) {
            OmsOrder order = new OmsOrder();
            order.setMemberId(memberId);
            order.setSourceType(2);
            order.setMemberUsername("nero");
            order.setOrderType(0);
            order.setPayType(1);
            order.setCreateTime(LocalDateTime.now());
            order.setUpdateTime(LocalDateTime.now());
            order.setConfirmStatus(0);
            order.setDeleteStatus(0);
            order.setNote("测试订单" + i);
            order.setReceiverCity("Shanghai");
            order.setReceiverName("尼禄");
            order.setReceiverPhone("17621504249");
            order.setReceiverProvince("Shanghai");
            order.setReceiverPostCode("200120");
            order.setReceiverRegion("浦东新区");
            order.setReceiverDetailAddress("浦东新区");
            order.setOrderId(generateOrderId(order));
            omsOrderMapper.insertOrder(order);
            count++;

        }
        long end = System.currentTimeMillis();
        System.out.println(count + " 笔订单共耗时: " + (end - start) + " 毫秒");
        System.out.println(count + " 笔订单共耗时: " + ((end - start) / 1000) + " 秒");
        return count;
    }

    @Override
    public Object batchSaveOrders(int nums, long memberId) {
        int sum = 0;
        int count = 0;
        List<OmsOrder> omsOrders = new ArrayList<>();
        long start = System.currentTimeMillis();
        int times1 = nums / 10000;
        int times2 = nums % 10000;
        for (int i = 0; i < (times1 * 10000); i++) {
            OmsOrder order = new OmsOrder();
            order.setSourceType(2);
            order.setMemberId(memberId);
            order.setMemberUsername("nero");
            order.setOrderType(0);
            order.setPayType(1);
            order.setTotalAmount(new BigDecimal(9999.00));
            order.setCreateTime(LocalDateTime.now());
            order.setUpdateTime(LocalDateTime.now());
            order.setConfirmStatus(0);
            order.setDeleteStatus(0);
            order.setNote("测试订单" + i);
            order.setReceiverCity("Shanghai");
            order.setReceiverName("尼禄");
            order.setReceiverPhone("17621504249");
            order.setReceiverProvince("Shanghai");
            order.setReceiverPostCode("200120");
            order.setReceiverRegion("浦东新区");
            order.setReceiverDetailAddress("浦东新区");
            //order.setOrderId(generateOrderId(order));
            omsOrders.add(order);
            count++;
            //每次只插入10000条数据
            if (count == 10000) {
                int result = omsOrderMapper.batchSaveOrder(omsOrders);
                omsOrders.clear();
                sum += count;
                count = 0;
            }
        }
        omsOrders.clear();
        if (times2 != 0) {
            for (int i = 0; i < times2; i++) {
                OmsOrder order = new OmsOrder();
                order.setSourceType(2);
                order.setMemberId(memberId);
                order.setMemberUsername("nero");
                order.setOrderType(0);
                order.setPayType(1);
                order.setTotalAmount(new BigDecimal(9999.00));
                order.setCreateTime(LocalDateTime.now());
                order.setUpdateTime(LocalDateTime.now());
                order.setConfirmStatus(0);
                order.setDeleteStatus(0);
                order.setNote("测试订单" + i);
                order.setReceiverCity("Shanghai");
                order.setReceiverName("尼禄");
                order.setReceiverPhone("17621504249");
                order.setReceiverProvince("Shanghai");
                order.setReceiverPostCode("200120");
                order.setReceiverRegion("浦东新区");
                order.setReceiverDetailAddress("浦东新区");
                //order.setOrderId(generateOrderId(order));
                omsOrders.add(order);
                count++;
            }
            //集合omsOrders的size大小不能为0,否则批处理执行会报错
            int result = omsOrderMapper.batchSaveOrder(omsOrders);
            sum += count;
        }

        long end = System.currentTimeMillis();
        System.out.println(sum + " 笔订单共耗时: " + (end - start) + " 毫秒");
        System.out.println(sum + " 笔订单共耗时: " + ((end - start) / 1000) + " 秒");
        return "总数 : " + sum;
    }

    /**
     * 生成16位订单号
     * 2位平台号码+用户ID后4位+6位自增ID+时间戳后4位
     */
    private Long generateOrderId(OmsOrder order) {
        long memberId = order.getMemberId();

        StringBuilder sb = new StringBuilder();
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ORDER_ID + date;
        String memberIdStr = String.valueOf(memberId);
        String lastFourNumberMemberId = null;
        if (memberIdStr.length() < 4) {
            lastFourNumberMemberId = String.format("%04d", memberId);
        } else {
            lastFourNumberMemberId = memberIdStr.substring(memberIdStr.length() - 4);
        }

        Long increment = redisService.increment(key, 1);
        String currentTime = String.valueOf(new Date().getTime());
        String lastFourNumberCurrentTime = currentTime.substring(currentTime.length() - 4);

        sb.append(String.format("%02d", order.getSourceType()));
        sb.append(lastFourNumberMemberId);
        String incrementStr = increment.toString();
        if (incrementStr.length() <= 6) {
            sb.append(String.format("%06d", increment));
        } else {
            sb.append(incrementStr);
        }
        sb.append(lastFourNumberCurrentTime);
        return Long.valueOf(sb.toString());
    }

    public static void main(String[] args) {
        long memberId = 12345;
        String memberIdStr = String.valueOf(memberId);
        String lastFourNumberMemberId = null;
        if (memberIdStr.length() < 4) {
            lastFourNumberMemberId = String.format("%06d", memberId);
        } else {
            lastFourNumberMemberId = memberIdStr.substring(memberIdStr.length() - 4);
        }
        System.out.println(lastFourNumberMemberId);
    }
}
