package ScheduleTask;

import Constant.Constant;
import FrontServer.FrontServer;
import FrontStructs.FrontSchelduledTask;
import FrontStructs.FrontSchelduledTaskUnit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static Command.CommandServer.insertIntToBytes;
import static Command.CommandServer.insertStringToBytes;

/**
 * Created by ss on 16-6-7.
 */
public class FrontScheduleTask extends ScheduleTask{
    public FrontServer frontServer = FrontServer.getFrontServer();

    @Override
    public void task() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                update();
            }
        };
        Timer timer = new Timer();
        long delay = 100-new Date().getTime()%100;
        long inteval = 100;
        timer.scheduleAtFixedRate(task,delay,inteval);

        TimerTask heartBeatTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    sendHeartBeat();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Timer heartBeatTimer = new Timer();
        heartBeatTimer.scheduleAtFixedRate(heartBeatTask,0,1000);
    }

    public void update() {
        List<FrontSchelduledTaskUnit> taskUnits = FrontSchelduledTask.getFrontSchelduledTask().getTaksUnits();
        for (FrontSchelduledTaskUnit frontSchelduledTaskUnit:taskUnits) {
            if (frontSchelduledTaskUnit.getTimestamp()<=new Date().getTime()) {
                frontServer.putInsertMap(frontSchelduledTaskUnit.getQueueName(),frontSchelduledTaskUnit.getAddr(),
                        frontSchelduledTaskUnit.getPort(),frontSchelduledTaskUnit.getQueueId());
            }
        }
    }

    public void sendHeartBeat() throws IOException {
        Socket socket = new Socket();
        InetSocketAddress address = new InetSocketAddress(Constant.NSR_ADDR,Constant.NSR_PORT);
        socket.connect(address);
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();

        String addr = Constant.FRONT_ADDR;
        int port = Constant.FRONT_PORT;
        String sendStr = addr+":"+port;
        int length = Constant.TOTAL_LENGTH + Constant.COMMAND_LENGTH +
                Constant.MESSAGE_LENGTH + sendStr.length();

        byte command[] = new byte[length];
        int position = 0;
        insertIntToBytes(command, length, Constant.TOTAL_LENGTH, position);
        position+=Constant.TOTAL_LENGTH;
        insertIntToBytes(command,16,Constant.COMMAND_LENGTH,position);
        position+=Constant.COMMAND_LENGTH;
        insertIntToBytes(command,sendStr.length(),Constant.MESSAGE_LENGTH,position);
        position+=Constant.MESSAGE_LENGTH;
        insertStringToBytes(command, sendStr, sendStr.length(), position);
        position+=sendStr.length();

        out.write(command);
        out.flush();
        socket.close();
    }

}
