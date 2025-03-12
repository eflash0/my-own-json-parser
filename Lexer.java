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
                    tokens.add(new JSONToken(JSONTokenType.STRING, readString()));
                    current++;
                    break;    
                default:
                    if(c == ' ') {
                        current++;
                    }
                    else if(Character.isLetter(c)){
                        String s = "";
                        s = readWord();
                        if(s.equals("true") || s.equals("false")) {
                            tokens.add(new JSONToken(JSONTokenType.BOOLEAN, s));
                        }
                        else if (s.equals("null")) {
                            tokens.add(new JSONToken(JSONTokenType.NULL, s));
                        }
                        else{
                            throw new IllegalArgumentException("unknown token : " + s);
                        }
                    }
                    else if(Character.isDigit(c) || c == '-') {
                        String s = readNumber();
                        if(!isNumber(s)){
                            throw new IllegalArgumentException("unknown token : " + s);
                        }
                        tokens.add(new JSONToken(JSONTokenType.NUMBER, s));
                    }
                    else{
                        throw new IllegalArgumentException("Unexpected character : " + c);
                    }
                    break;
            }
        }
        return tokens;
    }

    public String readString(){
        String s = "";
        char c = json.charAt(++current);
        while(c != '"'){
            s += c;
            c = json.charAt(++current);
            if(current >= json.length())
                throw new IllegalArgumentException("string unterminated");
        }
        return s;
    }

    public String readWord(){
        String s = "";
        while(current < json.length() && Character.isLetter(json.charAt(current))) {
            s+=json.charAt(current++);
        }
        return s;
    }

    public String readNumber(){
        String s = "";
        while(current < json.length() && Character.isDigit(json.charAt(current)) || 
        json.charAt(current) == '.' || json.charAt(current) == '-' || json.charAt(current) == 'e') {
            s+=json.charAt(current++);
        }
        return s;
    }

    public boolean isNumber(String s){
        try{
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }    
}