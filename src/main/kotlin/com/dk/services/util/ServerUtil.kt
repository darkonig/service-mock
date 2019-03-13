package com.dk.services.util

import com.dk.parser.nginxparser.NgxBlock
import com.dk.parser.nginxparser.NgxConfig
import com.dk.parser.nginxparser.NgxIfBlock
import com.dk.parser.nginxparser.NgxParam
import com.dk.services.config.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

@Component
class ServerUtil {

    @Value("\${response.path}")
    private lateinit var path: String

    fun getLocation(requestUri:String, request: ServerHttpRequest): Response {
        println("PATH " + path)
        //val requestUri = "http://l:9090/api/xyz?api=risk.newton.global.order.audit.list.1"
        var vars = hashMapOf(
            "\$request_uri" to ConfigParam(listOf(StrEqFunction(), StrLikeFunction()), requestUri),
            "\$param" to ConfigParamParam(listOf(StrEqFunction(), StrLikeFunction()), request),
            "\$random" to ConfigParamRandom(listOf(FltEqFunction(), FltGreaterFunction(), FltLessFunction()), Random.nextFloat())
        )

        //var path = "/Users/caio.andrade/IdeaProjects/kotlin-demo/src/main/resources/"
        val conf = NgxConfig.read(path + "/server.conf")

        val serverBlock = conf.findBlock("server")

        val locations = serverBlock.findAll(NgxConfig.BLOCK,"location")

        for (location in locations) {
            val l = location as NgxBlock
            if (!requestUri.contains(Regex(l.values[1]))) {
                continue;
            }

            println(location)

            val type = l.findParam("default_type")
            var blReturn = l.findParam("return")
            if (type != null) {
                println(type.value)
            }

            val bl = ngIf(l, vars, l)

            blReturn = bl.findParam("return")
            val addHeader = bl.findAll(NgxConfig.PARAM, "add_header")

            var cookies = ArrayList<String>()
            if (addHeader != null) {
                for (h in addHeader)  {
                    val param = h as NgxParam
                    if ("Set-Cookie" == param.values[0]) {
                        cookies.add(param.values[1])
                    }
                }
            }


            if (blReturn != null) {
                println("->" + blReturn.values)
                var value = blReturn.values[1]
                if (value.startsWith('\'')) {
                    value = value.substring(1, value.length - 1)
                } else if ("file" == value) {
                    value = blReturn.values[2]
                    value = File(path + "/" + value.substring(1, value.length - 1)).readText(Charsets.UTF_8)
                }
                return Response((if (type != null) type.value else ""), blReturn.values[0].toInt(), value, cookies)
            }
        }

        return Response("application/text", 404, "", ArrayList<String>())
    }

    private fun ngIf(l: NgxBlock, vars: HashMap<String, CfgParam<out Any>>, ret: NgxBlock): NgxBlock {
        //var blReturn1 = blReturn
        for (entry in l.entries) {
            println("Entry " + entry)
            if (entry is NgxIfBlock) {
                val _var = entry.values[0]

                val configParam = vars.get(_var)
                if (configParam != null) {
                    if (configParam.getValue(entry)) {
                        return ngIf(entry, vars, entry)
                    }
                }

            }
        }

        return ret
    }

}

data class Response(val contentType:String, val status:Int, val body:String, var cookies:List<String>)
