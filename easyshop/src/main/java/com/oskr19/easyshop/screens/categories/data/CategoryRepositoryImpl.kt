package com.oskr19.easyshop.screens.categories.data

import com.oskr19.easyshop.core.data.preferences.EasyShopPrefs
import com.oskr19.easyshop.core.data.repository.BaseRepository
import com.oskr19.easyshop.core.domain.failure.Failure
import com.oskr19.easyshop.core.domain.network.NetworkHandler
import com.oskr19.easyshop.screens.categories.domain.CategoryRepository
import com.oskr19.easyshop.screens.common.dto.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by oscar.vergara on 4/08/2021
 */
class CategoryRepositoryImpl(
    networkHandler: NetworkHandler,
    private val prefs: EasyShopPrefs,
    private val api: CategoriesAPI
) : BaseRepository(networkHandler), CategoryRepository {

    override suspend fun getCategories(): Flow<List<Category>> {
        return flow {
            try {
                val response = api.getCategories(prefs.getSiteID())
                with(response){
                    if (isSuccessful){
                        body()?.let { body->
                            emit(body)
                        }
                    } else {
                        throw Failure.ServerError(response.message())
                    }
                }
            } catch (e: Exception){
                throw resolveFailure(e)
            }
        }
    }

    override suspend fun getCategoryInfo(categoryId: String): Flow<Category> {
        return flow {
            try {
                val response = api.getCategoryInfo(categoryId)
                with(response){
                    if (isSuccessful){
                        body()?.let { body->
                            emit(body)
                        }
                    } else {
                        throw Failure.ServerError(response.message())
                    }
                }
            } catch (e: Exception){
                throw resolveFailure(e)
            }
        }
    }
}
