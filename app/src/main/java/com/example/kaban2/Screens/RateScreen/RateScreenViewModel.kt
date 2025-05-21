package com.example.kaban2.Screens.RateScreen

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
import com.example.kaban2.Domain.models.Profile
import com.example.kaban2.Domain.models.Resources

class RateScreenViewModel : ViewModel() {

    private val _resultState = MutableStateFlow<ResultState>(ResultState.Loading)
    val resultState: StateFlow<ResultState> = _resultState.asStateFlow()

    // MutableLiveData для хранения списка книг
    private val _books = MutableLiveData<List<Profile>>()
    val books: LiveData<List<Profile>> get() = _books

    private val _books2 = MutableLiveData<List<Resources>>()
    val books2: LiveData<List<Resources>> get() = _books2

    var kolvo:Int = 0;

    // MutableLiveData для хранения списка категорий
    //private val _categories = MutableLiveData<List<Category>>()
    //val categories: LiveData<List<Category>> get() = _categories

    // Хранит все книги для фильтрации
    private var allBooks: List<Profile> = listOf()
    private var allBooks2: List<Resources> = listOf()

    init {
        loadBooks()
        //loadBooks2()
    }


    private fun loadBooks() {
        _resultState.value = ResultState.Loading
        viewModelScope.launch {
            try {
                /*allBooks2 = supabase.postgrest.from("resources").select().decodeList<Resources>()
                val allb = allBooks2.sortedByDescending { it.balance_in_rubles }

                allBooks = supabase.postgrest.from("profile").select().decodeList<Profile>()
                _books.value = allBooks.sortedByDescending {  }*/

                val allBooks2 = supabase.postgrest.from("resources").select().decodeList<Resources>()
                _books2.value = allBooks2.sortedByDescending { it.balance_in_rubles }
                kolvo = allBooks2.size

                val allb = allBooks2.sortedByDescending { it.balance_in_rubles }

                val allBooks = supabase.postgrest.from("profile").select().decodeList<Profile>()

                val userIdOrder = allb.mapIndexed { index, resource -> resource.user_id to index }.toMap()

                val sortedAllBooks = allBooks.sortedBy { userIdOrder[it.user_id] ?: Int.MAX_VALUE }

                _books.value = sortedAllBooks


                //kolvo = allBooks.size
                Log.d("MainBooks1", "Success")
                Log.d("MainBooks", allBooks.toString())
                //_books.value = allBooks

                _resultState.value = ResultState.Success("Success")
            } catch (_ex: AuthRestException) {
                Log.d("MainBooks", _ex.message.toString())
                Log.d("MainBooks", _ex.errorCode.toString())
                Log.d("MainBooks", _ex.errorDescription)

                _resultState.value = ResultState.Error(_ex.errorDescription)
            }
        }
    }

    private fun loadBooks2() {
        _resultState.value = ResultState.Loading
        viewModelScope.launch {
            try {
                allBooks2 = supabase.postgrest.from("resources").select().decodeList<Resources>()
                _books2.value = allBooks2.sortedByDescending { it.balance_in_rubles }
                kolvo = allBooks2.size
                Log.d("MainBooks2", "Success")
                Log.d("MainBooks", allBooks2.toString())
                //_books.value = allBooks

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