package meadowhawk.controller

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import meadowhawk.service.Pi
import meadowhawk.service.PiService

@Controller("/")
class RootController(val piService: PiService) {
    private val mapper = jacksonObjectMapper().registerModule(JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)


    @Get("/")
    @Produces(MediaType.TEXT_PLAIN)
    fun index(): String {
        return "Hello Root"
    }

    @Get("/pis")
    @Produces(MediaType.APPLICATION_JSON)
    fun listPis(): String {
        return mapper.writeValueAsString(piService.getPis())
    }

    @Get("/pi/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getPiByName(@PathVariable name: String): String {
        return mapper.writeValueAsString(piService.getPiByName(name))
    }

    @Post("/pi")
    fun createPi(@Body pi: Pi) : HttpResponse<Pi>{
        piService.createPi(pi)
        return HttpResponse.created(pi)
    }

    @Post("/pi/alive/{name}")
    fun statusPi(@PathVariable name: String) : HttpResponse<Pi>{
        //TODO: check to see if exists, update pi status

        return HttpResponse.ok()
    }

}