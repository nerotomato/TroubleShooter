package filter;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * Created by nero on 2021/4/5.
 */
public class MyHttpResponseFilter implements HttpResponseFilter{
    @Override
    public void filter(FullHttpResponse response) {
        response.headers().set("finalStatus","200");
    }
}
