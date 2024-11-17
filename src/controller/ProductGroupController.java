package controller;

import model.ProductGroup;
import repository.ProductGroupDAO;
import view.ProductGroupView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductGroupController {
    public static void addProductGroup(String productGroupName) throws IllegalArgumentException {
        if (productGroupName == null || productGroupName.isEmpty()) {
            throw new IllegalArgumentException("O campo 'Nome do Grupo' não pode estar vazio!");
        }

        ProductGroup productGroup = new ProductGroup();
        productGroup.setGroupName(productGroupName);

        ProductGroupDAO.addGroup(productGroup);
    }

    public static void editProductGroup(String productGroupId, String productGroupName) throws IllegalArgumentException {
        ProductGroup productGroup = new ProductGroup();
        productGroup.setGroupId(Integer.parseInt(productGroupId));
        productGroup.setGroupName(productGroupName);
        ProductGroupDAO.updateGroup(productGroup);
    }

    public static void deleteProductGroup(String productGroupId) throws IllegalArgumentException {
        ProductGroup productGroup = new ProductGroup();
        productGroup.setGroupId(Integer.parseInt(productGroupId));
        ProductGroupDAO.deleteGroup(productGroup);
    }

    public static Object[][] searchProductGroup(String cmbSearch, String productGroupSearch) throws IllegalArgumentException {
        ArrayList<ProductGroup> data;

        if (productGroupSearch == null) {
            throw new IllegalArgumentException("O nome não pode ser nulo!");
        }

        if (productGroupSearch.trim().isEmpty()) {
            Optional<ArrayList<ProductGroup>> result = ProductGroupDAO.getAllProductGroups();
            if (result.isPresent()) {
                data = result.get();
                return convertProductListToTableData(data);
            }
        }

        switch (cmbSearch) {
            case "Id":
                try {
                    System.out.println(productGroupSearch);
                    int id = Integer.parseInt(productGroupSearch);
                    Optional<ArrayList<ProductGroup>> result = ProductGroupDAO.getProductGroupById(id);
                    if (result.isPresent()) {
                        data = result.get();
                        return convertProductListToTableData(data);
                    } else {
                        return new Object[0][0];
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("ID deve ser um número válido.");
                }

            case "Nome":
                Optional<ArrayList<ProductGroup>> nameResult = ProductGroupDAO.getProductGroupByName(productGroupSearch);
                if (nameResult.isPresent()) {
                    data = nameResult.get();
                    return convertProductListToTableData(data);
                } else {
                    return new Object[0][0];
                }

            default:
                throw new IllegalStateException("Unexpected value: " + cmbSearch);
        }
    }

    private static Object[][] convertProductListToTableData(ArrayList<ProductGroup> products) {
        Object[][] data = new Object[products.size()][2];
        for (int i = 0; i < products.size(); i++) {
            ProductGroup productGroup = products.get(i);
            data[i][0] = productGroup.getGroupId();
            data[i][1] = productGroup.getGroupName();
        }
        return data;
    }
}

