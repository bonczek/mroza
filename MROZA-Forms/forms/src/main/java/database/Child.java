package database;

import java.util.List;
import database.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table CHILD.
 */
public class Child {

    private Long id;
    /** Not-null value. */
    private String code;
    private boolean isArchived;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient ChildDao myDao;

    private List<TermSolution> termSolutionList;
    private List<Program> programList;

    public Child() {
    }

    public Child(Long id) {
        this.id = id;
    }

    public Child(Long id, String code, boolean isArchived) {
        this.id = id;
        this.code = code;
        this.isArchived = isArchived;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getChildDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getCode() {
        return code;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setCode(String code) {
        this.code = code;
    }

    public boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<TermSolution> getTermSolutionList() {
        if (termSolutionList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TermSolutionDao targetDao = daoSession.getTermSolutionDao();
            List<TermSolution> termSolutionListNew = targetDao._queryChild_TermSolutionList(id);
            synchronized (this) {
                if(termSolutionList == null) {
                    termSolutionList = termSolutionListNew;
                }
            }
        }
        return termSolutionList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetTermSolutionList() {
        termSolutionList = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Program> getProgramList() {
        if (programList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProgramDao targetDao = daoSession.getProgramDao();
            List<Program> programListNew = targetDao._queryChild_ProgramList(id);
            synchronized (this) {
                if(programList == null) {
                    programList = programListNew;
                }
            }
        }
        return programList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetProgramList() {
        programList = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}