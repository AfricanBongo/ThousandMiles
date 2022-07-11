package com.thousandmiles.service.steps

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.aggregate.AggregationResult
import androidx.health.connect.client.records.ActivitySession
import androidx.health.connect.client.records.Distance
import androidx.health.connect.client.records.Steps
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.time.TimeRangeFilter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.random.Random
import kotlin.random.nextLong

class StepCountService(private val context: Context) {
    private val client: HealthConnectClient by lazy {
        HealthConnectClient.getOrCreate(context)
    }

    // Health aggregate metrics
    private val stepMetric = Steps.COUNT_TOTAL
    private val distanceMetric = Distance.DISTANCE_TOTAL
    private val activityMetric = ActivitySession.ACTIVE_TIME_TOTAL

    private val shortIntervalInMs: Long = 5000L // 5 seconds
    private val minuteIntervalInMs: Long = 60000L // 1 minute

    /**
     * The number of steps a user has taken from midnight of that day.
     */
    val cumulativeStepCount: Flow<Long> = flow {
        var stepCount: Long = 0

        while (true) {
            // Fetch the step count from the Health Connect app.
            val aggregate: AggregationResult =
                client.aggregate(
                    request = AggregateRequest(
                        metrics = setOf(stepMetric),
                        timeRangeFilter = TimeRangeFilter.between(
                            LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT), LocalDateTime.now()
                        ),
                        dataOriginFilter = emptyList()
                    )
                )


            // Simulate someone walking
            val stepsAggregate: Long = aggregate[stepMetric] ?: 0L
            stepCount = if (stepsAggregate != 0L) stepsAggregate
            else stepCount + Random.nextLong(10L..25L)

            emit(stepCount)
            delay(shortIntervalInMs)
        }
    }

    val cumulativeDistanceCovered: Flow<Double> = flow {
        var distanceCovered: Double = 0.0

        while (true) {
            // Fetch the step count from the Health Connect app.
            val aggregate: AggregationResult =
                client.aggregate(
                    request = AggregateRequest(
                        metrics = setOf(distanceMetric),
                        timeRangeFilter = TimeRangeFilter.between(
                            LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT), LocalDateTime.now()
                        ),
                        dataOriginFilter = emptyList()
                    )
                )

            // Simulate someone walking
            val distanceAggregate: Double = aggregate[distanceMetric] ?: 0.0
            distanceCovered = if (distanceAggregate != 0.0) distanceAggregate
            else distanceCovered + Random.nextDouble(from = 10.00, until = 25.00)

            emit(distanceCovered)
            delay(shortIntervalInMs)
        }
    }

    val cumulativeActiveTime: Flow<Duration> = flow {
        var activeTime = Duration.ZERO

        while (true) {
            // Fetch the step count from the Health Connect app.
            val aggregate: AggregationResult =
                client.aggregate(
                    request = AggregateRequest(
                        metrics = setOf(activityMetric),
                        timeRangeFilter = TimeRangeFilter.between(
                            LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT), LocalDateTime.now()
                        ),
                        dataOriginFilter = emptyList()
                    )
                )

            // Simulate someone walking
            val activeTimeAggregate: Duration = aggregate[activityMetric] ?: Duration.ZERO
            activeTime = if (activeTimeAggregate != Duration.ZERO) activeTimeAggregate
            else activeTime + Duration.ofSeconds(Random.nextLong(2L..5L))
            emit(activeTime)
            delay(shortIntervalInMs)
        }
    }
}