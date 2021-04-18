package com.wutsi.tracking.config

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.wutsi.storage.StorageService
import com.wutsi.storage.aws.S3HealthIndicator
import com.wutsi.storage.aws.S3StorageService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(
    value = ["storage.s3.enabled"],
    havingValue = "true"
)
class StorageS3Configuration(
    @Value("\${aws.s3.region}") private val region: String,
    @Value("\${storage.s3.bucket}") private val bucket: String

) {
    @Bean
    fun amazonS3(): AmazonS3 {
        return AmazonS3ClientBuilder
            .standard()
            .withRegion(region)
            .build()
    }

    @Bean
    fun storageService(): StorageService {
        return S3StorageService(amazonS3(), bucket)
    }

    @Bean
    fun s3HealthIndicator(): HealthIndicator {
        return S3HealthIndicator(amazonS3(), bucket)
    }
}
