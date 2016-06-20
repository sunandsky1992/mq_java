import Constant.Constant;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

import static Command.CommandServer.byteToInt;
import static Command.CommandServer.byteToString;

/**
 * Created by ss on 16-6-1.
 */
public class readTest {

    public void test() throws IOException {
        Socket socket = new Socket();
        InetSocketAddress address = new InetSocketAddress("localhost", 8002);
        socket.connect(address);
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        byte[] res;
        int length;
        int num = 0;
        while (true) {
            num ++;
            byte command2[] = {0, 7, 64, 1, 97, 0, 2};
            out.write(command2);
            out.flush();
            res = new byte[2];
            in.read(res, 0, 2);
            length = byteToInt(res, 0, Constant.TOTAL_LENGTH);
            byte[] command3 = new byte[length];
            in.read(command3, 0, length - 2);
            if (num%100==0) {
                System.out.println(num+" "+new Date());
            }
        }
    }
    @Test
    public void test2(){

    }
}
