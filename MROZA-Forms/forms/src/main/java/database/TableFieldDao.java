package database;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import database.TableField;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table TABLE_FIELD.
*/
public class TableFieldDao extends AbstractDao<TableField, Long> {

    public static final String TABLENAME = "TABLE_FIELD";

    /**
     * Properties of entity TableField.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Type = new Property(1, String.class, "type", false, "TYPE");
        public final static Property InOrder = new Property(2, Integer.class, "inOrder", false, "IN_ORDER");
        public final static Property TableRowId = new Property(3, long.class, "tableRowId", false, "TABLE_ROW_ID");
    };

    private DaoSession daoSession;

    private Query<TableField> tableRow_TableFieldListQuery;

    public TableFieldDao(DaoConfig config) {
        super(config);
    }
    
    public TableFieldDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'TABLE_FIELD' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'TYPE' TEXT," + // 1: type
                "'IN_ORDER' INTEGER," + // 2: inOrder
                "'TABLE_ROW_ID' INTEGER NOT NULL );"); // 3: tableRowId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TABLE_FIELD'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TableField entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(2, type);
        }
 
        Integer inOrder = entity.getInOrder();
        if (inOrder != null) {
            stmt.bindLong(3, inOrder);
        }
        stmt.bindLong(4, entity.getTableRowId());
    }

    @Override
    protected void attachEntity(TableField entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public TableField readEntity(Cursor cursor, int offset) {
        TableField entity = new TableField( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // type
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // inOrder
            cursor.getLong(offset + 3) // tableRowId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TableField entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setType(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setInOrder(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setTableRowId(cursor.getLong(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TableField entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(TableField entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "tableFieldList" to-many relationship of TableRow. */
    public List<TableField> _queryTableRow_TableFieldList(long tableRowId) {
        synchronized (this) {
            if (tableRow_TableFieldListQuery == null) {
                QueryBuilder<TableField> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.TableRowId.eq(null));
                tableRow_TableFieldListQuery = queryBuilder.build();
            }
        }
        Query<TableField> query = tableRow_TableFieldListQuery.forCurrentThread();
        query.setParameter(0, tableRowId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getTableRowDao().getAllColumns());
            builder.append(" FROM TABLE_FIELD T");
            builder.append(" LEFT JOIN TABLE_ROW T0 ON T.'TABLE_ROW_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected TableField loadCurrentDeep(Cursor cursor, boolean lock) {
        TableField entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        TableRow tableRow = loadCurrentOther(daoSession.getTableRowDao(), cursor, offset);
         if(tableRow != null) {
            entity.setTableRow(tableRow);
        }

        return entity;    
    }

    public TableField loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<TableField> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<TableField> list = new ArrayList<TableField>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<TableField> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<TableField> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}