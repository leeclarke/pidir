package meadowhawk.pidir.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime


@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc(addFilters = false)
internal class EnrichmentServiceTest{

   var enrichmentService = EnrichmentService()

    @Test
    fun `When Request to evaluate a GREEN Pi status is called the entry is updated to reflect GREEN status`() {
        val pi =  Pi("pi-test-green-service", "168.192.1.10", "test-network", "tag")

        val status = enrichmentService.evaluateStatus(pi)

        Assertions.assertEquals(PiStatus.GREEN, status)
    }

    @Test
    fun `When Request to evaluate a YELLOW Pi status is called the entry is updated to reflect YELLOW status`() {
        val timeOver6Min = LocalDateTime.now().minusMinutes(7)
        val pi =  Pi("pi-test-yellow-service", "168.192.1.10", "test-network", "tag", timeOver6Min)

        val status = enrichmentService.evaluateStatus(pi)

        Assertions.assertEquals(PiStatus.YELLOW, status)
    }

    @Test
    fun `When Request to evaluate a RED Pi status is called the entry is updated to reflect RED status`() {
        val timeOver40Min = LocalDateTime.now().minusMinutes(40)
        val pi =  Pi("pi-test-redn-service", "168.192.1.10", "test-network", "tag", timeOver40Min)

        val status = enrichmentService.evaluateStatus(pi)

        Assertions.assertEquals(PiStatus.RED, status)
    }
}