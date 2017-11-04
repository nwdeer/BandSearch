package search.band.vilner.dmitry.bandsearch.network.model;


import java.util.List;

public class SearchData {
    public String query;
    public List<BandShortInfo> search_results;

    @Override
    public String toString() {
        return "SearchData{" +
                "query='" + query + '\'' +
                ", search_results=" + search_results +
                '}';
    }

}
