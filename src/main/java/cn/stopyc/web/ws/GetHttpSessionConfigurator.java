package cn.stopyc.web.ws;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
 * @program: tomcat-demo01
 * @description: 获取session
 * @author: stop.yc
 * @create: 2022-04-25 19:28
 **/
public class GetHttpSessionConfigurator extends Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        //将httpSession对象存储到配置对象中
        sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }
}
