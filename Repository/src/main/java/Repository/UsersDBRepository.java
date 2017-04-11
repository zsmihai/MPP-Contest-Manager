package Repository;

import Domain.Proba;
import Domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utilizator on 20-Mar-17.
 */
public class UsersDBRepository implements IRepository<User, String> {

    Connection connection;

    public UsersDBRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User add(User elem) throws SQLException {
        return null;
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> userList = new ArrayList<>();

        PreparedStatement selectStatement = null;
        try {
            selectStatement = connection.prepareStatement("select * from utilizatori");
            ResultSet resultSet = selectStatement.executeQuery();

            while(resultSet.next()){
                String nume_utilizator = resultSet.getString("nume_utilizator");
                String parola_utilizator = resultSet.getString("parola_utilizator");
                User user= new User(nume_utilizator, parola_utilizator);
                userList.add(user);
            }
            return userList;

        }
        finally {
            if(null!=selectStatement)
                selectStatement.close();
        }
    }

    @Override
    public void delete(String s) throws SQLException {
    }

    @Override
    public void update(String s, User elem) throws SQLException {

    }

    public boolean findUser(User user) throws SQLException {
        PreparedStatement findStatement = null;
        try {
            findStatement = connection.prepareStatement("select count(*) from utilizatori where nume_utilizator=? AND  parola_utilizator=password(?)");
            findStatement.setString(1, user.getUsername());
            findStatement.setString(2, user.getPassword());
            ResultSet resultSet = findStatement.executeQuery();
            resultSet.next();
            return (resultSet.getInt(1) != 0);
        } finally {
            if (null != findStatement)
                findStatement.close();
        }
    }
}
