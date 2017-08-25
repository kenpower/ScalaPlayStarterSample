import com.github.tomakehurst.wiremock.WireMockServer
import controllers.WhatsMyIPController
import org.mockito.Mockito
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneServerPerTest
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.IpApi
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future


/**
  * Runs a browser test using Fluentium against a play application on a server port.
  */
class BrowserSpec extends PlaySpec
  with OneBrowserPerTest
  with GuiceOneServerPerTest
  with HtmlUnitFactory
  with ServerProvider
  with MockitoSugar {

  "Application" should {

    "work from within a browser" in {

      go to ("http://localhost:" + port)

      pageSource must include("Your new application is ready.")
    }


  }

  "HomeController" should {
    "return name received as parameter" in {
      go to s"http://localhost:$port/name/ken"

      pageSource must include("ken")
    }
  }

  "IPController" should {

    "get my ip address" in {
      val ipAddress = "1.1.1.1"
      val ipApi = mock[IpApi]
      when(ipApi.ip).thenReturn(Future(ipAddress))
      val result = new WhatsMyIPController(stubControllerComponents(), ipApi).getIP(FakeRequest())


      contentAsString(result) mustEqual ipAddress
    }

    "get my IP with wiremock" in {
      val ipAddress = "1.1.1.1"
      import com.github.tomakehurst.wiremock.client.WireMock._
      val mockServer = new WireMockServer(44444)
      mockServer.start()
      mockServer.stubFor(get(anyUrl()).willReturn(ok(ipAddress)))

      go to s"http://localhost:$port/whats-my-ip"

      pageSource must include(ipAddress)
      mockServer.stop()
    }
  }
}
