package com.org.planningapp.presentation.feature.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.planningapp.data.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _message = MutableStateFlow<String>("")
    val message = _message

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

    fun onSignUp() {
        viewModelScope.launch {
            val res = authenticationRepository.signUp(_email.value, _password.value)
            when (res) {
                true -> _message.emit("Account created successfully!")
                false -> _message.emit("Create account failed !")
            }
        }
    }
}