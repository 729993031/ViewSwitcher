package com.example.ViewSwitcher;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.text.SpanWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

public class MyActivity extends Activity {
    public static final int NUMBER_PER_SCREEN=12;
    public static class DataItem
    {
        public String dataName;
        public Drawable drawable;
    }
    private ArrayList<DataItem>items=new ArrayList<DataItem>();
    private int screenNo=-1;
    private int screenCount;
    ViewSwitcher switcher;
    LayoutInflater inflater;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        inflater=LayoutInflater.from(MyActivity.this);
        for (int i=0; i<40;i++)
        {
            String label=""+i; Drawable drawable=getResources().getDrawable(R.drawable.ic_launcher);
            DataItem item=new DataItem();
            item.dataName=label;
            item.drawable=drawable;
            items.add(item);
        }
        screenCount=items.size()% NUMBER_PER_SCREEN==0?
                items.size()/NUMBER_PER_SCREEN:
                items.size()/NUMBER_PER_SCREEN+1;
        switcher=(ViewSwitcher)findViewById(R.id.viewSwitcher);
        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return inflater.inflate(R.layout.slidelistview,null);
            }
        });
        next(null);

    }
    public void next(View v)
    {
        if (screenNo < screenCount-1)
        {
            screenNo++;
            switcher.setInAnimation(this,R.anim.slide_in_right);
            switcher.setOutAnimation(this,R.anim.slide_out_left);
            ((GridView)switcher.getNextView()).setAdapter(adapter);
            switcher.showNext();
        }
    }
    public void prev(View v)
    {
        if (screenNo>0)
        {
            screenNo--;
            switcher.setInAnimation(this,android.R.anim.slide_in_left);
            switcher.setOutAnimation(this,android.R.anim.slide_out_right);
            ((GridView)switcher.getNextView()).setAdapter(adapter);
            switcher.showPrevious();
        }
    }
    private BaseAdapter adapter=new BaseAdapter()
    {
        @Override
        public int getCount()
            {
                if (screenNo == screenCount - 1
                        && items.size() % NUMBER_PER_SCREEN != 0)
                {
                    return items.size() % NUMBER_PER_SCREEN;
                }

                    return NUMBER_PER_SCREEN;
                }
            @Override
            public DataItem getItem(int position)
            {
            return items.get(screenNo *NUMBER_PER_SCREEN+position);
        }
        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position
                , View convertView, ViewGroup parent)
        {
           View view=convertView;
                if(convertView==null) {
                    view = inflater.inflate(R.layout.labelicon, null);
                }

                    ImageView imageView=(ImageView)
                            view.findViewById(R.id.imageview);
                    imageView.setImageDrawable(getItem(position).drawable);
                    TextView textView=(TextView)
                            view.findViewById(R.id.textview);
                    textView.setText(getItem(position).dataName);
                    return view;
                }
        };
}
