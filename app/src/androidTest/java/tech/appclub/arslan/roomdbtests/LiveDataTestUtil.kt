package tech.appclub.arslan.roomdbtests

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import tech.appclub.arslan.roomdbtests.data.Customer
import tech.appclub.arslan.roomdbtests.data.CustomerDAO
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

object LiveDataTestUtil {

    @Throws(InterruptedException::class)
    fun <T> getValue(liveData: LiveData<T>): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer: Observer<T> = object : Observer<T> {
            override fun onChanged(t: T) {
                data[0] = t
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)
        @Suppress("UNCHECKED_CAST")
        return data[0] as T
    }

    fun addCustomers(mCustomerDAO: CustomerDAO) {

        var customer =
            Customer(fullName = "Bill Hoffman", age = 43, gender = 0, isCustomer = 0)
        mCustomerDAO.insertCustomer(customer)

        customer =
            Customer(fullName = "Catherine Jones", age = 19, gender = 2, isCustomer = 0)
        mCustomerDAO.insertCustomer(customer)

        customer =
            Customer(fullName = "Henry Williams", age = 28, gender = 2, isCustomer = 1)
        mCustomerDAO.insertCustomer(customer)

        customer = Customer(fullName = "John Smith", age = 21, gender = 0, isCustomer = 1)
        mCustomerDAO.insertCustomer(customer)

        customer =
            Customer(fullName = "Maria Garcia", age = 17, gender = 1, isCustomer = 0)
        mCustomerDAO.insertCustomer(customer)

        customer =
            Customer(fullName = "Martha Stewart", age = 33, gender = 1, isCustomer = 1)
        mCustomerDAO.insertCustomer(customer)

    }
}