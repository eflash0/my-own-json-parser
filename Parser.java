import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    private List<JSONToken> tokens;
    private int index = 0;

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
        if(tokens == null || tokens.isEmpty())
            throw new IllegalArgumentException("nothing to parse");
        JSONToken token = tokens.get(index);
        // System.out.println(token.getValue());
        if(token.getType() == JSONTokenType.START_OBJECT){
            index++;
            while (index < tokens.size() && tokens.get(index).getType() != JSONTokenType.END_OBJECT) {
                JSONToken keyToken = tokens.get(index++);
                if(keyToken.getType() != JSONTokenType.STRING) {
                    throw new IllegalArgumentException("key must be a string");
                }
                String key = keyToken.getValue();
                System.out.println(key);

                JSONToken colonToken = tokens.get(index++);
                if(colonToken.getType() != JSONTokenType.COLON)
                    throw new IllegalArgumentException("expected a colon after key");

                token = tokens.get(index++);
                System.out.println(token.getValue());
                jsonObject.put(key, parseObject(token));

                if(tokens.get(index).getType() == JSONTokenType.COMMA)
                    index++;
            }    
        }
        index++;
        return jsonObject;
    }

    public Object parseObject(JSONToken token){
        switch (token.getType()) {
            case STRING:
                return parseString(token);
            case NUMBER:
                return parseNumber(token);
            case BOOLEAN:
                return parseBoolean(token);
            case NULL:
                return null;
            case START_ARRAY:
                return parseArray();
            case START_OBJECT:
                --index;
                return parse();
            default:
                throw new IllegalArgumentException("unexpected token");
        }
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

    public boolean parseBoolean(JSONToken token){
        if(token.getType() == JSONTokenType.BOOLEAN) {
            if(token.getValue() == "true") {
                return true;
            }
            else{
                return false;
            }
        }
        throw new IllegalArgumentException("unexpected value as boolean");
    }

    public List<Object> parseArray(){
        List<Object> list = new ArrayList<>();
        while(index < tokens.size() && tokens.get(index).getType() != JSONTokenType.END_ARRAY) {
            Object object = parseObject(tokens.get(index++));
            list.add(object);
            if(tokens.get(index).getType() == JSONTokenType.COMMA) {
                index++;
            }
        }
        index++;
        return list;
    }
}
