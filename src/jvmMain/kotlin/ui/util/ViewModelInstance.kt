package ui.util

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

open class ViewModelInstance : InstanceKeeper.Instance {
    val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    override fun onDestroy() {
        viewModelScope.cancel()
    }
}