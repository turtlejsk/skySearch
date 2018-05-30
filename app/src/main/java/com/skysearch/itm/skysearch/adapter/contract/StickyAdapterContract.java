package com.skysearch.itm.skysearch.adapter.contract;

import com.skysearch.itm.skysearch.DTO.DTO_SCHD;
import com.skysearch.itm.skysearch.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public interface StickyAdapterContract {
    interface View {

        void setOnClickListener(OnItemClickListener clickListener);

        void notifyAdapter();
    }

    interface Model {

        void addItems(ArrayList<DTO_SCHD> items);

        void clearItem();

        DTO_SCHD getItem(int position);
    }
}
