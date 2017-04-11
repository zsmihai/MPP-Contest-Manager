package Networking.utils;

import Networking.rpcprotocol.ContestClientRpcWorker;
import Service.IServer;

import java.net.Socket;

/**
 * Created by Utilizator on 10-Apr-17.
 */
public class ContestRpcConcurrentServer extends AbstractConcurrentServer{
    private IServer server;

    //constructor
    public ContestRpcConcurrentServer(Integer port, IServer server) {
        super(port);
        this.server = server;
    }

    @Override
    protected Thread createWorker(Socket socketClient) {
        ContestClientRpcWorker worker = new ContestClientRpcWorker(server, socketClient);
        Thread tw = new Thread(worker);
        return tw;
    }

}
