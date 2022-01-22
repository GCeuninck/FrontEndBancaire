import io.gatling.core.Predef._
import io.gatling.http.Predef.http
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Enums.{AccountType, Currency}

class DeleteSimulationScala extends Simulation {

  var string = ""
  val ibanFeeder = jsonFile("IbanFeeder.json").circular

  val httpProtocol = http // 4
    .baseUrl("http://localhost:8081") // 5
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // 6
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")
  val scn = scenario("Delete Account Front End Bancaire")
    .feed(ibanFeeder)
    .exec{
      session =>
        string = session("iban").as[String]
        println(string)
        println("Test")
        session
    }
    .exec(http("Post : Account Creation Operation")
      .post("/accounts")
        .formParam("ownerLastName", "GatlingLastName2")
        .formParam("ownerFirstName", "GatlingFirstName2")
        .formParam("accountName", "GatlingName2")
        .formParam("balance", "50")
        .formParam("accountType", AccountType.CURRENT)
        .formParam("currency", Currency.EURO)
        .formParam("iban","${iban}")
    )
    .exec(http("Delete : ${iban} Account").delete("/accounts/${iban}"))
  setUp( // 11
    scn.inject(atOnceUsers(4)) // 12
  ).protocols(httpProtocol) // 13


}
