package SocketServer;

import Command.CommandServer;

/**
 * Created by ss on 16-4-6.
 */
public abstract class SockerServer {
    protected String addr;
    protected int port;
    protected boolean isRunning;

    public SockerServer(String addr, int port) {
        this.addr = addr;
        this.port = port;
        isRunning = true;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddr() {
        return this.addr;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean getIsRunning() {
        return isRunning;
    }

    public abstract void listen();
}
