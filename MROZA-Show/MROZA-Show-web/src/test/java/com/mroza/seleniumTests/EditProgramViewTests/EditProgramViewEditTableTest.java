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
package com.mroza.seleniumTests.EditProgramViewTests;

import com.mroza.models.Kid;
import com.mroza.models.Program;
import com.mroza.utils.DatabaseUtils;
import com.mroza.utils.SeleniumUtils;
import com.mroza.utils.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EditProgramViewEditTableTest {

    private Kid kid;
    private Program program;
    private EditProgramViewPage editProgramViewPage;
    private DatabaseUtils databaseUtils;
    private String oldTableName;
    private List<String> oldRowsNames;
    private String expectedErrorMessage = Utils.getMsgFromResources("editProgramView.wrongNumberOfRows");

    @Before
    public void setUp() {
        SeleniumUtils.setUpDriverConnection();
        databaseUtils = new DatabaseUtils();
        databaseUtils.cleanUpDatabase();
        kid = databaseUtils.setUpKid("CODE_1");
        program  = databaseUtils.setUpProgram("A_SYMBOL_1", "NAME_1", "DESCTIPTION_1", kid);
        oldTableName = "TABLE_NAME";
        oldRowsNames = new ArrayList<String>(){{add("ROW_NAME");}};
        databaseUtils.setUpTableWithRows(oldTableName, oldRowsNames, "DESCRIPTION", 1, 1, program);
        editProgramViewPage = PageFactory.initElements(new ChromeDriver(), EditProgramViewPage.class);
        editProgramViewPage.open(SeleniumUtils.editProgramsViewUrl, program);
    }

    @After
    public void tearDown() {
        editProgramViewPage.close();
        DatabaseUtils databaseUtils = new DatabaseUtils();
        databaseUtils.cleanUpDatabase();
    }


    @Test
    public void deleteAllRowsTableToProgramTest() {

        List<String> tablesNamesListContent = editProgramViewPage.getTablesNamesListContent();
        assertEquals("Content should only one table", tablesNamesListContent.size(), 1);
        assertEquals("Table should have expected header", tablesNamesListContent.get(0), oldTableName);
        editProgramViewPage.clickEditTableButton(oldTableName);
        editProgramViewPage.clickDeleteRow(oldTableName, oldRowsNames.get(0));
        editProgramViewPage.clickSaveTableButton(oldTableName);
        String errorMessage = editProgramViewPage.getErrorMessage();
        assertEquals("Message should show that table with no rows cannot be save", expectedErrorMessage, errorMessage);

    }

    @Test
    public void editRowsTableToProgramTest() {
        List<String> tablesNamesListContent = editProgramViewPage.getTablesNamesListContent();
        assertEquals("Content should only one table", tablesNamesListContent.size(), 1);
        assertEquals("Table should have expected header", tablesNamesListContent.get(0), oldTableName);
        editProgramViewPage.clickEditTableButton(oldTableName);
        String newTableName = "NEW_TABLE_NAME";
        String newTableRowName = "NEW_TABLE_ROW_NAME";
        editProgramViewPage.changeTableName(newTableName, oldTableName);
        editProgramViewPage.changeTableRow(newTableName, newTableRowName, oldRowsNames.get(0));
        editProgramViewPage.clickSaveTableButton(newTableName);

        refreshEditProgramView();
        tablesNamesListContent = editProgramViewPage.getTablesNamesListContent();
        assertEquals("Content should only one table", tablesNamesListContent.size(), 1);
        assertEquals("Table should have changed header", tablesNamesListContent.get(0), newTableName);
        List<String> tableRowsNames = editProgramViewPage.getTableRowsNamesForTable(newTableName, false);
        assertEquals("Table should contain only one row", tableRowsNames.size(), 1);
        assertEquals("Table row name should have changed", tableRowsNames.get(0), newTableRowName);
    }

    @Test
    public void copyTableToProgramTest() {

        List<String> tableNamesListContent = editProgramViewPage.getTablesNamesListContent();
        assertEquals("Content should only one table", tableNamesListContent.size(), 1);
        assertEquals("Table should have expected header", tableNamesListContent.get(0), oldTableName);
        editProgramViewPage.clickCopyTableButton(oldTableName);

        tableNamesListContent = editProgramViewPage.getTablesNamesListContent();
        assertEquals("Content should contain copied tables", tableNamesListContent.size(), 2);
        assertEquals("Table should have expected header", tableNamesListContent.get(0), oldTableName);
        assertEquals("Copied table should have empty header", tableNamesListContent.get(1), "");

    }


    private void refreshEditProgramView() {
        editProgramViewPage.close();
        editProgramViewPage = PageFactory.initElements(new ChromeDriver(), EditProgramViewPage.class);
        editProgramViewPage.open(SeleniumUtils.editProgramsViewUrl, program);
    }


}