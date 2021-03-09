package meadowhawk.service

import java.time.LocalDateTime
import javax.inject.Singleton
import java.util.concurrent.TimeUnit

data class Pi(val name: String, val ip: String, val network: String, val tag: String, val timestamp: LocalDateTime = LocalDateTime.now())

@Singleton
class PiService{
    private val pis = mutableMapOf<String, Pi>()

    fun getPiByName(name: String): Pi? {
        return try {
            TimeUnit.SECONDS.sleep(3)
            pis[name]
        } catch (e: InterruptedException) {
            null
        }
    }

    fun createPi(newPi: Pi): Pi? {
        pis[newPi.name] = newPi
        return pis[newPi.name]
    }

    fun removePi(pi: Pi) {
        pis.remove(pi.name)
    }

    fun getPis() : List<Pi> {
        return pis.values.toList()
    }
}
