package com.org.planningapp.data.repository.impl

import android.content.Context
import com.org.planningapp.data.repository.IAuthenticationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import javax.inject.Inject

class SupabaseAuthRepository @Inject constructor(
    private val goTrue: GoTrue,
    @ApplicationContext private val context: Context
) : IAuthenticationRepository {
    override suspend fun signIn(email: String, password: String): Boolean {
        return try {
            goTrue.loginWith(Email) {
                this.email = email
                this.password = password
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun signUp(email: String, password: String): Boolean {
        return try {
            goTrue.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}