package com.dk.services.controller

import com.dk.services.messages.Message
import com.dk.services.util.ServerUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.net.URI
import java.util.*


@RestController
class ServerController (private val message: Message) {

    @Autowired
    lateinit var util: ServerUtil

    @GetMapping("/i18n-test")
    fun getLocation(loc: Locale): Mono<String> {
        return Mono.just(message.get("msg.test", loc))
    }

    @RequestMapping("**")
    fun process(request: ServerHttpRequest): ResponseEntity<Mono<String>> {
        val response = util.getLocation(request.uri.toASCIIString(), request)

        var headers = HttpHeaders()

        println("Response " + response)
        for (cookie in response.cookies) {
            headers.add(HttpHeaders.SET_COOKIE, cookie.substring(1, cookie.length - 1))
        }

        if (response.status == 302 || response.status == 301) {
            return status(response.status)
                    .headers(headers)
                    .location(URI.create(response.body))
                    .build()
        }

        if (!response.contentType.isEmpty()) {
            return status(response.status)
                    .header(HttpHeaders.CONTENT_TYPE, response.contentType)
                    .headers(headers)
                    .body(Mono.just(response.body))
        }


        return status(response.status)
                .headers(headers)
                .body(Mono.just(response.body))
    }

    @PostMapping
    fun hello(@RequestBody abc:Map<String, String>): String {
        return abc.toString()
    }


}