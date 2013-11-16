package se.splish.votemaster;

import java.util.ArrayList;
import java.util.List;

import se.splish.votemaster.helper.DatabaseHelper;
import se.splish.votemaster.model.Vote;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class VoteListFragment extends ListFragment {
	OnVoteNameSelectedListener mCallback;

	public interface OnVoteNameSelectedListener {
		/** Called by HeadlinesFragment when a list item is selected */
		public void onNameSelected(int pos);
	}

	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);

		List<String> names = new ArrayList<String>();
		for (Vote v : getVotes()) {
			names.add(v.getName());
		}
		// We need to use a different list item layout for devices older than
		// Honeycomb
		int layout = android.R.layout.simple_list_item_activated_1;

		// Create an array adapter for the list view, using the Ipsum headlines
		// array
		setListAdapter(new ArrayAdapter<String>(getActivity(), layout, names));
	}

	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
			mCallback.onNameSelected(pos);
			// Set the item as checked to be highlighted when in two-pane layout
			getListView().setItemChecked(pos, true);
	}

	@Override
	public void onStart() {
		super.onStart();

		// When in two-pane layout, set the listview to highlight the selected
		// list item
		// (We do this during onStart because at the point the listview is
		// available.)
		if (getFragmentManager().findFragmentById(R.id.details_fragment) != null) {
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception.
		try {
			mCallback = (OnVoteNameSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnVoteNameSelectedListener");
		}
	}

	private List<Vote> getVotes() {
		DatabaseHelper dbh = new DatabaseHelper(this.getActivity());
		List<Vote> votes = dbh.getAllVotes();
		if(votes.size()==0){
			Toast.makeText(getActivity(), "Skapa en omröstning först", Toast.LENGTH_LONG).show();
			getActivity().finish();
		}
		return votes;
	}
}
