package search.band.vilner.dmitry.bandsearch.network.model;


public class ResponseData<T> {
    public String status;
    public int code;
    public String message;
    public T data;

    @Override
    public String toString() {
        return "ResponseData{" +
                "status='" + status + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
