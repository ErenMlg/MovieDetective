package com.softcross.moviedetective.core.domain.repository

import com.softcross.moviedetective.core.common.Resource
import com.softcross.moviedetective.core.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {

    fun loginUser(email: String, password: String): Flow<Resource<UserModel>>
    fun registerUser(email: String,password: String,name: String,surname: String): Flow<Resource<UserModel>>
    suspend fun addUserDetailsToFirestore(userModel: UserModel): UserModel
    suspend fun getUserDetailFromFirestore(userID: String): UserModel

}