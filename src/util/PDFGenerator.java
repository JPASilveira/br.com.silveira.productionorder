package util;

import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;

public class PDFGenerator {

    public static String generateHtml(String productName, String quantityProduce, Object[][] basicProducts, Object[][] compositeProducts, String status, double productionCost) {
        String backgroundColorHex = "#FFFFFF";
        String textColorHex = "#000000";
        String borderColorHex = "#C6E7FF";
        String btnColorHex = "#C6E7FF";

        StringBuilder html = new StringBuilder();

        html.append("<html>")
                .append("<body style='background-color: ").append(backgroundColorHex).append("; color: ").append(textColorHex).append("; font-family: Arial, sans-serif;'>")
                .append("<h1 style='text-align: center; font-size: 28px;'>Ordem de Produção</h1>")
                .append("<p style='font-size: 18px;'><strong>Produto:</strong> ").append(productName).append("</p>")
                .append("<p style='font-size: 18px;'><strong>Quantidade:</strong> ").append(quantityProduce).append(" unidades</p>")
                .append("<div style='display: flex; justify-content: space-between; gap: 20px;'>")
                .append("<div style='flex: 1; box-sizing: border-box;'>")
                .append("<p style='font-size: 14px;'><strong>Insumos básicos:</strong></p>")
                .append("<table style='border-collapse: collapse; width: 100%; border: 1px solid ").append(borderColorHex).append(";'>")
                .append("<tr style='background-color: ").append(btnColorHex).append(";'>")
                .append("<th style='border: 1px solid ").append(borderColorHex).append("; padding: 8px;'>Produto</th>")
                .append("<th style='border: 1px solid ").append(borderColorHex).append("; padding: 8px;'>Quantidade</th>")
                .append("<th style='border: 1px solid ").append(borderColorHex).append("; padding: 8px;'>Unidade</th>")
                .append("</tr>");

        for (Object[] row : basicProducts) {
            html.append("<tr>")
                    .append("<td style='border: 1px solid ").append(borderColorHex).append("; padding: 8px;'>").append(row[0]).append("</td>")
                    .append("<td style='border: 1px solid ").append(borderColorHex).append("; padding: 8px;'>").append(row[1]).append("</td>")
                    .append("<td style='border: 1px solid ").append(borderColorHex).append("; padding: 8px;'>").append(row[2]).append("</td>")
                    .append("</tr>");
        }

        html.append("</table>")
                .append("</div>")

                .append("<div style='flex: 1; box-sizing: border-box;'>")
                .append("<p style='font-size: 14px;'><strong>Insumos compostos:</strong></p>")
                .append("<table style='border-collapse: collapse; width: 100%; border: 1px solid ").append(borderColorHex).append(";'>")
                .append("<tr style='background-color: ").append(btnColorHex).append(";'>")
                .append("<th style='border: 1px solid ").append(borderColorHex).append("; padding: 8px;'>Produto</th>")
                .append("<th style='border: 1px solid ").append(borderColorHex).append("; padding: 8px;'>Quantidade</th>")
                .append("<th style='border: 1px solid ").append(borderColorHex).append("; padding: 8px;'>Unidade</th>")
                .append("</tr>");

        for (Object[] row : compositeProducts) {
            html.append("<tr>")
                    .append("<td style='border: 1px solid ").append(borderColorHex).append("; padding: 8px;'>").append(row[0]).append("</td>")
                    .append("<td style='border: 1px solid ").append(borderColorHex).append("; padding: 8px;'>").append(row[1]).append("</td>")
                    .append("<td style='border: 1px solid ").append(borderColorHex).append("; padding: 8px;'>").append(row[2]).append("</td>")
                    .append("</tr>");
        }

        html.append("</table>")
                .append("</div>")
                .append("</div>")

                .append("<div style='clear: both; margin-top: 20px;'>")
                .append("<p style='font-size: 18px;'><strong>Status:</strong> ").append(status).append("</p>")
                .append("<p style='font-size: 18px;'><strong>Custo de produção:</strong> R$ ").append(String.format("%.2f", productionCost)).append("</p>")
                .append("</div>")
                .append("</body>")
                .append("</html>");

        return html.toString();
    }


    public static void generatePDF(String htmlContent, String outputPdfPath) throws IOException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();

        try (FileOutputStream fos = new FileOutputStream(outputPdfPath)) {
            renderer.createPDF(fos);
        }
    }
}