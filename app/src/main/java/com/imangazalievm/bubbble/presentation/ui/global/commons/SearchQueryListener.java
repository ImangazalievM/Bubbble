package com.imangazalievm.bubbble.presentation.ui.global.commons;

import android.support.v7.widget.SearchView;

public class SearchQueryListener implements SearchView.OnQueryTextListener {

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}
