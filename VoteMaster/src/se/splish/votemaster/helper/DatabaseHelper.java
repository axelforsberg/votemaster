package se.splish.votemaster.helper;

import se.splish.votemaster.model.Candidate;
import se.splish.votemaster.model.Vote;
import android.content.ContentValues;
import android.content.Context;
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
	// ***DENNA SKA HA TVÅ FOREIGN PRIMARY KEYS***
	private static final String CREATE_TABLE_VOTES = "CREATE TABLE " + TABLE_VOTES + "(" + KEY_VID
			+ " INTEGER PRIMARY KEY," + KEY_CID + " INTEGER PRIMARY KEY," + KEY_VOTES + " INTEGER"
			+ ")";

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
}
