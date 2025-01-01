package controller;

import controller.exceptions.ProductGroupControllerException;
import controller.exceptions.ProductUnitControllerException;
import model.ProductUnit;
import repository.ProductUnitDAO;

import java.util.ArrayList;
import java.util.Optional;

public class ProductUnitController{
    public static void addProductUnit(String productUnitName, String productUnitAcronym){
        if (productUnitName == null || productUnitName.trim().isEmpty() || productUnitName.length() > 50){
            throw new ProductUnitControllerException("O campo 'Nome da unidade' não pode estar vazio");
        }
        if ((productUnitAcronym == null || productUnitAcronym.trim().isEmpty()) || productUnitAcronym.length() > 10){
            throw new ProductUnitControllerException("O campo 'Sigla da unidade' não pode estar vazio ou ser superior a 2 caracteres");
        }

        try {
            ProductUnit productUnit = new ProductUnit();
            productUnit.setUnitName(productUnitName);
            productUnit.setUnitAcronym(productUnitAcronym);

            ProductUnitDAO.addUnit(productUnit);
        } catch (Exception e) {
            throw new ProductUnitControllerException("Falha ao adicionar unidade");
        }
    }

    public static void updateProductUnit(String productUnitID, String productUnitName, String productUnitAcronym){
        if (productUnitID == null || productUnitID.trim().isEmpty()){
            throw new ProductUnitControllerException("O campo 'Id' não pode estar vazio");
        }
        if (productUnitName == null || productUnitName.trim().isEmpty() || productUnitName.length() > 50){
            throw new ProductUnitControllerException("O campo 'Nome da unidade' não pode estar vazio");
        }
        if ((productUnitAcronym == null || productUnitAcronym.trim().isEmpty()) || productUnitAcronym.length() > 10){
            throw new ProductUnitControllerException("O campo 'Sigla da unidade' não pode estar vazio ou ser maior que 2 caracteres");
        }

        try {
            ProductUnit productUnit = new ProductUnit();
            productUnit.setUnitId(Integer.parseInt(productUnitID));
            productUnit.setUnitName(productUnitName);
            productUnit.setUnitAcronym(productUnitAcronym);

            ProductUnitDAO.updateUnit(productUnit);
        }catch (Exception e) {
            throw new ProductUnitControllerException("Falha ao atualizar unidade");
        }
    }

    public static void deleteProductUnit(String productUnitID){
        if (productUnitID == null || productUnitID.trim().isEmpty()){
            throw new ProductUnitControllerException("O campo 'Id' não pode estar vazio");
        }

        try {
            ProductUnit productUnit = new ProductUnit();
            productUnit.setUnitId(Integer.parseInt(productUnitID));

            ProductUnitDAO.deleteUnit(productUnit);
        }catch (Exception e) {
            throw new ProductUnitControllerException("Falha ao remover unidade, verifique se não existe algum vinculo com outro registro");
        }
    }

    public static Object[][] searchProductUnit(String cmbSearch, String productUnitSearch){
        ArrayList<ProductUnit> data;
        Optional<ArrayList<ProductUnit>> result;

        if (productUnitSearch == null){
            throw new ProductUnitControllerException("A busca não pode ser nula");
        }

        switch (cmbSearch){
            case "ID":
                try {
                    result = ProductUnitDAO.getProductUnitById(Integer.parseInt(productUnitSearch));
                    if (result.isPresent()){
                        data = result.get();
                        return convertProductUnitListToTableData(data);
                    }
                    return new Object[0][0];
                }catch (Exception e){
                    throw new ProductUnitControllerException("ID deve ser um número válido");
                }

            case "NOME":
                result = ProductUnitDAO.getProductUnitByName(productUnitSearch);
                if (result.isPresent()){
                    data = result.get();
                    return convertProductUnitListToTableData(data);
                }
                return new Object[0][0];

            case "UN":
                if(productUnitSearch.trim().length() != 2){
                    throw new ProductUnitControllerException("A sigla deve conter 2 caracteres");
                }
                result = ProductUnitDAO.getProductUnitsByAcronym(productUnitSearch);
                if (result.isPresent()){
                    data = result.get();
                    return convertProductUnitListToTableData(data);
                }
                return new Object[0][0];

            default:
                result = ProductUnitDAO.getAllProductUnits();
                if (result.isPresent()){
                    data = result.get();
                    return convertProductUnitListToTableData(data);
                }
                return new Object[0][0];
        }
    }

    public static String getIdByName(String productUnitName){
        try {
            ProductUnit productUnit = ProductUnitDAO.getProductUnitByName(productUnitName).get().getFirst();
            return String.valueOf(productUnit.getUnitId());
        } catch (Exception e) {
            throw new ProductGroupControllerException("Erro ao buscar ID da unidade");
        }
    }

    private static Object[][] convertProductUnitListToTableData(ArrayList<ProductUnit> productsUnit) {
        Object[][] data = new Object[productsUnit.size()][3];

        for (int i = 0; i < productsUnit.size(); i++) {
            ProductUnit productUnit = productsUnit.get(i);
            data[i][0] = productUnit.getUnitId();
            data[i][1] = productUnit.getUnitName();
            data[i][2] = productUnit.getUnitAcronym();
        }
        return data;
    }
}
