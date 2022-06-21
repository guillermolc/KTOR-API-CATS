package lagos.guillermo

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import lagos.guillermo.plugins.configureRouting
import lagos.guillermo.plugins.configureSerialization

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8090,
        host = "0.0.0.0"
    ) {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
