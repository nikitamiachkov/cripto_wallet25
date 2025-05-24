package com.example.kaban2.Screens.MainScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kaban2.Domain.Constant.supabase
import com.example.kaban2.Domain.models.Resources
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

import androidx.lifecycle.viewModelScope
import com.example.kaban2.Domain.models.Cripto
import com.example.kaban2.Domain.models.Profile
import com.example.kaban2.Domain.models.User_cripto
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainScreenViewModel : ViewModel() {

    var username by mutableStateOf<String?>(null)
        public set

    private val _reload = MutableStateFlow(false)
    val reload: StateFlow<Boolean> = _reload

    fun triggerReload() {
        _reload.value = !_reload.value
    }

    fun refreshData() {
        viewModelScope.launch {
            // Здесь вызываем всю нужную логику обновления
            // например: загрузка username, balance, cripto, kolvo и т.д.
            loadUserData()
            //loadCriptoList()
        }
    }

    /*var kaban_level by mutableStateOf<String>("none")
        public set

    var url by mutableStateOf<String>("none")
        public set*/

    var kolvo:Int = 0;



    var balance by mutableStateOf<Double?>(0.0)
        private set

    private val _count4 = balance
    val count4: StateFlow<Int> = MutableStateFlow(0)

    private val _cripto = MutableLiveData<List<User_cripto>>()
    val cripto: LiveData<List<User_cripto>> get() = _cripto


    init {
        loadUserData()
    }

    private val _username = MutableLiveData<String>()
    //val username: LiveData<String> get() = _username


    private fun loadUserData() {
        val userId = supabase.auth.currentUserOrNull()?.id ?: return
        Log.d("SignUp", userId)

        // Загружаем username из profiles
        viewModelScope.launch {
            try {
                val profileResult = supabase.from("profile")
                    .select(
                        columns = Columns.list(
                            "user_id",
                            "username",
                            "surname",
                            "date_of_birth"
                        )
                    ) {
                        filter {
                            eq("user_id", userId)
                        }
                    }
                    .decodeSingle<Profile>()



                username = profileResult.username
            } catch (_ex: AuthRestException) {
                username = "Noy"
                Log.d("SignUp", "lox")
            }

            try {
                val profileResult = supabase.from("users_have_cryptocurrency")
                    .select(columns = Columns.list("id", "cripto", "quantity", "purchase_price","user_id")) {
                        filter {
                            eq("user_id", userId)
                        }}
                    .decodeList<User_cripto>()



                _cripto.value = profileResult
                kolvo = profileResult.size
            } catch (_ex: AuthRestException) {
                Log.d("SignUp", "lox")
            }

            try {
                val profileResult = supabase.from("resources")
                    .select(
                        columns = Columns.list(
                            "user_id", "balance_in_rubles"
                        )
                    ) {
                        filter {
                            eq("user_id", userId)
                        }
                    }
                    .decodeSingle<Resources>()



                balance = profileResult.balance_in_rubles
            } catch (_ex: AuthRestException) {
                balance = 0.0
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

    suspend fun getCripto(id: Int):Cripto {
            val profileResult = supabase.from("cryptocurrency")
                .select(
                    columns = Columns.list(
                        "id", "name", "cost","image","last_cost"
                    )
                ) {
                    filter {
                        eq("id", id)
                    }
                }
                .decodeSingle<Cripto>()


            return profileResult

    }

}