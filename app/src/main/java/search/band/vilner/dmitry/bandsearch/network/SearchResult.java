package search.band.vilner.dmitry.bandsearch.network;


import java.util.List;

public class SearchResult {
    public String status;
    public int code;
    public String message;
    public Data data;


    public class Data {
        public String query;
        public List<Result> search_results;
    }

    public class Result {
        public String name;
        public String id;
        public String genre;
        public String country;
    }
}
