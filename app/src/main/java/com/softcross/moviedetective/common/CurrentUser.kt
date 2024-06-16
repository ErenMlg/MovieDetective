package com.softcross.moviedetective.common

import com.softcross.moviedetective.domain.model.User

object CurrentUser {

    private var currentUser: User? = null

    fun setCurrentUser(user: User) {
        currentUser = user
    }

    fun getCurrentUser(): User? {
        return currentUser
    }

    fun removeCurrentUser() {
        currentUser = null
    }

    fun getCurrentUserName(): String = "${currentUser?.name} ${currentUser?.surname?.first()}."
}