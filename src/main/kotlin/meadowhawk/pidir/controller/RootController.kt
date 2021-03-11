package meadowhawk.pidir.controller

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import meadowhawk.pidir.service.Pi
import meadowhawk.pidir.service.PiService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
class RootController() {
    @Autowired
    lateinit var piService: PiService

    private val mapper = jacksonObjectMapper().registerModule(JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

////TODO: ?need to persist data since Heroku can shutdown if your running the free dynamo

    @GetMapping("/", produces = arrayOf(MediaType.TEXT_PLAIN_VALUE))
    @ResponseStatus(HttpStatus.OK)
    fun index(): String {
        return "Welcome to PiDir"
    }

    @GetMapping("/pis", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun listPis(): List<Pi> {
        return piService.getPis()
    }

    @GetMapping("/pi/name/{name}", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun getPiByName(@PathVariable name: String): ArrayList<Pi?> {
        return arrayListOf(piService.getPiByName(name))
    }

    @DeleteMapping("/pi/name/{name}")
    fun removePi(@PathVariable name: String): ResponseEntity<String> {
        piService.removePi(name)
        return ResponseEntity(HttpStatus.ACCEPTED)
    }

    @PostMapping("/pi")
    fun createPi(@RequestBody pi: Pi) : List<Pi>{
        piService.createPi(pi)
        return arrayListOf(pi)
    }

}