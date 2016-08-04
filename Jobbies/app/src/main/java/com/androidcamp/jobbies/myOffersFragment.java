package com.androidcamp.jobbies;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidcamp.jobbies.timeframe.TimeFrame;

import java.util.ArrayList;

/**
 * Created by demouser on 8/4/16.
 */
public class myOffersFragment extends android.support.v4.app.Fragment{
    private itemClicked myAactivity;
    private myAdapter mAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_my_offers, container, false);
        ListView listview=(ListView) v.findViewById(R.id.my_offers_list_view);
        mAdapter = new myAdapter();
        listview.setAdapter(mAdapter);

        /*listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String countryName=(String) adapterView.getAdapter().getItem(i);
                myAactivity.showDetails(countryName);

            }
        });*/
        return v;
    }

    public interface itemClicked{
        public void showDetails(String name);
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        if(activity instanceof itemClicked){
            myAactivity=(itemClicked) activity;
        }
        else{
            throw new IllegalStateException("the activiry should implement the itemclicked interface");
        }
    }

    public class myAdapter extends BaseAdapter {


        private ArrayList<JobDescription> jobs;
        private int color;


        public myAdapter () {
            jobs = new ArrayList<>();
            color= Color.BLACK;
        }

        public void setQuotes(ArrayList<JobDescription> newJobs) {
            jobs = newJobs;
            //Log.d("first quote", quotes.get(0).getQuote());
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return jobs.size();
        }

        @Override
        public Object getItem(int position) {
            return jobs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            View view=null;
            ViewHolder holder;
            if(convertView==null){
                LayoutInflater inflater= LayoutInflater.from(viewGroup.getContext());
                view=inflater.inflate(R.layout.job_card, null);
                holder=new ViewHolder();
                holder.title((TextView) view.findViewById(R.id.));
                holder.by((TextView) view.findViewById(R.id.));
                holder.time((TextView) view.findViewById(R.id.));
                holder.img((ImageView) view.findViewById(R.id.));
                view.setTag(holder);
            }
            else{
                view=convertView;
                holder=(ViewHolder) view.getTag();
            }

            JobDescription currJob=jobs.get(i);
            holder.title.setText(currJob.getTitle());
            holder.by.setText(currJob.get);
            holder.time.setText(currJob.getTimeFrame());

            String[] dates=currJob.getTimeFrame().getDates();

            if(isMultipleTimes(currJob.getTimeFrame())) {
                holder.moreTime.setText("more");
            }
            else{
                holder.date.setText(Float.toString(currJob.getTimeFrame().getDayTimeFrames()[0].getHourFrames()[0].getStartingHour())+"-"+
                        Float.toString(currJob.getTimeFrame().getDayTimeFrames()[0].getHourFrames()[0].getEndingHour()));
                holder.moreTime.setText("");
            }

            if(isMultipleDates(dates)) {
                String datesToShow=getDates(dates);
                holder.date.setText(datesToShow);
                holder.moreDate.setText("more");
            }
            else{
                holder.date.setText(dates[0]);
                holder.moreDate.setText("");
            }
            holder.description.setText(currJob.getDescription());
            holder.payment.setText(currJob.getPayment());
            holder.img.setText(currJob.get);
            //holder.title.setTextColor(color);
            Picasso.with(viewGroup.getContext()).load(currJob.getPicUrl()).into(holder.img)

            return view;
        }

        /*private String getTimes(TimeFrame timeFrame){

        }*/

        private String getDates(String[] dates){
            StringBuilder datesString=new StringBuilder();
            datesString.append(dates[0]);
            datesString.append(", ");
            datesString.append(dates[1]);
            datesString.append("...");

            return datesString.toString();
        }

        private boolean isMultipleDates(String[] dates){
            if(dates.length>1){
                return true;
            }
            else{
                return false;
            }
        }

        private boolean isMultipleTimes(TimeFrame timeFrame){
            if(timeFrame.getDayTimeFrames().length>1){
                return true;
            }
            else{
                return false;
            }
        }
    }
}
