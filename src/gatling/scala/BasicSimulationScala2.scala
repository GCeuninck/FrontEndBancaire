import io.gatling.core.Predef._
import io.gatling.http.Predef.http
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Enums.{AccountType, Currency}

class BasicSimulationScala2 extends Simulation {

  var string = ""
  val ibanFeeder = jsonFile("IbanFeeder.json").circular

  val httpProtocol = http // 4
    .baseUrl("http://localhost:8081") // 5
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // 6
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")
  val scn = scenario("Bancaire Front End")
    .feed(ibanFeeder)
    .exec{
      session =>
        string = session("iban").as[String]
        println(string)
        println("Test")
        session
    }
    .exec(http("Get : Index").get("/"))
    .pause(1) // Note that Gatling has recorded real time pauses
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
    .pause(1).exec(http("Post : Transaction Creation Operation")
    .post("/transactions")
    .formParam("debtorIBAN", "${iban}")
    .formParam("creditorIBAN", "${iban}")
    .formParam("value", "60")
    .formParam("date", "2022-01-20"))
    .pause(1)
    .exec(http("Get : Accounts List")
      .get("/accounts"))
    .pause(1)
    .exec(http("Get : Account Creation Page")
      .get("/addAccount"))
    .pause(1)
    .exec(http("Get : Transactions").get("/accounts/${iban}/transactions"))
    .pause(1)
    .exec(http("Get : Transactions List").get("/transactions")).pause(2)
    .exec(http("Get : Transaction Creation Page").get("/addTransaction"))

  setUp( // 11
    scn.inject(atOnceUsers(4)) // 12
  ).protocols(httpProtocol) // 13


}
