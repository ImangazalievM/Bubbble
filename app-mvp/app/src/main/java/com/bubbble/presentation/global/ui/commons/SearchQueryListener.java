package com.bubbble.presentation.global.ui.commons;

import androidx.appcompat.widget.SearchView;

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
