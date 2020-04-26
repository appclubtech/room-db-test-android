package tech.appclub.arslan.roomdbtests

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tech.appclub.arslan.roomdbtests.data.Customer
import tech.appclub.arslan.roomdbtests.data.CustomerDAO
import tech.appclub.arslan.roomdbtests.db.CustomerRoomDatabase

/*
* We will be writing all the tests in this class
* Copyright © 2020 App Club
* Author - Arslan Mushtaq (contact@appclub.tech)
* */

@RunWith(AndroidJUnit4::class)
class CustomerDatabaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mCustomerDAO: CustomerDAO
    private lateinit var mCustomerDatabase: CustomerRoomDatabase

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        mCustomerDatabase = Room.inMemoryDatabaseBuilder(context, CustomerRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        mCustomerDAO = mCustomerDatabase.customerDAO()
        LiveDataTestUtil.addCustomers(mCustomerDAO)
    }

    @Test
    @Throws(Exception::class)
    fun verifyInsertedData() {
        val mAllCustomers = LiveDataTestUtil.getValue(mCustomerDAO.getAllCustomer())
        assertEquals("Bill Hoffman", mAllCustomers[0].fullName)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllCustomers() {
        mCustomerDAO.deleteAllCustomers()
        val mAllCustomers = LiveDataTestUtil.getValue(mCustomerDAO.getAllCustomer())
        assertEquals(0, mAllCustomers.size)
    }

    @Test
    @Throws(Exception::class)
    fun deleteACustomer() {
        val mCustomerListBefore = LiveDataTestUtil.getValue(mCustomerDAO.getAllCustomer())
        mCustomerDAO.deleteACustomer(mCustomerListBefore[0])
        val mCustomerListAfter = LiveDataTestUtil.getValue(mCustomerDAO.getAllCustomer())
        assertEquals(mCustomerListBefore.size - 1, mCustomerListAfter.size)
    }

    @Test
    @Throws(Exception::class)
    fun allAdultCustomers() {
        val mAllCustomers = LiveDataTestUtil.getValue(mCustomerDAO.getAllAdultCustomers())
        assertEquals(5, mAllCustomers.size)
        assertEquals("Bill Hoffman", mAllCustomers[0].fullName)
    }

    @Test
    @Throws(Exception::class)
    fun allMaleCustomers() {
        val mAllCustomers = LiveDataTestUtil.getValue(mCustomerDAO.getAllMaleCustomers())
        assertEquals(2, mAllCustomers.size)
        assertEquals("John Smith", mAllCustomers[1].fullName)
    }

    @Test
    @Throws(Exception::class)
    fun allFemaleCustomers() {
        val mAllCustomers = LiveDataTestUtil.getValue(mCustomerDAO.getAllFemaleCustomers())
        assertEquals(2, mAllCustomers.size)
        assertEquals("Maria Garcia", mAllCustomers[0].fullName)
    }

    @Test
    @Throws(Exception::class)
    fun allOtherGenderCustomers() {
        val mAllCustomers = LiveDataTestUtil.getValue(mCustomerDAO.getAllOtherGenderCustomers())
        assertEquals(2, mAllCustomers.size)
        assertEquals("Catherine Jones", mAllCustomers[0].fullName)
    }

    @Test
    @Throws(Exception::class)
    fun allActiveCustomers() {
        val mAllCustomers = LiveDataTestUtil.getValue(mCustomerDAO.getAllActiveCustomers())
        assertEquals(3, mAllCustomers.size)
        assertEquals("Maria Garcia", mAllCustomers[2].fullName)
    }

    @Test
    @Throws(Exception::class)
    fun allNonActiveCustomers() {
        val mAllCustomers = LiveDataTestUtil.getValue(mCustomerDAO.getAllNonActiveCustomers())
        assertEquals(3, mAllCustomers.size)
        assertEquals("John Smith", mAllCustomers[1].fullName)
    }

    @Test
    @Throws(Exception::class)
    fun updateCustomerStatus() {
        val customer = Customer(id = 1, fullName = "Bill Hoffman", age = 43, gender = 0, isCustomer = 1)
        mCustomerDAO.updateACustomer(customer)
        val mAllCustomers = LiveDataTestUtil.getValue(mCustomerDAO.getAllCustomer())
        assertEquals(1, mAllCustomers[0].isCustomer)
    }

    @Test
    @Throws(Exception::class)
    fun totalCustomers() {
        val count = mCustomerDAO.totalCustomers()
        val mAllCustomers = LiveDataTestUtil.getValue(mCustomerDAO.getAllCustomer())
        assertEquals(count, mAllCustomers.size)
    }

    @Test
    @Throws(Exception::class)
    fun isIDIncrementing() {
        val mAllCustomers = LiveDataTestUtil.getValue(mCustomerDAO.getAllCustomer())
        assertEquals(2, mAllCustomers[1].id)
        assertEquals(3, mAllCustomers[2].id)
    }

    @Test
    @Throws(Exception::class)
    fun eldestCustomer() {
        val eldest = mCustomerDAO.eldestCustomer()
        assertEquals(43, eldest)
    }

    @After
    fun closeDatabase() {
        mCustomerDatabase.close()
    }

}