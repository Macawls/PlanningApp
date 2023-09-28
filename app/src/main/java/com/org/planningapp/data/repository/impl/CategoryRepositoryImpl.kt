package com.org.planningapp.data.repository.impl

import com.org.planningapp.data.network.dto.CategoryDto
import com.org.planningapp.data.repository.CategoryRepository
import com.org.planningapp.domain.model.Category
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

const val CATEGORIES_TABLE_ID = "categories"

class CategoryRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
) : CategoryRepository {
    override suspend fun createCategory(category: Category): Boolean {
        return try {
            val categoryDto = CategoryDto(
                name = category.name,
                createdAt = category.createdAt
            )

            postgrest[CATEGORIES_TABLE_ID].insert(categoryDto)
            true

        } catch (e: java.lang.Exception) {
            throw e
        }
    }

    override suspend fun getCategories(): List<CategoryDto> {
        return postgrest[CATEGORIES_TABLE_ID].select().decodeList<CategoryDto>()
    }

    override suspend fun getCategory(id: String): CategoryDto {
        return postgrest[CATEGORIES_TABLE_ID].select {
            eq("id", id)
        }.decodeSingle<CategoryDto>()
    }

    override suspend fun deleteCategory(id: String) {
        postgrest[CATEGORIES_TABLE_ID].delete {
            eq("id", id)
        }
    }
}