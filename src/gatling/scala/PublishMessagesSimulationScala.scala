import io.gatling.core.Predef._
import io.gatling.http.Predef.http
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Enums.{AccountType, Currency}

class PublishMessagesSimulationScala extends Simulation {

  var string = ""
  val ibanFeeder = jsonFile("IbanFeeder.json").circular

  val httpProtocol = http // 4
    .baseUrl("http://localhost:8081") // 5
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // 6
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")
  val scn = scenario("Publish 100 Transactions Front End Bancaire")
    .exec(http("Get : Publish 100 Transactions").get("/rabbitmq/publish100"))
  setUp( // 11
    scn.inject(atOnceUsers(4)) // 12
  ).protocols(httpProtocol) // 13


}
