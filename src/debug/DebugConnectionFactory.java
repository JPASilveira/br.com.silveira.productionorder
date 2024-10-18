package debug;

import repository.ConnectionFactory;
import repository.exceptions.ExceptionConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DebugConnectionFactory {
    public static void main(String[] args) {
        try (Connection connection = ConnectionFactory.getConnection()){
            ConnectionFactory.createTables(connection);
        }catch (ExceptionConnectionFactory e){
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
