/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.fragments;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/* A Fragment is a piece of an application's user interface or behavior that can be placed in an
 * Activity. Interaction with fragments is done through FragmentManager, which can be obtained via
 * Activity.getFragmentManager() and Fragment.getFragmentManager().
 */

/* ListFragment: a fragment that displays a list of items by binding to a data source such as an
 * array or Cursor, and exposes event handlers when the user selects an item. ListFragment hosts a
 * ListView object that can be bound to different data sources.
 */
public class HeadlinesFragment extends ListFragment {

  OnHeadlineSelectedListener mCallback;

  // The container Activity must implement this interface so the frag can deliver messages.
  public interface OnHeadlineSelectedListener {

    // Called by HeadlinesFragment when a list item is selected.
    public void onArticleSelected(int position);
  }

  // Called to do initial creation of a fragment.
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // We need to use a different list item layout for devices older than Honeycomb
    int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
        android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

    // Create an array adapter for the list view, using the Ipsum headlines array
    setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Ipsum.Headlines));
  }

  // Called when the Fragment is visible to the user.
  @Override
  public void onStart() {
    super.onStart();

    // When in two-pane layout, set the listview to highlight the selected list item
    // (We do this during onStart because at the point the listview is available.)
    if (getFragmentManager().findFragmentById(R.id.article_fragment) != null) {
      getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
  }

  // Called when a fragment is first attached to its activity.
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);

    // This makes sure that the container activity has implemented
    // the callback interface. If not, it throws an exception.
    try {
      mCallback = (OnHeadlineSelectedListener) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(activity.toString()
          + " must implement OnHeadlineSelectedListener");
    }
  }

  // This method will be called when an item in the list is selected.
  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    // Notify the parent activity of selected item.
    mCallback.onArticleSelected(position);

    // Set the item as checked to be highlighted when in two-pane layout.
    getListView().setItemChecked(position, true);
  }
}