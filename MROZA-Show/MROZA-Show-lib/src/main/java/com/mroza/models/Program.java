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

@AllArgsConstructor
@Getter
@Setter
@Entity
@javax.persistence.Table(name = "Program")
public class Program extends FilterableModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String symbol;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private boolean finished;
    //TODO: Does it field is used for something?
    private Integer collectedData;
    
    private Integer kidId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kid", referencedColumnName = "id")
    private Kid kid;
    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Table> tables;

    public String getFirstSymbolLetter() {
        return symbol != null ? String.valueOf(symbol.charAt(0)).toUpperCase() : "";
    }

    @Override
    public boolean containsText(String text) {
        return name.toLowerCase().contains(text.toLowerCase()) || symbol.toLowerCase().contains(text.toLowerCase());
    }

    public Program() {
        this.id = null;
        this.symbol = "";
        this.name = "";
        this.description = "";
        this.finished = false;
        this.tables = new ArrayList<>();
        this.kidId = null;
    }

    public Program(String symbol) {
        this.id = null;
        this.symbol = symbol;
        this.name = "Program " + symbol;
        this.description = "";
        this.finished = false;
        this.tables = new ArrayList<>();
        this.kidId = null;
    }

    public Program(String symbol, String name, String description, Integer kidId) {
        this.symbol = symbol;
        this.name = name;
        this.description = description;
        this.kidId = kidId;
        this.finished = false;
        this.tables = new ArrayList<>();
    }

    public Program(Integer id, boolean finished) {
        this.id = id;
        this.finished = finished;
    }

    public Program(int id, String symbol, String name, String description, boolean finished, int kidId, List<Table> tables) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.description = description;
        this.finished = finished;
        this.tables = tables;
        this.kidId = kidId;
    }
}
