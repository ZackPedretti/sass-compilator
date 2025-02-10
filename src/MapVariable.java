import java.util.HashMap;
import java.util.Set;

public class MapVariable implements Variable {

    HashMap<String, Variable> values;
    boolean temp;

    public MapVariable(HashMap<String, Variable> values) {
        this.values = values;
        temp = false;
    }

    public MapVariable(HashMap<String, Variable> values, boolean temp) {
        this.values = values;
        this.temp = temp;
    }

    @Override
    public String getValue() {
        return values.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue().getValue())
                .reduce((entry1, entry2) -> entry1 + ",\n        " + entry2)
                .orElse("");
    }

    public String getValue(String key){
        return values.get(key).getValue();
    }

    public Set<String> getKeySet(){
        return values.keySet();
    }

    public boolean contains(String key){
        return values.containsKey(key);
    }

    @Override
    public boolean isTemporary() {
        return temp;
    }

    @Override
    public String getType() {
        return "map";
    }
}
