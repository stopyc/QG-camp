package cn.stopyc.web.ws;


import cn.stopyc.constant.Result;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: qg-engineering-management-system
 * @description:
 * @author: stop.yc
 * @create: 2022-04-24 23:36
 **/

@ServerEndpoint(value = "/webSocket", configurator = GetHttpSessionConfigurator.class)
public class WebSocket {


    /**
     * map集合,key是登录用户,value是建立的websocket
     */
    private static Map<String , WebSocket> clients = new ConcurrentHashMap<>();

    /**
     * websocket建立时带的session
     */
    private Session session;

    /**
     * request的session,用户获取session中的value
     */
    private HttpSession httpSession;

    @OnOpen
    public void onOpen(Session session,EndpointConfig config) {

        //1.记录保存当前的ws的session
        this.session = session;

        //3.通过配置config获取httpsession
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;

        //4.获取httpsession域中存放的username对应的值
        String username = (String) httpSession.getAttribute("username");

        //5.存放到clients中保存
        clients.put(username,this);

    }

    @OnClose
    public void onClose() {

        //1.获取httpsession域中存放的username对应的值
        String username = (String) httpSession.getAttribute("username");

        //2.下线,
        clients.remove(username);

        //3.销毁session
        httpSession.removeAttribute(username);

    }

    @OnMessage
    public void onMessage(String message) {
        //客户端发送的信息
        System.out.println("客户端发送的给后台消息:" + message);

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        //开启ws错误
        System.out.println("错误!");
    }

    /**
     * 这个是处理消息的,可以封装成json的,可以通过名字,获取要发送给谁.
     * @param msgToUsers:要发送给用户的消息
     */
    public static void sendMessage(Result<List<String>> msgToUsers) {

        // 向所有连接websocket的客户端发送消息
        // 也可以修改为对某个客户端发消息,通过msg中的值进行判断

        //1.判断消息结果集中的用户列表,如果为空,表示是系统消息
        if (msgToUsers.getData() == null) {
            //2.全部发送
            for (WebSocket item : clients.values()) {
                try {
                    item.session.getBasicRemote().sendText(msgToUsers.getMsg());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //2.如果用户集合中有数据,表示要发给这些人
        else {
            //虽然不是系统消息,但是没有用户集合个数为0
            if (msgToUsers.getData().size() == 0) {
                return;
            }
            for (String username : clients.keySet()) {
                //3.判断集合中是否包含这个链接的用户
                if (msgToUsers.getData().contains(username)) {
                    try {
                        //4.通过键(姓名)获取ws链接,获取session,发送消息.
                        clients.get(username).session.getBasicRemote().sendText(msgToUsers.getMsg());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
