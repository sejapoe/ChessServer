package ru.sejapoe.chess

import io.ktor.network.tls.certificates.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.slf4j.LoggerFactory
import ru.sejapoe.chess.plugins.configureRouting
import ru.sejapoe.chess.plugins.configureSecurity
import ru.sejapoe.chess.plugins.configureSerialization
import ru.sejapoe.chess.plugins.configureSockets
import java.io.File

fun main() {
    val keyStoreFile = File("./keystore.p12")
    val keystore = generateCertificate(
        file = keyStoreFile,
        keyAlias = "sampleAlias",
        keyPassword = "grace",
        jksPassword = "grace"
    )

    val environment = applicationEngineEnvironment {
        log = LoggerFactory.getLogger("ktor.application")
        connector {
            port = 8080
        }
        sslConnector(
            keyStore = keystore,
            keyAlias = "sampleAlias",
            keyStorePassword = { "grace".toCharArray() },
            privateKeyPassword = { "grace".toCharArray() }
        ) {
            port = 8443
            keyStorePath = keyStoreFile
        }
        module(Application::configureSecurity)
        module(Application::configureSerialization)
        module(Application::configureSockets)
        module(Application::configureRouting)
    }

    embeddedServer(Netty, environment).start(wait = true)
}