package util;

public class DecimalValidator {
    public static boolean isValid(String value) {
            // Regex para DECIMAL(10, 2)
            String decimalPattern = "^\\d{1,7}(\\.\\d{1,2})?$";

            if (value == null || !value.matches(decimalPattern)) {
                return false;
            }

            // Verificar o tamanho total de até 10 caracteres (incluindo o ponto)
            return value.replace(".", "").length() <= 10;
    }
}
