package SocketServer;

import Command.CommandServer;
import Command.NSRCommandServer;
import Command.StoreCommandServer;
import Constant.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ss on 16-4-6.
 */
public  class SocketServer {
    private String addr;
    private int port;
    private boolean isRunning;
    private String serverName;
    private String commandServerType;
    public SocketServer(String addr, int port,String commandServerType, String serverName) {
        this.addr = addr;
        this.port = port;
        isRunning = true;
        this.serverName = serverName;
        this.commandServerType = commandServerType;
    }

    public String getAddr() {
        return this.addr;
    }

    public int getPort() {
        return this.port;
    }

    public boolean getIsRunning() {
        return isRunning;
    }

    public String getServerName() {
        return serverName;
    }

    public String getCommandServerType() {
        return commandServerType;
    }

    public CommandServer getCommandServerByType(String type) {
        if (type.equals(Constant.STORE_COMMAND_SERVER)) {
            return new StoreCommandServer();
        }
        if (type.equals(Constant.NSR_COMMAND_SERVER)) {
            return new NSRCommandServer();
        }
        if (type.equals(Constant.FRONT_COMMAND_SERVER)) {
            return new StoreCommandServer();
        }
        return null;
    }

    public void listen() {
        System.out.println("listen begin");
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
                Thread thread = new ToCommandServer(connection,getCommandServerByType(commandServerType));
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static class ToCommandServer extends  Thread{
        private Socket connection;

        private CommandServer commandServer;
        ToCommandServer(Socket connection,CommandServer commandServer) {
            this.connection = connection;
            this.commandServer = commandServer;
        }

        public void run() {
            try {
                InputStream in = connection.getInputStream();
                byte[] firstTwoByte = new byte[Constant.TOTAL_LENGTH];

                while (in.read(firstTwoByte, 0, 2)==2) {
                    int commandLength = (firstTwoByte[0] << 8) + firstTwoByte[1];
                    System.out.println("commandLength: " + commandLength);
                    byte command[] = new byte[commandLength];
                    int res = in.read(command, 0, commandLength-Constant.TOTAL_LENGTH);
                    commandServer.analysisCommand(command);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}