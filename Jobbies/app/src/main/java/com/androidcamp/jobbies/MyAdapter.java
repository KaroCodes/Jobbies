package com.androidcamp.jobbies;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
    //debugging
    public myAdapter (int filter) {
        final long time = System.currentTimeMillis();
        if(filter==getAllJobs) {
            databaseProvider.getJobs(null, 0, 0, null, null, new DatabaseProvider.GetJobListener() {
                @Override
                public void apply(Job job) {
                    Log.d("DATABASE", "!!!!!! " + (System.currentTimeMillis() - time));
                    jobs.add(job);
                    notifyDataSetChanged();
                }
            });
        }

        else if (filter==getJobsByUser) {
            databaseProvider.getJobs(null, 0, 0, null, null, new DatabaseProvider.GetJobListener() {
                @Override
                public void apply(Job job) {
                    Log.d("DATABASE", "!!!!!! " + (System.currentTimeMillis() - time));
                    jobs.add(job);
                    notifyDataSetChanged();
                }
            });
        }

        else if (filter==getApplicationsByUser) {
            databaseProvider.getJobs(null, 0, 0, null, null, new DatabaseProvider.GetJobListener() {
                @Override
                public void apply(Job job) {
                    Log.d("DATABASE", "!!!!!! " + (System.currentTimeMillis() - time));
                    jobs.add(job);
                    notifyDataSetChanged();
                }
            });
        }

        else if (filter==getApplicatsByUser) {
            databaseProvider.getJobs(null, 0, 0, null, null, new DatabaseProvider.GetJobListener() {
                @Override
                public void apply(Job job) {
                    Log.d("DATABASE", "!!!!!! " + (System.currentTimeMillis() - time));
                    jobs.add(job);
                    notifyDataSetChanged();
                }
            });
        }


       /* JobDescription firstJob=new JobDescription();
        firstJob.setTitle("first job");
        firstJob.setPayment(new Payment(10, Currency.getInstance("GBP")));
        firstJob.setDate(new Date());
        jobs.add(firstJob);

        JobDescription secondJob=new JobDescription();
        secondJob.setTitle("second job");
        secondJob.setPayment(new Payment(10, Currency.getInstance("GBP")));
        secondJob.setDate(new Date());
        jobs.add(secondJob);*/


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
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        Log.d("begining","!!!!!");
        View view=null;
        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater= LayoutInflater.from(viewGroup.getContext());
            view=inflater.inflate(R.layout.job_card, null);
            holder=new ViewHolder();
            holder.title=((TextView) view.findViewById(R.id.title));
            //holder.description=((TextView) view.findViewById(R.id.description));
            //holder.payment=((TextView) view.findViewById(R.id.payment));
            holder.date=((TextView) view.findViewById(R.id.date));
            //holder.more=((TextView)  view.findViewById(R.id.more));
            Log.d("after more", holder.more + "");

            holder.delete_button = (Button) view.findViewById(R.id.delete_button);
            holder.delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (view == null)
                        Log.d("NULLLLLL", "NULLLLLL");
                    int position = (int) view.getTag();
                    jobs.remove(getItem(position));
                    notifyDataSetChanged();
                    //databaseProvider.delete(getItem(position));

                }
            });

            view.setTag(holder);
        }
        else{
            view=convertView;
            holder=(ViewHolder) view.getTag();
        }

        Job currJob=jobs.get(i);

        holder.title.setText(currJob.getTitle());
        holder.delete_button.setTag(i);
        // holder.by.setText(currJob.getTitle()); // need to change to something with the user
        //holder.payment.setText(Integer.toString(currJob.getPayment().getPrice()));
        //holder.date.setText(currJob.getDate().toString());
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

    public ArrayList<Job> getJobs()
    {
        return jobs;
    }

}
