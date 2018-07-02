package com.system.bhouse.bhouse.Scoket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2018-07-02.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Scoket
 */

public class EchoServer {

    private final ServerSocket mServerSocket;

    public EchoServer(int port) throws IOException {
        mServerSocket= new ServerSocket(port);
    }

    public void  run() throws IOException {
        Socket accept = mServerSocket.accept();
        handlerSocket(accept);
    }

    private void handlerSocket(Socket accept) throws IOException {
        //这里处理
        InputStream in = accept.getInputStream();
        OutputStream out = accept.getOutputStream();
        byte[] bytes = new byte[1024];
        int n;
         while ((n=in.read(bytes))>0){
            out.write(bytes,0,n);
         }
    }
}
