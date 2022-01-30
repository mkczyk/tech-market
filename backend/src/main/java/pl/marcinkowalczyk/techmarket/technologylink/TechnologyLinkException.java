package pl.marcinkowalczyk.techmarket.technologylink;

public class TechnologyLinkException extends RuntimeException {

    public TechnologyLinkException(String message) {
        super(message);
    }

    public TechnologyLinkException(String message, Throwable cause) {
        super(message, cause);
    }
}
