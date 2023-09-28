package com.org.planningapp.ui.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.planningapp.data.repository.CategoryRepository
import com.org.planningapp.domain.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class CategoriesListViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _categoriesList = MutableStateFlow<List<Category>>(listOf())
    val categoriesList = _categoriesList

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading

    init {
        getCategories()
    }

    fun refresh() {
        getCategories()
    }

    private fun getCategories(){
        viewModelScope.launch {
            val categoryDtos = categoryRepository.getCategories()
            _categoriesList.emit(categoryDtos.map { it ->
                Category(
                    id = it.id ?: "",
                    name = it.name,
                    createdAt = it.createdAt ?: Clock.System.now().toLocalDateTime()
                )
            })

            _isLoading.emit(false)
        }
    }

    fun removeCategory(category: Category){
        viewModelScope.launch {
            val newList = mutableListOf<Category>().apply { addAll(_categoriesList.value) }
            newList.remove(category)
            _categoriesList.emit(newList.toList())

            // use repository to delete
            categoryRepository.deleteCategory(id = category.id)

            // fetch again
            getCategories()
        }
    }
}