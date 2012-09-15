organization := "org.goldenport"

name := "goldenport-swing"

version := "0.2.0"

// scalaVersion := "2.9.1"

crossScalaVersions := Seq("2.9.2", "2.9.1")

scalacOptions += "-deprecation"

scalacOptions += "-unchecked"

resolvers += "Asami Maven Repository" at "http://www.asamioffice.com/maven"

libraryDependencies <+= scalaVersion { "org.scala-lang" % "scala-swing" % _ }

libraryDependencies += "org.scalaz" %% "scalaz-core" % "6.0.4"

libraryDependencies += "org.goldenport" %% "goldenport-scala-lib" % "0.1.2"

libraryDependencies += "org.goldenport" %% "goldenport-record" % "0.2.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.6.1" % "test"

libraryDependencies += "junit" % "junit" % "4.8" % "test"

libraryDependencies += "org.goldenport" %% "goldenport-scalatest-lib" % "0.2.0" % "test"

//
publishTo := Some(Resolver.file("asamioffice", file("target/maven-repository")))
