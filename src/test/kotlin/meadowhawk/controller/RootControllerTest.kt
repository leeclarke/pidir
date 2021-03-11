package meadowhawk.pidir.controller


import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import meadowhawk.pidir.service.Pi
import meadowhawk.pidir.service.PiService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(RootController::class)
@AutoConfigureMockMvc(addFilters = false)
internal class RootControllerTest{
    private val mapper = jacksonObjectMapper().registerModule(JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var mockPiService: PiService

    @Test
    fun `when Request to Create Pi is called the entry is created and returned to requester`() {
        val testPi = Pi("pi-test-service", "168.192.1.10", "test-network", "tag")
        val json = mapper.writeValueAsString(testPi)
        `when`(mockPiService.createPi(testPi)).thenReturn(testPi)

        callPostPi(json).andExpect(status().isOk)
                .andExpect(jsonPath("$[:1].name").value("pi-test-service"))
                .andExpect(jsonPath("$[:1].ip").value("168.192.1.10"))
                .andExpect(jsonPath("$[:1].network").value("test-network"))
                .andExpect(jsonPath("$[:1].tag").value("tag"))
                .andExpect(jsonPath("$[:1].timestamp").isNotEmpty)
                .andReturn()

        verify(mockPiService).createPi(testPi)
    }


    private fun callGetPi() =
            mockMvc.perform(MockMvcRequestBuilders.get("/pi").contentType(MediaType.APPLICATION_FORM_URLENCODED))

    private fun callPostPi(testPi: String) =
                    mockMvc.perform(MockMvcRequestBuilders.post("/pi").contentType(MediaType.APPLICATION_JSON).content(testPi))



}
