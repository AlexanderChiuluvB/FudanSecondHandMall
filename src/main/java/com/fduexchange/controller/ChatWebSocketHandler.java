package com.fduexchange.controller;

import com.fduexchange.utils.SaveSession;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final static List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<WebSocketSession>());

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {

        try {
            String m = message.getPayload();
            String[] parts = m.split(",");
            String phone = parts[0];
            long time = Long.parseLong(parts[1]);
            String action = parts[2];
            if (action.equals("start")){
                session.sendMessage(new TextMessage("success"));
                SaveSession.getInstance().save(phone,time);
                return;
            }
            boolean b = SaveSession.getInstance().isHave(phone,time);
            if (b) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage("error"));
                }
            } else {
                if (session.isOpen())
                    session.sendMessage(new TextMessage("success"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                session.sendMessage(new TextMessage("error"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        sessions.remove(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        sessions.remove(session);
    }

}