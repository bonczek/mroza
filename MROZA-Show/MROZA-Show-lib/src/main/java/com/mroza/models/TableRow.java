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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@javax.persistence.Table(name = "TabRow")
public class TableRow implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ord", nullable = false)
    private Integer orderNum;
    @Column(name = "value", nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "table", referencedColumnName = "id")
    private Table table;
    
    private Integer tableId;
    
    @OneToMany(mappedBy = "row", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TableField> rowFields;

    public TableRow(String name, Integer orderNum, Integer numberOfLearningCols, Integer numberOfGeneralizationCols) {
        this.id = null;
        this.name = name;
        this.orderNum = orderNum;
        updateRowFields(numberOfLearningCols, numberOfGeneralizationCols);
    }

    public Integer getNumberOfCols(String colTypeSymbol) {
        if (rowFields == null)
            return 0;

        return (int) rowFields.stream().filter(rowField -> rowField.getType().equals(colTypeSymbol)).count();
    }

    public void updateRowFields(Integer numberOfLearningCols, Integer numberOfGeneralizationCols) {
        this.rowFields = new ArrayList<>();
        for (int learningColIndex = 0; learningColIndex < numberOfLearningCols; learningColIndex++)
            rowFields.add(new TableField(learningColIndex, "U"));

        int MAX_GENERALIZATION_FIELD_INDEX = numberOfGeneralizationCols + numberOfLearningCols;
        for (int generalizationColIndex = numberOfLearningCols; generalizationColIndex < MAX_GENERALIZATION_FIELD_INDEX; generalizationColIndex++)
            rowFields.add(new TableField(generalizationColIndex, "G"));
    }
}
