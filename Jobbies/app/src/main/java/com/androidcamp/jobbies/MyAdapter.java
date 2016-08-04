package com.androidcamp.jobbies;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;

/**
 * Created by demouser on 04/08/2016.
 */
class myAdapter extends BaseAdapter {

    private ArrayList<JobDescription> jobs = new ArrayList<JobDescription>();
    //debugging
    public myAdapter () {
        jobs = new ArrayList<>();

        JobDescription firstJob=new JobDescription();
        firstJob.setTitle("first job");
        firstJob.setPayment(new Payment(10, Currency.getInstance("GBP")));
        firstJob.setDate(new Date());
        jobs.add(firstJob);

        JobDescription secondJob=new JobDescription();
        secondJob.setTitle("second job");
        secondJob.setPayment(new Payment(10, Currency.getInstance("GBP")));
        secondJob.setDate(new Date());
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
            holder.date=((TextView) view.findViewById(R.id.date));
            //holder.more=((TextView)  view.findViewById(R.id.more));
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
    private boolean isMultipleDates(Date[] dates){
        if(dates.length>1){
            return true;
        }
        else{
            return false;
        }
    }

}
