/*
 * Copyright 2011-2021 GatlingCorp (https://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Enums.AccountType;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Enums.Currency;

public class BasicSimulation extends Simulation {

  FeederBuilder.FileBased<Object> ibanFeeder = jsonFile("IbanFeeder.json").circular();
  HttpProtocolBuilder httpProtocol =
      http
          // Here is the root for all relative URLs
          .baseUrl("http://localhost:8081")
          // Here are the common headers
          .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
          .doNotTrackHeader("1")
          .acceptLanguageHeader("en-US,en;q=0.5")
          .acceptEncodingHeader("gzip, deflate")
          .userAgentHeader(
              "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0");


  // A scenario is a chain of requests and pauses
  ScenarioBuilder scn =
      scenario("Bancaire Front End")
          .exec(http("Get : Index").get("/"))
          // Note that Gatling has recorded real time pauses
          .pause(2)
          .exec(http("Get : Publish 100 Transactions").get("/rabbitmq/publish100"))
          .pause(7)
          .exec(
                  http("Post : Account Creation Operation")
                          .post("/accounts")
                          .formParam("ownerLastName", "GatlingLastName")
                          .formParam("ownerFirstName", "GatlingFirstName")
                          .formParam("accountName", "GatlingName")
                          .formParam("balance", "50")
                          .formParam("accountType", AccountType.CURRENT)
                          .formParam("currency", Currency.EURO)
                          .formParam("iban", ibanFeeder))
          .pause(7)
          .exec(
                  http("Post : Account Creation Operation")
                          .post("/accounts")
                          .formParam("ownerLastName", "GatlingLastName2")
                          .formParam("ownerFirstName", "GatlingFirstName2")
                          .formParam("accountName", "GatlingName2")
                          .formParam("balance", "50")
                          .formParam("accountType", AccountType.CURRENT)
                          .formParam("currency", Currency.EURO)
                          .formParam("iban", ibanFeeder))
          .pause(7)
          .exec(
                  http("Post : Transaction Creation Operation")
                          .post("/transactions")
                          .formParam("debtorIBAN", "FR7630006000011234567890189")
                          .formParam("creditorIBAN", "FR7630001007941234567890185")
                          .formParam("value", "60")
                          .formParam("date", "2022-01-20"))
          .pause(7)
          .exec(http("Get : Accounts List").get("/accounts"))
          .pause(2)
          .exec(http("Get : Account Creation Page").get("/addAccount"))
          .pause(2)
          .exec(http("Get : FR7630006000011234567890189 Transactions").get("/accounts/FR7630006000011234567890189/transactions"))
          .pause(2)
          .exec(http("Get : Transactions List").get("/transactions"))
          .pause(2)
          .exec(http("Get : Transaction Creation Page").get("/addTransaction"))
          .pause(2)
          .exec(http("Delete : FR7630006000011234567890189 Account").delete("/accounts/FR7630006000011234567890189"));
  {
    setUp(scn.injectOpen(atOnceUsers(1)).protocols(httpProtocol));
  }
}
