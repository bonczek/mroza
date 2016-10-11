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
import java.util.Date;
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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@javax.persistence.Table(name = "resolveperiod")
@NamedQueries({
    @NamedQuery(name = "Period.selectAllPeriods", query = "SELECT p FROM Period p"),
    @NamedQuery(name = "Period.selectPeriodById", query = "SELECT p FROM Period p WHERE p.id = :id")    
})
public class Period implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "begin_date")
    @Temporal(TemporalType.DATE)
    private Date beginDate;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @Transient
    private Integer kidId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kid_id", referencedColumnName = "id")
    private Kid kid;
    
    @OneToMany(mappedBy = "period", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<KidTable> kidTables;

    public Period(Date beginDate, Date endDate) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.kidTables = new ArrayList<>();
    }

    public Period(Date beginDate, Date endDate, Integer kidId) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.kidId = kidId;
        this.kidTables = new ArrayList<>();
    }

    public Period(Integer id) {
        this.id = Integer.toUnsignedLong(id);
        this.kidTables = new ArrayList<>();
    }
}
