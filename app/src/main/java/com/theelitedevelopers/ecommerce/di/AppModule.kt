package com.theelitedevelopers.ecommerce.di

import com.theelitedevelopers.ecommerce.data.ApiInterface
import com.theelitedevelopers.ecommerce.data.repository.ProductRepositoryImpl
import com.theelitedevelopers.ecommerce.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** @Module annotation which will make this class a module
 *  to inject dependency to other class within it's scope.
 * @InstallIn(SingletonComponent::class) this will make
 * this class to inject dependencies across the entire application.
 */

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
//    @Provides
//    @Singleton
//    fun providesProductRepository() : ProductRepository = ProductRepositoryImpl()
}