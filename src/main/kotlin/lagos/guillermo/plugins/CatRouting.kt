package lagos.guillermo.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import lagos.guillermo.model.Cat
import lagos.guillermo.model.GenericResponse
import lagos.guillermo.model.catsStorage

fun Route.catRouting() {
    route("/cats") {
        get {
            if (catsStorage.isNotEmpty()) call.respond(
                message = GenericResponse<List<Cat>>(
                    status = "Ok",
                    message = "Cats found: ${catsStorage.size}",
                    response = catsStorage
                ),
                status = HttpStatusCode.OK
            ) else call.respond(
                message = GenericResponse<List<Cat>>(
                    status = "Error",
                    message = "No cats found",
                    response = listOf()
                ),
                status = HttpStatusCode.NotFound
            )
        }
    }
    route("/cat") {
        get("{id?}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(
                message = GenericResponse<List<Cat>>(
                    status = "Error",
                    message = "Missing id"
                ),
                status = HttpStatusCode.BadRequest
            )
            val cat = catsStorage
                .find { it.id == id }
                ?: return@get call.respond(
                message = GenericResponse<Cat>(
                    status = "Error",
                    message = "No cat found with id $id",
                ),
                status = HttpStatusCode.NotFound
            )
            call.respond(
                message = GenericResponse(
                    status = "Ok",
                    message = "Cat found with id $id",
                    response = cat
                ),
                status = HttpStatusCode.OK
            )
        }
        post {
            val customer = call.receive<Cat>()
            catsStorage.add(customer)
            call.respond(
                message = GenericResponse<Cat>(
                    status = "Ok",
                    message = "Cat stored correctly"
                ),
                status = HttpStatusCode.Created
            )
        }
        delete("{id?}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (catsStorage.removeIf { it.id == id }) call.respond(
                message = GenericResponse<Cat>(
                    status = "Ok",
                    message = "Cat removed correctly",
                ),
                status = HttpStatusCode.Accepted
            ) else call.respond(
                message = GenericResponse<Cat>(
                    status = "Error",
                    message = "Not Found",
                ),
                status = HttpStatusCode.NotFound
            )
        }
    }
}