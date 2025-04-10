package org.jetbrains.bsp.sdkcompat.workspacemodel

import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectModelExternalSource
import com.intellij.platform.workspace.jps.serialization.impl.FileInDirectorySourceNames
import com.intellij.platform.workspace.storage.EntitySource
import com.intellij.platform.workspace.storage.url.VirtualFileUrl
import com.intellij.workspaceModel.ide.impl.LegacyBridgeJpsEntitySourceFactory as PlatformLegacyBridgeJpsEntitySourceFactory

// v243: impl.LegacyBridgeJpsEntitySourceFactory renamed to legacyBridge.LegacyBridgeJpsEntitySourceFactory
// and changed method signatures
object LegacyBridgeJpsEntitySourceFactory {
  fun createEntitySourceForModule(
    project: Project,
    baseModuleDir: VirtualFileUrl,
    externalSource: ProjectModelExternalSource?,
    fileInDirectoryNames: FileInDirectorySourceNames? = null,
    moduleFileName: String? = null,
  ): EntitySource =
    PlatformLegacyBridgeJpsEntitySourceFactory.createEntitySourceForModule(
      project,
      baseModuleDir,
      externalSource,
      fileInDirectoryNames,
      moduleFileName,
    )

  fun createEntitySourceForProjectLibrary(
    project: Project,
    externalSource: ProjectModelExternalSource?,
    fileInDirectoryNames: FileInDirectorySourceNames? = null,
    fileName: String? = null,
  ): EntitySource =
    PlatformLegacyBridgeJpsEntitySourceFactory.createEntitySourceForProjectLibrary(
      project,
      externalSource,
      fileInDirectoryNames,
      fileName,
    )
}
