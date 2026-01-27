package io.foodmind.data.repository

import io.foodmind.data.source.local.InMemoryFoodDataSource
import io.foodmind.domain.model.Food
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Unit test for FoodRepositoryImpl.
 * Demonstrates testing repository with in-memory data source.
 */
class FoodRepositoryImplTest {
    
    private lateinit var repository: FoodRepositoryImpl
    private lateinit var dataSource: InMemoryFoodDataSource
    
    @Before
    fun setup() {
        dataSource = InMemoryFoodDataSource()
        repository = FoodRepositoryImpl(dataSource)
    }
    
    @Test
    fun `getAllFoods returns list of foods`() = runTest {
        // When
        val foods = repository.getAllFoods().first()
        
        // Then
        assertTrue("Foods list should not be empty", foods.isNotEmpty())
        assertEquals("Should have 5 sample foods", 5, foods.size)
    }
    
    @Test
    fun `getFoodById returns correct food`() = runTest {
        // Given
        val targetId = "1"
        
        // When
        val food = repository.getFoodById(targetId)
        
        // Then
        assertNotNull("Food should not be null", food)
        assertEquals("Food ID should match", targetId, food?.id)
        assertEquals("Food name should be Grilled Chicken Breast", 
            "Grilled Chicken Breast", food?.name)
    }
    
    @Test
    fun `getFoodById returns null for non-existent id`() = runTest {
        // Given
        val nonExistentId = "999"
        
        // When
        val food = repository.getFoodById(nonExistentId)
        
        // Then
        assertNull("Food should be null for non-existent ID", food)
    }
    
    @Test
    fun `searchFoods filters by name`() = runTest {
        // Given
        val query = "chicken"
        
        // When
        val foods = repository.searchFoods(query).first()
        
        // Then
        assertTrue("Should find at least one matching food", foods.isNotEmpty())
        assertTrue("All results should contain query in name, category, or description",
            foods.all { 
                it.name.contains(query, ignoreCase = true) ||
                it.category.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true)
            }
        )
    }
    
    @Test
    fun `searchFoods returns empty list for non-matching query`() = runTest {
        // Given
        val query = "NonExistentFood"
        
        // When
        val foods = repository.searchFoods(query).first()
        
        // Then
        assertTrue("Should return empty list for non-matching query", foods.isEmpty())
    }
}
