package com.example.database

import com.example.model.User

interface DatabaseService {
    fun insertUser(user: User)
    fun getAllUser(): ArrayList<User>
}