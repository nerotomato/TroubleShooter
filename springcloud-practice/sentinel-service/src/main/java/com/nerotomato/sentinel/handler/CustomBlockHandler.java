package com.nerotomato.sentinel.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.nerotomato.sentinel.common.CommonResult;

/**
 * 自定义通用的限流处理逻辑
 *
 * @author nerotomato
 * @create 2021/7/26 下午4:23
 */
public class CustomBlockHandler {
    /**
     * 可以指定 blockHandlerClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
     * */
    public static CommonResult handleException(BlockException exception) {
        return new CommonResult("自定义通用限流信息", 200);
    }
}
