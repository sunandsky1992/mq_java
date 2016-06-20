import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.Timestamp;
import Constant.Constant;
import static Command.CommandServer.insertIntToBytes;
import static Command.CommandServer.insertStringToBytes;

/**
 * Created by ss on 16-6-1.
 */

class InsertTask extends Thread {
    private String queueName;

    private int interval;

    private int messageNum;

    private String message;

    private String frontAddr;

    private int port;

    public InsertTask(String queueName, int interval,int messageNum, String message, String frontAddr, int port) {
        this.queueName = queueName;
        this.interval = interval;
        this.messageNum = messageNum;
        this.message = message;
        this.frontAddr=frontAddr;
        this.port=port;
    }

    public void run(){
        Socket socket = new Socket();
        InetSocketAddress address = new InetSocketAddress(frontAddr, port);
        try {
            socket.connect(address);

            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            int times = 0;
            int num = 0;
            while (true) {
                for (int t=0;t<interval;t++) {
                    byte[] res;
                    int length = 0;
                    length = Constant.TOTAL_LENGTH + Constant.COMMAND_LENGTH + Constant.QUEUE_NAME_LENGTH
                            + queueName.length() + Constant.MESSAGE_NUMBER +
                            (Constant.MESSAGE_LENGTH + message.length()) * messageNum;

                    byte command[] = new byte[length];
                    int position = 0;
                    insertIntToBytes(command, length, Constant.TOTAL_LENGTH, position);
                    position += Constant.TOTAL_LENGTH;
                    insertIntToBytes(command, -128, Constant.COMMAND_LENGTH, position);
                    position += Constant.COMMAND_LENGTH;
                    insertIntToBytes(command, queueName.length(), Constant.QUEUE_NAME_LENGTH, position);
                    position += Constant.QUEUE_NAME_LENGTH;
                    insertStringToBytes(command, queueName, queueName.length(), position);
                    position += queueName.length();
                    insertIntToBytes(command, messageNum, Constant.MESSAGE_NUMBER, position);
                    position += Constant.MESSAGE_NUMBER;
                    for (int i = 0; i < messageNum; i++) {
                        insertIntToBytes(command, message.length(), Constant.MESSAGE_LENGTH, position);
                        position += Constant.MESSAGE_LENGTH;
                        insertStringToBytes(command, message, message.length(), position);
                        position += message.length();
                    }

                    out.write(command);
                    out.flush();
                }
                if (num<40)
                    interval++;
                if (num>120)
                    interval--;
                num++;
             //   interval+=1;
                Thread.sleep(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

public class insertTest {
    @Test
    public void test() throws IOException, InterruptedException {
        System.out.println("begin");
        String t = "";
        for (int i=0;i<100;i++)
            t+="a";
        InsertTask insertTaska = new InsertTask("a",0,1,t,"127.0.0.1",8200);
        InsertTask insertTaskb = new InsertTask("b",0,1,"b","127.0.0.1",8300);
        InsertTask insertTaskc = new InsertTask("c",0,1,"c","127.0.0.1",8200);
        InsertTask insertTaskd = new InsertTask("d",0,1,"d","127.0.0.1",8300);
        insertTaska.start();
//        Thread.sleep(20000);
//        insertTaskb.start();
//        Thread.sleep(20000);
//        insertTaskc.start();
//        Thread.sleep(20000);
//        insertTaskd.start();
        insertTaska.join();
    }

    @Test
    public void test2() {
        Timestamp timestamp = new Timestamp(1411000000000l);
        System.out.println(timestamp);
    }
}
