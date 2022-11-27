package com.example.background.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.background.R

private const val TAG = "BlurWorker"
class BlurWorker(ctx: Context, parameters: WorkerParameters): Worker(ctx, parameters) {
    override fun doWork(): Result {
        val appContext = applicationContext
        makeStatusNotification("Blurring image", appContext)
        return try {
            // Load and decode picture
            val picture = BitmapFactory.decodeResource(
                appContext.resources, R.drawable.android_cupcake
            )

            // blur the image
            val output = blurBitmap(picture, appContext)

            // write to a temp file
            val outputUri = writeBitmapToFile(appContext, output)
            makeStatusNotification("Output is $outputUri", appContext)
            Result.success()

        } catch (throwable: Throwable) {
            Log.e(TAG, "Error applying blur")
            Result.failure()
        }
    }

}