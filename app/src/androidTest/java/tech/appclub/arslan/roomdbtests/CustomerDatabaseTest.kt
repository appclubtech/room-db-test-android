package tech.appclub.arslan.roomdbtests

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.*
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
        assertEquals("Bill Hoffman", mAllCustomers.first().fullName)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllCustomers() {
        mCustomerDAO.deleteAllCustomers()
        val mAllCustomers = LiveDataTestUtil.getValue(mCustomerDAO.getAllCustomer())
        assertEquals(mCustomerDAO.totalCustomers(), mAllCustomers.size)
        assertTrue(mAllCustomers.isEmpty())
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
        val mAllAdultCustomers = LiveDataTestUtil.getValue(mCustomerDAO.getAllAdultCustomers())
        val mAllCustomers = LiveDataTestUtil.getValue(mCustomerDAO.getAllCustomer())
        assertNotEquals(mAllCustomers.size, mAllAdultCustomers.size)
        assertEquals("Bill Hoffman", mAllAdultCustomers.first().fullName)
    }

    @Test
    @Throws(Exception::class)
    fun allMaleCustomers() {
        val mAllMaleCustomers = LiveDataTestUtil.getValue(mCustomerDAO.getAllMaleCustomers())
        assertEquals(mCustomerDAO.totalMaleCustomers(), mAllMaleCustomers.size)
        assertEquals("John Smith", mAllMaleCustomers[1].fullName)
    }

    @Test
    @Throws(Exception::class)
    fun allFemaleCustomers() {
        val mAllFemaleCustomers = LiveDataTestUtil.getValue(mCustomerDAO.getAllFemaleCustomers())
        assertEquals(mCustomerDAO.totalFemaleCustomers(), mAllFemaleCustomers.size)
        assertEquals("Maria Garcia", mAllFemaleCustomers.first().fullName)
    }

    @Test
    @Throws(Exception::class)
    fun allOtherGenderCustomers() {
        val mAllOtherGenderCustomers =
            LiveDataTestUtil.getValue(mCustomerDAO.getAllOtherGenderCustomers())
        assertEquals(mCustomerDAO.totalOtherGenderCustomers(), mAllOtherGenderCustomers.size)
        assertEquals("Catherine Jones", mAllOtherGenderCustomers.first().fullName)
    }

    @Test
    @Throws(Exception::class)
    fun allActiveCustomers() {
        val mAllActiveCustomers = LiveDataTestUtil.getValue(mCustomerDAO.getAllActiveCustomers())
        assertEquals(mCustomerDAO.allActiveCustomerCount(), mAllActiveCustomers.size)
        assertEquals("Maria Garcia", mAllActiveCustomers[2].fullName)
    }

    @Test
    @Throws(Exception::class)
    fun allNonActiveCustomers() {
        val mAllNonActiveCustomers =
            LiveDataTestUtil.getValue(mCustomerDAO.getAllNonActiveCustomers())
        assertEquals(mCustomerDAO.allNonActiveCustomerCount(), mAllNonActiveCustomers.size)
        assertEquals("John Smith", mAllNonActiveCustomers[1].fullName)
    }

    @Test
    @Throws(Exception::class)
    fun updateCustomerStatus() {
        val customer =
            Customer(id = 1, fullName = "Bill Hoffman", age = 43, gender = 0, isCustomer = 1)
        mCustomerDAO.updateACustomer(customer)
        val mAllCustomers = LiveDataTestUtil.getValue(mCustomerDAO.getAllCustomer())
        assertEquals(1, mAllCustomers.first().isCustomer)
    }

    @Test
    @Throws(Exception::class)
    fun isIDIncrementing() {
        val mAllCustomers = LiveDataTestUtil.getValue(mCustomerDAO.getAllCustomer())
        assertEquals(1, mAllCustomers.first().id)
        assertEquals(2, mAllCustomers[1].id)
        assertEquals(3, mAllCustomers[2].id)
    }

    @Test
    @Throws(Exception::class)
    fun eldestCustomer() {
        val eldest = mCustomerDAO.eldestCustomer()
        val maxAge = mCustomerDAO.listOfAges().max()
        assertEquals(maxAge, eldest)
    }

    @After
    fun closeDatabase() {
        mCustomerDatabase.close()
    }

}