package se.splish.votemaster.helper;

import java.util.ArrayList;
import java.util.List;

import se.splish.votemaster.model.Candidate;
import se.splish.votemaster.model.Vote;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "voteManager";

	// Table Names
	private static final String TABLE_VOTES = "votes";
	private static final String TABLE_VOTE = "vote";
	private static final String TABLE_CANDIDATE = "candidate";

	// VOTE Table - column names
	private static final String KEY_VID = "vid";
	private static final String KEY_VOTE_NAME = "name";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_NBR_OF_VOTES = "nbrOfVotes";

	// VOTES Table - column names
	private static final String KEY_VOTES = "votes";

	// CANDIDATE Table - column names
	private static final String KEY_CID = "cid";
	private static final String KEY_CANDIDATE_NAME = "name";

	// Table Create Statements
	// vote table create statement
	private static final String CREATE_TABLE_VOTE = "CREATE TABLE " + TABLE_VOTE + "(" + KEY_VID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_VOTE_NAME + " TEXT," + KEY_DESCRIPTION
			+ " TEXT," + KEY_NBR_OF_VOTES + " INTEGER" + ")";

	// candidate table create statement
	private static final String CREATE_TABLE_CANDIDATE = "CREATE TABLE " + TABLE_CANDIDATE + "("
			+ KEY_CID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CANDIDATE_NAME + " TEXT" + ")";

	// votes table create statement
	private static final String CREATE_TABLE_VOTES = "CREATE TABLE " + TABLE_VOTES + "(" + KEY_VID
			+ " INTEGER PRIMARY KEY, " + KEY_CID + " INTEGER PRIMARY KEY, " + KEY_VOTES
			+ " INTEGER, " + "FOREIGN KEY(" + KEY_VID + ") REFERENCES " + TABLE_VOTE + "("
			+ KEY_VID + "), " + "FOREIGN KEY(" + KEY_CID + ") REFERENCES " + TABLE_CANDIDATE + "("
			+ KEY_CID + ")" + ")";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// creating required tables
		db.execSQL(CREATE_TABLE_VOTE);
		db.execSQL(CREATE_TABLE_CANDIDATE);
		db.execSQL(CREATE_TABLE_VOTES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOTE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CANDIDATE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOTES);

		// create new tables
		onCreate(db);
	}

	public Boolean createVote(Vote vote) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_VOTE_NAME, vote.getName());
		values.put(KEY_DESCRIPTION, vote.getDescription());
		values.put(KEY_NBR_OF_VOTES, vote.getNbrOfVotes());

		// insert row
		long id = db.insert(TABLE_VOTE, null, values);
		return id != -1;
	}

	public Boolean createCandidate(Candidate candidate) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_CANDIDATE_NAME, candidate.getName());

		// insert row
		long id = db.insert(TABLE_CANDIDATE, null, values);
		return id != -1;
	}

	public Boolean createVC(int vid, int cid) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_VID, vid);
		values.put(KEY_CID, cid);
		values.put(KEY_VOTES, 0);

		// insert row
		long id = db.insert(TABLE_VOTES, null, values);
		return id != -1;
	}

	public void incrementVote(int vid, int cid) {
		// SQLiteDatabase db = this.getWritableDatabase();
		String q = "UPDATE " + TABLE_VOTES + " SET " + KEY_VOTES + " = " + KEY_VOTES
				+ " + 1 WHERE cid = " + cid + " AND vid = " + vid;
		Log.d("createVC", q);
		// db.execSQL(q);
	}

	public Vote getVote(int vid) {
		return null;
	}

	public List<Vote> getAllVote() {
		List<Vote> votelist = new ArrayList<Vote>();
		String selectQuery = "SELECT  * FROM " + TABLE_VOTE;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Vote v = new Vote(
						c.getInt(c.getColumnIndex(KEY_VID)), 
						c.getString(c.getColumnIndex(KEY_VOTE_NAME)), 
						c.getString(c.getColumnIndex(KEY_DESCRIPTION)), 
						c.getInt(c.getColumnIndex(KEY_NBR_OF_VOTES)));
				
				votelist.add(v);
			} while (c.moveToNext());
		}
		return votelist;
	}

	public Candidate getCandidate(int cid) {
		return null;
	}

	public List<Candidate> getCandidates() {
		List<Candidate> candidatelist = new ArrayList<Candidate>();
		String selectQuery = "SELECT  * FROM " + TABLE_CANDIDATE;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Candidate v = new Candidate(
						c.getInt(c.getColumnIndex(KEY_CID)), 
						c.getString(c.getColumnIndex(KEY_CANDIDATE_NAME)));
				candidatelist.add(v);
			} while (c.moveToNext());
		}
		return candidatelist;
	}
}
