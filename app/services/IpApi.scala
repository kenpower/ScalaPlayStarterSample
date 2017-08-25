package services

import javax.inject.Inject

import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}

trait IpApi {
  def ip: Future[String]
}

class DummyIpApi (implicit ec: ExecutionContext) extends IpApi {
  override def ip = Future("1.1.1.1")
}

class WebServicesIpApi @Inject()(wSClient: WSClient, url: String)(implicit ec: ExecutionContext) extends IpApi {
  override def ip: Future[String] = wSClient.url(url).get().map { response => response.body }
}