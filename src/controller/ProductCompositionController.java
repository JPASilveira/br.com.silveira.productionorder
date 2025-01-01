package controller;

import controller.exceptions.ProductCompositionControllerException;
import controller.exceptions.ProductControllerException;
import model.ProductComposition;
import repository.ProductCompositionDAO;
import repository.ProductDAO;


import java.util.ArrayList;
import java.util.Optional;

public class ProductCompositionController {
    public static void addProductComposition(String parentProductId, String childProductId, String childProductQuantity) {
        if (childProductId == null || childProductId.trim().isEmpty()) {
            throw new ProductCompositionControllerException("O subproduto não pode ser nulo ou inválido");
        }
        if (childProductQuantity == null || childProductQuantity.trim().isEmpty()) {
            throw new ProductControllerException("A quantidade não pode ser nula ou inválida");
        }

        ProductComposition productComposition = new ProductComposition();

        try {
            productComposition.setProductCompositionParentProduct(ProductDAO.getProductById(Integer.parseInt(parentProductId)).get().getFirst());
        }catch (Exception ex) {
            throw new ProductCompositionControllerException("Falha ao obter produto base");
        }

        try {
            productComposition.setProductCompositionChildProduct(ProductDAO.getProductById(Integer.parseInt(childProductId)).get().getFirst());
        }catch (Exception ex) {
            throw new ProductCompositionControllerException("O subproduto é inválido");
        }

        try {
            productComposition.setProductCompositionQuantityUsed(Double.parseDouble(childProductQuantity));
        }catch (Exception ex) {
            throw new ProductCompositionControllerException("A quantidade é inválida");
        }

        try {
            ProductCompositionDAO.addProductComposition(productComposition);
        }catch (Exception ex) {
            throw new ProductCompositionControllerException("Falha ao adicionar subproduto");
        }
    }

    public static void updateProductComposition(String productCompositionID, String parentProductId, String childProductId, String childProductQuantity) {
        if (childProductId == null || childProductId.trim().isEmpty()) {
            throw new ProductCompositionControllerException("O subproduto não pode ser nulo ou inválido");
        }
        if (childProductQuantity == null || childProductQuantity.trim().isEmpty()) {
            throw new ProductControllerException("A quantidade não pode ser nula ou inválida");
        }

        ProductComposition productComposition = new ProductComposition();

        try {
            productComposition.setProductCompositionId(Integer.parseInt(productCompositionID));
        }catch (Exception ex) {
            throw new ProductCompositionControllerException("Falha ao obter composição");
        }

        try {
            productComposition.setProductCompositionParentProduct(ProductDAO.getProductById(Integer.parseInt(parentProductId)).get().getFirst());
        }catch (Exception ex) {
            throw new ProductCompositionControllerException("Falha ao obter produto base");
        }

        try {
            productComposition.setProductCompositionChildProduct(ProductDAO.getProductById(Integer.parseInt(childProductId)).get().getFirst());
        }catch (Exception ex) {
            throw new ProductCompositionControllerException("O subproduto é inválido");
        }

        try {
            productComposition.setProductCompositionQuantityUsed(Double.parseDouble(childProductQuantity));
        }catch (Exception ex) {
            throw new ProductCompositionControllerException("A quantidade é inválida");
        }

        try {
            ProductCompositionDAO.updateProductComposition(productComposition);
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new ProductCompositionControllerException("Falha ao editar subproduto");
        }
    }

    public static void deleteProductComposition(String productCompositionID){
        ProductComposition productComposition  = new ProductComposition();
        try {
            productComposition.setProductCompositionId(Integer.parseInt(productCompositionID));
            ProductCompositionDAO.deleteProductComposition(productComposition);
        }catch (Exception ex) {
            throw new ProductCompositionControllerException("Falha ao remover subproduto, verifique se não existe algum vinculo com outro registro");
        }
    }

    public static Object[][] searchProductComposition(String productCompositionParentProductId){
        ArrayList<ProductComposition> data;
        Optional<ArrayList<ProductComposition>> result;
        try {
            result = ProductCompositionDAO.getProductCompositionByParentProductId(Integer.parseInt(productCompositionParentProductId));
        }catch (Exception e){
            throw new ProductCompositionControllerException("Falha ao obter produto base");
        }

        if (result.isPresent()) {
            data = result.get();
            return convertProductCompositionListToTableData(data);
        }
        return new Object[0][0];
    }

    public static boolean isDuplicated(String parentProductId, String childProductId) {
        if (parentProductId.equals(childProductId)) {
            return true;
        }
        return false;
    }

    public static boolean isEmptyRecord(String productCompositionParentProductId) {
        ArrayList<ProductComposition> data = null;
        Optional<ArrayList<ProductComposition>> result;
        try {
            result = ProductCompositionDAO.getProductCompositionByParentProductId(Integer.parseInt(productCompositionParentProductId));
        }catch (Exception e){
            throw new ProductCompositionControllerException("Falha ao obter produto base");
        }

        try {
            if (result.isPresent()) {
                data = result.get();
            }
            return data.isEmpty();
        }catch (Exception e){
            return true;
        }
    }

    private static Object[][] convertProductCompositionListToTableData(ArrayList<ProductComposition> productsComposition) {
        Object[][] data = new Object[productsComposition.size()][5];

        for (int i = 0; i < productsComposition.size(); i++) {
            ProductComposition productComposition = productsComposition.get(i);
            data[i][0] = productComposition.getProductCompositionId();
            data[i][1] = productComposition.getProductCompositionParentProduct();
            data[i][2] = productComposition.getProductCompositionChildProduct();
            data[i][3] = productComposition.getProductCompositionQuantity();
            data[i][4] = productComposition.getProductCompositionChildProduct().getProductUnit().getUnitName();
        }
        return data;
    }
}
