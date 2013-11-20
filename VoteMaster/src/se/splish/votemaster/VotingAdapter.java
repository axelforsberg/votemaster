package se.splish.votemaster;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class VotingAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<String> names;

    public VotingAdapter(Context c, ArrayList<String> names) {
        mContext = c;
        this.names = names;
    }

    public int getCount() {
        return names.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new Textview for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
    	Display display = wm.getDefaultDisplay();
    	Point size = new Point();
    	display.getSize(size);
    	int width = size.x;
    	int height = size.y;
    	
        TextView tw;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            tw = new TextView(mContext);
            tw.setLayoutParams(new GridView.LayoutParams(width/5-4, height/6));
            tw.setPadding(2, 2, 2, 2);
            tw.setGravity(Gravity.CENTER);
            tw.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            tw.setBackgroundColor(Color.LTGRAY);
        } else {
            tw = (TextView) convertView;
        }

        tw.setText(names.get(position));
        return tw;
    }
}