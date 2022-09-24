package com.example.mealapp.screens

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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mealapp.BottomBarScreen
import com.example.mealapp.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun ProfileScreen(auth: FirebaseAuth, navController: NavHostController) {
    LoginScreen(auth, navController)
}

@Composable
fun LoginScreen(auth: FirebaseAuth, navController: NavHostController, ) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isEmailValid by derivedStateOf{ Patterns.EMAIL_ADDRESS.matcher(email).matches()}
    val isPasswordValid by derivedStateOf { password.length > 7 }
    var isPasswordVisible by remember { mutableStateOf(false)}
    var isSignInSuccess by remember { mutableStateOf(false)}
    var isLoginFail by remember { mutableStateOf(false)}
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colors.primary)
                .requiredSize(width = 600.dp, height = 75.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
        Text(
            text = "Welcome to Toasty!",
            style = MaterialTheme.typography.h1,
            modifier = Modifier
                .padding(top = 16.dp)
        )
    }
        Spacer (modifier = Modifier.padding(24.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            backgroundColor = MaterialTheme.colors.secondary,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color.White)
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(all = 10.dp)
            ){
                OutlinedTextField(
                    textStyle = MaterialTheme.typography.body1,
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
                    textStyle = MaterialTheme.typography.body1,
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
                    trailingIcon = {
                            IconButton(onClick = {isPasswordVisible = !isPasswordValid}){
                                Icon(imageVector = if (isPasswordVisible) Icons.Default.Visibility
                                else Icons.Default.VisibilityOff,
                                    contentDescription = "Toggle password visibility")
                            }
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None
                    else PasswordVisualTransformation()
                )
                Button(onClick = {
                                 auth.signInWithEmailAndPassword(email,password)
                                     .addOnCompleteListener{
                                         if (it.isSuccessful){
                                             User.emailAddress = email
                                             navController.navigate("${BottomBarScreen.Goals.route}/$email")
                                             //isLoginSuccessful = true
                                             isSignInSuccess = false
                                             isLoginFail = false
                                         }
                                         else{
                                             isSignInSuccess = false
                                             isLoginFail = true
                                             //isLoginSuccessful = false
                                         }
                                     }
                },
                    enabled = isEmailValid && isPasswordValid,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondaryVariant)
                ){
                    Text(
                        text = "Log in",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        //fontSize = 16.sp,
                    )
                }
            }
        }
        Row(horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ){
            Button(onClick = {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { it ->
                    if (it.isSuccessful) {
                        //Registration OK
                        val firebaseUser = auth.currentUser!!
                        isSignInSuccess = true
                    } else {
                        //Registration error
                        isSignInSuccess = false
                    }
                }
            },
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant)
            ){
                Text(
                    text = "Register",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    //fontSize = 16.sp,
                )
            }
        }
        if (isLoginFail){
            logInFail()
        }
        else{
        }
        if (isSignInSuccess){
            signedUp(email)
        }
        else{
        }
    }
}
@Composable
fun logInFail() {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = "Invalid email or password.",
            color = Color.Red,
            fontFamily = FontFamily.Companion.SansSerif,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = 22.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
@Composable
fun signedUp(user: String?) {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = "You are signed up as $user!",
            color = Color.Black,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "Please Login.",
            color = Color.Black,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

//@Composable
//fun logInSuccess(email: String){
//    Column(
//        modifier = Modifier
//            .background(color = Color.LightGray)
//            .fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top,
//    ) {
//        Text(
//            text = "Welcome back $email!",
//            fontFamily = FontFamily.Companion.SansSerif,
//            fontWeight = FontWeight.Bold,
//            fontStyle = FontStyle.Italic,
//            fontSize = 32.sp,
//            modifier = Modifier.padding(top = 16.dp)
//        )
//    }
//}



