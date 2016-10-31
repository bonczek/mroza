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
import javafx.scene.control.Tab;
import org.apache.ibatis.session.SqlSession;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class TablesDao {
   
    @PersistenceContext
    private EntityManager entityManager;

    public List<Table> selectAllTables() {
        List<Table> tables = entityManager.createNamedQuery("Table.selectAllTables").getResultList();                
        if(tables == null)
            return new ArrayList<>();
        return tables;
    }

    public void insertTable(Table table) {
        entityManager.persist(table);        
    }

    public List<Table> selectTablesByProgramIdWithEdgesRowsFields(int programId) {
        List<Table> tables = entityManager.createNamedQuery("Table.selectTablesByProgramIdWithEdgesRowsFields")
                .setParameter("id", programId).getResultList();
        if(tables == null)
            return new ArrayList<>();
        return tables;
    }

    public void updateTable(Table table) {
        entityManager.merge(table);        
    }

    public void deleteTables(List<Table> tableList) {        
      tableList.forEach((table) -> entityManager.remove(table));
    }

    public void deleteTable(Table table) {
        entityManager.remove(table);        
    }
}
