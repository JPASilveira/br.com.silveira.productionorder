package debug;

import repository.ConnectionFactory;
import repository.exceptions.ExceptionConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DebugConnectionFactory {
    public static void main(String[] args) {
        try (Connection connection = ConnectionFactory.getConnection()){
            ConnectionFactory.createTables(connection);
        }catch (ExceptionConnectionFactory | SQLException e){
            System.out.println(e.getMessage() + e.getCause());
        }
    }
}
