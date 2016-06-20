package ScheduleTask;

import Constant.Constant;
import StoreServer.StoreHistoryInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import static Command.CommandServer.insertIntToBytes;
import static Command.CommandServer.insertStringToBytes;

/**
 * Created by ss on 16-6-1.
 */
public class StoreScheduleTask extends ScheduleTask{
    private static final Logger logger = LogManager.getLogger("StoreScheduleTask");
    @Override
    public void task() {
        System.out.println("HeartBeat begin");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    sendStoreLoad();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer();
        long delay = 0;
        long inteval = 1 * 1000;
        timer.scheduleAtFixedRate(task,delay,inteval);
    }

    public void sendStoreLoad() throws IOException {
        Socket socket = new Socket();
        InetSocketAddress address = new InetSocketAddress(Constant.NSR_ADDR,Constant.NSR_PORT);
        socket.connect(address);
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();

        String addr = Constant.NATIVE_ADDR;
        int port = Constant.NATIVE_PORT;
        int length = Constant.TOTAL_LENGTH + Constant.COMMAND_LENGTH + Constant.QUEUE_NAME_LENGTH
                + Constant.MODEL_NAME.length() + Constant.INT_LENGTH+Constant.NATIVE_ADDR.length()+
                Constant.INT_LENGTH + Constant.INT_LENGTH*4+ Constant.INT_LENGTH;
        byte[] res;
        byte command[] = new byte[length];
        int position = 0;
        insertIntToBytes(command, length, Constant.TOTAL_LENGTH, position);
        position+=Constant.TOTAL_LENGTH;
        insertIntToBytes(command,32,Constant.COMMAND_LENGTH,position);
        position+=Constant.COMMAND_LENGTH;
        insertIntToBytes(command,Constant.MODEL_NAME.length(),Constant.QUEUE_NAME_LENGTH,position);
        position+=Constant.QUEUE_NAME_LENGTH;
        insertStringToBytes(command, Constant.MODEL_NAME, Constant.MODEL_NAME.length(), position);
        position+=Constant.MODEL_NAME.length();
        insertIntToBytes(command, Constant.NATIVE_ADDR.length(), Constant.INT_LENGTH, position);
        position+=Constant.INT_LENGTH;
        insertStringToBytes(command, Constant.NATIVE_ADDR, Constant.NATIVE_ADDR.length(), position);
        position+= Constant.NATIVE_ADDR.length();
        insertIntToBytes(command, Constant.NATIVE_PORT, Constant.INT_LENGTH, position);
        position+=Constant.INT_LENGTH;
        insertIntToBytes(command,20,Constant.INT_LENGTH,position);
        position+=Constant.INT_LENGTH;
        insertIntToBytes(command,20,Constant.INT_LENGTH,position);
        position+=Constant.INT_LENGTH;
        insertIntToBytes(command,20,Constant.INT_LENGTH,position);
        position+=Constant.INT_LENGTH;
        insertIntToBytes(command,20,Constant.INT_LENGTH,position);
        position+=Constant.INT_LENGTH;
        insertIntToBytes(command,StoreHistoryInfo.getStoreHistoryInfo().updateRecord(),Constant.INT_LENGTH,position);
        position+=Constant.INT_LENGTH;
        //byte command[] ={0,27,32,2,97,97,0,9,49,50,55,46,48,46,48,46,49,31,65,0,20,0,20,0,20,0,20};
        out.write(command);
        out.flush();
        socket.close();

        int[] t = StoreHistoryInfo.getStoreHistoryInfo().getArr();

      //  /home/ss/workspace/mq_java/log4j.properties

        logger.trace("");
        logger.error("===============================");
        logger.debug(t[0]+" " +t[1]+" "+t[2]);
        logger.info("===============================");
        logger.info("");

    }
}
