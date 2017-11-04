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

        @Override
        public String toString() {
            return "Details{" +
                    "countryOfOrigin='" + countryOfOrigin + '\'' +
                    ", genre='" + genre + '\'' +
                    ", yearsActive='" + yearsActive + '\'' +
                    '}';
        }
    }

    public class Album {
        public String id;
        public String title;
        public String type;
        public String year;

        @Override
        public String toString() {
            return "Album{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", type='" + type + '\'' +
                    ", year='" + year + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BandDetailsData{" +
                "id='" + id + '\'' +
                ", details=" + details +
                ", photo='" + photo + '\'' +
                ", discography=" + discography +
                '}';
    }
}
