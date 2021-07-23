package br.com.dio.businesscard.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import java.io.OutputStream
import java.lang.Exception
import android.util.Log


class Image {
    fun share(context: Context, card: View) {
        val bitmap = getScreenShotFromView(card)
        bitmap?.let {
            saveMediaToStorage(context, bitmap)
        }
    }

    private fun getScreenShotFromView(card: View): Bitmap? {
        var screenshot: Bitmap? = null
        try {
            screenshot = Bitmap.createBitmap(
                card.measuredWidth,
                card.measuredHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(screenshot)
            card.draw(canvas)
        } catch (e: Exception) {
            Log.e("Error ->", "Falha ao capturar imagem" + e.message)
        }
        return screenshot
    }

    private fun saveMediaToStorage(context: Context, bitmap: Bitmap){
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            context.contentResolver?.also { resolver ->

                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                var imageUri: Uri?=
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let {
                    shareIntent(context, imageUri)
                }
            }
        }
    }
    private fun shareIntent(context: Context, imageUri: Uri): OutputStream?{
        TODO("Not yet implemented")
    }
}