import sbt.Keys.{publishMavenStyle, scalaVersion}

import scala.util.Try

name := "ergo-appkit"

lazy val sonatypePublic = "Sonatype Public" at "https://oss.sonatype.org/content/groups/public/"
lazy val sonatypeReleases = "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
lazy val sonatypeSnapshots = "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

lazy val scala212 = "2.12.10"
lazy val scala211 = "2.11.12"

//javacOptions ++=
//    "-source" :: "1.7" ::
//    "-target" :: "1.7" ::
//    Nil

lazy val commonSettings = Seq(
  organization := "org.ergoplatform",
  crossScalaVersions := Seq(scala212, scala211),
  scalaVersion := scala212,
  resolvers ++= Seq(sonatypeReleases,
    "SonaType" at "https://oss.sonatype.org/content/groups/public",
    "Typesafe maven releases" at "https://dl.bintray.com/typesafe/maven-releases/",
    sonatypeSnapshots,
    Resolver.mavenCentral),
  homepage := Some(url("https://github.com/ergoplatform/ergo-appkit")),
  licenses := Seq("CC0" -> url("https://creativecommons.org/publicdomain/zero/1.0/legalcode")),
  description := "A Library for Polyglot Development of Ergo Applications",
  pomExtra :=
      <developers>
        <developer>
          <id>aslesarenko</id>
          <name>Alexander Slesarenko</name>
          <url>https://github.com/aslesarenko/</url>
        </developer>
      </developers>,
  publishArtifact in (Compile, packageSrc) := true,
  publishArtifact in (Compile, packageDoc) := true,
  publishMavenStyle := true,
  publishTo := sonatypePublishToBundle.value
)

enablePlugins(GitVersioning)

version in ThisBuild := {
  if (git.gitCurrentTags.value.nonEmpty) {
    git.gitDescribedVersion.value.get
  } else {
    if (git.gitHeadCommit.value.contains(git.gitCurrentBranch.value)) {
      // see https://docs.travis-ci.com/user/environment-variables/#default-environment-variables
      if (Try(sys.env("TRAVIS")).getOrElse("false") == "true") {
        // pull request number, "false" if not a pull request
        if (Try(sys.env("TRAVIS_PULL_REQUEST")).getOrElse("false") != "false") {
          // build is triggered by a pull request
          val prBranchName = Try(sys.env("TRAVIS_PULL_REQUEST_BRANCH")).get
          val prHeadCommitSha = Try(sys.env("TRAVIS_PULL_REQUEST_SHA")).get
          prBranchName + "-" + prHeadCommitSha.take(8) + "-SNAPSHOT"
        } else {
          // build is triggered by a push
          val branchName = Try(sys.env("TRAVIS_BRANCH")).get
          branchName + "-" + git.gitHeadCommit.value.get.take(8) + "-SNAPSHOT"
        }
      } else {
        git.gitHeadCommit.value.get.take(8) + "-SNAPSHOT"
      }
    } else {
      git.gitCurrentBranch.value + "-" + git.gitHeadCommit.value.get.take(8) + "-SNAPSHOT"
    }
  }
}

git.gitUncommittedChanges in ThisBuild := true

val mockitoScalaVerstion = "1.11.4"

lazy val testingDependencies = Seq(
  "org.scalatest" %% "scalatest" % "3.0.8" % "test",
  "org.scalacheck" %% "scalacheck" % "1.14.+" % "test",
  "com.lihaoyi" %% "pprint" % "0.5.4" % "test",  // the last version with Scala 2.11 support
  (sigmaState % Test).classifier("tests")
)

lazy val testSettings = Seq(
  libraryDependencies ++= testingDependencies,
  parallelExecution in Test := false,
  baseDirectory in Test := file("."),
  publishArtifact in Test := true,
  publishArtifact in(Test, packageSrc) := true,
  publishArtifact in(Test, packageDoc) := false,
  test in assembly := {})


lazy val allResolvers = Seq(
  sonatypePublic,
  sonatypeReleases,
  sonatypeSnapshots,
  Resolver.mavenCentral
)

publishArtifact in Compile := true
publishArtifact in Test := true

credentials ++= (for {
  username <- Option(System.getenv().get("SONATYPE_USERNAME"))
  password <- Option(System.getenv().get("SONATYPE_PASSWORD"))
} yield Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", username, password)).toSeq

// set bytecode version to 8 to fix NoSuchMethodError for various ByteBuffer methods
// see https://github.com/eclipse/jetty.project/issues/3244
// these options applied only in "compile" task since scalac crashes on scaladoc compilation with "-release 8"
// see https://github.com/scala/community-builds/issues/796#issuecomment-423395500
//scalacOptions in(Compile, compile) ++= Seq("-release", "8")
assemblyJarName in assembly := s"ergo-appkit-${version.value}.jar"

assemblyMergeStrategy in assembly := {
  case "logback.xml" => MergeStrategy.first
  case "module-info.class" => MergeStrategy.discard
  case other => (assemblyMergeStrategy in assembly).value(other)
}

lazy val allConfigDependency = "compile->compile;test->test"

val sigmaStateVersion = "4.0.4"
val ergoWalletVersion = "4.0.12"
lazy val sigmaState = ("org.scorexfoundation" %% "sigma-state" % sigmaStateVersion).force()
    .exclude("ch.qos.logback", "logback-classic")
    .exclude("org.scorexfoundation", "scrypto")
    .exclude("org.typelevel", "machinist")
    .exclude("org.typelevel", "cats-kernel")

lazy val ergoWallet = "org.ergoplatform" %% "ergo-wallet" % ergoWalletVersion

lazy val mockWebServer = "com.squareup.okhttp3" % "mockwebserver" % "3.12.0" % "test"

libraryDependencies ++= Seq(
  sigmaState,
  (sigmaState % Test).classifier("tests"),
  ergoWallet,
  "org.scalatest" %% "scalatest" % "3.0.8" % "test",
  "org.scalacheck" %% "scalacheck" % "1.14.+" % "test",
  "com.squareup.retrofit2" % "retrofit" % "2.6.2",
  "com.squareup.retrofit2" % "converter-scalars" % "2.6.2",
  "com.squareup.retrofit2" % "converter-gson" % "2.6.2"
)

val apiClientDeps = Seq(
  "com.squareup.okhttp3" % "okhttp" % "3.12.0",
  "com.google.code.findbugs" % "jsr305" % "3.0.2",
  "io.gsonfire" % "gson-fire" % "1.8.3" % "compile",
  "io.swagger.core.v3" % "swagger-annotations" % "2.0.0",
  "com.squareup.retrofit2" % "retrofit" % "2.6.2",
  "com.squareup.retrofit2" % "converter-scalars" % "2.6.2",
  "com.squareup.retrofit2" % "converter-gson" % "2.6.2",
  "junit" % "junit" % "4.12" % "test",
  "com.novocode" % "junit-interface" % "0.11" % Test
)

lazy val javaClientGenerated = (project in file("java-client-generated"))
    .settings(
      commonSettings,
      name := "java-client-generated",
      crossPaths := false,
      libraryDependencies ++= apiClientDeps,
      testOptions += Tests.Argument(TestFrameworks.JUnit),
      publishArtifact in (Compile, packageDoc) := false,
      publish / skip := true
    )

lazy val common = (project in file("common"))
    .settings(
      commonSettings ++ testSettings,
      name := "common",
      resolvers ++= allResolvers,
      libraryDependencies ++= Seq(
        sigmaState,
        ergoWallet
      ),
      publish / skip := true
    )

lazy val libApi = (project in file("lib-api"))
    .dependsOn(common % allConfigDependency)
    .settings(
      commonSettings ++ testSettings,
      resolvers ++= allResolvers,
      name := "lib-api",
      libraryDependencies ++= Seq(
      ),
      publish / skip := true
    )

lazy val libImpl = (project in file("lib-impl"))
    .dependsOn(javaClientGenerated % allConfigDependency, libApi % allConfigDependency)
    .settings(
      commonSettings ++ testSettings,
      name := "lib-impl",
      resolvers ++= allResolvers,
      libraryDependencies ++= Seq(
      ),
      publish / skip := true
    )

lazy val appkit = (project in file("appkit"))
    .dependsOn(
      common % allConfigDependency,
      javaClientGenerated % allConfigDependency,
      libApi % allConfigDependency,
      libImpl % allConfigDependency)
    .settings(commonSettings ++ testSettings,
      libraryDependencies ++= Seq(
        mockWebServer//,
//        "org.mockito" %% "mockito-scala" % mockitoScalaVerstion % "test",
//        "org.mockito" %% "mockito-scala-scalatest" % mockitoScalaVerstion % "test"
      ))
    .settings(publish / skip := true)

lazy val aggregateCompile = ScopeFilter(
  inProjects(common, javaClientGenerated, libApi, libImpl, appkit),
  inConfigurations(Compile))

lazy val rootSettings = Seq(
  sources in Compile := sources.all(aggregateCompile).value.flatten,
  sources in (Compile, doc) := Seq(), // generate empty javadoc (required by sonatype)
  libraryDependencies := libraryDependencies.all(aggregateCompile).value.flatten,
  mappings in (Compile, packageSrc) ++= (mappings in(Compile, packageSrc)).all(aggregateCompile).value.flatten,
  mappings in (Test, packageBin) ++= (mappings in(Test, packageBin)).all(aggregateCompile).value.flatten,
  mappings in(Test, packageSrc) ++= (mappings in(Test, packageSrc)).all(aggregateCompile).value.flatten
)

lazy val root = (project in file("."))
    .aggregate(appkit, common, javaClientGenerated, libApi, libImpl)
    .settings(commonSettings ++ testSettings, rootSettings)
    .settings(publish / aggregate := false)
    .settings(publishLocal / aggregate := false)


// PGP key for signing a release build published to sonatype
// signing is done by sbt-pgp plugin
// how to generate a key - https://central.sonatype.org/pages/working-with-pgp-signatures.html
// how to export a key and use it with Travis - https://docs.scala-lang.org/overviews/contributors/index.html#export-your-pgp-key-pair
pgpPublicRing := file("ci/pubring.asc")
pgpSecretRing := file("ci/secring.asc")
pgpPassphrase := sys.env.get("PGP_PASSPHRASE").map(_.toArray)
usePgpKeyHex("C56E488A4B3A9E370275612F55B67E9C7DF9FACE")
