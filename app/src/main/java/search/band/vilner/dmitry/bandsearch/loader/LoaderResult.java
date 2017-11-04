package search.band.vilner.dmitry.bandsearch.loader;

public class LoaderResult <T> {
    /**
     * In case of successful operation will contain a result
     */
    public T data;

    /**
     * Indicates whether the operatoin was successful
     */
    public final boolean isSuccessful;

    /**
     * Error code (if any)
     */
    public int errorCode;

    /**
     * In case of unsuccesful operation might contain an exception
     */
    public Exception exception;

    public LoaderResult(T data) {
        this.data = data;
        this.isSuccessful = true;
    }

    public LoaderResult(int errorCode) {
        this.errorCode = errorCode;
        this.isSuccessful = false;
    }

    public LoaderResult(Exception exception) {
        this.exception = exception;
        this.isSuccessful = false;
    }
}
