package com.example.gulmadetektor.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.text.style.TextForegroundStyle.Unspecified.brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gulmadetektor.R
import com.example.gulmadetektor.data.repository.AuthRepository
import com.example.gulmadetektor.ui.navigation.Screen
import com.example.gulmadetektor.ui.theme.BackgroundDark
import com.example.gulmadetektor.ui.theme.BackgroundLight
import com.example.gulmadetektor.ui.theme.PrimaryGreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    navController: NavController,
    authRepository: AuthRepository,
    initialTab: Int = 0
) {
    var selectedTab by remember { mutableStateOf(initialTab) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // --- Login State ---
    var loginEmail by remember { mutableStateOf("") }
    var loginPassword by remember { mutableStateOf("") }
    var loginPasswordVisible by remember { mutableStateOf(false) }
    var isLoginLoading by remember { mutableStateOf(false) }

    // --- Register State ---
    var registerName by remember { mutableStateOf("") }
    var registerEmail by remember { mutableStateOf("") }
    var registerPassword by remember { mutableStateOf("") }
    var registerPasswordVisible by remember { mutableStateOf(false) }
    var isRegisterLoading by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = Color(0xFF1A1A1A),
                    contentColor = Color.White,
                    actionColor = PrimaryGreen,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            // ── Header ──────────────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .paint(
                        painter = painterResource(id = R.drawable.bagroundweed),
                        contentScale = ContentScale.Crop, // Agar gambar memenuhi kotak tanpa merusak rasio
                        // Efek warna transparan di atas gambar (Hitam 50%)
                        // Anda bisa mengganti Color.Black dengan PrimaryGreen jika ingin efek kehijauan
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                            color = Color.Gray.copy(alpha = 0.5f),
                            blendMode = androidx.compose.ui.graphics.BlendMode.SrcOver
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Logo circle
                    Box(
                        modifier = Modifier
                            .size(76.dp) // Ukuran lingkaran putih latar logo
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo), // Ganti dengan nama file logo Anda
                            contentDescription = "WeedGuard Logo",
                            modifier = Modifier.size(80.dp), // Ukuran gambar logo di dalam lingkaran
                            contentScale = ContentScale.Fit // Memastikan gambar muat dan tidak terpotong
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "WeedGuard",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            // ── Card (Tabs + Form) ───────────────────────────────────────────────
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 230.dp),
                color = Color.White,
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp, vertical = 70.dp)
                ) {

                    // ── Tab Toggle ──────────────────────────────────────────────
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color(0xFFEEEEEE))
                            .padding(4.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            TabChip(
                                label = "Masuk",
                                selected = selectedTab == 0,
                                modifier = Modifier.weight(1f),
                                onClick = { selectedTab = 0 }
                            )
                            TabChip(
                                label = "Daftar",
                                selected = selectedTab == 1,
                                modifier = Modifier.weight(1f),
                                onClick = { selectedTab = 1 }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // ── LOGIN FORM ──────────────────────────────────────────────
                    if (selectedTab == 0) {
                        Text(
                            "Selamat Datang!",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A1A1A)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Masuk untuk melanjutkan deteksi gulma",
                            fontSize = 14.sp,
                            color = Color(0xFF888888)
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        // Email
                        AuthFieldLabel("Email")
                        Spacer(modifier = Modifier.height(6.dp))
                        AuthTextField(
                            value = loginEmail,
                            onValueChange = { loginEmail = it },
                            placeholder = "contoh@email.com",
                            leadingIcon = Icons.Default.Email,
                            keyboardType = KeyboardType.Email
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Password
                        AuthFieldLabel("Kata Sandi")
                        Spacer(modifier = Modifier.height(6.dp))
                        AuthTextField(
                            value = loginPassword,
                            onValueChange = { loginPassword = it },
                            placeholder = "Masukkan kata sandi",
                            leadingIcon = Icons.Default.Lock,
                            keyboardType = KeyboardType.Password,
                            isPassword = true,
                            passwordVisible = loginPasswordVisible,
                            onTogglePassword = { loginPasswordVisible = !loginPasswordVisible }
                        )

                        // Lupa Password
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = { navController.navigate(Screen.ForgotPassword.route) }) {
                                Text(
                                    "Lupa kata sandi?",
                                    color = PrimaryGreen,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Tombol Masuk
                        Button(
                            onClick = {
                                when {
                                    loginEmail.isBlank() || loginPassword.isBlank() ->
                                        scope.launch { snackbarHostState.showSnackbar("Mohon isi semua field") }
                                    !android.util.Patterns.EMAIL_ADDRESS.matcher(loginEmail.trim()).matches() ->
                                        scope.launch { snackbarHostState.showSnackbar("Format email tidak valid") }
                                    loginPassword.length < 6 ->
                                        scope.launch { snackbarHostState.showSnackbar("Password minimal 6 karakter") }
                                    else -> {
                                        isLoginLoading = true
                                        scope.launch {
                                            val result = authRepository.login(loginEmail.trim(), loginPassword)
                                            isLoginLoading = false
                                            if (result.isSuccess) {
                                                navController.navigate(Screen.Home.route) {
                                                    popUpTo(Screen.Login.route) { inclusive = true }
                                                }
                                            } else {
                                                val msg = result.exceptionOrNull()?.message ?: "Login gagal"
                                                snackbarHostState.showSnackbar("Login gagal: $msg")
                                            }
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                            enabled = !isLoginLoading
                        ) {
                            if (isLoginLoading) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(22.dp),
                                    strokeWidth = 2.5.dp
                                )
                            } else {
                                Text(
                                    "Masuk",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Switch to Register
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Belum punya akun? ", fontSize = 14.sp, color = Color(0xFF888888))
                            TextButton(onClick = { selectedTab = 1 }) {
                                Text(
                                    "Daftar",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryGreen
                                )
                            }
                        }

                    } else {
                        // ── REGISTER FORM ───────────────────────────────────────
                        Text(
                            "Buat Akun Baru",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A1A1A)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Daftar untuk memulai melindungi lahan Anda",
                            fontSize = 14.sp,
                            color = Color(0xFF888888)
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        // Nama Lengkap
                        AuthFieldLabel("Nama Lengkap")
                        Spacer(modifier = Modifier.height(6.dp))
                        AuthTextField(
                            value = registerName,
                            onValueChange = { registerName = it },
                            placeholder = "Masukkan nama lengkap",
                            leadingIcon = Icons.Default.Person,
                            keyboardType = KeyboardType.Text
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Email
                        AuthFieldLabel("Email")
                        Spacer(modifier = Modifier.height(6.dp))
                        AuthTextField(
                            value = registerEmail,
                            onValueChange = { registerEmail = it },
                            placeholder = "contoh@email.com",
                            leadingIcon = Icons.Default.Email,
                            keyboardType = KeyboardType.Email
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Password
                        AuthFieldLabel("Kata Sandi")
                        Spacer(modifier = Modifier.height(6.dp))
                        AuthTextField(
                            value = registerPassword,
                            onValueChange = { registerPassword = it },
                            placeholder = "Masukkan kata sandi",
                            leadingIcon = Icons.Default.Lock,
                            keyboardType = KeyboardType.Password,
                            isPassword = true,
                            passwordVisible = registerPasswordVisible,
                            onTogglePassword = { registerPasswordVisible = !registerPasswordVisible }
                        )

                        Spacer(modifier = Modifier.height(28.dp))

                        // Tombol Daftar
                        Button(
                            onClick = {
                                when {
                                    registerName.isBlank() || registerEmail.isBlank() || registerPassword.isBlank() ->
                                        scope.launch { snackbarHostState.showSnackbar("Mohon isi semua field") }
                                    !android.util.Patterns.EMAIL_ADDRESS.matcher(registerEmail.trim()).matches() ->
                                        scope.launch { snackbarHostState.showSnackbar("Format email tidak valid") }
                                    registerPassword.length < 6 ->
                                        scope.launch { snackbarHostState.showSnackbar("Password minimal 6 karakter") }
                                    else -> {
                                        isRegisterLoading = true
                                        scope.launch {
                                            val result = authRepository.register(
                                                registerName.trim(),
                                                registerEmail.trim(),
                                                registerPassword
                                            )
                                            isRegisterLoading = false
                                            if (result.isSuccess) {
                                                snackbarHostState.showSnackbar("Registrasi berhasil! Silakan login.")
                                                registerName = ""
                                                registerEmail = ""
                                                registerPassword = ""
                                                selectedTab = 0
                                            } else {
                                                val msg = result.exceptionOrNull()?.message ?: "Registrasi gagal"
                                                snackbarHostState.showSnackbar("Error: $msg")
                                            }
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                            enabled = !isRegisterLoading
                        ) {
                            if (isRegisterLoading) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(22.dp),
                                    strokeWidth = 2.5.dp
                                )
                            } else {
                                Text(
                                    "Daftar",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Switch to Login
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Sudah punya akun? ", fontSize = 14.sp, color = Color(0xFF888888))
                            TextButton(onClick = { selectedTab = 0 }) {
                                Text(
                                    "Masuk",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryGreen
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

// ── Reusable composables ────────────────────────────────────────────────────────

@Composable
private fun TabChip(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .background(if (selected) Color.White else Color.Transparent)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) PrimaryGreen else Color(0xFF999999),
            fontSize = 15.sp
        )
    }
}

@Composable
private fun AuthFieldLabel(label: String) {
    Text(
        text = label,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF333333)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onTogglePassword: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color(0xFFBBBBBB), fontSize = 14.sp) },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = Color(0xFFBBBBBB),
                modifier = Modifier.size(20.dp)
            )
        },
        trailingIcon = if (isPassword && onTogglePassword != null) {
            {
                IconButton(onClick = onTogglePassword) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible) "Sembunyikan password" else "Tampilkan password",
                        tint = Color(0xFFBBBBBB),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        } else null,
        visualTransformation = if (isPassword && !passwordVisible)
            PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PrimaryGreen,
            unfocusedBorderColor = Color(0xFFDDDDDD),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color(0xFFFAFAFA),
            cursorColor = PrimaryGreen
        )
    )
}
