package search.band.vilner.dmitry.bandsearch.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BandDetailsData {
    public String id;
    public Details details;
    public String photo;
    public List<Album> discography;

    public class Details {
        @SerializedName("country of origin") public String countryOfOrigin;
        public String genre;
        @SerializedName("years active") public String yearsActive;
    }

    public class Album {
        public String id;
        public String title;
        public String type;
        public String year;
    }
}
