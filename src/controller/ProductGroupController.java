package controller;

import controller.exceptions.ProductGroupControllerException;
import model.ProductGroup;
import repository.ProductGroupDAO;

import java.util.ArrayList;
import java.util.Optional;

public class ProductGroupController {
    public static void addProductGroup(String productGroupName){
        if (productGroupName == null || productGroupName.isEmpty() || productGroupName.length() > 50) {
            throw new ProductGroupControllerException("O campo 'Nome do Grupo' não pode estar vazio");
        }
        try {
            ProductGroup productGroup = new ProductGroup();
            productGroup.setGroupName(productGroupName);

            ProductGroupDAO.addGroup(productGroup);
        } catch (Exception e) {
            throw new ProductGroupControllerException("Falha ao adicionar grupo");
        }
    }

    public static void updateProductGroup(String productGroupId, String productGroupName){
        if (productGroupId == null || productGroupId.trim().isEmpty()) {
            throw new ProductGroupControllerException("O campo 'Id' não pode estar vazio");
        }
        if (productGroupName == null || productGroupName.trim().isEmpty() || productGroupName.length() > 50) {
            throw new ProductGroupControllerException("O campo 'Nome do grupo' não pode estar vazio");
        }

        try {
            ProductGroup productGroup = new ProductGroup();
            productGroup.setGroupId(Integer.parseInt(productGroupId));
            productGroup.setGroupName(productGroupName);

            ProductGroupDAO.updateGroup(productGroup);
        }catch (Exception e) {
            throw new ProductGroupControllerException("Falha ao atualizar os dados do grupo");
        }
    }

    public static void deleteProductGroup(String productGroupId){
        if (productGroupId == null || productGroupId.trim().isEmpty()) {
            throw new ProductGroupControllerException("O campo 'Id' não pode estar vazio");
        }
        try {
            ProductGroup productGroup = new ProductGroup();
            productGroup.setGroupId(Integer.parseInt(productGroupId));

            ProductGroupDAO.deleteGroup(productGroup);
        }catch(Exception e) {
            throw new ProductGroupControllerException("Erro ao remover produto, verifique se não existe algum vinculo com outro registro");
        }
    }

    public static Object[][] searchProductGroup(String cmbSearch, String productGroupSearch){
        ArrayList<ProductGroup> data;
        Optional<ArrayList<ProductGroup>> result;

        if (productGroupSearch == null) {
            throw new ProductGroupControllerException("A busca não pode ser nula");
        }

        switch (cmbSearch) {
            case "Id":
                try {
                    result = ProductGroupDAO.getProductGroupById(Integer.parseInt(productGroupSearch));
                    if (result.isPresent()) {
                        data = result.get();
                        return convertProductListToTableData(data);
                    }
                    return new Object[0][0];
                } catch (NumberFormatException e) {
                    throw new ProductGroupControllerException("ID deve ser um número válido");
                }

            case "Nome":
                result = ProductGroupDAO.getProductGroupByName(productGroupSearch);
                if (result.isPresent()) {
                    data = result.get();
                    return convertProductListToTableData(data);
                }
                return new Object[0][0];


            default:
                result = ProductGroupDAO.getAllProductGroups();
                if (result.isPresent()) {
                    data = result.get();
                    return convertProductListToTableData(data);
                }
                return new Object[0][0];
        }
    }

    public static String getIdByName(String productGroupName){
        try {
            ProductGroup productGroup = ProductGroupDAO.getProductGroupByName(productGroupName).get().getFirst();
            return String.valueOf(productGroup.getGroupId());
        } catch (Exception e) {
            throw new ProductGroupControllerException("Erro ao buscar ID do grupo");
        }
    }

    private static Object[][] convertProductListToTableData(ArrayList<ProductGroup> productsGroup) {
        Object[][] data = new Object[productsGroup.size()][2];

        for (int i = 0; i < productsGroup.size(); i++) {
            ProductGroup productGroup = productsGroup.get(i);
            data[i][0] = productGroup.getGroupId();
            data[i][1] = productGroup.getGroupName();
        }
        return data;
    }
}

