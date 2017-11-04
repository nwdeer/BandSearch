package search.band.vilner.dmitry.bandsearch.network;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class BandSearchTest {
    public BandService service = BandService.getInstance();

    @Test
    public void searchByName() throws Exception {
        SearchResult searchResult = service.searchByBandName("Metallica");
        SearchResult.Data data = searchResult.data;
        assertNotNull(data);
        List<SearchResult.Result> search_results = data.search_results;
        assertEquals(1, search_results.size());
        SearchResult.Result result = search_results.get(0);
        assertNotNull(result.country);
        assertNotNull(result.genre);
        assertNotNull(result.id);
        assertNotNull(result.name);
    }
}
