package domain

import data.SIZE_M
import data.SIZE_N
import presentation.model.OutputModel
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

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
        val time: LocalDateTime
        try {
            time = LocalDateTime.parse(timeStart, DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm:ss"))
        } catch (ex: DateTimeParseException) {
            return Error(OutputModel("Time of session start is in incorrect format"))
        }
        if (startTimes.isEmpty()) {
            return Success
        }
        for (i in startTimes.indices) {
            if (time < startTimes[i]) {
                if (time + duration <= startTimes[i]) {
                    return Success
                }
            }
            if (time <= startTimes[i] + durations[i]) {
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