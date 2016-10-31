/*
 * MROZA - supporting system of behavioral therapy of people with autism
 *     Copyright (C) 2015-2016 autyzm-pg
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mroza.dao;

import com.mroza.models.Table;
import com.mroza.models.TableRow;
import org.apache.ibatis.session.SqlSession;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class TableRowsDao {
        
    @PersistenceContext
    private EntityManager entityManager;

    public List<TableRow> selectAllTableRows() {
        List<TableRow> tableRows = entityManager.createNamedQuery("TableRow.selectAllTableRows").getResultList();                
        if(tableRows == null)
            return new ArrayList<>();
        return tableRows;
    }

    public void insertTableRow(TableRow tableRow) {
        entityManager.persist(tableRow);        
    }

    public void deleteRows(Table table) {
        entityManager.createNamedQuery("TableRow.deleteTableRows").setParameter("id", table.getId()).executeUpdate();        
    }
}
