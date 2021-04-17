package com.wutsi.tracking.config

import com.wutsi.storage.StorageService
import com.wutsi.storage.StorageServlet
import com.wutsi.storage.file.FileStorageService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(
    value = ["storage.s3.enabled"],
    havingValue = "false"
)
public class StorageLocalConfiguration(
    @Value("\${storage.local.root-directory}") private val rootDirectory: String,
    @Value("\${storage.local.base-url}") private val baseUrl: String
) {
    @Bean
    fun storageService(): StorageService =
        FileStorageService(rootDirectory, baseUrl)

    @Bean
    fun storageServlet(): ServletRegistrationBean<*> {
        return ServletRegistrationBean(StorageServlet(rootDirectory), "/storage/*")
    }
}
