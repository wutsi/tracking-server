package com.wutsi.tracking.config

import com.wutsi.tracking.service.TrackPersister
import com.wutsi.tracking.service.pipeline.Pipeline
import com.wutsi.tracking.service.pipeline.step.StepPersist
import com.wutsi.tracking.service.pipeline.step.StepTrafficType
import com.wutsi.tracking.service.pipeline.step.StepUTM
import com.wutsi.tracking.service.pipeline.step.StepUserAgent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TrackingConfiguration {
    @Autowired
    lateinit var persister: TrackPersister

    var bufferSize: Int = 1000

    @Bean
    fun pipeline() = Pipeline(
        arrayListOf(
            stepUTM(), /* IMPORTANT: This MUST be the first step */

            stepUserAgent(),
            stepTrafficType(),

            stepPersist() /* IMPORTANT: This MUST always be the last step */
        )
    )

    @Bean
    fun stepUserAgent() = StepUserAgent()

    @Bean
    fun stepPersist() = StepPersist(persister, bufferSize)

    @Bean
    fun stepTrafficType() = StepTrafficType()

    @Bean
    fun stepUTM() = StepUTM()
}
