package tech.appclub.arslan.roomdbtests.db

import androidx.room.Database
import androidx.room.RoomDatabase
import tech.appclub.arslan.roomdbtests.data.Customer
import tech.appclub.arslan.roomdbtests.data.CustomerDAO

@Database(entities = [Customer::class], version = 1, exportSchema = false)
abstract class CustomerRoomDatabase : RoomDatabase() {

    abstract fun customerDAO(): CustomerDAO

}