package com.example.mealapp.screens

import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun ProfileScreen(auth: FirebaseAuth) {
    LoginScreen(auth)
}

@Composable
fun LoginScreen(auth: FirebaseAuth) {
    val focusManager = LocalFocusManager.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isEmailValid by derivedStateOf{ Patterns.EMAIL_ADDRESS.matcher(email).matches()}
    val isPasswordValid by derivedStateOf { password.length > 7 }
    var isPasswordVisible by remember { mutableStateOf(false)}
    Column(
        modifier = Modifier
            .background(color = Color.LightGray)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ){
        Text(
            text = "Welcome to Create a Meal",
            fontFamily = FontFamily.Companion.SansSerif,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = 32.sp,
            modifier = Modifier.padding(top = 16.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color.Black)
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(all = 10.dp)
            ){
                OutlinedTextField(
                    value = email,
                    onValueChange = {email = it},
                    label = {Text("Email Address")},
                    placeholder = {Text("abc@domain.com")},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {focusManager.moveFocus(FocusDirection.Down)}
                    ),
                    isError = !isEmailValid,
                    trailingIcon = {
                        if (email.isNotBlank()){
                            IconButton(onClick = {email = ""}){
                                Icon(imageVector = Icons.Filled.Clear,
                                    contentDescription = "Clear email")
                            }
                        }
                    }
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = {password = it},
                    label = {Text("Password")},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {focusManager.clearFocus()}
                    ),
                    isError = !isPasswordValid,
//                    trailingIcon = {
//                            IconButton(onClick = {isPasswordVisible = !isPasswordValid}){
//                                Icon(imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
//                                    contentDescription = "Toggle password visibility")
//                            }
//                    }
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None
                    else PasswordVisualTransformation()
                )
                Button(onClick = {
                                 auth.signInWithEmailAndPassword(email,password)
                                     .addOnCompleteListener{
                                         if (it.isSuccessful){
                                             logInSuccess(email)
                                         }
                                         else{
                                             logInFail()
                                         }
                                     }
                },
                    enabled = isEmailValid && isPasswordValid,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                ){
                    Text(
                        text = "Log in",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 16.sp,
                    )
                }
            }
        }
        Row(horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ){
            Button(onClick = {},
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ){
                Text(
                    text = "Register",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 16.sp,
                )
            }
        }
    }
}
@Composable
fun logInFail() {
    Column(
        modifier = Modifier
            .background(color = Color.LightGray)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = "Invalid email or password.",
            fontFamily = FontFamily.Companion.SansSerif,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = 32.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun logInSuccess(email: String){
    Column(
        modifier = Modifier
            .background(color = Color.LightGray)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = "Welcome back $email!",
            fontFamily = FontFamily.Companion.SansSerif,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = 32.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
@Preview
fun ProfileScreenPreview() {
    ProfileScreen(Firebase.auth)
}

