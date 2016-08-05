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

    //debugging
    public myAdapter (int filter, int fromPrice) {
        final long time = System.currentTimeMillis();
        Date date = new Date();
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
            view=inflater.inflate(R.layout.job_card, null);
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
        // holder.by.setText(currJob.getTitle()); // need to change to something with the user
        holder.payment.setText(currJob.getPayment().toString());
        holder.date.setText(currJob.getDate().toString());
           /* if(isMultipleDates(dates)) {
               holder.time2.setText(dates[1].toString());
                holder.more.setText(R.string.more);
            }
            else{
                //holder.more.setText(R.string.empty_string);
            }*/


        // holder.img.setText(currJob.get);
        //holder.title.setTextColor(color);
        //Picasso.with(viewGroup.getContext()).load(currJob.getPicUrl()).into(holder.img)*/

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
