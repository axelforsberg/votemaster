package se.splish.votemaster;

import java.util.ArrayList;

import se.splish.votemaster.model.Candidate;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

public class CandidateAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    public ArrayList<ListItem> myItems = new ArrayList<ListItem>();

    public CandidateAdapter(Context c, int noc) {
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < noc; i++) {
            ListItem listItem = new ListItem();
            myItems.add(listItem);
        }
        notifyDataSetChanged();
    }
    
    public void setCount(int noc){
    	int temp = getCount();
    	if(noc >= temp){
            for (int i = 0; i < noc-temp; i++) {
                ListItem listItem = new ListItem();
                myItems.add(listItem);
            }
            notifyDataSetChanged();
    	} else {
            for (int i = temp; i > noc; i--) {
                myItems.remove(i-1);
            }
            notifyDataSetChanged();    		
    	}
    }
    
    public Boolean isNamesPresent(){
    	for(ListItem li: myItems){
    		if("".equals(li.caption) || li.caption == null){
    			return false;
    		}
    	}
    	return true;
    }
    
    public ArrayList<Candidate> getCandidates(){
    	ArrayList<Candidate> names = new ArrayList<Candidate>();
	    	for(ListItem li:myItems){
	    		if (!"".equals(li.caption) && li.caption != null){
	    			names.add(new Candidate(li.caption));
	    		}
	    	}
	    return names;
    }

    public int getCount() {
        return myItems.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item, null);
            holder.caption = (EditText) convertView
                    .findViewById(R.id.ItemCaption);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //Fill EditText with the value you have in data source
        holder.caption.setText(((ListItem)myItems.get(position)).caption);
        holder.caption.setId(position);

        //we need to update adapter once we finish with editing
        holder.caption.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    final int position = v.getId();
                    final EditText Caption = (EditText) v;
                    ((ListItem)myItems.get(position)).caption = Caption.getText().toString();
                }
            }
        });

        return convertView;
    }
}

class ViewHolder {
	EditText caption;
}

class ListItem {
	String caption;
}
