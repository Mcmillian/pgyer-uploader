package org.linghuchong.pgyer

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException

class PgyerUploader {
    private val client = OkHttpClient()

    fun uploadApk(apkFile: File, apiKey: String, buildUpdateDescription: String?): String? {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("_api_key", apiKey)
            .addFormDataPart("file", apkFile.name,
                apkFile.asRequestBody("application/vnd.android.package-archive".toMediaTypeOrNull())
            )
            .apply {
                if (buildUpdateDescription != null){
                    addFormDataPart("buildUpdateDescription", buildUpdateDescription)
                }
            }
            .build()

        val request = Request.Builder()
            .url("https://www.pgyer.com/apiv2/app/upload")
            .post(requestBody)
            .build()

        return try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.string()
            } else {
                throw IOException("Unexpected code $response")
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}
