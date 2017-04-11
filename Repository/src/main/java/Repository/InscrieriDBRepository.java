package Repository;

import Domain.Inscriere;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utilizator on 14-Mar-17.
 */
public class InscrieriDBRepository implements IRepository<Inscriere, Integer> {

    Connection connection;

    public InscrieriDBRepository(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Inscriere add(Inscriere elem) throws SQLException {

        PreparedStatement insertStatement = null;

        try {
            insertStatement = connection.prepareStatement("insert into inscrieri(proba_id, participant_nume, participant_varsta, categorie_varsta_id) values (?, ?, ?, ?) ", PreparedStatement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, elem.getProba_id());
            insertStatement.setString(2, elem.getParticipant_nume());
            insertStatement.setInt(3, elem.getParticipant_varsta());
            insertStatement.setInt(4, elem.getCategorie_varsta_id());

            insertStatement.executeUpdate();
            /*
            ResultSet resultSet = insertStatement.getResultSet();
            resultSet.next();
            Integer newKey = resultSet.getInt(1);
            elem.setID(newKey);
            */
            return elem;

        } finally {
            if (null != insertStatement)
                insertStatement.close();
        }
    }


    @Override
    public List<Inscriere> getAll() throws SQLException {

        List<Inscriere> inscriereList = new ArrayList<>();

        PreparedStatement selectStatement = null;
        try {
            selectStatement = connection.prepareStatement("select * from inscrieri");
            ResultSet resultSet = selectStatement.executeQuery();

            while(resultSet.next()){
                Integer ID = resultSet.getInt("inscriere_id");
                Integer proba_id = resultSet.getInt("proba_id");
                String part_nume = resultSet.getString("participant_nume");
                Integer part_varsta = resultSet.getInt("participant_varsta");
                Integer categorie_varsta_id = resultSet.getInt("categorie_varsta_id");
                Inscriere inscriere = new Inscriere(ID, proba_id, part_varsta, part_nume, categorie_varsta_id);
                inscriereList.add(inscriere);
            }
            return inscriereList;

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
            connection.prepareStatement("delete from inscrieri where inscriere_id = ?");
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
    public void update(Integer ID, Inscriere elem) throws SQLException {

    }
}
