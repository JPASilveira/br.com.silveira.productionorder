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
            throw new ProductCompositionControllerException("Falha ao excluir subproduto");
        }
    }

    public static Object[][] searchProductComposition(){
        ArrayList<ProductComposition> data;
        Optional<ArrayList<ProductComposition>> result;

        result = ProductCompositionDAO.getAllProductsComposition();
        if (result.isPresent()) {
            data = result.get();
            return convertProductCompositionListToTableData(data);
        }
        return new Object[0][0];
    }

    private static Object[][] convertProductCompositionListToTableData(ArrayList<ProductComposition> productsComposition) {
        Object[][] data = new Object[productsComposition.size()][4];

        for (int i = 0; i < productsComposition.size(); i++) {
            ProductComposition productComposition = productsComposition.get(i);
            data[i][0] = productComposition.getProductCompositionId();
            data[i][1] = productComposition.getProductCompositionParentProduct();
            data[i][2] = productComposition.getProductCompositionChildProduct();
            data[i][3] = productComposition.getProductCompositionQuantityUsed();
        }
        return data;
    }
}
