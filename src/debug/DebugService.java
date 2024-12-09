package debug;

import com.fasterxml.jackson.databind.JsonNode;
import util.BrasilAPI;
import util.CpfCnpjValidator;

import java.io.IOException;

public class DebugService {
    public static void main(String[] args) throws IOException, InterruptedException {
        //JsonNode teste = BrasilAPI.getCnpjData("08020035000102");
        //System.out.println(teste.toPrettyString());
        System.out.println(CpfCnpjValidator.isValidCnpj("0125932061"));
    }
}
