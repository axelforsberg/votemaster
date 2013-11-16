package se.splish.votemaster;

import se.splish.votemaster.model.Vote;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class VotesActivity extends FragmentActivity implements VoteListFragment.OnVoteNameSelectedListener {
	boolean detailPage = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 setContentView(R.layout.activity_votes);
		 
		// Check whether the activity is using the layout version with
	        // the fragment_container FrameLayout. If so, we must add the first fragment
	        if (findViewById(R.id.fragment_container) != null) {

	            // However, if we're being restored from a previous state,
	            // then we don't need to do anything and should return or else
	            // we could end up with overlapping fragments.
	            if (savedInstanceState != null) {
	                return;
	            }

	            // Create an instance of ExampleFragment
	            VoteListFragment firstFragment = new VoteListFragment();

	            // In case this activity was started with special instructions from an Intent,
	            // pass the Intent's extras to the fragment as arguments
	            firstFragment.setArguments(getIntent().getExtras());

	            // Add the fragment to the 'fragment_container' FrameLayout
	            getSupportFragmentManager().beginTransaction()
	                    .add(R.id.fragment_container, firstFragment).commit();
	        }
	}
	
	public void hideUI(){
	    VoteDetailsFragment myFrag = (VoteDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.details_fragment);
        getSupportFragmentManager().beginTransaction()
        .hide(myFrag).commit();
	}
	
	public void showUI(){

		VoteDetailsFragment myFrag = (VoteDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.details_fragment);
        getSupportFragmentManager().beginTransaction()
        .show(myFrag).commit();
    }

	@Override
	public void onNameSelected(Vote vote) {
		 VoteDetailsFragment detailsFrag = (VoteDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.details_fragment);

	        if (detailsFrag != null) {
	            // If article frag is available, we're in two-pane layout...

	            // Call a method in the ArticleFragment to update its content
	        	detailsFrag.updateDetailsView(vote);

	        } else {
	            // If the frag is not available, we're in the one-pane layout and must swap frags...

	            // Create fragment and give it an argument for the selected article
	        	VoteDetailsFragment newFragment = new VoteDetailsFragment();
	            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

	            // Replace whatever is in the fragment_container view with this fragment,
	            // and add the transaction to the back stack so the user can navigate back
	            transaction.replace(R.id.fragment_container, newFragment);
	            transaction.addToBackStack(null);

	            // Commit the transaction
	            transaction.commit();
	        }
		
	}
}
