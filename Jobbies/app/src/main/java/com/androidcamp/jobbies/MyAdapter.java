package com.androidcamp.jobbies;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by demouser on 04/08/2016.
 */
class myAdapter extends BaseAdapter {

    private final ArrayList<Job> jobs = new ArrayList<Job>();
    private DatabaseProvider databaseProvider=new DatabaseProvider();
    final int getAllJobs=1;
    final int getJobsByUser=2;
    final int getApplicationsByUser=3;
    final int getApplicatsByUser=4;
    final int getJobsVolunteer=5;
    final int filterByPrice=6;

    final int regularCard=0;
    final int myOffersCard=1;

    private int type_card;   // 0 for regular card, 1 for my offers card

    //debugging
    public myAdapter (int filter, int fromPrice, int cardType) {
        final long time = System.currentTimeMillis();
        Date date = new Date();
        type_card=cardType;
        databaseProvider.getJobs(null, 50,0, date, null, new DatabaseProvider.GetJobListener() {

            @Override
            public void apply(Job job) {
                Log.d("DATABASE6", "!!!!!! " + (System.currentTimeMillis() - time));
                jobs.add(job);
                notifyDataSetChanged();
            }
        });
        /*if(filter==getAllJobs) {
            databaseProvider.getJobs(null,50, 0, date, null, new DatabaseProvider.GetJobListener() {
                @Override
                public void apply(Job job) {
                    Log.d("DATABASE1", "!!!!!! " + (System.currentTimeMillis() - time));
                    jobs.add(job);
                    notifyDataSetChanged();
                }
            });
        }

        else if (filter==getJobsByUser) {
            databaseProvider.getJobs(null, 50,0, date, null, new DatabaseProvider.GetJobListener() {

                @Override
                public void apply(Job job) {
                    Log.d("DATABASE2", "!!!!!! " + (System.currentTimeMillis() - time));
                    jobs.add(job);
                    notifyDataSetChanged();
                }
            });
        }

        else if (filter==getApplicationsByUser) {
            databaseProvider.getJobs(null,50, 0, date, null, new DatabaseProvider.GetJobListener() {

                @Override
                public void apply(Job job) {
                    Log.d("DATABASE3", "!!!!!! " + (System.currentTimeMillis() - time));
                    jobs.add(job);
                    notifyDataSetChanged();
                }
            });
        }

        else if (filter==getApplicatsByUser) {

            databaseProvider.getJobs(null,50, 0, date, null, new DatabaseProvider.GetJobListener() {
                @Override
                public void apply(Job job) {
                    Log.d("DATABASE4", "!!!!!! " + (System.currentTimeMillis() - time));
                    jobs.add(job);
                    notifyDataSetChanged();
                }
            });
        }

        else if (filter==getJobsVolunteer) {
            databaseProvider.getJobs(null,50, 0, date, null, new DatabaseProvider.GetJobListener() {
                @Override
                public void apply(Job job) {
                    Log.d("DATABASE5", "!!!!!! " + (System.currentTimeMillis() - time));
                    jobs.add(job);
                    notifyDataSetChanged();
                }
            });
        }

        else if (filter==filterByPrice) {
            databaseProvider.getJobs(null, 50,0, date, null, new DatabaseProvider.GetJobListener() {

                @Override
                public void apply(Job job) {
                    Log.d("DATABASE6", "!!!!!! " + (System.currentTimeMillis() - time));
                    jobs.add(job);
                    notifyDataSetChanged();
                }
            });
        }*/


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
            if (regularCard==0){
                view = inflater.inflate(R.layout.job_card, null);
            }
            else if(myOffersCard==1) {
                view = inflater.inflate(R.layout.job_card_my_offers, null);
            }


            holder=new ViewHolder();
            holder.title=((TextView) view.findViewById(R.id.title));
            //holder.description=((TextView) view.findViewById(R.id.description));
            holder.payment=((TextView) view.findViewById(R.id.payment));
            holder.date=((TextView) view.findViewById(R.id.date));
            Log.d("after more", holder.more + "");



            view.setTag(holder);
        }
        else{
            view=convertView;
            holder=(ViewHolder) view.getTag();
        }

        Job currJob=jobs.get(i);
        holder.title.setText(currJob.getTitle());
        holder.payment.setText(currJob.getPayment().toString());
        holder.date.setText(currJob.getDate().toString());

        return view;
    }

    public void getJobsListener(ViewHolder holder){

    }
    private boolean isMultipleDates(Date[] dates){
        if(dates.length>1){
            return true;
        }
        else{
            return false;
        }
    }

    public void apply(Job job){
        jobs.add(job);
    }

}
