package controller;

import controller.exceptions.ProductControllerException;
import model.Product;
import model.ProductGroup;
import model.ProductUnit;
import repository.ProductDAO;
import repository.ProductGroupDAO;
import repository.ProductUnitDAO;

import java.util.ArrayList;
import java.util.Optional;

public class ProductController {
    public static void addProduct(String productReference, String productName, String  productPrice, String productQuantity, String productUnitId, String productGroupId, Boolean productIsCompose) {
        Product product = new Product();

        if (productReference == null){
            throw new ProductControllerException("A referencia não pode ser nula");
        }
        product.setProductReference(productReference);

        if (productName == null || productName.trim().isEmpty()){
            throw new ProductControllerException("O nome não pode estar vazio ou nulo");
        }
        product.setProductName(productName);

        try {
            if (productPrice == null || productPrice.trim().isEmpty()){
                throw new ProductControllerException("O preço não pode estar vazio ou nulo");
            }
            product.setProductPrice(Double.parseDouble(productPrice));
        } catch (Exception e) {
            throw new ProductControllerException("O preço é inválido");
        }

        try {
            if (productQuantity == null || productQuantity.trim().isEmpty()){
                throw new ProductControllerException("A quantidade não pode estar vazia ou nula");
            }
            product.setProductQuantity(Double.parseDouble(productQuantity));
        }catch (Exception e) {
            throw new ProductControllerException("Quantidade inválida");
        }

        try {
            if (productUnitId == null || productUnitId.trim().isEmpty()){
                throw new ProductControllerException("A unidade não pode estar vazia ou nula");
            }
            product.setProductUnit(ProductUnitDAO.getProductUnitById(Integer.parseInt(productUnitId)).get().getFirst());
        }catch (Exception e) {
            throw new ProductControllerException("Unidade inválida");
        }

        try {
            if (productGroupId == null || productGroupId.trim().isEmpty()){
                throw new ProductControllerException("O grupo não pode estar vazio ou nulo");
            }
            product.setProductGroup(ProductGroupDAO.getProductGroupById(Integer.parseInt(productGroupId)).get().getFirst());
        }catch (Exception e) {
            throw new ProductControllerException("Grupo inválido");
        }

        product.setProductIsComposite(productIsCompose);

        try {
            ProductDAO.addProduct(product);
        }catch (Exception e) {
            e.printStackTrace();
            throw new ProductControllerException("Erro ao adicionar um produto");
        }

    }

    public static void updateProduct(String productId, String productReference, String productName, String  productPrice, String productQuantity, String productUnitId, String productGroupId, Boolean productIsCompose) {
        Product product = new Product();

        try {
            if (productId == null || productId.trim().isEmpty()){
                throw new ProductControllerException("O ID não pode estar vazio ou nulo");
            }
            product.setProductId(Integer.parseInt(productId));
        } catch (Exception e) {
            throw new ProductControllerException("O ID é inválido");
        }

        if (productReference == null){
            throw new ProductControllerException("A referencia não pode ser nula");
        }
        product.setProductReference(productReference);

        if (productName == null || productName.trim().isEmpty()){
            throw new ProductControllerException("O nome não pode estar vazio ou nulo");
        }
        product.setProductName(productName);

        try {
            if (productPrice == null || productPrice.trim().isEmpty()){
                throw new ProductControllerException("O preço não pode estar vazio ou nulo");
            }
            product.setProductPrice(Double.parseDouble(productPrice));
        } catch (Exception e) {
            throw new ProductControllerException("O preço é inválido");
        }

        try {
            if (productQuantity == null || productQuantity.trim().isEmpty()){
                throw new ProductControllerException("A quantidade não pode estar vazia ou nula");
            }
            product.setProductQuantity(Double.parseDouble(productQuantity));
        }catch (Exception e) {
            throw new ProductControllerException("Quantidade inválida");
        }

        try {
            if (productUnitId == null || productUnitId.trim().isEmpty()){
                throw new ProductControllerException("A unidade não pode estar vazia ou nula");
            }
            product.setProductUnit(ProductUnitDAO.getProductUnitById(Integer.parseInt(productUnitId)).get().getFirst());
        }catch (Exception e) {
            throw new ProductControllerException("Unidade inválida");
        }

        try {
            if (productGroupId == null || productGroupId.trim().isEmpty()){
                throw new ProductControllerException("O grupo não pode estar vazio ou nulo");
            }
            product.setProductGroup(ProductGroupDAO.getProductGroupById(Integer.parseInt(productGroupId)).get().getFirst());
        }catch (Exception e) {
            throw new ProductControllerException("Grupo inválido");
        }

        product.setProductIsComposite(productIsCompose);

        try {
            ProductDAO.addProduct(product);
        }catch (Exception e) {
            throw new ProductControllerException("Erro ao adicionar um produto");
        }

    }

    public static void deleteProduct(String productId) {
        int id;

        try {
            id = Integer.parseInt(productId);
        }catch (Exception e) {
            throw new ProductControllerException("Erro ao obter o id do produto");
        }

        try {
            Product product = new Product();
            product.setProductId(id);
            ProductDAO.deleteProduct(product);
        } catch (Exception e) {
            throw new ProductControllerException("Erro ao excluir o produto");
        }
    }
}
