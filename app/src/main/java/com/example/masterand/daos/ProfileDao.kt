package com.example.masterand.daos

import android.net.Uri
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.example.masterand.models.Profile

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(profile: Profile)

    @Query("""
            UPDATE profile SET
                login = :login,
                description = :description,
                picture = :picture
            WHERE
                email = :email
            """)
    suspend fun update(email: String, login: String, description: String, picture: String)

    @Query("SELECT * FROM profile WHERE email = :email")
    suspend fun getProfileByEmail(email: String) : Profile

    @Query("SELECT * FROM profile")
    suspend fun getAllProfiles() : List<Profile>
}