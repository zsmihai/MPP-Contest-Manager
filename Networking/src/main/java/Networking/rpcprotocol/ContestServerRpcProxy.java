package Networking.rpcprotocol;

import Domain.CategorieVarsta;
import Domain.Inscriere;
import Domain.Proba;
import Domain.User;
import Exceptions.ServiceException;
import Networking.dtos.*;
import Service.IClient;
import Service.IServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Utilizator on 04-Apr-17.
 */
public class ContestServerRpcProxy implements IServer{

    private String host;
    private Socket connection;
    private int port;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private IClient client;

    private BlockingQueue<Response> responsesQueue;
    private volatile boolean finished;

    public ContestServerRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        responsesQueue = new LinkedBlockingQueue<>();
    }


    private void sendRequest(Request request) throws ServiceException {
        try {
            System.out.println("Sending request: "+ request.getType());
            outputStream.writeObject(request);
            outputStream.flush();
            System.out.println("Request sent");
        } catch (IOException e) {
            throw new ServiceException("Error sending request\n");
        }

    }

    @Override
    public List<CategorieVarsta> getAllCategorieVarstaForProba(Integer probaID) throws ServiceException {
        Request request = new Request.Builder().type(RequestType.GET_CATEGORII_FOR_PROBA).data(probaID).build();

        sendRequest(request);
        Response response = readResponse();

        if(response.getType()==ResponseType.ERROR){
            throw new ServiceException((String)response.getData());
        }

        return DTOUtils.getCategorieListFromCategorieDTOList((List<CategorieVarstaDTO>) response.getData());
    }

    @Override
    public boolean validUser(String username, String password) throws ServiceException {
        return false;
    }

    @Override
    public List<Proba> getAllProbe() throws ServiceException {
        Request request = new Request.Builder().type(RequestType.GET_PROBE).data(null).build();

        sendRequest(request);
        Response response = readResponse();

        if(response.getType()==ResponseType.ERROR){
            throw new ServiceException((String)response.getData());
        }

        return DTOUtils.getProbaListFromProbaListDTO((List<ProbaDTO>) response.getData());
    }

    private Response readResponse(){
        Response response = null;
        try{
            response = responsesQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }


    @Override
    public List<Inscriere> getAllInscrieri() throws ServiceException {
       return null;
    }

    @Override
    public List<Inscriere> getInscriereByProbaCategorie(Proba proba, CategorieVarsta categorieVarsta) throws ServiceException {
        ProbaCategorieDTO probaCategorieDTO = DTOUtils.getProbaCategorieDTOFromProbaCategorie(proba, categorieVarsta);
        Request request = new Request.Builder().type(RequestType.GET_INSCRIERI_FOR_PROBA_CATEGORIE).data(probaCategorieDTO).build();

        sendRequest(request);
        Response response = readResponse();
        if(response.getType()==ResponseType.ERROR){
            throw new ServiceException((String)response.getData());
        }
        List<InscriereDTO> inscriereDTOList = (List<InscriereDTO>)response.getData();

        return DTOUtils.getInscriereListFromInscriereDTOList(inscriereDTOList);
    }

    @Override
    public Integer countInscrieriByProbaCategorie(Proba proba, CategorieVarsta categorieVarsta) throws ServiceException {
        ProbaCategorieDTO probaCategorieDTO = DTOUtils.getProbaCategorieDTOFromProbaCategorie(proba, categorieVarsta);
        Request request = new Request.Builder().type(RequestType.COUNT_INSCRIERI_FOR_PROBA_CATEGORIE).data(probaCategorieDTO).build();

        sendRequest(request);
        Response response = readResponse();
        if(response.getType()==ResponseType.ERROR){
            throw new ServiceException((String)response.getData());
        }

        return (Integer) response.getData();

    }

    @Override
    public void addInscriere(Proba proba, String participant_nume, Integer participant_varsta) throws Exception {

        InscriereProbaDTO inscriereProbaDTO = new InscriereProbaDTO(participant_nume, participant_varsta, DTOUtils.getProbaDTOFromProba(proba));
        Request request = new Request.Builder().type(RequestType.ADD_INSCRIERE).data(inscriereProbaDTO).build();
        sendRequest(request);
        Response response = readResponse();
        if(response.getType()==ResponseType.ERROR){
            throw new ServiceException((String)response.getData());
        }
    }

    @Override
    public void login(User user, IClient client) throws ServiceException {
        initializeConnection();
        UserDTO userDTO = DTOUtils.getUserDTO(user);
        Request request = new Request.Builder().type(RequestType.LOGIN).data(userDTO).build();
        sendRequest(request);
        Response response = readResponse();
        if (response.getType() == ResponseType.OK){
            this.client = client;
            return;
        }
        if (response.getType() == ResponseType.ERROR){
            String message = (String)response.getData();
            throw new ServiceException(message);
        }
    }

    @Override
    public void logout(User user, IClient client) throws ServiceException {
        UserDTO userDTO = DTOUtils.getUserDTO(user);
        Request request = new Request.Builder().type(RequestType.LOGOUT).data(userDTO).build();
        sendRequest(request);
        Response response = readResponse();
        closeConnection();
        if (response.getType() == ResponseType.ERROR){
            throw new ServiceException("Error logout");
        }
    }

    private void initializeConnection() throws ServiceException {
        try{
            connection = new Socket(host, port);
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            throw new ServiceException("Connection to server refused");
        }
    }

    /*
    Closes the connection
     */
    private void closeConnection(){
        finished = true;
        try{
            inputStream.close();
            outputStream.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //starts the reader thread
    private void startReader(){
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }



    private class ReaderThread implements Runnable{

        @Override
        public void run() {
            while (!finished){
                try{
                    System.out.println("Tries to read..");
                    Object response = inputStream.readObject();
                    System.out.println("Response received " + ((Response)response).getType());

                    try{

                        if(((Response) response).getType()==ResponseType.INVALIDATE){
                            client.invalidate();

                        }
                        else
                            responsesQueue.put((Response) response);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ServiceException e) {
                        e.printStackTrace();
                    }

                } catch (IOException | ClassNotFoundException ex){
                    System.out.println("Client reader stopped with "+ex.getMessage());
                    finished = true;
                }
            }
        }
    }

}
