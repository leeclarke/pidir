package meadowhawk.pidir

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PiDirApplication

fun main(args: Array<String>) {
	runApplication<PiDirApplication>(*args)
}
