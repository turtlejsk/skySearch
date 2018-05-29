package com.skysearch.itm.skysearch.ListLayout;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.skysearch.itm.skysearch.R;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class PlaceholderFragment extends Fragment implements
        AdapterView.OnItemClickListener, StickyListHeadersListView.OnHeaderClickListener,
        StickyListHeadersListView.OnStickyHeaderOffsetChangedListener,
        StickyListHeadersListView.OnStickyHeaderChangedListener{

    private static final String ARG_SECTION_NUMBER = "section_number";
    StickyListHeadersListView stickyListHeadersListView;

    private boolean fadeHeader = false; // 조금 투명한 header 뒤로 item 글자 보이도록

    public PlaceholderFragment() {
        // Required empty public constructor
    }

    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment, container,false);
        stickyListHeadersListView = (StickyListHeadersListView)view.findViewById(R.id.sticky_list_view);
        stickyListHeadersListView.setEmptyView(view.findViewById(R.id.empty));

        stickyListHeadersListView.setAdapter(new StickyBaseAdapter(container.getContext()));
        stickyListHeadersListView.setDrawingListUnderStickyHeader(true);
        stickyListHeadersListView.setAreHeadersSticky(true);
        stickyListHeadersListView.setOnItemClickListener(this);
        stickyListHeadersListView.setOnHeaderClickListener(this);
        stickyListHeadersListView.setOnStickyHeaderChangedListener(this);
        stickyListHeadersListView.setOnStickyHeaderOffsetChangedListener(this);

        stickyListHeadersListView.addHeaderView(inflater.inflate(R.layout.list_header, null));
        stickyListHeadersListView.addFooterView(inflater.inflate(R.layout.list_footer, null));

        stickyListHeadersListView.setStickyHeaderTopOffset(-20);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(view.getContext(), "Item " + position + " clicked!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
        Toast.makeText(header.getContext(), "Header " + headerId + " currentlySticky ? " + currentlySticky, Toast.LENGTH_SHORT).show();
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onStickyHeaderOffsetChanged(StickyListHeadersListView l, View header, int offset) {
        if (fadeHeader && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            header.setAlpha(1 - (offset / (float) header.getMeasuredHeight()));
        }
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onStickyHeaderChanged(StickyListHeadersListView l, View header, int itemPosition, long headerId) {
        header.setAlpha(1);
    }
}