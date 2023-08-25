package top.vita;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author vita
 * @Date 2023/8/25 23:13
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private WebSocketServer webSocketServer;

    @GetMapping
    public void test() {
        CopyOnWriteArraySet<WebSocketServer> webSockets = WebSocketServer.webSockets;
        for (WebSocketServer webSocket : webSockets) {
            webSocket.sendOneMessage(webSockets.size() + "");
        }
    }
}
