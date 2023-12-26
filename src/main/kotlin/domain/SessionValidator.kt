package domain

import data.SIZE_M
import data.SIZE_N
import kotlinx.datetime.toKotlinLocalDateTime
import presentation.model.OutputModel
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.time.Duration
import kotlin.time.toJavaDuration

interface SessionValidator {
    fun validateTimeStart(
        timeStart: String,
        duration: Duration,
        startTimes: List<LocalDateTime>,
        durations: List<Duration>
    ): Result

    fun validatePlace(x: Int, y: Int): Result
}

class SessionValidatorImpl : SessionValidator {
    override fun validateTimeStart(
        timeStart: String,
        duration: Duration,
        startTimes: List<LocalDateTime>,
        durations: List<Duration>
    ): Result {
        val timeJava: java.time.LocalDateTime
        try {
            timeJava = java.time.LocalDateTime.parse(timeStart, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
        } catch (ex: DateTimeParseException) {
            return Error(OutputModel("Time of session start is in incorrect format"))
        }
        if (startTimes.isEmpty()) {
            return Success
        }
        val time = timeJava.toKotlinLocalDateTime()
        for (i in startTimes.indices) {
            if (time < startTimes[i]) {
                if (time.toJavaLocalDateTime() + duration.toJavaDuration() <= startTimes[i].toJavaLocalDateTime()) {
                    return Success
                }
            }
            if (time.toJavaLocalDateTime() <= startTimes[i].toJavaLocalDateTime() + durations[i].toJavaDuration()) {
                return Error(OutputModel("Incorrect time to start session. There will be an intersection with the session at ${startTimes[i]}"))
            }
        }
        return Success
    }

    override fun validatePlace(x: Int, y: Int): Result {
        return when {
            x in 0..SIZE_N && y in 0..SIZE_M -> Success
            else -> Error(OutputModel("Incorrect place number"))
        }
    }

}