package com.example.LeftLoversApp.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    private val token = stringPreferencesKey("token")
    private val isLogin = booleanPreferencesKey("isLogin")
<<<<<<< HEAD
//    private val userId = stringPreferencesKey("id")
    private val listData = listOf(stringPreferencesKey(""))
=======
    private val username = stringPreferencesKey("username")
    private val id = stringPreferencesKey("id")
    private val transactionId = stringPreferencesKey("transactionId")
>>>>>>> 0fd771ca8df55ad34ffc14c006b91c44077c3cfb

    fun getUsername(): Flow<String> {
        return dataStore.data.map {
            it[username] ?: "null"
        }
    }
    suspend fun saveUsername(username: String) {
        dataStore.edit {
            it[this.username] = username
        }
    }
    fun getTransactionId(): Flow<String> {
        return dataStore.data.map {
            it[transactionId] ?: "null"
        }
    }
    suspend fun saveTransactionId(transactionId: String) {
        dataStore.edit {
            it[this.transactionId] = transactionId
        }
    }
    suspend fun resetTransactionId() {
        dataStore.edit {
            it[transactionId] = "null"
        }
    }
    fun getId(): Flow<String> {
        return dataStore.data.map {
            it[id] ?: "null"
        }
    }
    suspend fun saveId(username: String) {
        dataStore.edit {
            it[this.id] = username
        }
    }
    fun getToken(): Flow<String> {
        return dataStore.data.map {
            it[token] ?: "null"
        }
    }
    fun isLogin(): Flow<Boolean> {
        return dataStore.data.map {
            it[isLogin] ?:true
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit {
            it[this.token] = token
        }
    }

//    suspend fun saveUserId(id: String) {
//        dataStore.edit {
//            it[this.userId] = id
//        }
//    }
//
//    fun getUserId(): Flow<String> {
//        return dataStore.data.map {
//            it[token] ?: "null"
//        }
//    }

    suspend fun setIsLogin(isLogin: Boolean) {
        dataStore.edit {
            it[this.isLogin] = isLogin
        }
    }

    suspend fun logout() {
        dataStore.edit {
            it[token] = "null"
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}