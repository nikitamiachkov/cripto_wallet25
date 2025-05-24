package com.example.kaban2.Screens.Components

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kaban2.Domain.Constant.supabase
import com.example.kaban2.Domain.models.Cripto
import com.example.kaban2.Domain.models.Profile
import com.example.kaban2.Domain.models.Resources
import com.example.kaban2.Domain.models.User_cripto
import com.example.kaban2.Screens.MainScreen.MainScreenViewModel
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CardCriptoViewModel : ViewModel() {

    // UI-состояние, если нужно отображать прогресс или ошибку
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun showMessage(message: String) {
        _userMessage.value = message
    }

    fun clearMessage() {
        _userMessage.value = null
    }

    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage

    private val _criptoList = MutableStateFlow<List<User_cripto>>(emptyList())
    val criptoList: StateFlow<List<User_cripto>> = _criptoList

    val userId = supabase.auth.currentUserOrNull()?.id

    private val _reloadTrigger = MutableSharedFlow<Unit>()
    val reloadTrigger: SharedFlow<Unit> = _reloadTrigger

    fun triggerReload() {
        viewModelScope.launch {
            _reloadTrigger.emit(Unit)
        }
    }

    fun buyCripto(cripto: Cripto) {
        viewModelScope.launch {
            _loading.value = true
            try {

                val profileResult2 = supabase.from("resources")
                    .select(
                        columns = Columns.list(
                            "user_id", "balance_in_rubles"
                        )
                    ) {
                        filter {
                            eq("user_id", userId.toString())
                        }
                    }
                    .decodeSingle<Resources>()

                profileResult2.balance_in_rubles?.let {
                    if (it >= cripto.cost) {

                        supabase.from("resources").update(
                            {
                                profileResult2.balance_in_rubles?.let { set("balance_in_rubles", (it - cripto.cost)) }
                            }
                        ) {
                            filter {
                                eq("user_id", userId.toString())
                            }
                        }

                        val profileResult3 = supabase.from("users_have_cryptocurrency")
                            .select(columns = Columns.list("id", "cripto", "quantity", "purchase_price","user_id")) {
                                filter {
                                    eq("user_id", userId.toString())
                                    eq("cripto", cripto.id)
                                }}
                            .decodeSingle<User_cripto>()

                        if (profileResult3.quantity > 0) {
                            Log.d("quantity", profileResult3.quantity.toString())
                            supabase.from("users_have_cryptocurrency").update(
                                {
                                    set("quantity", profileResult3.quantity + 1)
                                }
                            ) {
                                filter {
                                    eq("user_id", userId.toString())
                                    eq("cripto", cripto.id)
                                }
                            }
                        } else {
                            val rnds = (100..10000000).random()

                            val user_cripto = User_cripto(rnds, cripto.id, 1, cripto.cost, userId.toString())
                            supabase.from("users_have_cryptocurrency").insert(user_cripto)
                        }



                        val profileResult4 = supabase.from("users_have_cryptocurrency")
                            .select(columns = Columns.list("id", "cripto", "quantity", "purchase_price","user_id")) {
                                filter {
                                    eq("user_id", userId.toString())
                                }}
                            .decodeList<User_cripto>()

                        _criptoList.value = profileResult4

                    } else {
                        showMessage("У вас недостаточно средств")
                    }
                }





            } catch (e: Exception) {

                try {

                        val rnds = (100..10000000).random()

                        val user_cripto = User_cripto(rnds, cripto.id, 1, cripto.cost, userId.toString())
                        supabase.from("users_have_cryptocurrency").insert(user_cripto)

                } catch (e: Exception) { Log.e("ViewModel", "Ошибка покупки2", e) }



                Log.e("ViewModel", "Ошибка покупки", e)
            } finally {
                _loading.value = false
            }
        }
    }

    fun sellCripto(cripto: Cripto) {
        viewModelScope.launch {
            _loading.value = true
            try {

                val profileResult86 = supabase.from("users_have_cryptocurrency")
                    .select(columns = Columns.list("id", "cripto", "quantity", "purchase_price","user_id")) {
                        filter {
                            eq("user_id", userId.toString())
                            eq("cripto", cripto.id)
                        }}
                    .decodeSingle<User_cripto>()

                if (profileResult86.quantity != null && profileResult86.quantity > 0) {
                    Log.d("quantity", profileResult86.quantity.toString())
                    val profileResult2 = supabase.from("resources")
                        .select(
                            columns = Columns.list(
                                "user_id", "balance_in_rubles"
                            )
                        ) {
                            filter {
                                eq("user_id", userId.toString())
                            }
                        }
                        .decodeSingle<Resources>()

                    supabase.from("resources").update(
                        {
                            profileResult2.balance_in_rubles?.let { set("balance_in_rubles", (it + cripto.cost)) }
                        }
                    ) {
                        filter {
                            eq("user_id", userId.toString())
                        }
                    }

                    if (profileResult86.quantity == 1) {
                        supabase.from("users_have_cryptocurrency").delete {
                            filter {
                                eq("user_id", userId.toString())
                                eq("cripto", cripto.id)

                            }
                        }
                    } else {
                        supabase.from("users_have_cryptocurrency").update(
                            {
                                set("quantity", profileResult86.quantity - 1)
                            }
                        ) {
                            filter {
                                eq("user_id", userId.toString())
                                eq("cripto", cripto.id)
                            }
                        }
                    }

                    //val rnds = (100..10000000).random()

                    //val user_cripto = User_cripto(rnds, cripto.id, 1, cripto.cost, userId.toString())
                    //supabase.from("users_have_cryptocurrency").insert(user_cripto)



                    val profileResult4 = supabase.from("users_have_cryptocurrency")
                        .select(columns = Columns.list("id", "cripto", "quantity", "purchase_price","user_id")) {
                            filter {
                                eq("user_id", userId.toString())
                            }}
                        .decodeList<User_cripto>()

                    _criptoList.value = profileResult4
                } else {
                    showMessage("У вас нет такой криптовалюты")
                }




            } catch (e: Exception) {
                showMessage("У вас нет такой криптовалюты")
                Log.e("ViewModel", "Ошибка продажи", e)
            } finally {
                _loading.value = false
            }
        }
    }
}