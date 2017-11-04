package search.band.vilner.dmitry.bandsearch.network.model;

public class BandShortInfo {
    public String name;
    public String id;
    public String genre;
    public String country;

    @Override
    public String toString() {
        return "BandShortInfo{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", genre='" + genre + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
