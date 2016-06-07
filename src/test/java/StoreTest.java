import Constant.Constant;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import static Command.CommandServer.byteToInt;
import static Command.CommandServer.byteToString;

/**
 * Created by ss on 16-4-18.
 */
public class StoreTest {
    @Test
    public void test() throws IOException, InterruptedException {
        Socket socket = new Socket();
        InetSocketAddress address = new InetSocketAddress("localhost",8001);
        socket.connect(address);
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        byte res[] = new byte[2];
        int length;
        byte[] command = {0,10,(byte)128,1,97,0,1,0,1,97};
        out.write(command);
        out.flush();
//
//        byte[] command2 = {0,7,(byte)64,1,97,0,1};
//        out.write(command2);
//        out.flush();
//
//        res[] = new byte[2];
//        in.read(res,0,2) ;
//        length = byteToInt(res, 0, Constant.TOTAL_LENGTH);
//        byte[] command3 = new byte[length];
//        in.read(command3, 0, length - 2);
//        int messageNum = byteToInt(command3,0,Constant.MESSAGE_NUMBER);
//        int position = Constant.MESSAGE_NUMBER;
//        while (messageNum-->0) {
//            int l = byteToInt(command3,position,Constant.MESSAGE_LENGTH);
//            position+=Constant.MESSAGE_LENGTH;
//            String message = byteToString(command3,position,l);
//            position+=l;
//        }


//        byte[] command4 = {0,5,32,1,97};
//        out.write(command4);
//        out.flush();
//
//        res = new byte[2];
//        in.read(res,0,2);
//        length = byteToInt(res, 0, Constant.TOTAL_LENGTH);
//        byte[] command5 = new byte[length];
//        in.read(command5, 0, length - 2);
//        int l = byteToInt(command5,0,Constant.INT_LENGTH);
//        System.out.println(l);
        socket.close();
    }
}
