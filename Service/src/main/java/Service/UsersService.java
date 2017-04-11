package Service;

import Repository.UsersDBRepository;
import Domain.User;

import java.sql.SQLException;
import Exceptions.ServiceException;
/**
 * Created by Utilizator on 20-Mar-17.
 */
public class UsersService implements IUsersService{

    UsersDBRepository usersRepository;


    public UsersService(UsersDBRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    public boolean find(String userName, String password) throws ServiceException {
        try {
            return usersRepository.findUser(new User(userName, password));
        } catch (SQLException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public User getForNamePass(String userName, String password) throws ServiceException {
        if (find(userName, password))
            return new User(userName, password);
        else
            return null;
    }

}
