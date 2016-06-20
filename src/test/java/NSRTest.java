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
 * Created by ss on 16-5-25.
 */
//127.0.0.1  49,50,55,46,48,46,48,46,49
//8001 31 65
public class NSRTest {

    public void test() throws IOException, InterruptedException {

        Socket socket = new Socket();
        InetSocketAddress address = new InetSocketAddress("localhost",8000);
        socket.connect(address);
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        byte[] res;
        int length;
        byte command[] ={0,27,32,2,97,97,0,9,49,50,55,46,48,46,48,46,49,31,65,0,20,0,20,0,20,0,20};
        out.write(command);
        out.flush();

        byte command2[] = {0,5,64,1,97};
        out.write(command2);
        out.flush();
        res = new byte[2];
        in.read(res, 0, 2) ;
        length = byteToInt(res, 0, Constant.TOTAL_LENGTH);
        System.out.println(length);
        byte[] command3 = new byte[length];
        in.read(command3, 0, length - 2);
        String res1 = byteToString(command3,0,length-2);
        System.out.println(res1);

        byte[] command4 = {0,5,-128,1,97};
        out.write(command4);
        out.flush();

        res = new byte[2];
        in.read(res,0,2);
        length = byteToInt(res, 0, Constant.TOTAL_LENGTH);
        byte[] command5 = new byte[length];
        in.read(command5, 0, length - 2);
        String res2 = byteToString(command5,0,length-2);
        System.out.println(res2);
        socket.close();
    }
    @Test
    public void test2(){

    }
}
