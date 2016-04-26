package NSR.Structs;

/**
 * Created by ss on 16-4-14.
 */
public class StoreLoad {
    private String storeId;
    private String ipAddr;
    private int port;

    private double cpuUsed;
    private double memoryUsed;
    private double networdWidthUsed;

    StoreLoad(String ipAddr, int port) {
        this.ipAddr = ipAddr;
        this.port = port;
        this.storeId = ipAddr+":"+port;
    }


    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public double getCpuUsed() {
        return cpuUsed;
    }

    public void setCpuUsed(double cpuUsed) {
        this.cpuUsed = cpuUsed;
    }

    public double getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(double memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public double getNetwordWidthUsed() {
        return networdWidthUsed;
    }

    public void setNetwordWidthUsed(double networdWidthUsed) {
        this.networdWidthUsed = networdWidthUsed;
    }
}
