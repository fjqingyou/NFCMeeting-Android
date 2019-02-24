package com.nfcmeeting.nfcmeeting.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MEETING".
*/
public class MeetingDao extends AbstractDao<Meeting, Short> {

    public static final String TABLENAME = "MEETING";

    /**
     * Properties of entity Meeting.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property MeetingId = new Property(0, short.class, "meetingId", true, "MEETING_ID");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Content = new Property(2, String.class, "content", false, "CONTENT");
        public final static Property BeginTime = new Property(3, String.class, "beginTime", false, "BEGIN_TIME");
        public final static Property EndTime = new Property(4, String.class, "endTime", false, "END_TIME");
    }


    public MeetingDao(DaoConfig config) {
        super(config);
    }
    
    public MeetingDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MEETING\" (" + //
                "\"MEETING_ID\" INTEGER PRIMARY KEY NOT NULL ," + // 0: meetingId
                "\"TITLE\" TEXT NOT NULL ," + // 1: title
                "\"CONTENT\" TEXT NOT NULL ," + // 2: content
                "\"BEGIN_TIME\" TEXT NOT NULL ," + // 3: beginTime
                "\"END_TIME\" TEXT NOT NULL );"); // 4: endTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MEETING\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Meeting entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getMeetingId());
        stmt.bindString(2, entity.getTitle());
        stmt.bindString(3, entity.getContent());
        stmt.bindString(4, entity.getBeginTime());
        stmt.bindString(5, entity.getEndTime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Meeting entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getMeetingId());
        stmt.bindString(2, entity.getTitle());
        stmt.bindString(3, entity.getContent());
        stmt.bindString(4, entity.getBeginTime());
        stmt.bindString(5, entity.getEndTime());
    }

    @Override
    public Short readKey(Cursor cursor, int offset) {
        return cursor.getShort(offset + 0);
    }    

    @Override
    public Meeting readEntity(Cursor cursor, int offset) {
        Meeting entity = new Meeting( //
            cursor.getShort(offset + 0), // meetingId
            cursor.getString(offset + 1), // title
            cursor.getString(offset + 2), // content
            cursor.getString(offset + 3), // beginTime
            cursor.getString(offset + 4) // endTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Meeting entity, int offset) {
        entity.setMeetingId(cursor.getShort(offset + 0));
        entity.setTitle(cursor.getString(offset + 1));
        entity.setContent(cursor.getString(offset + 2));
        entity.setBeginTime(cursor.getString(offset + 3));
        entity.setEndTime(cursor.getString(offset + 4));
     }
    
    @Override
    protected final Short updateKeyAfterInsert(Meeting entity, long rowId) {
        return entity.getMeetingId();
    }
    
    @Override
    public Short getKey(Meeting entity) {
        if(entity != null) {
            return entity.getMeetingId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Meeting entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}