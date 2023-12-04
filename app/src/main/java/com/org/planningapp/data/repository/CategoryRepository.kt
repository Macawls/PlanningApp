package com.org.planningapp.data.repository

import com.org.planningapp.data.network.dto.CategoryDto
import com.org.planningapp.domain.model.Category

interface CategoryRepository {
    suspend fun createCategory(category: Category): Boolean
    suspend fun getCategories(): List<CategoryDto>
    suspend fun getCategory(id: String): Category

    // deleting a category would mean
    // deleting all the timesheets associated with it
    // done on the supabase side
    suspend fun deleteCategory(id: String)
}