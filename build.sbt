organization := "org.goldenport"

name := "goldenport-swing"

version := "0.1.0"

scalaVersion := "2.9.1"

scalacOptions += "-deprecation"

scalacOptions += "-unchecked"

resolvers += "Asami Maven Repository" at "http://www.asamioffice.com/maven"

libraryDependencies += "org.scala-lang" % "scala-swing" % "2.9.1"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "6.0.3"

libraryDependencies += "org.goldenport" %% "goldenport-scala-lib" % "0.1.2"

libraryDependencies += "org.goldenport" %% "goldenport-record" % "0.1.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.6.1" % "test"

libraryDependencies += "junit" % "junit" % "4.8" % "test"

libraryDependencies += "org.goldenport" %% "scalatestlib" % "0.1.0" % "test"

//
publishTo := Some(Resolver.file("asamioffice", file("target/maven-repository")))
