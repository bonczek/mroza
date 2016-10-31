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

package com.mroza.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@javax.persistence.Table(name = "tab")
@NamedQueries({
    @NamedQuery(name = "Table.selectAllTables", query = "SELECT t FROM Table t"),
    @NamedQuery(name = "Table.selectTablesByProgramIdWithEdgesRowsFields", query = "SELECT t FROM Table t WHERE t.program.id = :id")            
})
public class Table implements Serializable {

    @Transient
    private final String LEARNING_TYPE_SYMBOL = "U";
    @Transient
    private final String GENERALIZATION_TYPE_SYMBOL = "G";
    @Transient
    private boolean edited = false;
    @Transient
    private Integer numberOfLearningCols = 0;
    @Transient
    private Integer numberOfGeneralizationCols = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "name")
    private String name;
    
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
        
    @NotNull
    @Column(name = "create_datetime")    
    private LocalDate createDate;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "archived")
    private boolean archived;
    
    @Transient
    private Integer programId;
        
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "program_id", referencedColumnName = "id")
    private Program program;
    
    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<KidTable> kidTables;
    
    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TableRow> tableRows;

    public Table(String name) {
        this.id = -1L;
        this.name = name;
        this.description = "Opis";
        this.createDate = null;
        this.archived = false;

        this.program = null;
        this.kidTables = new ArrayList<>();
        this.tableRows = new ArrayList<>();
    }

    public Table(String name, LocalDate createDate) {
        this.id = -1L;
        this.name = name;
        this.description = "Opis";
        this.createDate = createDate;
        this.archived = false;

        this.program = null;
        this.kidTables = new ArrayList<>();
        this.tableRows = new ArrayList<>();
    }

    public Integer getNumberOfLearningCols() {
        return countColumns(LEARNING_TYPE_SYMBOL, numberOfLearningCols);
    }

    public Integer getNumberOfGeneralizationCols() {
        return countColumns(GENERALIZATION_TYPE_SYMBOL, numberOfGeneralizationCols);
    }

    private Integer countColumns(final String symbol, Integer modelValue) {
        if(isEdited()) {
            return modelValue;
        } else {
            if (!areTableRows())
                return 0;

            return tableRows.get(0).getNumberOfCols(symbol);
        }
    }

    private boolean areTableRows(){
        return tableRows != null && tableRows.size() != 0;
    }

    public void addTableRow(String rowName, Integer learningColsNum, Integer generalizationColsNum) {
        if (tableRows == null)
            tableRows = new ArrayList<>();

        tableRows.add(new TableRow(rowName, tableRows.size(), learningColsNum, generalizationColsNum));
    }

    public void addTableRow(String rowName) {
        addTableRow(rowName, getNumberOfLearningCols(), getNumberOfGeneralizationCols());
    }

    public boolean isLearningCollecting() {
        return getNumberOfLearningCols() != 0;
    }

    public boolean isGeneralizationCollecting() {
        return getNumberOfGeneralizationCols() != 0;
    }
}
