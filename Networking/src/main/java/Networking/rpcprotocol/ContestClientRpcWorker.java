package Networking.rpcprotocol;

import Domain.CategorieVarsta;
import Domain.Inscriere;
import Domain.Proba;
import Domain.User;
import Exceptions.ServiceException;
import Networking.dtos.*;
import Repository.UsersDBRepository;
import Service.IClient;
import Service.IServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Created by Utilizator on 04-Apr-17.
 */
public class ContestClientRpcWorker implements Runnable, IClient {

    private IServer server;
    private Socket connection;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private volatile boolean connected;

    public ContestClientRpcWorker(IServer server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            outputStream.flush();
            inputStream =new ObjectInputStream( connection.getInputStream() );
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("Sending response " + response);
        outputStream.writeObject(response);
        System.out.println("flushing");
        outputStream.flush();
    }


    @Override
    public void run() {
        while (connected) {
            //read request
            try {
                Object request = inputStream.readObject();
                System.out.println("Received a request!");

                Response response = handleRequest((Request) request);
                System.out.println("Processed request!");

                if (null != response) {
                    sendResponse(response);
                    System.out.println("Sent response!");
                }

            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("Disconnected while trying to read");
                break;
            }

        }
        try {
            outputStream.close();
            inputStream.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Response handleRequest(Request request) {

        Response response = null;

        if(request.getType()==RequestType.LOGIN) {
            UserDTO userDTO = (UserDTO) request.getData();
            User user = DTOUtils.getUserFromUserDTO(userDTO);
            try {
                server.login(user, this);
                System.out.println("Login successful!");
                return new Response.Builder().type(ResponseType.OK).data(null).build();

            } catch (ServiceException e) {
                return new Response.Builder().type(ResponseType.ERROR).data("Login failed! " + e.getMessage()).build();
            }
        }

        if(request.getType()==RequestType.LOGOUT) {
            UserDTO userDTO = (UserDTO) request.getData();
            User user = DTOUtils.getUserFromUserDTO(userDTO);
            try {
                server.logout(user, this);
                System.out.println("Logout succesful!");
                return new Response.Builder().type(ResponseType.OK).data(null).build();

            } catch (ServiceException e) {
                return new Response.Builder().type(ResponseType.ERROR).data("Logout failed! " + e.getMessage()).build();
            }
        }
        if(request.getType() == RequestType.GET_PROBE){
            List<Proba> probaList = null;
            try {
                probaList = server.getAllProbe();
                System.out.println(probaList.size());
                return new Response.Builder().type(ResponseType.GET_PROBE).data(DTOUtils.getProbaDTOListFromProbaList(probaList)).build();
            } catch (ServiceException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();

            }

        }

        if(request.getType()==RequestType.GET_INSCRIERI_FOR_PROBA_CATEGORIE){

            ProbaCategorieDTO data = (ProbaCategorieDTO) request.getData();
            try {
                System.out.println("Processing request "+request.getType() + " "+ data.getProbaDTO().getProba_id().toString()+ " "+data.getCategorieVarstaDTO().getCategorie_id());
                List<Inscriere> inscrieri = server.getInscriereByProbaCategorie(DTOUtils.getProbaFromProbaDTO(data.getProbaDTO()), DTOUtils.getCategorieFromCategorieDTO(data.getCategorieVarstaDTO()));
                return new Response.Builder().type(ResponseType.GET_INSCRIERI_FOR_PROBA_CATEGORIE).data(DTOUtils.getInscriereDTOListFromInscriereList(inscrieri)).build();

            } catch (ServiceException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }

        }

        if(request.getType() == RequestType.COUNT_INSCRIERI_FOR_PROBA_CATEGORIE){
            ProbaCategorieDTO data = (ProbaCategorieDTO) request.getData();
            try {
                Integer nr = server.countInscrieriByProbaCategorie(DTOUtils.getProbaFromProbaDTO(data.getProbaDTO()), DTOUtils.getCategorieFromCategorieDTO(data.getCategorieVarstaDTO()));
                return new Response.Builder().type(ResponseType.COUNT_INSCRIERI_FOR_PROBA_CATEGORIE).data(nr).build();
            } catch (ServiceException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }

        }

        if(request.getType() == RequestType.ADD_INSCRIERE){
            InscriereProbaDTO inscriereDTO = (InscriereProbaDTO)request.getData();

            try {
                server.addInscriere(DTOUtils.getProbaFromProbaDTO(inscriereDTO.getProbaDTO()), inscriereDTO.getParticipant_nume(), inscriereDTO.getParticipant_varsta());
                return new Response.Builder().type(ResponseType.OK).data(null).build();

            } catch (Exception e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }

        }

        if(request.getType() == RequestType.GET_CATEGORII_FOR_PROBA){
            Integer proba_id = (Integer) request.getData();
            try {

                System.out.println("Processing request "+request.getType() + " "+ ((Integer)request.getData()).toString());
                List<CategorieVarsta> categorieVarstas = server.getAllCategorieVarstaForProba(proba_id);
                List<CategorieVarstaDTO> categorieVarstaDTOList =  DTOUtils.getCategorieDTOListFromCategorieList(server.getAllCategorieVarstaForProba(proba_id));


                return new Response.Builder().type(ResponseType.GET_CATEGORII_FOR_PROBA).data(categorieVarstaDTOList).build();
            } catch (Exception e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }

        }



        return new Response.Builder().type(ResponseType.ERROR).data("Invalid request!").build();



    }

    @Override
    public void invalidate() throws ServiceException {
        try {
            sendResponse(new Response.Builder().type(ResponseType.INVALIDATE).data(null).build());
        } catch (IOException e) {
            throw new ServiceException("Invalidate error!");
        }
    }
}
