package Service;

import Domain.User;
import Exceptions.ServiceException;
/**
 * Created by Utilizator on 03-Apr-17.
 */
public interface IUsersService {
    boolean find(String userName, String password) throws ServiceException;
    public User getForNamePass(String userName, String password) throws ServiceException;
}
