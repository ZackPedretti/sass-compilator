import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ListVariable implements Variable {

    Variable[] values;
    boolean temp;

    public ListVariable(Variable[] values) {
        this.values = values;
    }

    public ListVariable(Variable[] values, boolean temp) {
        this.values = values;
        this.temp = temp;
    }

    @Override
    public String getValue() {
        return Arrays.stream(values)
                .map(Variable::getValue)
                .reduce("", (acc, arg) -> acc + ", " + arg).substring(2);
    }

    public String getValue(int i) {
        return values[i].getValue();
    }

    public Variable[] getValues(){
        return values;
    }

    public int length(){
        return values.length;
    }

    @Override
    public boolean isTemporary() {
        return temp;
    }

    @Override
    public String getType() {
        return "list";
    }
}
