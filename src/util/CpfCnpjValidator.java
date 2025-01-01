package util;

public class CpfCnpjValidator {
    public static boolean isValidCpfOrCnpj(String value) {
        String digits = value.replaceAll("\\D", "");

        if (digits.length() == 11) {
            return isValidCpf(digits);
        } else if (digits.length() == 14) {
            return isValidCnpj(digits);
        } else {
            return false;
        }
    }

    public static boolean isValidCpf(String cpf) {
        if (cpf.matches("(\\d)\\1{10}")) return false; // Verifica se todos os dígitos são iguais

        int[] weights1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        try {
            return calculateVerifier(cpf.substring(0, 9), weights1) == Character.getNumericValue(cpf.charAt(9))
                    && calculateVerifier(cpf.substring(0, 10), weights2) == Character.getNumericValue(cpf.charAt(10));
        } catch (Exception e) {
            return false;
        }

    }

    public static boolean isValidCnpj(String cnpj) {
        if (cnpj.matches("(\\d)\\1{13}")) return false; // Verifica se todos os dígitos são iguais

        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        try {
            return calculateVerifier(cnpj.substring(0, 12), weights1) == Character.getNumericValue(cnpj.charAt(12))
                    && calculateVerifier(cnpj.substring(0, 13), weights2) == Character.getNumericValue(cnpj.charAt(13));
        } catch (Exception e) {
            return false;
        }

    }

    private static int calculateVerifier(String base, int[] weights) {
        int sum = 0;
        for (int i = 0; i < base.length(); i++) {
            sum += Character.getNumericValue(base.charAt(i)) * weights[i];
        }
        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }
}
