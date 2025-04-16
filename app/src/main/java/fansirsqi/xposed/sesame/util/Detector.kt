package fansirsqi.xposed.sesame.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import fansirsqi.xposed.sesame.BuildConfig
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.zip.ZipFile

object Detector {

    fun getLibPath(context: Context): String? {
    var libSesamePath: String? = null
    try {
        val appInfo = context.packageManager.getApplicationInfo(
            BuildConfig.APPLICATION_ID, 0
        )
        libSesamePath = appInfo.nativeLibraryDir + File.separator + System.mapLibraryName("checker")
        Log.runtime("load libSesame success")
    } catch (e: PackageManager.NameNotFoundException) {
        ToastUtil.showToast(context, "请不要对应用宝隐藏TK模块")
        Log.record("请不要对应用宝隐藏TK模块")
        Log.error("getLibPath", e.message)
    }
    return libSesamePath
}

    fun loadLibrary(libraryName: String): Boolean {
        try {
            System.loadLibrary(libraryName)
            return true
        } catch (e: UnsatisfiedLinkError) {
            Log.error("loadLibrary", e.message)
            return false
        }
    }

    private external fun init(context: Context)
    external fun tips(context: Context, message: String?)
    external fun isEmbeddedNative(context: Context): Boolean
    external fun dangerous(context: Context)

    /**
     * 检测是否通过LSPatch运行
     */
    private fun isRunningInLSPatch(context: Context): Boolean {
        try {
            // 检查应用元数据中是否有LSPatch标记
            val appInfo = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )
            Log.runtime("DEBUG",appInfo.metaData.toString())
            return appInfo.metaData?.containsKey("lspatch") == true
        } catch (e: Exception) {
            Log.error("检查LSPatch运行环境时出错: ${e.message}")
            return false
        }
    }



    /**
     * 检测模块是否在合法环境中运行
     */
    fun isLegitimateEnvironment(context: Context): Boolean {
        val isRunningInLSPatch = isRunningInLSPatch(context)
        if (!isRunningInLSPatch) {
            return true
        }
        val isEmbedded = isEmbeddedNative(context)
        Log.runtime("isEmbedded: $isEmbedded")
        return isEmbedded
    }



    fun initDetector(context: Context) {
        try {
            init(context)
        } catch (e: Exception) {
            Log.error("initDetector", e.message)
        }
    }

    private fun getApkPath(context: Context, packageName: String): String? {
        try {
            val pm = context.packageManager
            val appInfo = pm.getApplicationInfo(packageName, 0)
            Log.runtime("appInfo.sourceDir: " + appInfo.sourceDir)
            return appInfo.sourceDir
        } catch (_: PackageManager.NameNotFoundException) {
            Log.runtime("Package not found: $packageName")
            return null
        }
    }

}