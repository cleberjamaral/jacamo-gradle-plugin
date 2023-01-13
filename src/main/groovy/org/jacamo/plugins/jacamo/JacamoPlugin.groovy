package org.jacamo.plugins.jacamo

import org.gradle.api.Plugin
import org.gradle.api.Project

class JacamoPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.tasks.register('testJacamo', TestJacamoTask)
    }
}
