package fr.polytech.netflix.exceptions;

public class ResourceAlreadyExistException extends RuntimeException {

    public ResourceAlreadyExistException(String message) {
        super(message);
    }
}
