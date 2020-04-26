package tech.appclub.arslan.roomdbtests.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CustomerDAO {

    // Get all customers
    @Query("SELECT * FROM customer_table ORDER BY fullName ASC")
    fun getAllCustomer(): LiveData<List<Customer>>

    // Get all adult customers
    @Query("SELECT * FROM customer_table WHERE age > 18 ORDER BY fullName ASC")
    fun getAllAdultCustomers(): LiveData<List<Customer>>

    // Get all male customers
    @Query("SELECT * FROM customer_table WHERE gender = 0 ORDER BY fullName ASC")
    fun getAllMaleCustomers(): LiveData<List<Customer>>

    // Get all female customers
    @Query("SELECT * FROM customer_table WHERE gender = 1 ORDER BY fullName ASC")
    fun getAllFemaleCustomers(): LiveData<List<Customer>>

    // Get all other gender customers
    @Query("SELECT * FROM customer_table WHERE gender = 2 ORDER BY fullName ASC")
    fun getAllOtherGenderCustomers(): LiveData<List<Customer>>

    // Get all active customers
    @Query("SELECT * FROM customer_table WHERE isCustomer = 0 ORDER BY fullName ASC")
    fun getAllActiveCustomers(): LiveData<List<Customer>>

    // Get all non-active customers
    @Query("SELECT * FROM customer_table WHERE isCustomer = 1 ORDER BY fullName ASC")
    fun getAllNonActiveCustomers(): LiveData<List<Customer>>

    // Delete all customers
    @Query("DELETE FROM customer_table")
    fun deleteAllCustomers()

    // Insert a customer
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomer(customer: Customer)

    // Delete a customer
    @Delete
    fun deleteACustomer(customer: Customer)

    // Update a customer
    @Update
    fun updateACustomer(customer: Customer)

    // Total customers
    @Query("SELECT COUNT(*) FROM customer_table")
    fun totalCustomers(): Int

    // Get eldest customer
    @Query("SELECT MAX(age) FROM customer_table LIMIT 1")
    fun eldestCustomer(): Long

}