package NSRStructs;

import Constant.Constant;

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
    private int[] historyRecord = new int[Constant.STORE_RECORD_LENGTH];

    public StoreLoad(String ipAddr, int port) {
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

    public void updateHistoryRecord(int value) {
        System.arraycopy(historyRecord,0,historyRecord,1,Constant.STORE_RECORD_LENGTH-1);
        historyRecord[0] = value;
    }

    public int[] getHistoryRecord(){
        return historyRecord;
    }

}
