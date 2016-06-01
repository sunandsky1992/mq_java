import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

/**
 * Created by ss on 16-6-1.
 */
public class insertTest {
    @Test
    public void test() throws IOException {
        Socket socket = new Socket();
        InetSocketAddress address = new InetSocketAddress("localhost", 8002);
        socket.connect(address);
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        int num = 0;
        while (true) {
            num ++;
            byte[] res;
            int length;
            byte command[] = {0, 13, -128, 1, 97, 0, 2, 0, 1, 97, 0, 1, 97};
            out.write(command);
            out.flush();
            if (num%1000==0) {
                System.out.println(num+" "+new Date());
            }
        }
    }

    @Test
    public void test2() {
        int a = 4 << 8;
        a = a|((byte)1);
        System.out.println(a);
    }
}
