package controllers

import javax.inject.Inject

import play.api.mvc.{AbstractController, ControllerComponents}
import services.IpApi

import scala.concurrent.ExecutionContext

class WhatsMyIPController  @Inject()(cc: ControllerComponents, api: IpApi)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def getIP = Action.async {
    api.ip.map{ ip => Ok(ip) }
  }

}
