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
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@javax.persistence.Table(name = "Kid")
@NamedQueries({
    @NamedQuery(name = "selectAllKids", query = "FROM Kid k WHERE k.archived = false ORDER BY k.code"),
    @NamedQuery(name = "selectKidWithEdgesProgramsAndPeriods", query = "FROM Kid k WHERE k.id = :kidId"),
    @NamedQuery(name = "selectKidWithCode", query = "FROM Kid k WHERE k.code = :code")
})
public class Kid implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;    
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private boolean archived;

    @OneToMany(mappedBy = "kid", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Program> programs;
    @OneToMany(mappedBy = "kid", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Period> periods;
}
