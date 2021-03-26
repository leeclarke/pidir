package meadowhawk.pidir.service

import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import meadowhawk.pidir.service.EnrichmentService
import org.springframework.beans.factory.annotation.Autowired

enum class PiStatus(val desc: String){
    GREEN("Good"),
    YELLOW("MIA"),
    RED("Offline")
}

data class Pi(val name: String, val ip: String, val network: String, val tag: String, val timestamp: LocalDateTime = LocalDateTime.now(), var status: PiStatus = PiStatus.GREEN)

@Service
class PiService{
    private val pis = mutableMapOf<String, Pi>()

    @Autowired
    lateinit var enrichmentService: EnrichmentService

    fun getPiByName(name: String): Pi? {
        return try {
            TimeUnit.SECONDS.sleep(3)
            var pi = pis[name]
            if (pi != null) {
                pi.status =  enrichmentService.evaluateStatus(pi)
            }
            pi
        } catch (e: InterruptedException) {
            null
        }
    }

    fun createPi(newPi: Pi): Pi? {
        pis[newPi.name] = newPi
        return pis[newPi.name]
    }

    fun removePi(name: String) {
        pis.remove(name)
    }

    fun getPis() : List<Pi> {
        var piList = pis.values.toList()
        piList.forEach {
            it.status = enrichmentService.evaluateStatus(it)
        }
        return piList
    }
}
