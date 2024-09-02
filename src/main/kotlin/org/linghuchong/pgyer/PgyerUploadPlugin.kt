package org.linghuchong.pgyer

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class PgyerUploadPlugin : Plugin<Project> {
    companion object {
        private const val EXTENSION_NAME = "pgyer"
    }

    override fun apply(project: Project) {
        val extension = project.extensions.create(EXTENSION_NAME, PgyerUploadExtension::class.java)

        /*project.tasks.register("uploadToPgyer", UploadToPgyerTask::class.java) {
            group = "upload"
            description = "Uploads the APK to Pgyer"

            doFirst {
                apiKey = extension.apiKey
                buildType = extension.buildType ?: "release"
                buildUpdateDescription = extension.buildUpdateDescription ?: ""
                apkFile = project.file("build/outputs/apk/$buildType/app-$buildType.apk")
            }
        }*/
        project.tasks.register("uploadToPgyer", UploadToPgyerTask::class.java) { task ->
            task.group = "upload"
            task.description = "Uploads the APK to Pgyer"
            task.apiKey = extension.apiKey ?: error("必须传入apiKey")
            task.buildType = extension.buildType ?: "release"
            task.buildUpdateDescription = extension.buildUpdateDescription

            val outputDir = File("build/outputs/apk/${task.buildType}")
            if (!outputDir.exists()) {
                error("找不到输出目录：${outputDir.absolutePath}")
            }
            val apk = outputDir.listFiles { _, name -> name.endsWith(".apk") }.first()
            // 动态设置APK文件路径
            task.apkFile = apk
        }
    }
}