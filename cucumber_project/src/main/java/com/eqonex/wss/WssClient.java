package com.eqonex.wss;

import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WssClient extends WebSocketClient {
//    private static final String WSSHOST = "wss://trading-api.dexp-uat.com/wsapi";
    private static final String WSSHOST = "wss://eqo-uat.com/wsapi";

    private static final long MIN_WSS_TIMEOUT=1100;
    public static final long MAX_WSS_TIMEOUT=2000;
    public static final long WSS_CONNECTION_TIMEOUT=120000;
    public static final long UNTIL_WSS_TIMEOUT=1000;
    //    private final LinkedBlockingDeque<WssPong> pongs;
//    private final LinkedBlockingDeque<WssPong> tmpPongs;
//    private final LinkedBlockingDeque<WssPong> logPongs;
    private boolean pongReceived;
    private boolean isWssConnected;
    private String subcriptionMessage;
    private String unSubcriptionMessage;
    private List<String> msgList = new ArrayList<String>();

//    String positioinSubcripti
//    = "{\"requestId\":\"R1657266133187\",\"types\":[1],\"symbols\":[\"BTC/USDC\"],\"event\":\"S\"}";
//    String positioinUnSubcriptionMessage = "{\"requestId\":\"R1657266133187\",\"types\":[1],\"symbols\":[\"BTC/USDC\"],\"event\":\"U\"}";

    public WssClient(String subcriptionMessage, String unSubcriptionMessage) {
        super(URI.create(WSSHOST));
//        this.pongs = new LinkedBlockingDeque<>();
//        this.tmpPongs = new LinkedBlockingDeque<>();
//        this.logPongs = new LinkedBlockingDeque<>();
        this.subcriptionMessage = subcriptionMessage;
        this.unSubcriptionMessage = unSubcriptionMessage;
        this.isWssConnected = false;
    }

    //When websocket open connection
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        subscribe();
    }

    public void subscribe(){
        send(this.subcriptionMessage);
    }


    //listener for message from websocket
    @Override
    public void onMessage(String message) {
        addMessage(message);
        msgList = addMsgtoList(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // The codecodes are documented in class org.java_websocket.framing.CloseFrame
        System.out.println(
                "Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: "
                        + reason + " wss channel: "+this.subcriptionMessage);
    }

    @Override
    public void onError(Exception ex) {
        // if the error is fatal then onClose will be called additionally
        throw new RuntimeException(Arrays.toString(ex.getStackTrace()));
    }

    public void openConnection(){
        this.connect();
    }

    private void unSubscribe(){
        send(unSubcriptionMessage);
    }


    public void openAndTillConnected(){
        connect();
        try{
            Awaitility.await().timeout(WSS_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS).until(() -> this.isWssConnected);
        }catch (ConditionTimeoutException exception){
            throw new RuntimeException(String.format("Connection to [%s] with [%s] is not opened after [%s]ms", WSSHOST, this.subcriptionMessage,WSS_CONNECTION_TIMEOUT));
        }
    }

    private void addMessage(String rawStr){
        if(!rawStr.contains("\"errorMessage\":")&&!rawStr.contains("heartbeat")){
            System.out.println("received: " + rawStr);
            pongReceived = true;
            isWssConnected=true;
//            if(pongs.size()>10){
//                pongs.removeLast();
//                tmpPongs.removeLast();
//            }
        }
    }

    private List<String> addMsgtoList(String rawMsg){
        if(!msgList.contains(rawMsg)){
            msgList.add(rawMsg);
        }
//        System.out.println("msgList: " + msgList);
        return msgList;
    }

    public JSONArray getMessageThatContain(String value) {
        JSONArray ordMsg = new JSONArray();
        try {
            Awaitility.await().timeout(WSS_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS).until(() -> this.msgList.stream().anyMatch(it -> it.contains(value)));
            for (String msg : this.msgList) {
                if(msg.contains(value)){
                    JSONObject jMsg = new JSONObject(msg);
                    ordMsg.put(jMsg);
                }
            }
        } catch (ConditionTimeoutException exception) {
            throw new RuntimeException(String.format("Wss to [%s] with [%s] is not returned expected value the after [%s]ms", WSSHOST, "this.subcriptionMessage", WSS_CONNECTION_TIMEOUT));}
        return ordMsg;
    }

    public List<String> getMessageContain(String value) {
        List<String> ordMsg = new ArrayList<>();
        try {
            Awaitility.await().timeout(WSS_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS).until(() -> this.msgList.stream().anyMatch(it -> it.contains(value)));
            for (String msg : this.msgList) {
                if(msg.contains(value)){
                    ordMsg.add(msg);
                }
            }
        } catch (ConditionTimeoutException exception) {
            throw new RuntimeException(String.format("Wss to [%s] with [%s] is not returned expected value the after [%s]ms", WSSHOST, "this.subcriptionMessage", WSS_CONNECTION_TIMEOUT));}
        return ordMsg;
    }

    private void pingTilPong(String pingMessage){
        try{
            pongReceived = false;
            send(pingMessage);
            Awaitility.await().timeout(WSS_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS).until(() -> this.pongReceived);
        }catch (ConditionTimeoutException ex){
            throw new RuntimeException(String.format("wss [%s] PING [%s] to but no PONG received | ERROR: [%s]",this.subcriptionMessage , pingMessage,ex.getCause()));
        }
    }

    public void dispose(){
        this.close();
    }

    public String messagesToString(){
        StringBuilder builder = new StringBuilder();
//        pongs.forEach(builder::append);
        return builder.toString();
    }

}
