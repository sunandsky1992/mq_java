import Constant.Constant;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import static Command.CommandServer.byteToInt;
import static Command.CommandServer.byteToString;

/**
 * Created by ss on 16-5-31.
 */
public class FrontTest {
    @Test
    public void test() throws IOException {
        Socket socket = new Socket();
        InetSocketAddress address = new InetSocketAddress("localhost", 8002);
        socket.connect(address);
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        byte[] res;
        int length;
        byte command[] ={0,13,-128,1,97,0,2,0,1,97,0,1,97};
        out.write(command);
        out.flush();

        byte command2[] = {0,7,64,1,97,0,4};
        out.write(command2);
        out.flush();
        res = new byte[2];
        in.read(res, 0, 2) ;
        length = byteToInt(res, 0, Constant.TOTAL_LENGTH);
        System.out.println(length);
        byte[] command3 = new byte[length];
        in.read(command3, 0, length - 2);
        int position = 0;
        int number = byteToInt(command3,position,Constant.INT_LENGTH);
        position += Constant.INT_LENGTH;
        for (int i=0;i<number;i++) {
            int messageLength = byteToInt(command3,position,Constant.INT_LENGTH);
            position += Constant.INT_LENGTH;
            System.out.println(messageLength);
            String message = byteToString(command3,position,messageLength);
            position += messageLength;
            System.out.println(message);
        }
        //String res1 = byteToString(command3,0,length-2);
        //System.out.println(res1);

    }
}
