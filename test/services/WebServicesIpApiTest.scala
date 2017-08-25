package services

import com.github.tomakehurst.wiremock.WireMockServer
import org.scalatestplus.play.PlaySpec
import play.api.test.WsTestClient
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await

class WebServicesIpApiTest extends PlaySpec{

  "WebServicesIpApi" should {
    "get my IP" in {

      val wsClient = WsTestClient.withClient { wsClient =>
        import com.github.tomakehurst.wiremock.client.WireMock._
        val ipAddress = "1.1.1.1"
        val mockServer = new WireMockServer
        mockServer.start()
        mockServer.stubFor(get(anyUrl()).willReturn(ok(ipAddress)))

        val ipApi = new WebServicesIpApi(wsClient, s"http://localhost:${mockServer.port()}")
        val ip = Await.result(ipApi.ip, 1.second)

        ip mustEqual ipAddress
      }
    }
  }

}
