package com.skysearch.itm.skysearch.ListLayout;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.skysearch.itm.skysearch.DTO.DTO_SCHD;
import com.skysearch.itm.skysearch.R;
import com.skysearch.itm.skysearch.Server.RetroService;
import com.skysearch.itm.skysearch.Util.DateParser;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
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
        getData();

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

    void processChannels(ArrayList<DTO_SCHD> list) {
        /* Adapter 생성 */
        stickyListHeadersListView = (StickyListHeadersListView) getView().findViewById(R.id.sticky_list_view);
        stickyListHeadersListView.setEmptyView(getView().findViewById(R.id.empty));
        stickyListHeadersListView.addHeaderView(FrameLayout.inflate(getContext(),R.layout.list_header, null));
        stickyListHeadersListView.addFooterView(FrameLayout.inflate(getContext(), R.layout.list_footer, null));
        stickyListHeadersListView.setAdapter(new StickyBaseAdapter(getContext(), list));
        stickyListHeadersListView.setDrawingListUnderStickyHeader(true);
        stickyListHeadersListView.setAreHeadersSticky(true);
        stickyListHeadersListView.setOnItemClickListener(this);
        stickyListHeadersListView.setOnHeaderClickListener(this);
        stickyListHeadersListView.setOnStickyHeaderChangedListener(this);
        stickyListHeadersListView.setOnStickyHeaderOffsetChangedListener(this);



        stickyListHeadersListView.setStickyHeaderTopOffset(-20);
    }


    public void getData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://211.211.54.158:3000/").addConverterFactory(GsonConverterFactory.create()).build();
        RetroService service = retrofit.create(RetroService.class);

        Call<JsonArray> req = service.getSchedule(101);
        req.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                ArrayList<DTO_SCHD> channelList = new ArrayList<>();

                //Log.d("Test", response.body().toString());

                JsonArray mList = response.body();
                for (JsonElement item : mList) {
                    JsonObject itemJson = item.getAsJsonObject();

                    DTO_SCHD targetChannel = new DTO_SCHD();
                    targetChannel.setSchdId(itemJson.get("SCHD_id").getAsInt());
                    targetChannel.setChId(itemJson.get("CH_id").getAsInt());
                    targetChannel.setEpId(itemJson.get("EP_id").getAsInt());
                    targetChannel.setChannelName(itemJson.get("CH_NAME").getAsString());
                    targetChannel.setActn(itemJson.get("ACTN").getAsString());
                    targetChannel.setTitle(itemJson.get("TITLE").getAsString());
                    targetChannel.setStTime(DateParser.setLocale(itemJson.get("ST_TIME").getAsString()));
                    targetChannel.setEnTime(DateParser.setLocale(itemJson.get("EN_TIME").getAsString()));

                    channelList.add(targetChannel);


                }
                processChannels(channelList);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });

    }
}