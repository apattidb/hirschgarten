package org.jetbrains.plugins.bsp.magicmetamodel.impl.workspacemodel.impl

import com.intellij.openapi.externalSystem.service.project.manage.ExternalProjectsManagerImpl
import com.intellij.openapi.project.Project
import com.intellij.platform.workspace.jps.entities.ModuleId
import com.intellij.platform.workspace.storage.MutableEntityStorage
import com.intellij.platform.workspace.storage.impl.url.toVirtualFileUrl
import com.intellij.platform.workspace.storage.url.VirtualFileUrl
import com.intellij.platform.workspace.storage.url.VirtualFileUrlManager
import org.jetbrains.plugins.bsp.magicmetamodel.impl.workspacemodel.WorkspaceModelUpdater
import org.jetbrains.plugins.bsp.magicmetamodel.impl.workspacemodel.impl.updaters.JavaModuleUpdater
import org.jetbrains.plugins.bsp.magicmetamodel.impl.workspacemodel.impl.updaters.LibraryEntityUpdater
import org.jetbrains.plugins.bsp.magicmetamodel.impl.workspacemodel.impl.updaters.PythonModuleUpdater
import org.jetbrains.plugins.bsp.magicmetamodel.impl.workspacemodel.impl.updaters.WorkspaceModelEntityUpdaterConfig
import org.jetbrains.plugins.bsp.magicmetamodel.impl.workspacemodel.impl.updaters.transformers.JavaModuleToDummyJavaModulesTransformerHACK
import org.jetbrains.plugins.bsp.workspacemodel.entities.BspProjectDirectoriesEntity
import org.jetbrains.plugins.bsp.workspacemodel.entities.BspProjectEntitySource
import org.jetbrains.plugins.bsp.workspacemodel.entities.JavaModule
import org.jetbrains.plugins.bsp.workspacemodel.entities.Library
import org.jetbrains.plugins.bsp.workspacemodel.entities.Module
import org.jetbrains.plugins.bsp.workspacemodel.entities.PythonModule
import java.nio.file.Path

class WorkspaceModelUpdaterImpl(
  workspaceEntityStorageBuilder: MutableEntityStorage,
  val virtualFileUrlManager: VirtualFileUrlManager,
  projectBasePath: Path,
  project: Project,
  isPythonSupportEnabled: Boolean,
  isAndroidSupportEnabled: Boolean,
) : WorkspaceModelUpdater {
  private val workspaceModelEntityUpdaterConfig =
    WorkspaceModelEntityUpdaterConfig(
      workspaceEntityStorageBuilder = workspaceEntityStorageBuilder,
      virtualFileUrlManager = virtualFileUrlManager,
      projectBasePath = projectBasePath,
      project = project,
    )
  private val javaModuleUpdater =
    JavaModuleUpdater(workspaceModelEntityUpdaterConfig, projectBasePath, isAndroidSupportEnabled)
  private val pythonModuleUpdater: PythonModuleUpdater? =
    if (isPythonSupportEnabled) PythonModuleUpdater(workspaceModelEntityUpdaterConfig) else null

  private val javaModuleToDummyJavaModulesTransformerHACK =
    JavaModuleToDummyJavaModulesTransformerHACK(projectBasePath)

  init {
    // store generated IML files outside the project directory
    ExternalProjectsManagerImpl.getInstance(project).setStoreExternally(true)
  }

  override fun loadModule(module: Module) {
    when (module) {
      is JavaModule -> {
        val dummyJavaModules = javaModuleToDummyJavaModulesTransformerHACK.transform(module)
        javaModuleUpdater.addEntities(dummyJavaModules.filterNot { it.isAlreadyAdded() })
        javaModuleUpdater.addEntity(module)
      }

      is PythonModule -> pythonModuleUpdater?.addEntity(module)
    }
  }

  override fun loadLibraries(libraries: List<Library>) {
    val libraryEntityUpdater = LibraryEntityUpdater(workspaceModelEntityUpdaterConfig)
    libraryEntityUpdater.addEntities(libraries)
  }

  override fun loadDirectories(includedDirectories: List<VirtualFileUrl>, excludedDirectories: List<VirtualFileUrl>) {
    val entity =
      BspProjectDirectoriesEntity(
        projectRoot =
          workspaceModelEntityUpdaterConfig.projectBasePath
            .toVirtualFileUrl(workspaceModelEntityUpdaterConfig.virtualFileUrlManager),
        includedRoots = includedDirectories,
        excludedRoots = excludedDirectories,
        entitySource = BspProjectEntitySource,
      )
    workspaceModelEntityUpdaterConfig.workspaceEntityStorageBuilder.addEntity(entity)
  }

  private fun Module.isAlreadyAdded() = workspaceModelEntityUpdaterConfig.workspaceEntityStorageBuilder.contains(ModuleId(getModuleName()))
}
