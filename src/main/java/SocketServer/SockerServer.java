package SocketServer;

import Command.CommandServer;

/**
 * Created by ss on 16-4-6.
 */
public abstract class SockerServer {
    private String addr;
    private int port;
    private CommandServer commandServer;

    public SockerServer(String addr, int port, CommandServer commandServer) {
        this.addr = addr;
        this.port = port;
        this.commandServer = commandServer;
    }

    public abstract void listen();
}
