package com.hazlin.transactionmanagement.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scala.language.postfixOps

class TransactionSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080/api")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")

  val transactionFeeder = jsonFile("data/transactions.json").random

  val createTransactionScenario = scenario("Create Transaction")
    .feed(transactionFeeder)
    .exec(http("Create Transaction")
      .post("/transactions")
      .body(StringBody("""{
        "amount": ${amount},
        "type": "${type}",
        "description": "${description}",
        "timestamp": "${timestamp}"
      }""")).asJson
      .check(status.is(201)))
    .pause(1)

  val listTransactionsScenario = scenario("List Transactions")
    .exec(http("List Transactions")
      .get("/transactions")
      .check(status.is(200)))
    .pause(1)

  setUp(
    createTransactionScenario.inject(rampUsers(10) during (10 seconds)),
    listTransactionsScenario.inject(rampUsers(10) during (10 seconds))
  ).protocols(httpProtocol)
}
