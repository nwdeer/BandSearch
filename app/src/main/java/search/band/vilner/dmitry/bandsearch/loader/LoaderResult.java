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

    LoaderResult(T data) {
        this.data = data;
        this.isSuccessful = true;
    }

    LoaderResult(int errorCode) {
        this.errorCode = errorCode;
        this.isSuccessful = false;
    }

    LoaderResult(Exception exception) {
        this.exception = exception;
        this.isSuccessful = false;
    }
}
