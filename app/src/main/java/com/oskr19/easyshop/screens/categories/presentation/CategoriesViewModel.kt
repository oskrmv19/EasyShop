package com.oskr19.easyshop.screens.categories.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oskr19.easyshop.core.domain.failure.Failure
import com.oskr19.easyshop.core.presentation.viewmodel.BaseViewModel
import com.oskr19.easyshop.screens.categories.domain.usecase.GetCategoriesUseCase
import com.oskr19.easyshop.screens.categories.domain.usecase.GetCategoryInfoUseCase
import com.oskr19.easyshop.screens.common.dto.Category
import com.oskr19.easyshop.screens.common.dto.DetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by oscar.vergara on 28/07/2021
 */
@HiltViewModel
class CategoriesViewModel @Inject constructor(
    application: Application,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getCategoryInfoUseCase: GetCategoryInfoUseCase
) : BaseViewModel(application) {

    private var currentChildren = listOf<Category>()

    fun getCategories(): LiveData<List<Category>> {
        val categories = MutableLiveData<List<Category>>()
        launch {
            getCategoriesUseCase.run(GetCategoriesUseCase.Params(""))
                .onStart { setEventLoading() }
                .onEmpty { setEventError(Failure.ServerError()) }
                .catch { handleFailure(it as Failure) }
                .collect {
                    currentChildren = listOf()
                    categories.postValue(it)
                    setEventFinished()
                }
        }
        return categories
    }

    fun getCategoryInfo(categoryId: String): LiveData<Category> {
        val categories = MutableLiveData<Category>()
        launch {
            getCategoryInfoUseCase.run(GetCategoryInfoUseCase.Params(categoryId))
                .onStart { setEventLoading() }
                .onEmpty { setEventError(Failure.ServerError()) }
                .catch { handleFailure(it as Failure) }
                .collect {
                    categories.postValue(it)
                    currentChildren = it.childrenCategories ?: listOf()
                    setEventFinished()
                }
        }
        return categories
    }

    fun getCategoryChildren() = currentChildren
}