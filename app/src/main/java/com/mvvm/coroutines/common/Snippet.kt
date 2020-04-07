package com.mvvm.coroutines.common

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.telephony.TelephonyManager
import android.util.TypedValue
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.nio.charset.Charset
import java.util.*

class Snippet(private val mContext: Context) {

    fun imeiProcessing(): String {
        var IMEINumber = ""
        if (ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val telephonyMgr =
                mContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
            IMEINumber = (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) telephonyMgr!!.imei
            else telephonyMgr!!.deviceId)
        }
        return IMEINumber
    }

    fun getMimeType(path: String?): String? {
        var type: String? = "image/jpeg" // Default Value
        val extension = MimeTypeMap.getFileExtensionFromUrl(path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

    private val currentDate: Date
        get() = Date(System.currentTimeMillis())

    fun getBitmapUri(bitmap: Bitmap): Uri? {
        val filesDir: File = mContext.filesDir
        val imageFile = File(filesDir, "$currentDate.jpg")

        val os: OutputStream
        return try {
            os = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
            convertFileToContentUri(imageFile)

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @Throws(Exception::class)
    fun convertFileToContentUri(file: File): Uri {
        //Uri localImageUri = Uri.fromFile(localImageFile); // Not suitable as it's not a content Uri
        val cr = mContext.contentResolver
        val imagePath = file.absolutePath
        val imageName: String? = null
        val imageDescription: String? = null
        val uriString =
            MediaStore.Images.Media.insertImage(cr, imagePath, imageName, imageDescription)
        return Uri.parse(uriString)
    }

    /**
     * Checking the internet connectivity
     *
     * @return true if the device has a network connection; false otherwise.
     */
    fun isInternetAvailable(): Boolean {
        var result = false
        val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        } else cm?.run {
            cm.activeNetworkInfo?.run {
                if (type == ConnectivityManager.TYPE_WIFI) result = true
                else if (type == ConnectivityManager.TYPE_MOBILE) result = true
            }
        }
        return result
    }

    private fun showGooglePlayDialog(
        context: Context,
        googlePlayServiceListener: OnGooglePlayServiceListener
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Get Google Play Service")
        builder.setMessage("This app won't run without Google Play ServicesData, which are missing from your phone")
        builder.setPositiveButton(
            "Get Google Play Service"
        ) { dialog, _ ->
            googlePlayServiceListener.onInstallingService()
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(("https://play.google.com/store/apps/details?" + "id=com.google.android.gms"))
                )
            )
            dialog.dismiss()
        }
        builder.setNegativeButton(
            "Cancel"
        ) { _, _ -> googlePlayServiceListener.onCancelServiceInstallation() }
        builder.setCancelable(false)
        val alert = builder.create()
        alert.show()
    }

    /**
     * Fetch the drawable object for the given resource id.
     *
     * @param resourceId to which the value is to be fetched.
     * @return drawable object for the given resource id.
     */

    fun getDrawable(resourceId: Int) =
        ResourcesCompat.getDrawable(mContext.resources, resourceId, null)

    /**
     * Fetch the string value from a xml file returns the value.
     *
     * @param resId to which the value has to be fetched.
     * @return String value of the given resource id.
     */

    fun getString(resId: Int) = mContext.resources.getString(resId)

    /**
     * Fetch the color value from a xml file returns the value.
     *
     * @param colorId to which the value has to be fetched.
     * @return Integer value of the given resource id.
     */

    fun getColor(colorId: Int): Int {
        return ContextCompat.getColor(mContext, colorId)
    }

    private interface OnGooglePlayServiceListener {
        fun onInstallingService()
        fun onCancelServiceInstallation()
    }

    fun loadJSONFromAsset(fileName: String): String? {
        val json: String?
        try {
            val `is` = mContext.assets.open("config/$fileName")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }

    fun setCircleImage(imageView: ImageView, url: String) {
        Glide.with(mContext).load(url)
            .apply(
                RequestOptions.circleCropTransform()
                    .priority(Priority.HIGH)
            ).into(imageView)
    }

    fun setImage(imageView: ImageView, url: String) {
        Glide.with(mContext).load(url)
            .apply(
                RequestOptions.centerCropTransform()
                    .priority(Priority.HIGH)
            ).into(imageView)
    }

    fun loadImageFromDrawable(
        imageView: ImageView,
        imageDrawable: Int,
        isSquare: Boolean,
        drawable: Int
    ) {
        if (isSquare) {
            Glide.with(imageView.context)
                .load(imageDrawable)
                .apply(
                    RequestOptions.fitCenterTransform()
                        .placeholder(drawable)
                        .error(drawable)
                )
                .into(imageView)
        } else {
            Glide.with(imageView.context)
                .load(imageDrawable) // Uri of the picture
                .apply(
                    RequestOptions.circleCropTransform()
                        .placeholder(drawable)
                        .error(drawable)
                )
                .into(imageView)
        }
    }

    fun loadImage(imageView: ImageView, imagePath: String, isSquare: Boolean, drawable: Int) {
        if (isSquare) {
            Glide.with(imageView.context)
                .load(imagePath)
                .apply(
                    RequestOptions.fitCenterTransform()
                        .placeholder(drawable)
                        .error(drawable)
                )
                .into(imageView)
        } else {
            Glide.with(imageView.context)
                .load(imagePath) // Uri of the picture
                .apply(
                    RequestOptions()
                        .centerCrop()
                        .placeholder(drawable)
                        .error(drawable)
                )
                .into(imageView)
        }
    }

    fun getGlide() = Glide.with(mContext)
        .setDefaultRequestOptions(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))

    fun getGlideForDp() =
        Glide.with(mContext).setDefaultRequestOptions(
            RequestOptions().diskCacheStrategy(
                DiskCacheStrategy.ALL
            )
        )

    fun loadImageFromSDCard(imageView: ImageView, imagePath: String, drawable: Int) {
        Glide.with(imageView.context)
            .load(imagePath)
            .apply(
                RequestOptions
                    .bitmapTransform(RoundedCorners(25))
                    .placeholder(drawable)
                    .error(drawable)
            ).into(imageView)
    }


    fun loadImageFromSDCard(imageView: AppCompatImageView?, imagePath: String) {
        imageView?.let {
            Glide.with(it.context)
                .load(imagePath)
                .into(it)
        }
    }

    fun loadImageFromSDCard(imageView: ImageView, imagePath: Uri, drawable: Int) {
        Glide.with(imageView.context)
            .load(imagePath)
            .apply(
                RequestOptions.bitmapTransform(RoundedCorners(25))
                    .placeholder(drawable)
                    .error(drawable)
            ).into(imageView)
    }

    fun loadImageFromSDCardCircleBitmap(imageView: ImageView, imagePath: Uri, drawable: Int) {
        Glide.with(imageView.context)
            .load(imagePath) // Uri of the picture
            .apply(
                RequestOptions().centerCrop()
                    .placeholder(drawable)
                    .error(drawable)
            ).into(imageView)
    }

    private fun getPixelValueFromDimension(i: Int): Int {
        val r = mContext.resources

        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            i.toFloat(),
            r.displayMetrics
        ).toInt()
    }

    fun recyclerViewHorizontalSpace(
        params: RelativeLayout.LayoutParams, adapterPosition: Int,
        spanCount: Int,
        leftWithOutSpace: Int,
        topWithOutSpace: Int,
        rightWithOutSpace: Int,
        bottomWithOutSpace: Int,
        leftWithSpace: Int,
        topWithSpace: Int,
        rightWithSpace: Int,
        bottomWithSpace: Int
    ): RelativeLayout.LayoutParams {
        if ((adapterPosition + 1) % spanCount == 0) {
            params.setMargins(
                getPixelValueFromDimension(leftWithOutSpace),
                getPixelValueFromDimension(topWithOutSpace),
                getPixelValueFromDimension(rightWithOutSpace),
                getPixelValueFromDimension(bottomWithOutSpace)
            )
        } else {
            params.setMargins(
                getPixelValueFromDimension(leftWithSpace),
                getPixelValueFromDimension(topWithSpace),
                getPixelValueFromDimension(rightWithSpace),
                getPixelValueFromDimension(bottomWithSpace)
            )
        }

        return params
    }
}