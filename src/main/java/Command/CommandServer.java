package Command;

import java.util.Arrays;

/**
 * Created by ss on 16-4-6.
 */
//用于解析命令的类的接口
public abstract class CommandServer {
    public abstract void analysisCommand(byte[] command);

    protected static int byteToInt(byte[] command, int begin, int length) {
        int res = 0;
        for (int i = begin;i<begin+length;i++){
            res = res << 8 | command[i];
        }
        return res;
    }

    protected static String byteToString(byte[] command, int begin, int length) {
        return new String (Arrays.copyOfRange(command, begin, begin+length));
    }
}
