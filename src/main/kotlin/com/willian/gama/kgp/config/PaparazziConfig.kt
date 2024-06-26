package com.willian.gama.kgp.config

import org.gradle.api.Project
import org.gradle.api.attributes.java.TargetJvmEnvironment

fun Project.setUpPaparazzi() {
    // pluginManager.apply("app.cash.paparazzi")

    // Paparazzi Google Guava compatibility
    plugins.withId("app.cash.paparazzi") {
        // Defer until afterEvaluate so that testImplementation is created by Android plugin.
        afterEvaluate {
            dependencies.constraints {
                add(
                    "testImplementation",
                    "com.google.guava:guava"
                ) {
                    attributes {
                        attribute(
                            TargetJvmEnvironment.TARGET_JVM_ENVIRONMENT_ATTRIBUTE,
                            objects.named(TargetJvmEnvironment::class.java, TargetJvmEnvironment.STANDARD_JVM)
                        )
                    }
                    because(
                        """
                            LayoutLib and sdk-common depend on Guava's -jre published variant.
                            See https://github.com/cashapp/paparazzi/issues/906.
                            """
                    )
                }
            }
        }
    }
}