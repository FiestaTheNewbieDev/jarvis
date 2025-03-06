package fr.fiesta.jarvis;

public interface ISmsListener {
    void onSmsReceived(String sender, String message);
}
