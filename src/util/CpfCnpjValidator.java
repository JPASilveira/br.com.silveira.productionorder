package util;

public class CpfCnpjValidator {
    public static boolean isValidCpfOrCnpj(String value) {
        String digits = value.replaceAll("\\D", ""); // Remove tudo que não for número

        if (digits.length() == 11) {
            return isValidCpf(digits);
        } else if (digits.length() == 14) {
            return isValidCnpj(digits);
        } else {
            return false;
        }
    }

    private static boolean isValidCpf(String cpf) {
        if (cpf.matches("(\\d)\\1{10}")) return false; // Verifica se todos os dígitos são iguais

        int[] weights1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        return calculateVerifier(cpf.substring(0, 9), weights1) == Character.getNumericValue(cpf.charAt(9))
                && calculateVerifier(cpf.substring(0, 10), weights2) == Character.getNumericValue(cpf.charAt(10));
    }

    private static boolean isValidCnpj(String cnpj) {
        if (cnpj.matches("(\\d)\\1{13}")) return false; // Verifica se todos os dígitos são iguais

        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        return calculateVerifier(cnpj.substring(0, 12), weights1) == Character.getNumericValue(cnpj.charAt(12))
                && calculateVerifier(cnpj.substring(0, 13), weights2) == Character.getNumericValue(cnpj.charAt(13));
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
