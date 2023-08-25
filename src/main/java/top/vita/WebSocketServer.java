package top.vita;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Slf4j
@ServerEndpoint("/webSocket/count")
public class WebSocketServer {
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    // 用来存在线连接数
    public static final CopyOnWriteArraySet<WebSocketServer> webSockets = new CopyOnWriteArraySet<>();
    // 存放用户连接
    public static final Map<String, Session> sessionPool = new HashMap<>();
    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        try {
            this.session = session;
            webSockets.add(this);
            sendOneMessage("用户总数为:" + webSockets.size());
            log.info("websocket消息: 有新的连接，总数为:" + webSockets.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(@PathParam(value = "userId") String userId) {
        webSockets.remove(this);
        sendOneMessage("用户总数为:" + webSockets.size());
        log.info("连接关闭");
    }

    /**
     * 单点消息
     */
    public void sendOneMessage(String message) {
        if (session != null && session.isOpen()) {
            try {
                log.info("websocket单点消息:" + message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
