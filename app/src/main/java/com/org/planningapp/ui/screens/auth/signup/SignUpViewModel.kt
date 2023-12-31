package com.org.planningapp.ui.screens.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.planningapp.data.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess = _isSuccess

    private val _email = MutableStateFlow<String>("")
    val email = _email

    private val _password = MutableStateFlow("")
    val password = _password
    
    fun onEmailChange(email: String) {
        this._email.value = email
    }

    fun onPasswordChange(password: String) {
        this._password.value = password
    }

    suspend fun onSignUp(): Boolean {
        return viewModelScope.async {
            val res = authenticationRepository.signIn(_email.value, _password.value)
            _isSuccess.value = res
            res
        }.await()
    }
}