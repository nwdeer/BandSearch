package search.band.vilner.dmitry.bandsearch.provider;

import android.content.SearchRecentSuggestionsProvider;

public class RecentSearchContentProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = "search.band.vilner.dmitry.recents.provider.RecentSearchContentProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;

    public RecentSearchContentProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
