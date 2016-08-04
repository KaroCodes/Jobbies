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


        private ArrayList<JobDecription> jobs;
        private int color;


        public myAdapter () {
            quotes = new ArrayList<>();
            color= Color.BLACK;
        }

        public void setQuotes(ArrayList<JobDecription> newJobs) {
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

            JobDecription currJob=jobs.get(i);
            holder.title.setText(currJob.get);
            holder.by.setText(currJob.get);
            holder.time.setText(currJob.get);
            //holder.title.setTextColor(color);
            Picasso.with(viewGroup.getContext()).load(currJob.getPicUrl()).into(holder.img);

            return view;
        }
}
