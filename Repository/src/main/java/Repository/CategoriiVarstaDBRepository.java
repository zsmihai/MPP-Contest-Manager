package Repository;

import Domain.CategorieVarsta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utilizator on 14-Mar-17.
 */
public class CategoriiVarstaDBRepository implements IRepository<CategorieVarsta, Integer> {

    Connection connection;

    public CategoriiVarstaDBRepository(Connection connection) {
        this.connection = connection;
    }


    @Override
    public CategorieVarsta add(CategorieVarsta elem) throws SQLException {

        PreparedStatement insertStatement = null;

        try {
            insertStatement = connection.prepareStatement("insert into categorii_varsta(minim_varsta, maxim_varsta, proba_id) values (?, ?, ?) ", PreparedStatement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, elem.getMinim_varsta());
            insertStatement.setInt(2, elem.getMaxim_varsta());
            insertStatement.setInt(3, elem.getProba_id());


            insertStatement.executeUpdate();
            ResultSet resultSet = insertStatement.getGeneratedKeys();
            resultSet.next();
            elem.setID(resultSet.getInt(1));
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
    public List<CategorieVarsta> getAll() throws SQLException {

        List<CategorieVarsta> categorie_varstaList = new ArrayList<>();

        PreparedStatement selectStatement = null;
        try {
            selectStatement = connection.prepareStatement("select * from categorii_varsta");
            ResultSet resultSet = selectStatement.executeQuery();

            while(resultSet.next()){
                Integer ID = resultSet.getInt("categorie_id");
                Integer minim_varsta = resultSet.getInt("minim_varsta");
                Integer maxim_varsta = resultSet.getInt("maxim_varsta");
                Integer proba_id = resultSet.getInt("proba_id");
                CategorieVarsta categorie_varsta = new CategorieVarsta(ID, minim_varsta, maxim_varsta, proba_id);
                categorie_varstaList.add(categorie_varsta);
            }
            return categorie_varstaList;

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
            deleteStatement =  connection.prepareStatement("delete from categorii_varsta where categorie_id = ?");
            deleteStatement.setInt(1, integer);
            deleteStatement.execute();


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
    public void update(Integer ID, CategorieVarsta elem) throws SQLException {

        PreparedStatement updateStatement = null;

        try {
            updateStatement = connection.prepareStatement("update categorii_varsta set minim_varsta=?, maxim_varsta=?, proba_id=? where categorie_id=?");
            updateStatement.setInt(1, elem.getMinim_varsta());
            updateStatement.setInt(2, elem.getMaxim_varsta());
            updateStatement.setInt(3, elem.getProba_id());
            updateStatement.setInt(4, ID);
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
