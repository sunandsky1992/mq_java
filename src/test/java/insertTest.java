import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by ss on 16-6-1.
 */
public class insertTest {
    @Test
    public void test() throws IOException, InterruptedException {
        Socket socket = new Socket();
        InetSocketAddress address = new InetSocketAddress("localhost", 8200);
        socket.connect(address);
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        int num = 0;
        while (true) {
            num ++;
            byte[] res;
            int length;
            byte command[] = {0, 13, -128, 1, 98, 0, 2, 0, 1, 97, 0, 1, 97};
            out.write(command);
            out.flush();
            //if (num%1000==0) {
                System.out.println(num+" "+new Date());
          //  }
            Thread.sleep(60);
        }
    }

    @Test
    public void test2() {
        Timestamp timestamp = new Timestamp(1411000000000l);
        System.out.println(timestamp);
    }
}
