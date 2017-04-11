package Service;

import Exceptions.ServiceException;

/**
 * Created by Utilizator on 04-Apr-17.
 */
public interface IClient {

    void invalidate() throws ServiceException;

}
