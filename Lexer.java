import java.util.ArrayList;
import java.util.List;

public class Lexer{

    private String json;
    private int current = 0;

    public Lexer(String json){
        this.json = json.trim();
    }

    public List<JSONToken> tokenize(){
        List<JSONToken> tokens = new ArrayList<>();
        while(current < json.length()) {
            char c = json.charAt(current);
            switch (c) {
                case '{':
                    tokens.add(new JSONToken(JSONTokenType.START_OBJECT, "{"));
                    current++;
                    break;
                case '}':
                    tokens.add(new JSONToken(JSONTokenType.END_OBJECT, "}"));
                    current++;
                    break;
                case '[':
                    tokens.add(new JSONToken(JSONTokenType.START_ARRAY, "["));
                    current++;
                    break;  
                case ']':
                    tokens.add(new JSONToken(JSONTokenType.END_ARRAY, "]"));
                    current++;
                    break;  
                case ':':
                    tokens.add(new JSONToken(JSONTokenType.COLON, ":"));
                    current++;
                    break;    
                case ',':
                    tokens.add(new JSONToken(JSONTokenType.COMMA, ","));
                    current++;
                    break;
                case '"':
                    String s = "";
                    c = json.charAt(++current);
                    while(c != '"') {
                        s += c;
                        c = json.charAt(++current);
                    }
                    tokens.add(new JSONToken(JSONTokenType.STRING, s));
                    current++;
                    break;
                default:
                    break;
            }
        }
    }

    public String readString(int position){
        String s = "";
        char c = json.charAt(++position);
        while(c != '"'){
            s += c;
            c = json.charAt(++position);
        }
    }
}