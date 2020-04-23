package tech.appclub.arslan.roomdbtests.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
* ----- Gender -----
* 0 - Male
* 1 - Female
* 2 - Prefer not to say
*
* */

/*
* ----- isCustomer -----
* 0 - Active
* 1 - No More
*
* */

@Entity(tableName = "person_table")
data class Person(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var fullName: String? = null,
    var age: Int? = null,
    var gender: Int? = null,
    var isCustomer: Int? = null

)