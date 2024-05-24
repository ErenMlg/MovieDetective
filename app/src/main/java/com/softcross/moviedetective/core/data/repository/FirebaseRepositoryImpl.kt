package com.softcross.moviedetective.core.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.softcross.moviedetective.core.common.Resource
import com.softcross.moviedetective.core.domain.model.UserModel
import com.softcross.moviedetective.core.domain.repository.FirebaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : FirebaseRepository {


    override fun loginUser(email: String, password: String): Flow<Resource<UserModel>> {
        return flow {
            emit(Resource.Loading)
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            if (result.user?.uid != null) {
                val user = getUserDetailFromFirestore(result.user?.uid!!)
                emit(Resource.Success(user))
            }
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun registerUser(
        email: String,
        password: String,
        name: String,
        surname: String
    ): Flow<Resource<UserModel>> {
        return flow {
            emit(Resource.Loading)
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            if (result.user != null) {
                val registeredUserModel =
                    UserModel(result.user!!.uid, name, surname, "$name $surname")
                val finalResult = addUserDetailsToFirestore(registeredUserModel)
                emit(Resource.Success(finalResult))
            }
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override suspend fun addUserDetailsToFirestore(userModel: UserModel): UserModel {
        val firestoreUsers = firebaseFirestore.collection("Users").document(userModel.id)
        firestoreUsers.set(
            hashMapOf(
                "name" to userModel.name,
                "surname" to userModel.surname
            )
        ).addOnFailureListener {
            throw Exception("User can not added")
        }
        return userModel
    }

    override suspend fun getUserDetailFromFirestore(userID: String): UserModel {
        val firestoreDoc = firebaseFirestore.collection("Users").document(userID).get().await()
        if (firestoreDoc.data != null) {
            val name = firestoreDoc.data?.get("Name").toString()
            val surname = firestoreDoc.data?.get("Surname").toString()
            val userModel = UserModel(
                userID,
                name,
                surname,
                "$name $surname"
            )
            return userModel
        } else {
            throw Exception("User can not find")
        }

    }


}