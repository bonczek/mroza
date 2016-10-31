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
import javax.persistence.Basic;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@javax.persistence.Table(name = "tabfieldresolve")
@NamedQueries({
    @NamedQuery(name = "ResolvedField.selectResolvedFieldById", query = "SELECT r FROM ResolvedField r WHERE r.id = :id"),
    @NamedQuery(name = "ResolvedField.selectAllResolvedFields", query = "SELECT r FROM ResolvedField"),
    @NamedQuery(name = "ResolvedField.selectResolvedFieldsByKidTableId", query = "SELECT r FROM ResolvedField r WHERE r.kidTable.id = :id"),
    @NamedQuery(name = "ResolvedField.selectResolvedFieldsByTableId", query = "SELECT r FROM ResolvedField r WHERE r.kidTable.table.id = :id")
})
public class ResolvedField implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @NotNull    
    @Column(name = "value")
    private String value;

    @Transient
    private Integer kidTableId;
    
    @Transient
    private Integer tableFieldId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kid_tab_id", referencedColumnName = "id")
    private KidTable kidTable;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tab_field_id", referencedColumnName = "id")
    private TableField tableField;

    public ResolvedField(String value, KidTable kidTable, TableField tableField) {
        this.id = null;
        this.value = value;
        this.kidTableId = kidTable.getId().intValue();
        this.tableFieldId = tableField.getId().intValue();
        this.kidTable = kidTable;
        this.tableField = tableField;
    }

    public ResolvedField(Long id, String value, Long kidTableId, Long tableFieldId) {
        this.id = id;
        this.value = value;
        this.kidTableId = (int)(long)kidTableId;
        this.tableFieldId = (int)(long)tableFieldId;
    }
}
