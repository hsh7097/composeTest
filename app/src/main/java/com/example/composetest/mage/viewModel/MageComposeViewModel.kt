package com.example.composetest.mage.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.mage.action.IntentAction
import com.example.composetest.mage.action.MageBaseIntentAction
import com.example.composetest.mage.list.ComposeListItem
import com.example.composetest.mage.state.MageUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

@Suppress("UNCHECKED_CAST")
abstract class MageComposeViewModel<Request, Response>(
    protected val refreshDuration: Long = 0,
) : ViewModel(), CoroutineScope {

    /**
     * 해당 [MageComposeViewModel]의 모든 coroutine의 root [Job].
     */
    protected val job: Job = SupervisorJob()

    /**
     * [MageComposeViewModel]의 [CoroutineContext]. [AppDispatchers.UI] 에서 실행된다.
     */
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    /**
     * [fetchData]에서 사용되었던 데이터 [Request]
     */
    public var firstRequest: Request? = null
        protected set

    /**
     * 첫번째 호출 데이터 [fetchData]의 마지막 성공 시간.
     */
    public var lastSuccessTime: Long = 0
        protected set

    /**
     * 자동 새로고침 사용 여부.
     *
     * [refreshDuration]을 값을 가지고 판단한다.
     */
    public val isAutoRefreshEnabled
        get() = refreshDuration > 0


    protected val _state = MutableStateFlow<MageUIState>(MageUIState.Loading)
    val state: StateFlow<MageUIState> = _state


    public val currentTime: Long
        get() = System.currentTimeMillis()

    private val events = Channel<IntentAction>()

    init {
        intentConsumer()
    }


    private fun intentConsumer() {
        viewModelScope.launch {
            events.consumeAsFlow().collect { intentAction ->
                actionIntentConsumer(intentAction)
            }
        }
    }


    /**
     * 액션(인텐트)을 처리한다.
     * 인텐트를 events 채널에 보내며, 이후 intentConsumer 함수에 의해 수집되어 처리됩니다.
     */
    fun handleIntent(intentAction: IntentAction) {
        viewModelScope.launch {
            events.send(intentAction)
        }
    }

    /**
     * events 채널로부터 수신된 인텐트를 실제로 처리하는 함수.
     * 인텐트의 타입에 따라 적절한 처리를 분기 한다
     */
    open fun actionIntentConsumer(intentAction: IntentAction) {
        (intentAction as? MageBaseIntentAction)?.let { mageBaseIntentAction ->
            mageActionIntent(mageBaseIntentAction)
        }
    }

    private fun mageActionIntent(intentAction: MageBaseIntentAction) {
        when (intentAction) {
            is MageBaseIntentAction.FetchData<*> -> {
                (intentAction.requestData as? Request)?.let { request ->
                    fetchData(request, intentAction.forceRefresh)
                }
            }

            is MageBaseIntentAction.Refresh -> {
                refresh(intentAction.forceRefresh)
            }
        }
    }

    /**
     * [repository]에 새로고침 요청.
     *
     * @param force false이면 상황에 따라 캐쉬된 response를 사용할 수 있다.
     */
    public open fun refresh(force: Boolean = false) {
        if (force || (isAutoRefreshEnabled && lastSuccessTime + refreshDuration < currentTime)) {
            firstRequest?.let { fetchData(it, force) }
        }
    }

    /**
     * [repository]에 [request]에 해당하는 data fetch 요청.
     *
     * - Response가 오기 전에 다른 [fetchData]가 새로 호출되는 경우, 이전 request를 취소하지는 않는다.
     * - [repository]에서 caching 등의 처리를 할 수도 있기 때문이며 대신에 UI 업데이트는 하지 않는다.
     *
     * @see MageRepository.fetchData
     * @see MageComposeViewModel.onFetchDataSuccess
     */
    protected open fun fetchData(request: Request, forceRequest: Boolean): Job = launch {
        firstRequest = request
        _state.emit(MageUIState.Loading)
        try {
//            onFetchDataSuccess(
//                request = request,
//                response =
//            )
        } catch (e: Exception) {
            onFetchException(e)
        }

    }

    /**
     * [fetchData] 성공 시 호출되는 callback.
     */
    public open suspend fun onFetchDataSuccess(
        request: Request,
        response: Response
    ) {
        lastSuccessTime = currentTime
        viewModelScope.launch {
            _state.emit(MageUIState.Success(getCreateList(response) ))
        }
    }

    /**
     * [MageRepository.fetchData] 에러 핸들링.
     */
    public open suspend fun onFetchException(e: Exception) {
        val errorMessabe = when (e) {
            else -> {
                ""
            }
        }
        _state.emit(MageUIState.Error(errorMessabe))
    }

    private suspend fun getCreateList(response: Response?): List<ComposeListItem<*>> =
        withContext(Dispatchers.Default) {
            createList(response)
        }

    /**
     * API [Response] [data]를 받아 View에서 뿌려줄 리스트 형태로 바꿔주는 함수.
     */
    protected abstract suspend fun createList(response: Response?): List<ComposeListItem<*>>


}

