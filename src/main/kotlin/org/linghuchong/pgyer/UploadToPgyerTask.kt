package org.linghuchong.pgyer

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class UploadToPgyerTask : DefaultTask() {
    lateinit var apkFile: File
    lateinit var apiKey: String
    var buildType: String = "release"
    var buildUpdateDescription: String? = ""

    @TaskAction
    fun upload() {
        val uploader = PgyerUploader()
        val response = uploader.uploadApk(apkFile, apiKey, buildUpdateDescription)
        println("上传结果: $response")
    }
}
