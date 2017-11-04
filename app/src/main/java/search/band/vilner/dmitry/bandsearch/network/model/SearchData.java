package search.band.vilner.dmitry.bandsearch.network.model;


import java.util.List;

public class SearchData {
    public String query;
    public List<BandShortInfo> search_results;

    public class BandShortInfo {
        public String name;
        public String id;
        public String genre;
        public String country;
    }
}
