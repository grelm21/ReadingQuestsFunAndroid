package com.example.readingquestsfun.repository

import com.example.readingquestsfun.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepo {

    /**
     * базовый репо для получения респонса обернутого в Resource
     */
    suspend fun <T> apiResponse(apiToCall: suspend () -> Response<T>): Resource<T>{

        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToCall()

                if (response.isSuccessful){
                    Resource.Success(data = response.body()!!)
                }else{
                    Resource.Error(errorMessage = response.message() ?: "Something went wrong for API side", code = response.code())
                }

            } catch (e: HttpException) {
                Resource.Error(errorMessage = e.message ?: "Something went wrong http")
            } catch (e: IOException) {
                // Returning no internet message
                // wrapped in Resource.Error
                Resource.Error("Please check your network connection")
            } catch (e: Exception) {
                // Returning 'Something went wrong' in case
                // of unknown error wrapped in Resource.Error
                Resource.Error(errorMessage = "Something went wrong")
            }
        }
    }

    /**
     * базовый репо на запрос
     */
    suspend fun <T> apiCall(apiToCall: suspend () -> Call<T>): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val request = apiToCall().execute()
                if (request.isSuccessful) {
                    Resource.Success(data = request.body()!!)
                } else {
                    Resource.Error(
                        errorMessage = request.message() ?: "Something went wrong for API side",
                        code = request.code()
                    )
                }

            } catch (e: HttpException) {
                Resource.Error(errorMessage = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                // Returning no internet message
                // wrapped in Resource.Error
                Resource.Error("Please check your network connection")
            } catch (e: Exception) {
                // Returning 'Something went wrong' in case
                // of unknown error wrapped in Resource.Error
                Resource.Error(errorMessage = "Something went wrong")
            }
        }
    }
}