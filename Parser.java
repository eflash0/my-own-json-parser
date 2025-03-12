import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    private List<JSONToken> tokens;

    public Parser(){
        tokens = new ArrayList<>();
    }

    public Parser(List<JSONToken> tokens){
        this.tokens = tokens;
    }

    public List<JSONToken> getTokens(){
        return tokens;
    }

    public Map<String,Object> parse(){
        Map<String,Object> jsonObject = new HashMap<>();
        if(tokens == null || !tokens.isEmpty())
            throw new IllegalArgumentException("nothing to parse");

    }

    public Object parseNumber(JSONToken token){
        if(token.getType() == JSONTokenType.NUMBER){
            try {
                if(token.getValue().contains(".") || token.getValue().contains("e"))
                    return Double.parseDouble(token.getValue());
                else{
                    try {
                        return Integer.parseInt(token.getValue());
                    } catch (NumberFormatException e) {
                        return Long.parseLong(token.getValue());
                    }
                    
                }    
            } catch (NumberFormatException e) {
                throw new NumberFormatException("invalid number format");
            }
        }
        throw new IllegalArgumentException("token is not a token");
    }

    public String parseString(JSONToken token){
        if(token.getType() == JSONTokenType.STRING)
            return new String(token.getValue());
        throw new IllegalArgumentException("Expected a string");    
    }

    
}
