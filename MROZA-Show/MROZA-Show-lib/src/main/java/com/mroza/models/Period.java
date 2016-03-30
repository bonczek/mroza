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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Period implements Serializable {

    private Integer id;
    private Date beginDate;
    private Date endDate;

    private Integer kidId;

    private Kid kid;
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
        this.id = id;
        this.kidTables = new ArrayList<>();
    }
}