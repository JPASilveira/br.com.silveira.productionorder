package services;

import controller.exceptions.ProductionOrderControllerException;
import model.Product;
import model.ProductComposition;
import model.ProductionOrder;
import model.ProductionProductSeparation;
import repository.ProductCompositionDAO;
import repository.ProductDAO;
import repository.ProductionOrderDAO;
import services.exceptions.ProducitionOrderServiceException;
import util.PDFGenerator;
import util.PathUtil;

import java.io.File;
import java.util.ArrayList;

public class ProductionOrderService {
    public static String getParentProductName(String id){
        Product parentProduct;
        int parentProductId;

        if(ProductDAO.existsById(id)){
            parentProductId = Integer.parseInt(id);
        }else {
            throw new ProducitionOrderServiceException("O produto não existe");
        }
        try {
            parentProduct = ProductDAO.getProductById(parentProductId).get().getFirst();
            return parentProduct.getProductName();
        }catch (Exception e){
            throw new ProducitionOrderServiceException("Erro ao obter o nome do produto");
        }
    }

    public static String getParentProductUnit(String id){
        Product parentProduct;
        int parentProductId;

        if(ProductDAO.existsById(id)){
            parentProductId = Integer.parseInt(id);
        }else {
            throw new ProducitionOrderServiceException("O produto não existe");
        }
        try {
            parentProduct = ProductDAO.getProductById(parentProductId).get().getFirst();
            return parentProduct.getProductUnit().getUnitAcronym();
        }catch (Exception e){
            throw new ProducitionOrderServiceException("Erro ao obter o unidade do produto");
        }
    }


    public static ProductionProductSeparation productSeparation(Integer parentProductId) {
        ArrayList<ProductComposition> compositions;
        ProductionProductSeparation separation = new ProductionProductSeparation();

        try {
            compositions = ProductCompositionDAO.getProductCompositionByParentProductId(parentProductId).get();
        } catch (Exception e) {
            throw new ProducitionOrderServiceException("Erro ao obter produtos da composição");
        }
        for (ProductComposition productComposition : compositions) {
            if(productComposition.getProductCompositionChildProduct().getProductIsComposite()){
                separation.getCompositeProducts().add(productComposition);
            }else {
                separation.getBasicProducts().add(productComposition);
            }
        }
        return separation;
    }

    public static Double getProductionCost(String parentProductId, String quantity) {
        ArrayList<ProductComposition> compositions;
        Double cost = 0.0;

        try {
            compositions = ProductCompositionDAO.getProductCompositionByParentProductId(Integer.parseInt(parentProductId)).get();
        } catch (Exception e) {
            throw new ProducitionOrderServiceException("Erro ao obter produtos da composição");
        }
        for (ProductComposition productComposition : compositions){
           try {
               cost += productComposition.getProductCompositionChildProduct().getProductPrice() * productComposition.getProductCompositionQuantity() * Double.parseDouble(quantity);
           }catch (Exception e){
               throw new ProducitionOrderServiceException("Erro ao obter calcular valor da composição");
           }

        }
        return cost;
    }

    public static Double getProductionUnitCost(String parentProductId) {
        ArrayList<ProductComposition> compositions;
        Double cost = 0.0;

        try {
            compositions = ProductCompositionDAO.getProductCompositionByParentProductId(Integer.parseInt(parentProductId)).get();
        } catch (Exception e) {
            throw new ProducitionOrderServiceException("Erro ao obter produtos da composição");
        }
        for (ProductComposition productComposition : compositions){
            try {
                cost += productComposition.getProductCompositionChildProduct().getProductPrice() * productComposition.getProductCompositionQuantity();
            }catch (Exception e){
                throw new ProducitionOrderServiceException("Erro ao obter calcular valor da composição");
            }

        }
        return cost;
    }

    public static Object[][] getBasicProducts(String id, String quantityProduce) {
        int parentProductId;
        double productionOrderQuantity;
        Object[][] data;

        try {
            if(ProductDAO.existsById(id)){
                parentProductId = Integer.parseInt(id);
            }else {
                throw new ProducitionOrderServiceException("O produto não existe");
            }
        }catch (Exception e){
            throw new ProducitionOrderServiceException("O produto não existe");
        }

        try {
            productionOrderQuantity = Double.parseDouble(quantityProduce);
        }catch (Exception e){
            throw new ProducitionOrderServiceException("Quantidade inválida");
        }

        ProductionProductSeparation separation = productSeparation(parentProductId);

        data = new Object[separation.getBasicProducts().size()][3];

        for (int i = 0; i < separation.getBasicProducts().size(); i++) {
            Product product = separation.getBasicProducts().get(i).getProductCompositionChildProduct();
            data[i][0] = product.getProductName();
            data[i][1] = separation.getBasicProducts().get(i).getProductCompositionQuantity() * productionOrderQuantity;
            data[i][2] = product.getProductUnit().getUnitAcronym();
        }
        return data;
    }

    public static Object[][] getCompositeProducts(String id, String quantityProduce) {
        int parentProductId;
        double productionOrderQuantity;
        Object[][] data;

        try {
            if(ProductDAO.existsById(id)){
                parentProductId = Integer.parseInt(id);
            }else {
                throw new ProducitionOrderServiceException("O produto não existe");
            }
        }catch (Exception e){
            throw new ProducitionOrderServiceException("O produto não existe");
        }

        try {
            productionOrderQuantity = Double.parseDouble(quantityProduce);
        }catch (Exception e){
            throw new ProducitionOrderServiceException("Quantidade inválida");
        }

        ProductionProductSeparation separation = productSeparation(parentProductId);

        data = new Object[separation.getCompositeProducts().size()][3];

        for (int i = 0; i < separation.getCompositeProducts().size(); i++) {
            Product product = separation.getCompositeProducts().get(i).getProductCompositionChildProduct();
            data[i][0] = product.getProductName();
            data[i][1] = separation.getCompositeProducts().get(i).getProductCompositionQuantity() * productionOrderQuantity;
            data[i][2] = product.getProductUnit().getUnitAcronym();
        }
        return data;
    }

    public static void getPDFReport(String productionOrderId) {
        ProductionOrder productionOrder;
        String html;
        try {
            productionOrder = ProductionOrderDAO.getByProductionOrderId(Integer.parseInt(productionOrderId),true).get().getFirst();
        }catch (Exception e){
            throw new ProductionOrderControllerException("Erro ao obter ordem de produção");
        }
        try {
            html = PDFGenerator.generateHtml(productionOrder.getProductionOrderProduct().getProductName(),
                    productionOrder.getProductionOrderQuantity().toString(),
                    getBasicProducts(productionOrder.getProductionOrderProduct().getProductId().toString(), productionOrder.getProductionOrderQuantity().toString()),
                    getCompositeProducts(productionOrder.getProductionOrderProduct().getProductId().toString(), productionOrder.getProductionOrderQuantity().toString()),
                    productionOrder.getProductionOrderStatus(),
                    getProductionCost(productionOrder.getProductionOrderProduct().getProductId().toString(), productionOrder.getProductionOrderQuantity().toString()));
        }catch (Exception e){
            throw new ProductionOrderControllerException("Erro ao obter dados do relatório");
        }

        try {
            String outputPath = PathUtil.getCurrentDirectory() + File.separator + "report" + File.separator;
            File reportDir = new File(outputPath);

            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

           PDFGenerator.generatePDF(html, PathUtil.getCurrentDirectory()+"/report/report.pdf");
        }catch (Exception e){
            throw new ProducitionOrderServiceException("Falha ao gerar relatório");
        }
    }
}
