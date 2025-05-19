package com.example.kaban2.Screens.MainScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kaban2.Domain.Constant.supabase
import com.example.kaban2.Domain.models.Resources
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

import androidx.lifecycle.viewModelScope
import com.example.kaban2.Domain.models.Profile

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel : ViewModel() {

    var username by mutableStateOf<String?>(null)
        public set

    /*var kaban_level by mutableStateOf<String>("none")
        public set

    var url by mutableStateOf<String>("none")
        public set*/



    var balance by mutableStateOf<Double?>(0.0)
        private set

    private val _count4 = balance
    val count4: StateFlow<Int> = MutableStateFlow(0)


    init {
        loadUserData()
    }

    private val _username = MutableLiveData<String>()
    //val username: LiveData<String> get() = _username

    public fun loadUserData2(count:Int) {
        val userId = supabase.auth.currentUserOrNull()?.id ?: return
        Log.d("SignUp", userId)

        //_count4.value += 1
        //quantity += 1

        // Выгружаем количество
        viewModelScope.launch {

            supabase.from("resources").update(
                {
                    set("quantity", count)
                }
            ) {
                filter {
                    eq("user_id", userId)
                }
            }

            try {
                val profileResult = supabase.from("resources")
                    .select(columns = Columns.list("user_id", "balance_in_rubles")) {
                        filter {
                            eq("user_id", userId)
                        }}
                    .decodeSingle<Resources>()



                balance = profileResult.balance_in_rubles
            } catch (_ex: AuthRestException) {
            }

        }
    }

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

}