package com.softcross.moviedetective.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.softcross.moviedetective.core.common.Resource
import com.softcross.moviedetective.domain.model.User
import com.softcross.moviedetective.domain.repository.FirebaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : FirebaseRepository {


    override fun loginUser(email: String, password: String): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading)
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            result.user?.let {
                val loggedUser = getUserDetailFromFirestore(it.uid)
                emit(Resource.Success(loggedUser))
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
    ): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading)
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.let {
                val userModel = User(result.user!!.uid, name, surname, "$name $surname")
                addUserDetailsToFirestore(userModel)
                emit(Resource.Success(userModel))
            }
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override suspend fun addUserDetailsToFirestore(userModel: User) {
        val firestoreUsers = firebaseFirestore.collection("Users").document(userModel.id)
        firestoreUsers.set(
            hashMapOf(
                "name" to userModel.name,
                "surname" to userModel.surname
            )
        ).addOnFailureListener {
            throw Exception(it.message)
        }.await()
    }

    override suspend fun getUserDetailFromFirestore(userID: String): User {
        val firestoreDoc = firebaseFirestore.collection("Users").document(userID).get().await()
        if (firestoreDoc.data != null) {
            val name = firestoreDoc.data?.get("name").toString()
            val surname = firestoreDoc.data?.get("surname").toString()
            val userModel = User(
                userID,
                name,
                surname,
                "$name $surname"
            )
            return userModel
        } else {
            throw Exception("Something went wrong while getting user details from firestore")
        }

    }


}