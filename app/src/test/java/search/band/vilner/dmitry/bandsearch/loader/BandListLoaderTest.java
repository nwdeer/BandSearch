package search.band.vilner.dmitry.bandsearch.loader;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import search.band.vilner.dmitry.bandsearch.BuildConfig;
import search.band.vilner.dmitry.bandsearch.network.BandService;
import search.band.vilner.dmitry.bandsearch.network.model.BandShortInfo;
import search.band.vilner.dmitry.bandsearch.network.model.ResponseData;
import search.band.vilner.dmitry.bandsearch.network.model.SearchData;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*" })
@PrepareForTest(BandService.class)
public class BandListLoaderTest {
    private BandService service;

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Before
    public void setup() {
        mockStatic(BandService.class);
        service = mock(BandService.class);
        when(BandService.getInstance()).thenReturn(service);
    }

    @Test
    public void successfulRequestReturnsSuccessfulResult() throws IOException {
        ResponseData<SearchData> responseData = new ResponseData<>();
        responseData.code = 200;
        responseData.data = new SearchData();
        List<BandShortInfo> expectedSearchData = Collections.singletonList(new BandShortInfo());
        responseData.data.search_results = expectedSearchData;
        when(service.searchByBandName("searchTerm")).thenReturn(responseData);
        BandListLoader loader = new BandListLoader(RuntimeEnvironment.application, "searchTerm");
        LoaderResult<List<BandShortInfo>> listLoaderResult = loader.loadInBackground();
        assertTrue(listLoaderResult.isSuccessful);
        assertEquals(expectedSearchData, listLoaderResult.data);
    }

    @Test
    public void OnErrorResponseCodeReturnsErrorResult() throws IOException {
        ResponseData<SearchData> responseData = new ResponseData<>();
        responseData.code = 404;
        when(service.searchByBandName("searchTerm")).thenReturn(responseData);
        BandListLoader loader = new BandListLoader(RuntimeEnvironment.application, "searchTerm");
        LoaderResult<List<BandShortInfo>> listLoaderResult = loader.loadInBackground();
        assertFalse(listLoaderResult.isSuccessful);
        assertEquals(404, listLoaderResult.errorCode);
    }

    @Test
    public void OnExceptionCodeReturnsErrorResult() throws IOException {
        when(service.searchByBandName("searchTerm")).thenThrow(new IOException());
        BandListLoader loader = new BandListLoader(RuntimeEnvironment.application, "searchTerm");
        LoaderResult<List<BandShortInfo>> listLoaderResult = loader.loadInBackground();
        assertFalse(listLoaderResult.isSuccessful);
        assertTrue(listLoaderResult.exception.getClass().getName().equals(IOException.class.getName()));
    }
}
