public class SimpleVariable implements Variable{

    String name;
    String value;
    boolean temp;

    public SimpleVariable(String name, String value){
        this.name = name;
        this.value = value;
        this.temp = false;
    }

    public SimpleVariable(String name, String value, boolean isTemporary){
        this.name = name;
        this.value = value;
        this.temp = isTemporary;
    }

    public String getVariableCall(){
        return "var(--" + name + ")";
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean isTemporary() {
        return temp;
    }

    @Override
    public String getType() {
        return "var";
    }
}
