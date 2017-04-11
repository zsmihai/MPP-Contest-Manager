package Repository;

import Domain.HasID;

import java.util.List;

/**
 * Created by Utilizator on 12-Mar-17.
 */
public abstract class DBRepository<T extends HasID, ID> implements IRepository<T, ID>{


    @Override
    public T add(T elem) {
        return null;
    }

    @Override
    public List<T> getAll() {
        return null;
    }

    @Override
    public void delete(ID id) {

    }

    @Override
    public void update(ID id, T elem) {

    }
}
