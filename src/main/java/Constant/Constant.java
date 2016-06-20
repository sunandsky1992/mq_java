package Constant;

/**
 * Created by ss on 16-4-7.
 */
public class Constant {
    //各种协议长度
    public static final int TOTAL_LENGTH = 2;
    public static final int QUEUE_NUMBER = 1;
    public static final int QUEUE_NAME_LENGTH = 1;
    public static final int MESSAGE_NUMBER = 2;
    public static final int MESSAGE_LENGTH = 2;
    public static final int CHAR_LENGTH = 2;
    public static final int INT_LENGTH = 2;
    public static final int COMMAND_LENGTH = 1;
    public static final int TIMESTAMP_LENGTH = 4;

    //各种类型commandServer
    public static final String STORE_COMMAND_SERVER = "store_command_server";
    public static final String NSR_COMMAND_SERVER = "nsr_command_server";
    public static final String FRONT_COMMAND_SERVER = "front_command_server";

    //各种类型的资源状态
    public static final String STATUS_RELEASE = "status_release";
    public static final double RELEASE_LINE = 20;
    public static final String STATUS_NORMAL = "status_normal";
    public static final double NORMAL_LINE = 70;
    public static final String STATUS_EXPAND = "status_expand";
    public static final double EXPAND_LINE = 90;

    //NSR地址
    public static final String NSR_ADDR = "10.108.114.110";
    public static final int NSR_PORT = 8000;
    public static final String NATIVE_ADDR = "10.108.114.47";
    public static final int NATIVE_PORT = 8111;
    public static final String MODEL_NAME = "TEST1";
    public static final String FRONT_ADDR = "127.0.0.1";
    public static final int FRONT_PORT = 8200;
    public static final String REDIS_ADDR = "10.108.114.172";
    public static final int REDIS_PORT = 6379;

    //
    public static final int STORE_RECORD_LENGTH = 1000;
    public static final int STORE_HEARTBEAT_INTERBAL = 1000;

    //
    public static final int STORE_BALANCE_LINE = 40;
    public static final int STORE_RELEASE_LINE =10;
}
