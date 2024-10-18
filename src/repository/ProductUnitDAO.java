package repository;

public class ProductUnitDAO {
    /*public static void addUnit(ProductUnit productUnit) {
        String sql = "INSERT INTO ProductUnit (name, unit) VALUES (?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, productUnit.getName());
            preparedStatement.setString(2, productUnit.getUnit());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionUnitDAO("error while adding productUnit", e);
        }
    }

    public static void updateUnit(ProductUnit productUnit) {
        StringBuilder sql = new StringBuilder("UPDATE Applicant SET ");
        boolean hasName = false;
        boolean hasUnit = false;
        boolean isFirst = true;

        if (productUnit.getName() != null) {
            sql.append("name = ?");
            hasName = true;
            isFirst = false;
        }

        if (productUnit.getUnit() != null) {
            if (!isFirst) {
                sql.append(", ");
            }
            sql.append("productUnit = ?");
        }

        sql.append(" WHERE id = ?");

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())){
            int parameterIndex = 1;

            if (hasName) {
                preparedStatement.setString(parameterIndex++, productUnit.getName());
            }
            if (hasUnit) {
                preparedStatement.setString(parameterIndex++, productUnit.getUnit());
            }

            preparedStatement.setInt(parameterIndex++, productUnit.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new ExceptionUnitDAO("error while updating productUnit", e);
        }
    }

    public static void deleteUnit(ProductUnit productUnit) {
        String sql = "DELETE FROM Applicant WHERE id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, productUnit.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionUnitDAO("error while deleting productUnit", e);
        }
    }*/
}
