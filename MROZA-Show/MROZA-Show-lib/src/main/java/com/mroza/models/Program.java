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
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
@Entity
@javax.persistence.Table(name = "program")
@NamedQueries({
    @NamedQuery(name = "Program.selectAllPrograms", query = "SELECT p FROM Program p"),
    @NamedQuery(name = "Program.selectProgramById", query = "SELECT p FROM Program p WHERE p.id = :id"),
    @NamedQuery(name = "Program.selectProgramBySymbol", query = "SELECT p FROM Program p WHERE p.symbol = :symbol"),
    @NamedQuery(name = "Program.selectAllTemplatePrograms", query = "SELECT p FROM Program p WHERE p.kid IS NULL"),
    @NamedQuery(name = "Program.deleteProgramBySymbol", query = "DELETE FROM Program p WHERE p.symbol = :symbol")
})
@NamedNativeQueries({
    @NamedNativeQuery(name = "Program.selectProgramDataForChart", query = 
            "WITH num_of_fields AS (\n"
            + "          SELECT t.id, tf.type, count(*)\n"
            + "          FROM tab t\n"
            + "           JOIN tabrow tr ON tr.tab_id = t.id\n"
            + "           JOIN tabfield tf ON tf.row_id = tr.id\n"
            + "          GROUP BY t.id, tf.type\n"
            + "        )\n"
            + "        SELECT\n"
            + "          t.id tab_id,\n"
            + "          t.name tab_name,\n"
            + "          kt.id kid_tab_id,\n"
            + "          count(CASE WHEN tfr.value = 'OK' THEN 1 ELSE null END) ok_fields,\n"
            + "          (SELECT count FROM num_of_fields WHERE id = t.id AND type = tf.type) fields_num,\n"
            + "          tf.type fields_type,\n"
            + "          kt.pretest,\n"
            + "          CASE WHEN tf.type = 'U' THEN kt.learning_fill_date ELSE kt.generalization_fill_date END AS fill_date\n"
            + "        FROM tab t\n"
            + "         JOIN kidtab kt ON kt.tab_id = t.id\n"
            + "         JOIN tabfieldresolve tfr ON tfr.kid_tab_id = kt.id\n"
            + "         JOIN tabfield tf ON tf.id = tfr.tab_field_id\n"
            + "        WHERE t.program_id = #{id}\n"
            + "          AND (	CASE WHEN tf.type = 'U'\n"
            + "                THEN kt.learning_fill_date IS NOT NULL AND finished_learning = true\n"
            + "                ELSE kt.generalization_fill_date IS NOT NULL AND finished_generalization = true\n"
            + "                END)\n"
            + "        GROUP BY t.id, t.name, kt.id, tf.type\n"
            + "        ORDER BY tab_name DESC, fields_type DESC")
})
public class Program extends FilterableModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "symbol")
    private String symbol;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "description")
    private String description;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "finished")
    private boolean finished;
    
    //TODO: Does it field is used for something?
    @Transient
    private Integer collectedData;
    
    @Transient
    private Integer kidId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kid_id", referencedColumnName = "id")    
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
        this.id = Integer.toUnsignedLong(id);
        this.finished = finished;
    }

    public Program(int id, String symbol, String name, String description, boolean finished, int kidId, List<Table> tables) {
        this.id = Integer.toUnsignedLong(id);
        this.symbol = symbol;
        this.name = name;
        this.description = description;
        this.finished = finished;
        this.tables = tables;
        this.kidId = kidId;
    }
}
