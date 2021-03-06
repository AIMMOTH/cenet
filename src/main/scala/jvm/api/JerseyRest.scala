package jvm.api

import com.google.gson.Gson

import jvm.api.logic.BackendLogic
import jvm.datastore.entity.ResourceEntity
import javax.ws.rs._
import javax.ws.rs.core._
import scalajs.shared.util.OK
import scalajs.shared.util.KO
import jvm.builder.LoggerBuilder
import java.util.logging.Logger

/**
 * Untested code. All logic is put into BackendLogic
 */
@Path("resource")
class JerseyRest {

  private lazy val gson = new Gson
  private lazy val log = Logger.getLogger(getClass.getName)
  private implicit lazy val jsLogger = LoggerBuilder(log)

  /**
   * POST http://localhost:8080/api/v1/resource
   */
  @POST
  @Produces(Array(MediaType.APPLICATION_JSON))
  def post(json : String) = {

    BackendLogic.create(json) match {

      case OK(entity) =>
        gson.toJson(entity.id).toString match {
          case serialized =>
            Response.ok(serialized).build
        }
      case KO(throwable) =>
        errorResponse(throwable)
    }
  }

  /**
   * GET http://localhost:8080/api/v1/resource/123
   */
  @GET
  @Path("{id}")
  @Produces(Array(MediaType.APPLICATION_JSON))
  def get(@PathParam("id") id : java.lang.Long) = {

    log.info("Get!")

    BackendLogic.read(id) match {
      case OK(entity) =>
        gson.toJson(entity.r).toString match {
          case json =>
            Response.ok(json).build
        }
      case KO(throwable) =>
        errorResponse(throwable)
    }
  }

  private def errorResponse(throwable : Throwable) : Response = {
    throwable.printStackTrace()
    errorResponse(throwable.getMessage)
  }

  private def errorResponse(message : String) : Response = {
    Response.serverError().entity(message).build
  }

}