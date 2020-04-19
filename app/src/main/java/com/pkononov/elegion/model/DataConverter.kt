package com.pkononov.elegion.model

import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

internal class DataConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val envelopedType =
            TypeToken.getParameterized(Data::class.java, type).type
        val delegate =
            retrofit.nextResponseBodyConverter<Data<*>>(
                this,
                envelopedType,
                annotations
            )
        return Converter { body: ResponseBody ->
            val data = delegate.convert(body)!!
            data.response
        }
    }
}