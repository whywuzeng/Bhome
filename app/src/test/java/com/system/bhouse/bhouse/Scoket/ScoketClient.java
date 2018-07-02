package com.system.bhouse.bhouse.Scoket;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2018-07-02.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Scoket
 */

public class ScoketClient {

    private Socket mSocket;

    public ScoketClient(Socket mSocket) throws IOException {
        mSocket=new Socket("localhost",7789);

    }

    public void sendEcho(Socket mSocket){

    }
}
