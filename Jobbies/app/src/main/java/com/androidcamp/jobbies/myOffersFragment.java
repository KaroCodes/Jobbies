package com.androidcamp.jobbies;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;

/**
 * Created by demouser on 8/4/16.
 */
public class myOffersFragment extends android.support.v4.app.Fragment{
   // private itemClicked myAactivity;
    private myAdapter mAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_view, container, false);
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

    /*public interface itemClicked{
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
    }*/

    public class myAdapter extends BaseAdapter {


        private ArrayList<JobDescription> jobs = new ArrayList<JobDescription>();
        //debugging
        public myAdapter () {
            jobs = new ArrayList<>();

            JobDescription firstJob=new JobDescription();
            firstJob.setTitle("first job");
            firstJob.setPayment(new Payment(10, Currency.getInstance("GBP")));
            Date d1=new Date();
            Date[] dates1={d1};
            firstJob.setDates(dates1);
            jobs.add(firstJob);

            JobDescription secondJob=new JobDescription();
            secondJob.setTitle("second job");
            secondJob.setPayment(new Payment(10, Currency.getInstance("GBP")));
            Date d2=new Date();
            Date[] dates2={d2};
            secondJob.setDates(dates2);
            jobs.add(secondJob);
        }

        public void setQuotes(ArrayList<JobDescription> newJobs) {
            jobs = newJobs;
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
            Log.d("begining","!!!!!");
            View view=null;
            ViewHolder holder;
            if(convertView==null){
                LayoutInflater inflater= LayoutInflater.from(viewGroup.getContext());
                view=inflater.inflate(R.layout.job_card, null);
                holder=new ViewHolder();
                holder.title=((TextView) view.findViewById(R.id.title));
                holder.by=((TextView) view.findViewById(R.id.by));
                //holder.description=((TextView) view.findViewById(R.id.description));
                //holder.payment=((TextView) view.findViewById(R.id.payment));
                holder.time1=((TextView) view.findViewById(R.id.time1));
                holder.time1=((TextView) view.findViewById(R.id.time2));
                holder.more=((TextView)  view.findViewById(R.id.more));
                Log.d("after more", holder.more + "");
                holder.img=((ImageView) view.findViewById(R.id.image));
                view.setTag(holder);
            }
            else{
                view=convertView;
                holder=(ViewHolder) view.getTag();
            }

            JobDescription currJob=jobs.get(i);
            holder.title.setText(currJob.getTitle());
           // holder.by.setText(currJob.getTitle()); // need to change to something with the user
            //holder.payment.setText(Integer.toString(currJob.getPayment().getPrice()));
            Date[] dates=currJob.getDates();
           holder.time1.setText(dates[0].toString());
            if(isMultipleDates(dates)) {
               holder.time2.setText(dates[1].toString());
                holder.more.setText(R.string.more);
            }
            else{
                //holder.more.setText(R.string.empty_string);
            }


           // holder.img.setText(currJob.get);
            //holder.title.setTextColor(color);
            //Picasso.with(viewGroup.getContext()).load(currJob.getPicUrl()).into(holder.img)*/

            return view;
        }


        private boolean isMultipleDates(Date[] dates){
            if(dates.length>1){
                return true;
            }
            else{
                return false;
            }
        }

    }
}
