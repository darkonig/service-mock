package com.dk.services.config

import com.dk.parser.nginxparser.NgxIfBlock
import org.springframework.http.server.reactive.ServerHttpRequest

interface CfgParam<T> {
    fun getFunction(fn:String, value:String): Boolean

    fun getValue(entry: NgxIfBlock): Boolean {
        var value = entry.values[2]
        if (value.startsWith("\"")) {
            value = value.substring(1, value.length - 1)
        }
        return getFunction(entry.values[1], value)
    }
}

class ConfigParam(val functions:List<Function<String>>, val paramValue:String): CfgParam<String> {
    override fun getFunction(fn:String, value:String): Boolean {
        for (function in functions) {
            if (function.isFn(fn)) {
                return function.exec(paramValue, value)
            }
        }

        return false
    }
}

class ConfigParamRandom(val functions:List<Function<Float>>, val paramValue:Float): CfgParam<Float> {
    override fun getFunction(fn:String, value:String): Boolean {
        for (function in functions) {
            if (function.isFn(fn)) {
                return function.exec(paramValue, value)
            }
        }

        return false
    }
}

class ConfigParamParam(val functions:List<Function<String>>, val request: ServerHttpRequest): CfgParam<String> {
    override fun getFunction(fn:String, value:String): Boolean {
        return false
    }

    override fun getValue(entry: NgxIfBlock): Boolean {
        //val body =  request.body.toMono().toFuture().get();

        var paramValue = request.queryParams.getFirst(entry.values[1])
        if (paramValue == null) {
            return false;
        }

        var value = entry.values[3]
        if (value.startsWith("\"")) {
            value = value.substring(1, value.length - 1)
        }
        val fn = entry.values[2]
        for (function in functions) {
            if (function.isFn(fn)) {
                return function.exec(value, paramValue)
            }
        }
        return false
    }
}

interface Function<T> {
    fun exec(paramValue:T, param:String):Boolean

    fun isFn(fn:String):Boolean
}

class StrEqFunction: Function<String> {
    override fun isFn(fn: String):Boolean {
        return "==" == fn
    }

    override fun exec(paramValue: String, param: String): Boolean {
        return paramValue == param
    }

}

class StrLikeFunction: Function<String> {
    override fun isFn(fn: String):Boolean {
        return "~" == fn
    }

    override fun exec(paramValue: String, param: String): Boolean {
        return paramValue.contains(Regex(param))
    }
}

class FltEqFunction: Function<Float> {
    override fun isFn(fn: String):Boolean {
        return "==" == fn
    }

    override fun exec(paramValue: Float, param: String): Boolean {
        return paramValue == param.toFloat()
    }

}

class FltGreaterFunction: Function<Float> {
    override fun isFn(fn: String):Boolean {
        return ">" == fn
    }

    override fun exec(paramValue: Float, param: String): Boolean {
        return paramValue > param.toFloat()
    }

}

class FltLessFunction: Function<Float> {
    override fun isFn(fn: String):Boolean {
        return "<" == fn
    }

    override fun exec(paramValue: Float, param: String): Boolean {
        return paramValue > param.toFloat()
    }

}