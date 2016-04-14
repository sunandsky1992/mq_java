package SocketServer;

import Command.CommandServer;
import Command.StoreCommandServer;
import StoreServer.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ss on 16-4-11.
 */
public class StoreSocketServer extends SockerServer{

    public StoreSocketServer(String addr, int port) {
        super(addr, port);
    }

    @Override
    public void listen() {
        ServerSocket server = null;
        try {
            server  = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (isRunning) {
            try {
                assert server != null;
                Socket connection = server.accept();
                Thread thread = new ToCommandServer(connection);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ToCommandServer extends  Thread{
        private Socket connection;

        private CommandServer commandServer;
        ToCommandServer(Socket connection) {
            this.connection = connection;
            this.commandServer = new StoreCommandServer(connection);
        }

        public void run() {
            int num1;
            int num2;
            try {
                InputStream in = connection.getInputStream();
                num1 = in.read();
                num2 = in.read();
                int commandLength = num1<<8+num2;
                byte command[] = new byte[commandLength];
                int res = in.read(command,0,commandLength);
                commandServer.analysisCommand(command);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
