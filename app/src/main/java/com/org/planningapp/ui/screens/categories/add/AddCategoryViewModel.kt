package com.org.planningapp.ui.screens.categories.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.planningapp.data.repository.CategoryRepository
import com.org.planningapp.domain.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title = _title

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess = _isSuccess

    suspend fun addCategory(category: Category): Boolean {
        return viewModelScope.async {
            _isLoading.value = true
            val res = categoryRepository.createCategory(category)
            _isSuccess.value = res
            _isLoading.value = false
            res
        }.await()
    }

    fun onTitleChange(title: String) {
        this._title.value = title
    }
}