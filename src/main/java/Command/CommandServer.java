package Command;

import Constant.Constant;

import java.net.Socket;
import java.util.Arrays;

/**
 * Created by ss on 16-4-6.
 */
//用于解析命令的类的接口
public abstract class CommandServer {
    Socket connect ;

    public abstract void analysisCommand(byte[] command);

    public static int byteToInt(byte[] command, int begin, int length) {
        int res = 0;
        for (int i = begin;i<begin+length;i++){
            res = res << 8 | command[i];
        }
        return res;
    }

    public static String byteToString(byte[] command, int begin, int length) {
        return new String (Arrays.copyOfRange(command, begin, begin+length));
    }

    public static void insertIntToBytes(byte[] command, int value, int length,int position) {
        for (int i=0;i<length;i++) {
            command[i+position] = (byte)((value>>((length-i-1)*8)) & 0xFF);
        }
    }

    public static String getQueueName(byte[] command,int beginPosition){
        return byteToString(command,beginPosition,Constant.QUEUE_NAME_LENGTH);
    }

    public static int getInt(byte[] command, int position) {
        return byteToInt(command,position,Constant.INT_LENGTH);
    }

    public void setConnect(Socket connect) {
        this.connect = connect;
    }
}
