package controller;

import controller.exceptions.ProductionOrderControllerException;
import model.*;
import repository.ProductCompositionDAO;
import repository.ProductDAO;
import repository.ProductionOrderDAO;
import repository.RegistrationDAO;
import services.ProductionOrderService;
import services.exceptions.ProducitionOrderServiceException;

import java.util.ArrayList;
import java.util.Optional;

public class ProductionOrderController {
    public static void addProductionOrder(String recipientId, String productId, String quantity, String status) {
        int recipientIdInteger;
        int productIdInteger;
        double quantityDouble;

            if (recipientId.isEmpty()) {
                throw new ProductionOrderControllerException("O campo do 'ID Solicitante' não pode estar vazio");
            }
            try {
                recipientIdInteger = Integer.parseInt(recipientId);
                if (recipientIdInteger <= 0) {
                    throw new ProductionOrderControllerException("O campo 'ID Solicitante' deve conter um número maior que zero");
                }
            } catch (NumberFormatException e) {
              throw new ProductionOrderControllerException("O campo 'ID Solicitante' deve conter apenas números inteiros");

            }
            try {
                if(!RegistrationDAO.existsById(recipientId)){
                    throw new ProductionOrderControllerException("O campo 'ID Solicitante' deve conter um cadastro existente");
                }
            } catch (Exception e) {
                throw new ProductionOrderControllerException("O campo 'ID Solicitante' deve conter um cadastro existente");
            }

            if (productId.isEmpty()) {
                throw new ProductionOrderControllerException("O campo 'ID Produto' deve ser preenchido");
            }
            try {
                productIdInteger = Integer.parseInt(productId);
                if (productIdInteger <= 0) {
                    throw new ProductionOrderControllerException("O campo 'ID Produto' deve conter um número maior que zero");
                }
            } catch (NumberFormatException e) {
                throw new ProductionOrderControllerException("O campo 'ID Produto' deve conter apenas números inteiros");
            }
            try {
                if(!ProductDAO.existsById(productId)){
                    throw new ProductionOrderControllerException("O campo 'ID Produto' deve conter um cadastro existente");
                }
            }catch (Exception e) {
                throw new ProductionOrderControllerException("O campo 'ID Produto' deve conter um cadastro existente");
            }
            try {
                if(!ProductDAO.isComposeById(productId)){
                    throw new ProductionOrderControllerException("O campo 'ID Produto' deve conter um cadastro composto");
                }
            }catch (Exception e) {
                throw new ProductionOrderControllerException("O campo 'ID Produto' deve conter um cadastro composto");
            }

            if (quantity.isEmpty()) {
                throw new ProductionOrderControllerException("O campo 'Quantidade' deve ser preenchido");
            }
            try {
                quantityDouble = Double.parseDouble(quantity);
                if (quantityDouble <= 0) {
                    throw new ProductionOrderControllerException("O campo 'Quantidade' deve conter um número maior que zero.");
                }
            } catch (NumberFormatException e) {
                throw new ProductionOrderControllerException("O campo 'Quantidade' deve conter apenas números");
            }

            ProductionOrder productionOrder = new ProductionOrder();

            try {
                Registration recipient = RegistrationDAO.getByRegistrationId(recipientIdInteger).get().getFirst();
                productionOrder.setProductionOrderRecipient(recipient);
            }catch (Exception e) {
                throw new ProductionOrderControllerException("Erro ao obter o registro do requisitante");
            }
            try {
                Product product = ProductDAO.getProductById(productIdInteger).get().getFirst();
                productionOrder.setProductionOrderProduct(product);
            }catch (Exception e) {
                throw new ProductionOrderControllerException("Erro ao obter o registro do produto");
            }
            productionOrder.setProductionOrderQuantity(quantityDouble);
            productionOrder.setProductionOrderStatus(status);

            try {
                ProductionOrderDAO.addProductionOrder(productionOrder);
            }catch (Exception e) {
                throw new ProductionOrderControllerException("Erro ao adicionar o registro");
            }
            try {
                ProductController.setProductPrice(productId, ProductionOrderService.getProductionUnitCost(productId));
            }catch (Exception e) {
                throw new ProductionOrderControllerException("Erro ao atualizar custo do produto");
            }
    }

    public static void addRecursiveProductionOrder(String recipientId, String productId, String quantity, String status) {
        int recipientIdInteger;
        int productIdInteger;
        double quantityDouble;

        if (recipientId.isEmpty()) {
            throw new ProductionOrderControllerException("O campo do 'ID Solicitante' não pode estar vazio");
        }
        try {
            recipientIdInteger = Integer.parseInt(recipientId);
            if (recipientIdInteger <= 0) {
                throw new ProductionOrderControllerException("O campo 'ID Solicitante' deve conter um número maior que zero");
            }
        } catch (NumberFormatException e) {
            throw new ProductionOrderControllerException("O campo 'ID Solicitante' deve conter apenas números inteiros");

        }
        try {
            if(!RegistrationDAO.existsById(recipientId)){
                throw new ProductionOrderControllerException("O campo 'ID Solicitante' deve conter um cadastro existente");
            }
        } catch (Exception e) {
            throw new ProductionOrderControllerException("O campo 'ID Solicitante' deve conter um cadastro existente");
        }

        if (productId.isEmpty()) {
            throw new ProductionOrderControllerException("O campo 'ID Produto' deve ser preenchido");
        }
        try {
            productIdInteger = Integer.parseInt(productId);
            if (productIdInteger <= 0) {
                throw new ProductionOrderControllerException("O campo 'ID Produto' deve conter um número maior que zero");
            }
        } catch (NumberFormatException e) {
            throw new ProductionOrderControllerException("O campo 'ID Produto' deve conter apenas números inteiros");
        }
        try {
            if(!ProductDAO.existsById(productId)){
                throw new ProductionOrderControllerException("O campo 'ID Produto' deve conter um cadastro existente");
            }
        }catch (Exception e) {
            throw new ProductionOrderControllerException("O campo 'ID Produto' deve conter um cadastro existente");
        }
        try {
            if(!ProductDAO.isComposeById(productId)){
                throw new ProductionOrderControllerException("O campo 'ID Produto' deve conter um cadastro composto");
            }
        }catch (Exception e) {
            throw new ProductionOrderControllerException("O campo 'ID Produto' deve conter um cadastro composto");
        }

        if (quantity.isEmpty()) {
            throw new ProductionOrderControllerException("O campo 'Quantidade' deve ser preenchido");
        }
        try {
            quantityDouble = Double.parseDouble(quantity);
            if (quantityDouble <= 0) {
                throw new ProductionOrderControllerException("O campo 'Quantidade' deve conter um número maior que zero.");
            }
        } catch (NumberFormatException e) {
            throw new ProductionOrderControllerException("O campo 'Quantidade' deve conter apenas números");
        }

        ProductionProductSeparation separation = ProductionOrderService.productSeparation(productIdInteger);


        ProductionOrder mainOrder = new ProductionOrder();
        try {
            Registration recipient = RegistrationDAO.getByRegistrationId(recipientIdInteger).get().getFirst();
            mainOrder.setProductionOrderRecipient(recipient);
        }catch (Exception e) {
            throw new ProductionOrderControllerException("Erro ao obter o registro do requisitante");
        }
        try {
            Product product = ProductDAO.getProductById(productIdInteger).get().getFirst();
            mainOrder.setProductionOrderProduct(product);
            ProductController.setProductPrice(productId, ProductionOrderService.getProductionUnitCost(productId));
        }catch (Exception e) {
            throw new ProductionOrderControllerException("Erro ao obter o registro do produto");
        }
        mainOrder.setProductionOrderQuantity(quantityDouble);
        mainOrder.setProductionOrderStatus(status);

        try {
            ProductionOrderDAO.addProductionOrder(mainOrder);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ProductionOrderControllerException("Erro ao adicionar a ordem de produção para o produto principal");
        }

        try {
            for (ProductComposition compositeProduct : separation.getCompositeProducts()) {
                Product childProduct = compositeProduct.getProductCompositionChildProduct();
                double childQuantity = compositeProduct.getProductCompositionQuantity() * quantityDouble;

                addRecursiveProductionOrder(recipientId, String.valueOf(childProduct.getProductId()), String.valueOf(childQuantity), status);
            }
        }catch (Exception e) {
            throw new ProductionOrderControllerException("Falha ao adicionar sub ordens de produção");
        }
    }

    public static void updateProductionOrder(String productionOrderId, String newStatus) throws ProductionOrderControllerException {
        ProductionOrder productionOrder;

        try {
            productionOrder = ProductionOrderDAO.getByProductionOrderId(Integer.parseInt(productionOrderId), true).get().getFirst();
        } catch (Exception e) {
            throw new ProductionOrderControllerException("Erro ao buscar ordem de produção");
        }

        String currentStatus = productionOrder.getProductionOrderStatus();

        switch (currentStatus + "-" + newStatus) {
            case "GERADO-GERADO":
            case "EM PRODUÇÃO-EM PRODUÇÃO":
            case "FINALIZADO-FINALIZADO":
            case "DESPACHADO-DESPACHADO":
            case "DESPACHADO-EM PRODUÇÃO":
                break;

            case "GERADO-EM PRODUÇÃO":
                removeStock(productionOrder.getProductionOrderProduct().getProductId(), productionOrder.getProductionOrderQuantity());
                break;

            case "GERADO-FINALIZADO":
                removeStock(productionOrder.getProductionOrderProduct().getProductId(), productionOrder.getProductionOrderQuantity());
                addProduct(productionOrder.getProductionOrderProduct().getProductId(), productionOrder.getProductionOrderQuantity());
                break;

            case "GERADO-DESPACHADO":
                removeStock(productionOrder.getProductionOrderProduct().getProductId(), productionOrder.getProductionOrderQuantity());
                break;

            case "EM PRODUÇÃO-GERADO":
                addStock(productionOrder.getProductionOrderProduct().getProductId(), productionOrder.getProductionOrderQuantity());
                break;

            case "EM PRODUÇÃO-FINALIZADO":
                addProduct(productionOrder.getProductionOrderProduct().getProductId(), productionOrder.getProductionOrderQuantity());
                break;

            case "EM PRODUÇÃO-DESPACHADO":
                removeProduct(productionOrder.getProductionOrderProduct().getProductId(), productionOrder.getProductionOrderQuantity());
                break;

            case "FINALIZADO-GERADO":
                removeProduct(productionOrder.getProductionOrderProduct().getProductId(), productionOrder.getProductionOrderQuantity());
                addStock(productionOrder.getProductionOrderProduct().getProductId(), productionOrder.getProductionOrderQuantity());
                break;

            case "FINALIZADO-EM PRODUÇÃO":
                removeProduct(productionOrder.getProductionOrderProduct().getProductId(), productionOrder.getProductionOrderQuantity());
                break;

            case "FINALIZADO-DESPACHADO":
                removeProduct(productionOrder.getProductionOrderProduct().getProductId(), productionOrder.getProductionOrderQuantity());
                break;

            case "DESPACHADO-GERADO":
                addStock(productionOrder.getProductionOrderProduct().getProductId(), productionOrder.getProductionOrderQuantity());
                break;

            case "DESPACHADO-FINALIZADO":
                addProduct(productionOrder.getProductionOrderProduct().getProductId(), productionOrder.getProductionOrderQuantity());
                break;

            default:
                throw new ProductionOrderControllerException("Transição de status inválida");
        }

        try {
            productionOrder.setProductionOrderStatus(newStatus);
            ProductionOrderDAO.updateProductionOrder(productionOrder);
        } catch (Exception e) {
            throw new ProductionOrderControllerException("Erro ao atualizar o status da ordem de produção");
        }
    }

    public static void removeProductionOrder(String productionOrderId, String status) throws ProductionOrderControllerException {
        Integer productionOrderIdInteger;
        ProductionOrder productionOrder;
        try {
            productionOrderIdInteger = Integer.parseInt(productionOrderId);
            productionOrder = ProductionOrderDAO.getByProductionOrderId(productionOrderIdInteger, true).get().getFirst();
        } catch (NumberFormatException e) {
            throw new ProductionOrderControllerException("Erro ao obter ordem de serviço");
        }

        try {
            switch (status) {
                case "GERADO":
                    ProductionOrderDAO.deleteProductionOrder(productionOrder);
                    return;
                case "EM PRODUÇÃO":
                    addStock(productionOrder.getProductionOrderProduct().getProductId(), productionOrder.getProductionOrderQuantity());
                    ProductionOrderDAO.deleteProductionOrder(productionOrder);
                    return;
                case "FINALIZADO":
                    addStock(productionOrder.getProductionOrderProduct().getProductId(), productionOrder.getProductionOrderQuantity());
                    removeProduct(productionOrder.getProductionOrderProduct().getProductId(), productionOrder.getProductionOrderQuantity());
                    ProductionOrderDAO.deleteProductionOrder(productionOrder);
                    return;
                case "DESPACHADO":
                    addStock(productionOrder.getProductionOrderProduct().getProductId(), productionOrder.getProductionOrderQuantity());
                    ProductionOrderDAO.deleteProductionOrder(productionOrder);
                    return;
                default:
                    throw new ProductionOrderControllerException("Erro ao remover registro");
            }
        } catch (Exception e) {
            throw new ProductionOrderControllerException("Falha ao remover ordem de produção, verifique se não existe algum vinculo com outro registro");
        }
    }

    public static Object[][] searchProductionOrder(String cmbSearch, String searchText, boolean includeDispatched) {
        ArrayList<ProductionOrder> data;
        Optional<ArrayList<ProductionOrder>> result;

        if(searchText == null){
            throw new ProductionOrderControllerException("A busca não pode ser nula");
        }
        if(searchText.trim().isEmpty()){
            cmbSearch = "ALL";
        }

        switch (cmbSearch) {
            case "REQUISITANTE":
                result = ProductionOrderDAO.getOrdersByRecipientName(searchText, includeDispatched);
                if(result.isPresent()){
                    data = result.get();
                    return convertProductionOrderListToTableData(data);
                }
                return new Object[0][0];

            case "PRODUTO":
                result = ProductionOrderDAO.getOrdersByProductName(searchText, includeDispatched);
                if(result.isPresent()){
                    data = result.get();
                    return convertProductionOrderListToTableData(data);
                }
                return new Object[0][0];

            case "STATUS":
                result = ProductionOrderDAO.getOrdersByStatus(searchText, includeDispatched);
                if (result.isPresent()) {
                    data = result.get();
                    return convertProductionOrderListToTableData(data);
                }
                return new Object[0][0];

            case "ID":
                try {
                    int idInteger = Integer.parseInt(searchText);
                    result = ProductionOrderDAO.getByProductionOrderId(idInteger, includeDispatched);
                }catch (Exception e) {
                    throw new ProductionOrderControllerException("O campo de busca 'ID' deve ser preenchido com um número inteiro");
                }
                if(result.isPresent()){
                    data = result.get();
                    return convertProductionOrderListToTableData(data);
                }
                return new Object[0][0];

            default:
                result = ProductionOrderDAO.getAllProductionOrders(includeDispatched);
                if(result.isPresent()){
                    data = result.get();
                    return convertProductionOrderListToTableData(data);
                }
                return new Object[0][0];
        }

    }

    public static String getRequestIdByProductionOrderId(String id) {
        try {
            int idInteger = Integer.parseInt(id);
            return ProductionOrderDAO.getByProductionOrderId(idInteger, true).get().getFirst().getProductionOrderRecipient().getRegistrationId().toString();
        }catch (Exception e){
            throw new ProductionOrderControllerException("Erro ao buscar requisitante");
        }
    }

    public static String getProductIdByProductionOrderId(String id) {
        try {
            int idInteger = Integer.parseInt(id);
            return ProductionOrderDAO.getByProductionOrderId(idInteger, true).get().getFirst().getProductionOrderProduct().getProductId().toString();
        }catch (Exception e){
            throw new ProductionOrderControllerException("Erro ao buscar produto");
        }
    }

    public static String getStatusByProductionOrderId(String id) {
        try {
            int idInteger = Integer.parseInt(id);
            return ProductionOrderDAO.getByProductionOrderId(idInteger, true).get().getFirst().getProductionOrderStatus();
        }catch (Exception e){
            throw new ProductionOrderControllerException("Erro ao buscar status");
        }
    }

    public static String getQuantityByProductionOrderId(String id) {
        try {
            int idInteger = Integer.parseInt(id);
            return ProductionOrderDAO.getByProductionOrderId(idInteger, true).get().getFirst().getProductionOrderQuantity().toString();
        }catch (Exception e){
            throw new ProductionOrderControllerException("Erro ao buscar produto");
        }
    }

    private static boolean verifyStock(Integer productId, Double quantity) {
        ArrayList<ProductComposition> compositions;

        try {
            compositions = ProductCompositionDAO.getProductCompositionByParentProductId(productId).get();
        } catch (Exception e) {
            throw new ProducitionOrderServiceException("Erro ao obter produtos da composição");
        }
        for (ProductComposition productComposition : compositions){
            if(productComposition.getProductCompositionChildProduct().getProductQuantity() < productComposition.getProductCompositionQuantity() * quantity){
                throw new ProducitionOrderServiceException(String.format("O estoque do produto: %s. Se encontra inferior ao necessário", productComposition.getProductCompositionChildProduct().getProductName()));
            }
        }
        return true;
    }

    private static void removeProduct(Integer productId, Double quantity) {
        try {
            Product product = ProductDAO.getProductById(productId).get().getFirst();
            product.setProductQuantity(product.getProductQuantity() - quantity);
            if(product.getProductQuantity() < 0){
                throw new ProductionOrderControllerException(String.format("A quantidade do produto: %s não pode ser inferior a 0"));
            }
            ProductDAO.updateProduct(product);
        } catch (Exception e) {
            throw new ProducitionOrderServiceException("Erro ao adicionar produto final ao estoque");
        }
    }

    private static void addProduct(Integer productId, Double quantity) {
        try {
            Product product = ProductDAO.getProductById(productId).get().getFirst();
            product.setProductQuantity(product.getProductQuantity() + quantity);
            ProductDAO.updateProduct(product);
        } catch (Exception e) {
            throw new ProducitionOrderServiceException("Erro ao adicionar produto final ao estoque");
        }
    }

    private static void removeStock(Integer productId, Double quantity) {
        ArrayList<ProductComposition> compositions;

        if (verifyStock(productId, quantity)) {
            try {
                compositions = ProductCompositionDAO.getProductCompositionByParentProductId(productId).get();
            } catch (Exception e) {
                throw new ProducitionOrderServiceException("Erro ao obter produtos da composição");
            }
            for (ProductComposition productComposition : compositions) {
                try {
                    productComposition.getProductCompositionChildProduct().setProductQuantity(productComposition.getProductCompositionChildProduct().getProductQuantity() - (productComposition.getProductCompositionQuantity() * quantity));
                    ProductDAO.updateProduct(productComposition.getProductCompositionChildProduct());
                } catch (Exception e) {
                    throw new ProducitionOrderServiceException("Erro ao descontar estoque do produto");
                }
            }
        }
    }

    private static void addStock(Integer productId, Double quantity) {
        ArrayList<ProductComposition> compositions;

        try {
            compositions = ProductCompositionDAO.getProductCompositionByParentProductId(productId).get();
        } catch (Exception e) {
            throw new ProducitionOrderServiceException("Erro ao obter produtos da composição");
        }
        for (ProductComposition productComposition : compositions) {
            try {
                productComposition.getProductCompositionChildProduct().setProductQuantity(productComposition.getProductCompositionChildProduct().getProductQuantity() + (productComposition.getProductCompositionQuantity() * quantity));
                ProductDAO.updateProduct(productComposition.getProductCompositionChildProduct());
            } catch (Exception e) {
                throw new ProducitionOrderServiceException("Erro ao descontar estoque do produto");
            }
        }
    }

    private static Object[][] convertProductionOrderListToTableData(ArrayList<ProductionOrder> productionOrderList) {
        Object[][] data = new Object[productionOrderList.size()][4];

        for (int i = 0; i < productionOrderList.size(); i++) {
            ProductionOrder productionOrder = productionOrderList.get(i);
            data[i][0] = productionOrder.getProductionOrderId();
            data[i][1] = productionOrder.getProductionOrderRecipient().getRegistrationName();
            data[i][2] = productionOrder.getProductionOrderProduct().getProductName();
            data[i][3] = productionOrder.getProductionOrderStatus();
        }
        return data;
    }
}
