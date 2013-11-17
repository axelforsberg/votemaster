package se.splish.votemaster;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class ResultActivity extends FragmentActivity implements
		VoteListFragment.OnVoteNameSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_results);
	}

	@Override
	public void onNameSelected(int pos) {
		ResultFragment detailsFrag = (ResultFragment) getSupportFragmentManager().findFragmentById(
				R.id.results_fragment);

		if (detailsFrag != null) {
			// If article frag is available, we're in two-pane layout...

			// Call a method in the ArticleFragment to update its content
			detailsFrag.updateDetailsView(pos);

		} else {
			// If the frag is not available, we're in the one-pane layout and
			// must swap frags...

			// Create fragment and give it an argument for the selected article
			VoteDetailsFragment newFragment = new VoteDetailsFragment();
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

			// Replace whatever is in the fragment_container view with this
			// fragment,
			// and add the transaction to the back stack so the user can
			// navigate back
			transaction.replace(R.id.fragment_container, newFragment);
			transaction.addToBackStack(null);

			// Commit the transaction
			transaction.commit();
		}
	}
}