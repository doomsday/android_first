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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * To create a fragment, extend the Fragment class, then override key lifecycle methods to insert
 * your app logic, similar to the way you would with an Activity class.
 */
public class ArticleFragment extends Fragment {
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;

    // Called to have the fragment instantiate its user interface view.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore the previous article
        // selection set by onSaveInstanceState(). This is primarily necessary when in the two-pane
        // layout.
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        // 1. Inflate a new view hierarchy from the specified XML resource.
        // 2. Return the View for the fragment's UI, or null.
        return inflater.inflate(R.layout.article_view, container, false);
    }

    // Called when the Fragment is visible to the user.
    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment. onStart is a good
        // place to do this because the layout has already been applied to the fragment at this
        // point so we can safely call the method below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in.
            updateArticleView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView.
            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(int position) {
        // When layout for large screen is used, 'article' id is ignored in favor of predefined
        // article_fragment at the time of the fragment initialization.
        // https://code.google.com/p/android/issues/detail?id=220738
        TextView article;
        View view = getActivity().findViewById(R.id.fragment_container);
        if (view == null) {
            article = (TextView) getActivity().findViewById(R.id.article_fragment); // large
        } else {
            article = (TextView) getActivity().findViewById(R.id.article);  // small
        }
//        TextView article = (TextView) getActivity().findViewById(R.id.article); // FIXME: 17-Mar-17 Crash
//        TextView article = (TextView) getActivity().findViewById(getActivity().findViewById(R.id.fragment_container) == null ? R.id.article_fragment : R.id.article);
        article.setText(Ipsum.Articles[position]);
        mCurrentPosition = position;
    }

    // Called to ask the fragment to save its current dynamic state, so it can later be
    // reconstructed in a new instance of its process is restarted
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
}