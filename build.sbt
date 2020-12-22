name := "WorderizeScala"

version := "0.1"

scalaVersion := "2.13.4"

val akka = "2.6.10"

/* dependencies */
libraryDependencies ++= Seq (
  // -- Logging --
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  // -- Akka --
  "com.typesafe.akka" %% "akka-actor-typed"   % akka,
  "com.typesafe.akka" %% "akka-cluster-typed" % akka
)
