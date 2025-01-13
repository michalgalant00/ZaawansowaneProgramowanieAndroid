package com.example.masterand.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.masterand.models.ProfileWithScore

@Dao
interface ProfileWithScoreDao {
    @Query(
        """
            SELECT
                profile.email AS profileEmail,
                score.id AS scoreId,
                profile.login AS profileName,
                score.value AS scoreValue,
                score.numberOfColors AS numberOfColors
            FROM
                profile, score
            WHERE
                profile.email = score.email AND score.numberOfColors = :selectedNumber
        """
    )
    suspend fun getProfilesWithScores(selectedNumber: Int) : List<ProfileWithScore>
}