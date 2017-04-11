package Networking.utils;

import java.net.Socket;

/**
 * Created by Utilizator on 03-Apr-17.
 */
public abstract class AbstractConcurrentServer extends AbstractServer {


    public AbstractConcurrentServer(int port) {

        super(port);
        System.out.println("Concurrent AbstractServer");
    }

    protected void processRequest(Socket client) {
        Thread tw=createWorker(client);
        tw.start();
    }

    protected abstract Thread createWorker(Socket client) ;


}


