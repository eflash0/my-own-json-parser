import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("tests/step4/valid.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();
            Lexer lexer = new Lexer(json);
            List<JSONToken> tokens = new ArrayList<>();
            tokens = lexer.tokenize();
            System.out.println(tokens);
        } 
        catch (IOException e) {
            e.printStackTrace();;
        }
    }
}
