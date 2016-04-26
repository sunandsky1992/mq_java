package Command;

import java.util.Arrays;

/**
 * Created by ss on 16-4-6.
 */
//用于解析命令的类的接口
public abstract class CommandServer {
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
}
