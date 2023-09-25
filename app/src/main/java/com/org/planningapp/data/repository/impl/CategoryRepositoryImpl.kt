package com.org.planningapp.data.repository.impl

import com.org.planningapp.data.network.dto.CategoryDto
import com.org.planningapp.data.repository.CategoryRepository
import com.org.planningapp.domain.model.Category
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import javax.inject.Inject

const val CATEGORIES_TABLE_ID = "categories"

class CategoryRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
    private val storage: Storage,
) : CategoryRepository {
    override suspend fun createCategory(category: Category): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getCategories(): List<CategoryDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategory(id: String): CategoryDto {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCategory(id: String) {
        TODO("Not yet implemented")
    }
}