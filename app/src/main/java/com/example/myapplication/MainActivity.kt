package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        // Загрузка изображения с помощью Glide
                        GlideImage(
                            url = "https://images.unsplash.com/photo-1748280816879-89b9ca0d4aa0?q=80&w=1960&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                            width = 300, // Укажите желаемую ширину
                            height = 500 // Укажите желаемую высоту
                        )

                        Spacer(modifier = Modifier.height(30.dp))

                        // Загрузка изображения по валидному URL с помощью Coil
                        LoadImage("https://images.unsplash.com/photo-1744539982356-336fac93f4b7?q=80&w=1972&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")

                        Spacer(modifier = Modifier.height(30.dp))

                        // Загрузка изображения по невалидному URL с помощью Coil
                        LoadImage("https://images.unsplash.com/photo-1756-336fac93f4b7?q=80&w=1972&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
                    }
                }
            }
        }
    }

    @Composable
    fun LoadImage(url: String) {
        val painter = rememberImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(url)
                .error(R.drawable.ic_error) // Укажите изображение ошибки
                .placeholder(R.drawable.ic_placeholder) // Укажите изображение загрузки
                .scale(Scale.FILL)
                .build()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Установите желаемую высоту
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.fillMaxSize() // Занимает всю ширину и высоту Box
            )
        }
    }

    @Composable
    fun GlideImage(url: String, width: Int, height: Int, modifier: Modifier = Modifier) {
        val context = LocalContext.current

        AndroidView(
            factory = { ctx: Context ->
                ImageView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(width, height) // Устанавливаем размеры
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    Glide.with(ctx)
                        .load(url)
                        .apply(RequestOptions().placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_error))
                        .into(this)
                }
            },
            modifier = modifier
        )
    }
}

