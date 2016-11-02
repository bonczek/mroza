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

package com.mroza.service;

import com.mroza.dao.*;
import com.mroza.interfaces.KidPeriodsService;
import com.mroza.interfaces.KidsService;
import com.mroza.models.*;
import com.mroza.models.charts.ResolvedTabData;
import com.mroza.models.queries.ResolvedTabQuery;
import com.mroza.utils.ReflectionWrapper;
import com.mroza.utils.Utils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(Arquillian.class)
public class KidsServiceDbImplTest {

    @Inject
    private KidsServiceDbImpl kidsService;
       
    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")                                
                .addClass(KidsDao.class)
                .addClass(org.mockito.ArgumentMatcher.class)
                .addPackage(KidTableArgumentMatcher.class.getPackage())
                .addPackage(KidsService.class.getPackage())
                .addPackage(Kid.class.getPackage())
                .addPackage(ResolvedTabQuery.class.getPackage())
                .addPackage(ResolvedTabData.class.getPackage())                                
                .addPackage(KidsServiceDbImpl.class.getPackage())                
                .addPackage(Utils.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @Test
    public void getAllKidsTest() {
        List<Kid> exampleKids = Utils.basicKids();
        exampleKids.forEach(kidsService::saveKid);
                
        List<Kid> kids = kidsService.getAllKids();
        Assert.assertEquals(2, kids.size());
    }

    @Test
    public void getKidDetailedData() {
        //TODO : NullPointerException in KidTable constructor - ResolvedField
//        Kid exampleKid = Utils.kidDeeply("QWE");
//        kidsService.saveKid(exampleKid);
//        
//        Kid kidWithDetailedData = kidsService.getKidDetailedData(exampleKids.get(0).getId().intValue());
//
//        Assert.assertEquals(exampleKids.get(0).getPrograms().size(), kidWithDetailedData.getPrograms().size());
//        Assert.assertEquals(exampleKids.get(0).getPeriods().size(), kidWithDetailedData.getPeriods().size());
    }

    @Test
    public void existsKidWithCode() {
        String[] codes = { "QWE" };
        List<Kid> exampleKids = Utils.basicKidWithCode(Arrays.asList(codes));
        exampleKids.forEach(kidsService::saveKid);

        Boolean addedKidExistence = kidsService.existsKidWithCode(codes[0]);
        Boolean notAddedKidExistence = kidsService.existsKidWithCode("ABC");

        Assert.assertTrue("Added kid should be found as existed",addedKidExistence);
        Assert.assertFalse("Not added kid should not be found as existed", notAddedKidExistence);
    }
        

}
