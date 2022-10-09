package com.theelitedevelopers.ecommerce.utils

/**
 * @created 08/10/2022 - 6:53 PM
 * @project Ecommerce app
 * @author The Elite Developers
 */

sealed class Resource<T>(data : T? = null, message : String? = null){
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message : String?, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
