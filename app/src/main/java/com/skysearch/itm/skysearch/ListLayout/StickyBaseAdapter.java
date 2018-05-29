package com.skysearch.itm.skysearch.ListLayout;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.skysearch.itm.skysearch.DB.DBHandler_schd;
import com.skysearch.itm.skysearch.R;

import java.sql.SQLException;
import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class StickyBaseAdapter extends BaseAdapter implements  StickyListHeadersAdapter, SectionIndexer {

    private final Context mContext;
    private LayoutInflater mInflater;
    private String[] mTimes;
    private String[] mTitles;
    private String[] mDates;
    private int[] mSectionIndices;
    private String[] mSectionDates;
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> times = new ArrayList<>();

    private ArrayList<Program> programList = new ArrayList<Program>() ;

    private static final int ITEM_VIEW_TYPE_FUTURE = 0;
    private static final int ITEM_VIEW_TYPE_NOW = 1;
    private static final int ITEM_VIEW_TYPE_PAST = 2;
    private static final int ITEM_VIEW_TYPE_MAX = 3;

    private static final String NOW_DATE = "2018년 5월 17일 목요일";
    private static final String NOW_TIME = "15:00";
    DBHandler_schd dbHandler_schd;

    public StickyBaseAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mTitles = context.getResources().getStringArray(R.array.JTBC_title);
        mTimes = context.getResources().getStringArray(R.array.JTBC_time);
        mDates = context.getResources().getStringArray(R.array.JTBC_date);

        try {
            dbHandler_schd = DBHandler_schd.open(mContext);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String temp= null;
        Cursor cursor =null;
        try {
            cursor=dbHandler_schd.select(101,"2018-02-16 00:00:00");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (cursor != null && cursor.getCount() != 0) {
            while (!cursor.isAfterLast()) {
                temp = cursor.getString(0);
                titles.add(temp);
                temp = cursor.getString(3);
                times.add(temp);
                cursor.moveToNext();
            }
        }

        int indexNow=0;

        for (int i = 0; i < mTimes.length ; i++) {
            if ((mDates[i].equals(NOW_DATE)) && (mTimes[i].equals(NOW_TIME))) {
                indexNow = i;
            }
        }

        //times.get(i);

        for (int i = 0; i < mTimes.length ; i++) {
            if (i < indexNow) {
                addItem(ITEM_VIEW_TYPE_PAST, mTimes[i], mTitles[i]);
            } else if(i == indexNow) {
                addItem(ITEM_VIEW_TYPE_NOW, mTitles[i]);
            } else if(i > indexNow){
                addItem(ITEM_VIEW_TYPE_FUTURE, mTimes[i], mTitles[i]);
            }
        }

        mSectionIndices = getSectionIndices();
        mSectionDates = getSectionDates();
    }

    public void addItem(int type, String title) {
        // 현재
        Program program = new Program(type, title);
        programList.add(program);
    }

    public void addItem(int type, String title, String time) {
        Program program = null;

        if(type == ITEM_VIEW_TYPE_FUTURE) { // 미래
            program = new Program(type, title, time, false);
        } else if(type == ITEM_VIEW_TYPE_PAST) { // 과거
            program = new Program(type, title, time);
        }
        programList.add(program);
    }

    // time의 첫번째 숫자가 작아졌을때 section 생김 14시 -> 18시 -> 23시 ->(섹션) -> 1시
    private int[] getSectionIndices() {
        ArrayList<Integer> sectionIndices = new ArrayList<Integer>();
        sectionIndices.add(0);
        for (int i = 1; i < mTimes.length; i++) {
            if (Integer.parseInt(String.valueOf(mTimes[i-1].charAt(0))) > Integer.parseInt(String.valueOf(mTimes[i].charAt(0)))) {
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
            dates[i] = mDates[mSectionIndices[i]];
        }
        return dates;
    }

    @Override
    public int getCount() {
        return programList.size();
    }

    @Override
    public Object getItem(int position) {
        return programList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return programList.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_MAX;
    }

    // item을 inflate, item에서 실제로 보여줄 것
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        int viewType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (viewType) {
                case ITEM_VIEW_TYPE_PAST :
                    convertView = mInflater.inflate(R.layout.list_item_past, parent, false);
                    holder.timeText = (TextView) convertView.findViewById(R.id.time_textview);
                    holder.titleText = (TextView) convertView.findViewById(R.id.title_textview);

                    holder.timeText.setText(programList.get(position).getTime());
                    holder.titleText.setText(programList.get(position).getTitle());

                    break;

                case ITEM_VIEW_TYPE_NOW :
                    convertView = mInflater.inflate(R.layout.list_item_now, parent, false);
                    holder.titleText = (TextView) convertView.findViewById(R.id.title_textview);

                    holder.titleText.setText(programList.get(position).getTitle());

                    break;

                case ITEM_VIEW_TYPE_FUTURE :
                    convertView = mInflater.inflate(R.layout.list_item_future, parent, false);
                    holder.timeText = (TextView) convertView.findViewById(R.id.time_textview);
                    holder.titleText = (TextView) convertView.findViewById(R.id.title_textview);
                    holder.reserveButton = (Button) convertView.findViewById(R.id.reserve_button);

                    holder.timeText.setText(programList.get(position).getTime());
                    holder.titleText.setText(programList.get(position).getTitle());
                    holder.reserveButton.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (programList.get(position).getReserve()) {
                                holder.reserveButton.setBackgroundResource(R.drawable.reserve_off);
                                programList.get(position).setReserve(false);
                                Toast.makeText(mContext, "예약이 취소되었습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            holder.reserveButton.setBackgroundResource(R.drawable.reserve_on);
                            programList.get(position).setReserve(true);
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
        CharSequence headerChar = mDates[position];
        holder.text.setText(headerChar);

        return convertView;
    }

    @Override
    public long getHeaderId(int position) { // 특정 item의 position을 보고 header id 반환
        // HeaderId가 같은 item들끼리 같은 section에 넣어버리므로, 같은 section에 들어갈 애들끼리 같은 id를 리턴해줘야해
        return mDates[position].subSequence(10, 11).charAt(0); // date : 17일, 18일 -> header id : 7, 8
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
