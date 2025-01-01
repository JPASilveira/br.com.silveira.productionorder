package controller;

import controller.exceptions.ProductControllerException;
import model.Product;
import repository.ProductDAO;
import repository.ProductGroupDAO;
import repository.ProductUnitDAO;
import util.BooleanString;
import util.DecimalValidator;

import java.util.ArrayList;
import java.util.Optional;

public class ProductController {
    public static void addProduct(String productReference, String productName, String productPrice, String productQuantity, String productUnitId, String productGroupId, Boolean productIsCompose) {
        Product product = new Product();

        if (productReference == null || productReference.length() > 50) {
            throw new ProductControllerException("A referencia não pode ser nula");
        }
        product.setProductReference(productReference);

        if (productName == null || productName.trim().isEmpty() || productName.length() > 100) {
            throw new ProductControllerException("O nome não pode estar vazio ou nulo");
        }
        product.setProductName(productName);

        try {
            if (productPrice == null || productPrice.trim().isEmpty() || !DecimalValidator.isValid(productPrice)) {
                throw new ProductControllerException("O preço não pode estar vazio ou nulo");
            }
            product.setProductPrice(Double.parseDouble(productPrice));
        } catch (Exception e) {
            throw new ProductControllerException("O preço é inválido");
        }

        try {
            if (productQuantity == null || productQuantity.trim().isEmpty() || !DecimalValidator.isValid(productQuantity)) {
                throw new ProductControllerException("A quantidade não pode estar vazia ou nula");
            }
            product.setProductQuantity(Double.parseDouble(productQuantity));
        } catch (Exception e) {
            throw new ProductControllerException("Quantidade inválida");
        }

        try {
            if (productUnitId == null || productUnitId.trim().isEmpty()) {
                throw new ProductControllerException("A unidade não pode estar vazia ou nula");
            }
            product.setProductUnit(ProductUnitDAO.getProductUnitById(Integer.parseInt(productUnitId)).get().getFirst());
        } catch (Exception e) {
            throw new ProductControllerException("Unidade inválida");
        }

        try {
            if (productGroupId == null || productGroupId.trim().isEmpty()) {
                throw new ProductControllerException("O grupo não pode estar vazio ou nulo");
            }
            product.setProductGroup(ProductGroupDAO.getProductGroupById(Integer.parseInt(productGroupId)).get().getFirst());
        } catch (Exception e) {
            throw new ProductControllerException("Grupo inválido");
        }

        product.setProductIsComposite(productIsCompose);

        try {
            ProductDAO.addProduct(product);
        } catch (Exception e) {
            throw new ProductControllerException("Erro ao adicionar um produto");
        }

    }

    public static void updateProduct(String productId, String productReference, String productName, String productPrice, String productQuantity, String productUnitId, String productGroupId, Boolean productIsCompose) {
        Product product = new Product();

        try {
            if (productId == null || productId.trim().isEmpty()) {
                throw new ProductControllerException("O ID não pode estar vazio ou nulo");
            }
            product.setProductId(Integer.parseInt(productId));
        } catch (Exception e) {
            throw new ProductControllerException("O ID é inválido");
        }

        if (productReference == null || productReference.length() > 50) {
            throw new ProductControllerException("A referencia não pode ser nula");
        }
        product.setProductReference(productReference);

        if (productName == null || productName.trim().isEmpty() || productName.length() > 100) {
            throw new ProductControllerException("O nome não pode estar vazio ou nulo");
        }
        product.setProductName(productName);

        try {
            if (productPrice == null || productPrice.trim().isEmpty() || !DecimalValidator.isValid(productPrice)) {
                throw new ProductControllerException("O preço não pode estar vazio ou nulo");
            }
            product.setProductPrice(Double.parseDouble(productPrice));
        } catch (Exception e) {
            throw new ProductControllerException("O preço é inválido");
        }

        try {
            if (productQuantity == null || productQuantity.trim().isEmpty() || !DecimalValidator.isValid(productQuantity)) {
                throw new ProductControllerException("A quantidade não pode estar vazia ou nula");
            }
            product.setProductQuantity(Double.parseDouble(productQuantity));
        } catch (Exception e) {
            throw new ProductControllerException("Quantidade inválida");
        }

        try {
            if (productUnitId == null || productUnitId.trim().isEmpty()) {
                throw new ProductControllerException("A unidade não pode estar vazia ou nula");
            }
            product.setProductUnit(ProductUnitDAO.getProductUnitById(Integer.parseInt(productUnitId)).get().getFirst());
        } catch (Exception e) {
            throw new ProductControllerException("Unidade inválida");
        }

        try {
            if (productGroupId == null || productGroupId.trim().isEmpty()) {
                throw new ProductControllerException("O grupo não pode estar vazio ou nulo");
            }
            product.setProductGroup(ProductGroupDAO.getProductGroupById(Integer.parseInt(productGroupId)).get().getFirst());
        } catch (Exception e) {
            throw new ProductControllerException("Grupo inválido");
        }

        product.setProductIsComposite(productIsCompose);

        try {
            ProductDAO.updateProduct(product);
        } catch (Exception e) {
            throw new ProductControllerException("Erro ao adicionar um produto");
        }

    }

    public static void deleteProduct(String productId) {
        int id;

        try {
            id = Integer.parseInt(productId);
        } catch (Exception e) {
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

    public static void enableComposite(String productId) {
        Product product = new Product();
        try {
            product.setProductId(Integer.parseInt(productId));
        }catch (Exception ex) {
            new ProductControllerException("Erro ao habilitar composição");
        }
        product.setProductIsComposite(true);
        try {
            ProductDAO.updateProduct(product);
        }catch (Exception ex) {
            new ProductControllerException("Erro ao habilitar composição");
        }
    }

    public static Object[][] searchProduct(String cmbSearch, String productSearch) {
        ArrayList<Product> data;
        Optional<ArrayList<Product>> result;

        if (productSearch == null) {
            throw new ProductControllerException("A busca não pode ser nula");
        }
        if (productSearch.trim().isEmpty()) {
            cmbSearch = "ALL";
        }

        switch (cmbSearch) {
            case "NOME":
                result = ProductDAO.getProductByName(productSearch);
                if (result.isPresent()) {
                    data = result.get();
                    return convertProductListToTableData(data);
                }
                return new Object[0][0];


            case "REFERÊNCIA":
                result = ProductDAO.getProductByReference(productSearch);
                if (result.isPresent()) {
                    data = result.get();
                    return convertProductListToTableData(data);
                }
                return new Object[0][0];

            case "PREÇO":
                try {
                    result = ProductDAO.getProductByPrice(Double.parseDouble(productSearch));
                    if (result.isPresent()) {
                        data = result.get();
                        return convertProductListToTableData(data);
                    }
                    return new Object[0][0];
                }catch (Exception e) {
                    throw new ProductControllerException("Preço inválido");
                }

            case "ID":
                try {
                    result = ProductDAO.getProductById(Integer.parseInt(productSearch));
                    if (result.isPresent()) {
                        data = result.get();
                        return convertProductListToTableData(data);
                    }
                    return new Object[0][0];
                }catch (Exception e) {
                    throw new ProductControllerException("ID inválido");
                }

            default:
                result = ProductDAO.getAllProducts();
                if (result.isPresent()) {
                    data = result.get();
                    return convertProductListToTableData(data);
                }
                return new Object[0][0];
        }

    }

    public static String getProductIdByName(String name){
        try {
            return ProductDAO.getProductByName(name).get().getFirst().getProductId().toString();
        }catch (Exception e) {
            throw new ProductControllerException("Erro ao obter o id do produto");
        }
    }

    public static void setProductPrice(String productId, double price) {
        Product product = new Product();
        try {
            product.setProductId(Integer.parseInt(productId));
            product.setProductPrice(price);
            ProductDAO.updateProduct(product);
        }catch (Exception e) {
            e.printStackTrace();
            throw new ProductControllerException("Erro ao atualizar o custo do produto");
        }
    }

    private static Object[][] convertProductListToTableData(ArrayList<Product> products) {
        Object[][] data = new Object[products.size()][8];

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            data[i][0] = product.getProductId();
            data[i][1] = product.getProductReference();
            data[i][2] = product.getProductName();
            data[i][3] = product.getProductPrice();
            data[i][4] = product.getProductQuantity();
            data[i][5] = product.getProductUnit().getUnitName();
            data[i][6] = product.getProductGroup().getGroupName();
            data[i][7] = BooleanString.toString(product.getProductIsComposite());
        }
        return data;
    }
}
