import com.nerotomato.gateway.NettyHttpServer;
import com.nerotomato.httpserver.BackEndServer01;
import com.nerotomato.httpserver.BackEndServer02;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现路由
 * Created by nero on 2021/4/3.
 */
public class Main {
    public static void main(String[] args) {
        Map<String, Class> map = new HashMap<>();
        map.put("1", BackEndServer01.class);
        map.put("2", BackEndServer02.class);
        map.put("3", NettyHttpServer.class);

        String id = (null == args || args.length == 0) ? "1" : args[0];
        Class clazz = map.get(id);
        if( null == clazz ) {
            System.out.println("No class for id: " + id);
        }

        try {
            Method method = clazz.getDeclaredMethod("main", new Class[]{String[].class});
            method.invoke(null, new Object[]{new String[]{}});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
