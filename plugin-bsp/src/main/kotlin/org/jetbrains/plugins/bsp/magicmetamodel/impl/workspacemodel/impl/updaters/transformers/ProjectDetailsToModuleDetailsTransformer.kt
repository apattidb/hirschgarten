package org.jetbrains.plugins.bsp.magicmetamodel.impl.workspacemodel.impl.updaters.transformers

import ch.epfl.scala.bsp4j.BuildTargetIdentifier
import org.jetbrains.plugins.bsp.magicmetamodel.ProjectDetails
import org.jetbrains.plugins.bsp.magicmetamodel.impl.workspacemodel.ModuleDetails

private const val WORKSPACE_MODEL_ENTITIES_FOLDER_MARKER = "workspace-model-entities-folder-marker"

class ProjectDetailsToModuleDetailsTransformer(private val projectDetails: ProjectDetails, private val libraryGraph: LibraryGraph) {
  private val targetsIndex = projectDetails.targets.associateBy { it.id }
  private val sourcesIndex = projectDetails.sources.groupBy { it.target }
  private val resourcesIndex = projectDetails.resources.groupBy { it.target }
  private val dependenciesSourcesIndex = projectDetails.dependenciesSources.groupBy { it.target }
  private val javacOptionsIndex = projectDetails.javacOptions.associateBy { it.target }
  private val scalacOptionsIndex = projectDetails.scalacOptions.associateBy { it.target }
  private val pythonOptionsIndex = projectDetails.pythonOptions.associateBy { it.target }
  private val jvmBinaryJarsIndex = projectDetails.jvmBinaryJars.groupBy { it.target }

  fun moduleDetailsForTargetId(targetId: BuildTargetIdentifier): ModuleDetails {
    val target = targetsIndex[targetId] ?: error("Cannot find target for target id: $targetId.")
    val allDependencies = libraryGraph.calculateAllDependencies(target)
    return ModuleDetails(
      target = target,
      sources = sourcesIndex[target.id].orEmpty(),
      resources = resourcesIndex[targetId].orEmpty(),
      dependenciesSources = dependenciesSourcesIndex[targetId].orEmpty(),
      javacOptions = javacOptionsIndex[targetId],
      scalacOptions = scalacOptionsIndex[targetId],
      pythonOptions = pythonOptionsIndex[targetId],
      outputPathUris = emptyList(),
      libraryDependencies = allDependencies.libraryDependencies.takeIf { projectDetails.libraries != null }?.toList(),
      moduleDependencies = allDependencies.moduleDependencies.toList(),
      defaultJdkName = projectDetails.defaultJdkName,
      jvmBinaryJars = jvmBinaryJarsIndex[targetId].orEmpty(),
      workspaceModelEntitiesFolderMarker =
        resourcesIndex[targetId].orEmpty().flatMap { it.resources }.any { it.endsWith(WORKSPACE_MODEL_ENTITIES_FOLDER_MARKER) },
    )
  }
}
