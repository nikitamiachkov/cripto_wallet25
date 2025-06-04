package com.example.kaban2.Screens.BuyScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaban2.Domain.Constant.supabase
import com.example.kaban2.Domain.State.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.kaban2.Domain.models.Cripto
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.runtime.getValue

class BuyScreenViewModel : ViewModel() {

    private val _resultState = MutableStateFlow<ResultState>(ResultState.Loading)
    val resultState: StateFlow<ResultState> = _resultState.asStateFlow()

    private val _books = MutableLiveData<List<Cripto>>()
    val books: LiveData<List<Cripto>> get() = _books

    private val _books2 = MutableLiveData<List<Cripto>>()
    val books2: LiveData<List<Cripto>> get() = _books2

    var kolvo:Int = 0;

    private var allBooks: List<Cripto> = listOf()

    init {
        loadBooks()
    }

    private fun loadBooks() {
        _resultState.value = ResultState.Loading
        viewModelScope.launch {
            try {
                allBooks = supabase.postgrest.from("cryptocurrency").select().decodeList<Cripto>()
                _books.value = allBooks
                kolvo = allBooks.size
                Log.d("MainBooks", "Success")
                Log.d("MainBooks", allBooks.toString())

                _resultState.value = ResultState.Success("Success")
            } catch (_ex: AuthRestException) {
                Log.d("MainBooks", _ex.message.toString())
                Log.d("MainBooks", _ex.errorCode.toString())
                Log.d("MainBooks", _ex.errorDescription)

                _resultState.value = ResultState.Error(_ex.errorDescription)
            }
        }
    }

    suspend fun getUrlImage(image: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val url = supabase.storage.from("logocripto").publicUrl(image)
                Log.d("buck", url)
                url
            } catch (ex: AuthRestException) {
                Log.e("Error", "Failed to get URL: ${ex.message}")
                ""
            }
        }
    }

    fun filterList(query: String?, categoryId: Int?) {
        val filteredBooks = allBooks
        _books.value = filteredBooks
    }

}