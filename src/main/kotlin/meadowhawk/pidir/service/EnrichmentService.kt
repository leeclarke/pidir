package meadowhawk.pidir.service

import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDateTime

@Component
class EnrichmentService {

    fun evaluateStatus(pi: Pi): PiStatus{

        return when (Duration.between(pi.timestamp, LocalDateTime.now()).toMinutes()) {
            in 0L..6L -> PiStatus.GREEN
            in 7L..16L -> PiStatus.YELLOW
            else -> PiStatus.RED
        }
    }
}