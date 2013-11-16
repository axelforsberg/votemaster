package se.splish.votemaster;

import java.util.List;

import se.splish.votemaster.helper.DatabaseHelper;
import se.splish.votemaster.model.Vote;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class VoteDetailsFragment extends Fragment implements OnClickListener {
	final static String ARG_POSITION = "position";
	int mCurrentPosition = -1;
	View v;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// If activity recreated (such as from screen rotate), restore
		// the previous article selection set by onSaveInstanceState().
		// This is primarily necessary when in the two-pane layout.
		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
		}

		// Inflate the layout for this fragment
		v = inflater.inflate(R.layout.details_view, container, false);
		return v;
	}

	@Override
	public void onStart() {
		super.onStart();
		// During startup, check if there are arguments passed to the fragment.
		// onStart is a good place to do this because the layout has already
		// been
		// applied to the fragment at this point so we can safely call the
		// method
		// below that sets the article text.
		Bundle args = getArguments();
		if (args != null) {
			// Set article based on argument passed in
			updateDetailsView(args.getInt(ARG_POSITION));
		} else if (mCurrentPosition != -1) {
			// Set article based on saved instance state defined during
			// onCreateView
			updateDetailsView(mCurrentPosition);
		} else {
			((VotesActivity) getActivity()).hideUI();
		}
		
	}


	public void updateDetailsView(int pos) {
		((VotesActivity) getActivity()).showUI();
		TextView name = (TextView) getActivity().findViewById(R.id.details_name);
		name.setText(getVotes().get(pos).getName());

		TextView description = (TextView) getActivity().findViewById(R.id.details_description);
		description.setText(getVotes().get(pos).getDescription());
		
		mCurrentPosition = pos;

		Button open = (Button) getActivity().findViewById(R.id.details_btn_open);
		open.setOnClickListener(this);
	}

	private List<Vote> getVotes() {
		DatabaseHelper dbh = new DatabaseHelper(this.getActivity());
		List<Vote> votes = dbh.getAllVotes();
		return votes;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// Save the current article selection in case we need to recreate the
		// fragment
		outState.putInt(ARG_POSITION, mCurrentPosition);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.details_btn_open:
				Intent in = new Intent(getActivity(),VotingActivity.class);
				in.putExtra("vid", getVotes().get(mCurrentPosition).getId());
				in.putExtra("nov", getVotes().get(mCurrentPosition).getNbrOfVotes());
				startActivity(in);
			break;
		}
	}

}