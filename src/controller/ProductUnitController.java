package controller;

import controller.exceptions.ProductUnitControllerException;
import model.ProductUnit;
import repository.ProductUnitDAO;

import java.util.ArrayList;
import java.util.Optional;

public class ProductUnitController{
    public static void addProductUnit(String productUnitName, String productUnitAcronym){
        if (productUnitName == null || productUnitName.trim().isEmpty()){
            throw new ProductUnitControllerException("O campo 'Nome da unidade' não pode estar vazio");
        }
        if ((productUnitAcronym == null || productUnitAcronym.trim().isEmpty()) || productUnitAcronym.length() != 2){
            throw new ProductUnitControllerException("O campo 'Sigla da unidade' não pode estar vazio ou ser superior a 2 caracteres");
        }
        ProductUnit productUnit = new ProductUnit();
        productUnit.setUnitName(productUnitName);
        productUnit.setUnitAcronym(productUnitAcronym);

        ProductUnitDAO.addUnit(productUnit);
    }

    public static void updateProductUnit(String productUnitID, String productUnitName, String productUnitAcronym){
        if (productUnitID == null || productUnitID.trim().isEmpty()){
            throw new ProductUnitControllerException("O campo 'Id' não pode estar vazio");
        }
        if (productUnitName == null || productUnitName.trim().isEmpty()){
            throw new ProductUnitControllerException("O campo 'Nome da unidade' não pode estar vazio");
        }
        if ((productUnitAcronym == null || productUnitAcronym.trim().isEmpty()) || productUnitAcronym.length() != 2){
            throw new ProductUnitControllerException("O campo 'Sigla da unidade' não pode estar vazio ou ser maior que 2 caracteres");
        }
        ProductUnit productUnit = new ProductUnit();
        productUnit.setUnitId(Integer.parseInt(productUnitID));
        productUnit.setUnitName(productUnitName);
        productUnit.setUnitAcronym(productUnitAcronym);

        ProductUnitDAO.updateUnit(productUnit);
    }

    public static void deleteProductUnit(String productUnitID){
        if (productUnitID == null || productUnitID.trim().isEmpty()){
            throw new ProductUnitControllerException("O campo 'Id' não pode estar vazio");
        }
        ProductUnit productUnit = new ProductUnit();
        productUnit.setUnitId(Integer.parseInt(productUnitID));

        ProductUnitDAO.deleteUnit(productUnit);
    }

    public static Object[][] searchProductUnit(String cmbSearch, String productUnitSearch){
        ArrayList<ProductUnit> data;
        Optional<ArrayList<ProductUnit>> result;

        if (productUnitSearch == null){
            throw new ProductUnitControllerException("A busca não pode ser nula");
        }

        switch (cmbSearch){
            case "Id":
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

            case "Nome":
                result = ProductUnitDAO.getProductUnitByName(productUnitSearch);
                if (result.isPresent()){
                    data = result.get();
                    return convertProductUnitListToTableData(data);
                }
                return new Object[0][0];

            case "Un":
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
