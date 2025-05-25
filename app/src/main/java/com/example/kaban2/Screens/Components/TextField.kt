package com.example.kaban2.Screens.Components


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kaban2.R

/**
 * Композируемые функции в Jetpack Compose для создания полей ввода текста: для email, стандартного текста и пароля
 * */


//Отличительная способность использлвание параметра ошибки
@Composable
// Эта функция создает поле ввода для email адреса.  Она принимает:
// value: Текущее значение поля ввода
// error: Булево значение, указывающее на наличие ошибки
// onvaluechange: Лямбда-функция, вызываемая при изменении значения поля
fun TextFieldEmail(value: String, error:Boolean, onvaluechange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { onvaluechange(it) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.LightGray,
            unfocusedBorderColor = Color.LightGray,
            focusedLabelColor = Color.LightGray,
            unfocusedLabelColor = Color.LightGray,
            focusedTextColor = Color.LightGray,
            unfocusedTextColor = Color.LightGray,

            ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        shape = RoundedCornerShape(25.dp),
        label = { Text("email", fontSize = 20.sp, color = Color.LightGray) }, // Текст подсказки внутри поля
        modifier = Modifier

    )
}
@Composable
fun TextFieldStandart(value: String, onvaluechange: (String) -> Unit, label:String) {
    OutlinedTextField(
        value = value,
        onValueChange = { onvaluechange(it) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.LightGray,
            unfocusedBorderColor = Color.LightGray,
            focusedLabelColor = Color.LightGray,
            unfocusedLabelColor = Color.LightGray,
            focusedTextColor = Color.LightGray,
            unfocusedTextColor = Color.LightGray,


            ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        shape = RoundedCornerShape(25.dp),
        label = { Text(label, fontSize = 20.sp, color = Color.LightGray) },
        modifier = Modifier

    )
}

//Отличительная особенность — использование скрытие и отображение пароля
@Composable
fun TextFieldPassword(value: String, onvaluechange: (String) -> Unit, label: String) {
    var passwordVisibility by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                if (passwordVisibility) {
                    Icon(
                        painter = painterResource(R.drawable.eye_open),
                        modifier = Modifier.padding(5.dp),
                        contentDescription = ""
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.eye_close),
                        modifier = Modifier.padding(5.dp),
                        contentDescription = ""
                    )
                }
            }
        },

        onValueChange = { onvaluechange(it) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.LightGray,
            unfocusedBorderColor = Color.LightGray,
            focusedLabelColor = Color.LightGray,
            unfocusedLabelColor = Color.LightGray,
            unfocusedTextColor = Color.LightGray,
            focusedTextColor = Color.LightGray,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        shape = RoundedCornerShape(25.dp),
        label = { Text(label, fontSize = 20.sp, color = Color.LightGray) },
        modifier = Modifier

    )
}