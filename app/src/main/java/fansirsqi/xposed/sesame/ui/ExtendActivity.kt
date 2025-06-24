package fansirsqi.xposed.sesame.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fansirsqi.xposed.sesame.R
import fansirsqi.xposed.sesame.data.DataCache
import fansirsqi.xposed.sesame.data.ViewAppInfo
import fansirsqi.xposed.sesame.entity.ExtendFunctionItem
import fansirsqi.xposed.sesame.ui.widget.ExtendFunctionAdapter
import fansirsqi.xposed.sesame.util.FansirsqiUtil
import fansirsqi.xposed.sesame.util.Log
import fansirsqi.xposed.sesame.util.ToastUtil

/**
 * 扩展功能页面
 */
class ExtendActivity : BaseActivity() {
    private val TAG = ExtendActivity::class.java.simpleName
    private var debugTips: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var extendFunctionAdapter: ExtendFunctionAdapter
    private val extendFunctions = mutableListOf<ExtendFunctionItem>()

    /**
     * 初始化Activity
     *
     * @param savedInstanceState 保存的实例状态
     */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extend) // 设置布局文件
        debugTips = getString(R.string.debug_tips)
        baseTitle = getString(R.string.extended_func)

        setupRecyclerView()
        populateExtendFunctions()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView_extend_functions)
        recyclerView.layoutManager = LinearLayoutManager(this)
        extendFunctionAdapter = ExtendFunctionAdapter(extendFunctions)
        recyclerView.adapter = extendFunctionAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun populateExtendFunctions() {
        // 添加查询功能
        addQueryFunctions()
        
        // 添加清除功能
        addClearFunctions()
        
        // 添加调试功能
        if (ViewAppInfo.isApkInDebug) {
            addDebugFunctions()
        }
        
        extendFunctionAdapter.notifyDataSetChanged()
    }
    
    /**
     * 添加查询相关功能
     */
    private fun addQueryFunctions() {
        val queryFunctions = listOf(
            R.string.query_the_remaining_amount_of_saplings to "getTreeItems",
            R.string.search_for_new_items_on_saplings to "getNewTreeItems", 
            R.string.search_for_unlocked_regions to "queryAreaTrees",
            R.string.search_for_unlocked_items to "getUnlockTreeItems"
        )
        
        queryFunctions.forEach { (stringRes, action) ->
            extendFunctions.add(
                ExtendFunctionItem(getString(stringRes)) {
                    sendItemsBroadcast(action)
                    showDebugToast()
                }
            )
        }
    }
    
    /**
     * 添加清除相关功能
     */
    private fun addClearFunctions() {
        extendFunctions.add(
            ExtendFunctionItem(getString(R.string.clear_photo)) {
                showClearPhotoDialog()
            }
        )
    }
    
    /**
     * 添加调试相关功能
     */
    private fun addDebugFunctions() {
        // 写入光盘功能
        extendFunctions.add(
            ExtendFunctionItem("写入光盘") {
                showWritePhotoDialog()
            }
        )
        
        // 获取DataCache字段功能
        extendFunctions.add(
            ExtendFunctionItem("获取DataCache字段") {
                showDataCacheDialog()
            }
        )
    }
    
    /**
     * 显示调试提示Toast
     */
    private fun showDebugToast() {
        ToastUtil.makeText(this@ExtendActivity, debugTips, Toast.LENGTH_SHORT).show()
    }
    
    /**
     * 显示清除照片对话框
     */
    private fun showClearPhotoDialog() {
        val photoCount = DataCache.getData<List<Map<String, String>>>("guangPanPhoto")?.size ?: 0
        createConfirmDialog(
            title = R.string.clear_photo,
            message = "确认清空${photoCount}组光盘行动图片？",
            onConfirm = {
                if (DataCache.removeData("guangPanPhoto")) {
                    ToastUtil.showToast(this, "光盘行动图片清空成功")
                } else {
                    ToastUtil.showToast(this, "光盘行动图片清空失败")
                }
            }
        ).show()
    }
    
    /**
     * 显示写入照片对话框
     */
    private fun showWritePhotoDialog() {
        createConfirmDialog(
            title = "Test",
            message = "xxxx",
            onConfirm = {
                val newPhotoEntry = HashMap<String, String>()
                val randomStr = FansirsqiUtil.getRandomString(10)
                newPhotoEntry["before"] = "before$randomStr"
                newPhotoEntry["after"] = "after$randomStr"

                val existingPhotos = DataCache.getData<MutableList<Map<String, String>>>("guangPanPhoto")?.toMutableList() ?: mutableListOf()
                existingPhotos.add(newPhotoEntry)

                if (DataCache.saveData("guangPanPhoto", existingPhotos)) {
                    ToastUtil.showToast(this, "写入成功$newPhotoEntry")
                } else {
                    ToastUtil.showToast(this, "写入失败$newPhotoEntry")
                }
            }
        ).show()
    }
    
    /**
     * 显示DataCache查询对话框
     */
    private fun showDataCacheDialog() {
        val inputEditText = EditText(this)
        createInputDialog(
            title = "输入字段Key",
            editText = inputEditText,
            onConfirm = {
                val inputText = inputEditText.text.toString()
                val output = DataCache.getData<Any>(inputText)
                ToastUtil.showToast(this, "$output \n输入内容: $inputText")
            }
        ).show()
    }
    
    /**
     * 创建确认对话框
     */
    private fun createConfirmDialog(
        title: Int,
        message: String,
        onConfirm: () -> Unit
    ): AlertDialog {
        return AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.ok) { _, _ -> onConfirm() }
            .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .create()
    }
    
    /**
     * 创建输入对话框
     */
    private fun createInputDialog(
        title: String,
        editText: EditText,
        onConfirm: () -> Unit
    ): AlertDialog {
        return AlertDialog.Builder(this)
            .setTitle(title)
            .setView(editText)
            .setPositiveButton(R.string.ok) { _, _ -> onConfirm() }
            .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .create()
    }

    /**
     * 发送广播事件
     *
     * @param type 广播类型
     */
    private fun sendItemsBroadcast(type: String) {
        val intent = Intent("com.eg.android.AlipayGphone.sesame.rpctest")
        intent.putExtra("method", "")
        intent.putExtra("data", "")
        intent.putExtra("type", type)
        sendBroadcast(intent) // 发送广播
        Log.debug(TAG,"扩展工具主动调用广播查询📢：$type")
    }
}
