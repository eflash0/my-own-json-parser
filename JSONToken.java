public class JSONToken {

    private final JSONTokenType type;
    private final String value;
    
    public JSONToken(JSONTokenType type,String value){
        this.type = type;
        this.value = value;
    }

    public JSONTokenType getType(){
        return type;
    }

    public String getValue(){
        return value;
    }

    @Override
    public String toString() {
        return "type : " + type + " / " + "value : " + value + "\n";
    }

}
