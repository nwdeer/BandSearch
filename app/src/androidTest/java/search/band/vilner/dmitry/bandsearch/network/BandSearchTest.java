package search.band.vilner.dmitry.bandsearch.network;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import search.band.vilner.dmitry.bandsearch.network.model.ResponseData;
import search.band.vilner.dmitry.bandsearch.network.model.SearchData;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class BandSearchTest {
    public BandService service = BandService.getInstance();

    @Test
    public void searchByName() throws Exception {
        ResponseData<SearchData> searchResult = service.searchByBandName("Metallica");
        SearchData data = searchResult.data;
        assertNotNull(data);
        List<SearchData.BandShortInfo> search_results = data.search_results;
        assertEquals(1, search_results.size());
        SearchData.BandShortInfo result = search_results.get(0);
        assertNotNull(result.country);
        assertNotNull(result.genre);
        assertNotNull(result.id);
        assertNotNull(result.name);
    }
}
