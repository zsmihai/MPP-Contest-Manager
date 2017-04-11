package Repository;

import Domain.Proba;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utilizator on 14-Mar-17.
 */
public class ProbeDBRepository  implements IRepository<Proba, Integer> {

    Connection connection;

    public ProbeDBRepository(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Proba add(Proba elem) throws SQLException {

        PreparedStatement insertStatement = null;

        try {
            insertStatement = connection.prepareStatement("insert into probe(nume) values (?) ", PreparedStatement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, elem.getNume());
            insertStatement.executeUpdate();
            ResultSet resultSet = insertStatement.getGeneratedKeys();

            resultSet.next();
            elem.setProba_id(resultSet.getInt(1));
            return elem;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (null != insertStatement)
                insertStatement.close();
            else
                throw new SQLException();
        }
        return null;
    }


    @Override
    public List<Proba> getAll() throws SQLException {

        List<Proba> probeList = new ArrayList<>();

        PreparedStatement selectStatement = null;
        try {
            selectStatement = connection.prepareStatement("select * from probe");
            ResultSet resultSet = selectStatement.executeQuery();

            while(resultSet.next()){
                Integer ID = resultSet.getInt("proba_id");
                String nume = resultSet.getString("nume");
                Proba proba = new Proba(ID, nume);
                probeList.add(proba);
            }
            return probeList;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(null!=selectStatement)
                selectStatement.close();
            else
                throw new SQLException();


        }
        return null;
    }

    @Override
    public void delete(Integer integer) throws SQLException {

        PreparedStatement deleteStatement = null;

        try{
            deleteStatement= connection.prepareStatement("delete from probe where proba_id = ?");
            deleteStatement.setInt(1, integer);
            deleteStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(null!=deleteStatement)
                deleteStatement.close();
            else
                throw new SQLException();
        }

    }

    @Override
    public void update(Integer ID, Proba elem) throws SQLException {

        PreparedStatement updateStatement = null;

        try {
            updateStatement = connection.prepareStatement("update probe set nume=? where proba_id=?");
            updateStatement.setString(1, elem.getNume());
            updateStatement.setInt(2, ID);
            updateStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (null != updateStatement)
                updateStatement.close();
            else
                throw new SQLException();
        }
    }
}
