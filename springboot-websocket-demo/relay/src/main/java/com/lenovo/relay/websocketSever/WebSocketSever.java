package com.lenovo.relay.websocketSever;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lenovo.relay.entity.Command;
import com.lenovo.relay.entity.RelayCommand;
import com.lenovo.relay.entity.RelayRequest;
import com.lenovo.relay.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;


@Component
@ServerEndpoint("/websocket/{guid}")
public class WebSocketSever {


    private  String guid;
    private  Session session;

    private static Map<String, Session> machineSessionMap = new HashMap<>();
    private static Map<String, Session> sessionMap = new HashMap<>();


    private static  ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        WebSocketSever.objectMapper = objectMapper;
    }

    @OnOpen
    public void open(@PathParam("guid") String guid, Session session){
        if (guid.startsWith("_VAN")) {
            machineSessionMap.put(guid, session);
        } else {
            sessionMap.put(guid, session);
        }
        System.out.println("guid: " + guid + "connect");
    }

    @OnClose
    public void close(){
        sessionMap.remove(guid);
        System.out.println("guid: " + "close..");
    }

    @OnError
    public void error(Throwable ex){
        ex.printStackTrace();
        System.out.println("guid: " + guid + "error produce..");
    }

    /*
        单一消息发送
     */
    @OnMessage
    public void one2One(String commandMessage) throws JsonProcessingException {

        // 创建
        Command command = objectMapper.readValue(commandMessage, Command.class);

        if (command.getRelayCommand() == null) {
            resultTofront(command.getResult());
        } else {
            sendRequestTobackend(command.getRelayCommand());
        }

    }


    public void sendRequestTobackend(RelayCommand relayCommand){
        RelayRequest relayRequest = new RelayRequest();
        relayRequest.setRelayCommand(relayCommand);
        // size 是1 ,目前遍历
        machineSessionMap.forEach((k ,v ) -> {
            try{
                relayRequest.setToguid(k);
                v.getAsyncRemote().sendText(objectMapper.writeValueAsString(relayRequest));
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    /*
        结果执行返回得时候
     */
    public void resultTofront(Result result) throws JsonProcessingException {
        if (machineSessionMap.containsKey(result.getToGuid())) {

            machineSessionMap.get(result.getToGuid()).getAsyncRemote().sendText(objectMapper.writeValueAsString(result));
        }

    }
}
