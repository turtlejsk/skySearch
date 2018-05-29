package com.skysearch.itm.skysearch.ListLayout;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.skysearch.itm.skysearch.DB.DBHandler_schd;
import com.skysearch.itm.skysearch.DTO.DTO_SCHD;
import com.skysearch.itm.skysearch.R;
import com.skysearch.itm.skysearch.Util.DateParser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import static com.skysearch.itm.skysearch.Util.DateParser.compare;

public class StickyBaseAdapter extends BaseAdapter implements  StickyListHeadersAdapter, SectionIndexer {

    private final Context mContext;
    private LayoutInflater mInflater;

    private int[] mSectionIndices;
    private String[] mSectionDates;
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> times = new ArrayList<>();

    List<DTO_SCHD> mList=null;

    private static final int ITEM_VIEW_TYPE_FUTURE = 0;
    private static final int ITEM_VIEW_TYPE_NOW = 1;
    private static final int ITEM_VIEW_TYPE_PAST = 2;
    private static final int ITEM_VIEW_TYPE_MAX = 3;



    public StickyBaseAdapter(Context context, List<DTO_SCHD> tempList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);

        mList = tempList;

        for (int i = 0; i < mList.size() ; i++) {
            int type = DateParser.compare(mList.get(i).getStTime());
            mList.get(i).setType(type);
        }

        mSectionIndices = getSectionIndices();
        mSectionDates = getSectionDates();
    }


    // time의 첫번째 숫자가 작아졌을때 section 생김 14시 -> 18시 -> 23시 ->(섹션) -> 1시
    private int[] getSectionIndices() {
        ArrayList<Integer> sectionIndices = new ArrayList<Integer>();
        sectionIndices.add(0);
        for (int i = 1; i < mList.size(); i++) {
            if (DateParser.compare(mList.get(i).getStTime())-DateParser.compare(mList.get(i-1).getStTime())!=0) {
                Log.d("getSectionIndice","add");
                sectionIndices.add(i);
            }
        }
        int[] sections = new int[sectionIndices.size()];
        for (int i = 0; i < sectionIndices.size(); i++) { // arraylist -> int[] 바꿔줌
            sections[i] = sectionIndices.get(i);
        }
        return sections;
    }

    // section이 바뀔 때 날짜(header) 바뀜
    public String[] getSectionDates() {
        String[] dates = new String[mSectionIndices.length];
        for (int i = 0; i<mSectionIndices.length; i++) {
            dates[i] = mList.get(mSectionIndices[i]).getStTime();
            //Log.d("getSectionDates","add");
        }
        return dates;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_MAX;
    }

    // item을 inflate, item에서 실제로 보여줄 것
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        int viewType = mList.get(position).getType();
        Log.d("ViewTest",String.valueOf(mList.get(position).getType()));
        if (convertView == null) {
            holder = new ViewHolder();
            switch (viewType) {
                case ITEM_VIEW_TYPE_PAST :
                    convertView = mInflater.inflate(R.layout.list_item_past, parent, false);
                    holder.timeText = (TextView) convertView.findViewById(R.id.time_textview);
                    holder.titleText = (TextView) convertView.findViewById(R.id.title_textview);

                    holder.timeText.setText(mList.get(position).getStTime());

                    holder.titleText.setText(mList.get(position).getTitle());
                    Log.d("ViewTest",mList.get(position).getTitle());
                    break;

                case ITEM_VIEW_TYPE_NOW :
                    convertView = mInflater.inflate(R.layout.list_item_now, parent, false);
                    holder.titleText = (TextView) convertView.findViewById(R.id.title_textview);

                    holder.titleText.setText(mList.get(position).getTitle());

                    break;

                case ITEM_VIEW_TYPE_FUTURE :
                    convertView = mInflater.inflate(R.layout.list_item_future, parent, false);
                    holder.timeText = (TextView) convertView.findViewById(R.id.time_textview);
                    holder.titleText = (TextView) convertView.findViewById(R.id.title_textview);
                    holder.reserveButton = (Button) convertView.findViewById(R.id.reserve_button);

                    holder.timeText.setText(mList.get(position).getStTime().substring(11,16));
                    holder.titleText.setText(mList.get(position).getTitle());
                    holder.reserveButton.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mList.get(position).isReserved()) {
                                holder.reserveButton.setBackgroundResource(R.drawable.reserve_off);
                                mList.get(position).setReserved(false);
                                Toast.makeText(mContext, "예약이 취소되었습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            holder.reserveButton.setBackgroundResource(R.drawable.reserve_on);
                            mList.get(position).setReserved(true);
                            Toast.makeText(mContext, "예약이 설정되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }


    // header를 inflate, header에서 실제로 보여줄 것
    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;

        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = mInflater.inflate(R.layout.header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.header_text);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        // set header text as first char in name
        CharSequence headerChar = mList.get(position).getStTime().substring(0,10);
        holder.text.setText(headerChar);

        return convertView;
    }

    @Override
    public long getHeaderId(int position) { // 특정 item의 position을 보고 header id 반환
        // HeaderId가 같은 item들끼리 같은 section에 넣어버리므로, 같은 section에 들어갈 애들끼리 같은 id를 리턴해줘야해
        //Log.d("getHeader",mList.get(position).getStTime().substring(8,10));
        return Integer.parseInt(mList.get(position).getStTime().substring(8,10)); // date : 17일, 18일 -> header id : 7, 8
    }

    @Override
    public int getPositionForSection(int section) {
        if (mSectionIndices.length == 0) {
            return 0;
        }

        if (section >= mSectionIndices.length) {
            section = mSectionIndices.length - 1;
        } else if (section < 0) {
            section = 0;
        }

        return mSectionIndices[section];
    }

    @Override
    public int getSectionForPosition(int position) {
        for (int i = 0; i < mSectionIndices.length; i++) {
            if (position < mSectionIndices[i]) {
                return i - 1;
            }
        }
        return mSectionIndices.length - 1;
    }

    @Override
    public Object[] getSections() {
        return mSectionDates;
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        TextView timeText;
        TextView titleText;
        Button reserveButton;
    }
}
