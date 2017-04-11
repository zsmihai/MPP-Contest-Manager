package Repository;

import Domain.HasID;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Utilizator on 12-Mar-17.
 */
public interface IRepository<E extends HasID, ID> {

    E add(E elem) throws SQLException;

    List<E> getAll() throws SQLException;

    void delete(ID id) throws SQLException;

    void update(ID id, E elem) throws SQLException;
}
