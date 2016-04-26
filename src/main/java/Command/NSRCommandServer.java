package Command;

import NSR.Structs.PositionBlock;
import Queue.Queue;
import StoreServer.ReadCommand;
import StoreServer.StoreServer;

import java.net.Socket;

/**
 * Created by ss on 16-4-25.
 */
public class NSRCommandServer extends CommandServer {
    int position;

    Socket connect;

    @Override
    public void analysisCommand(byte[] command) {
        position = 1;
        if ((command[0]>>7&0x1) ==1) {
            System.out.println("get next position " + command);
            
        } else if ((command[0]>>6 & 0x1)  == 1) {
            System.out.println("get read position " + command);

        } else if ((command[0]>>5 & 0x1) == 1) {
            System.out.println("heart beat " + command);

        }
    }
}
