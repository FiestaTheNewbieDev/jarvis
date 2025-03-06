package fr.fiesta.jarvis.constants.ateos;

public enum Response {
    STATUS(new String[]{"Alarme armee", "Alarme partielle", "Alarme desarmee"});

    private final Object value;

    Response(Object value) {
        if (value instanceof String || value instanceof String[]) this.value = value;
        else throw new IllegalArgumentException("Value must be a String or a String array");
    }

    public Object getValue() {
        return value;
    }
}
