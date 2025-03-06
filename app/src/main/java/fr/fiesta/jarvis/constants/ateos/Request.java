package fr.fiesta.jarvis.constants.ateos;

public enum Request {
    STATUS("MOT DE PASSE:%s\nSTATUT DU SYSTEME");

    private final String message;

    Request(String message) {
        this.message = message;
    }

    public String getValue() {
        return message;
    }
}
