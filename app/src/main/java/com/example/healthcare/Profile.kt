package com.example.healthcare

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 */
class Profile : Fragment() {
    var  ProfileImage:CircleImageView?=null
    val PICK_IMAGE=1234
     var imageUri:Uri?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ProfileImage=view.findViewById(R.id.Profile_Image)
        if(ProfileImage!=null && imageUri!=null)
        {
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver , imageUri)
            ProfileImage!!.setImageBitmap(bitmap)
        }
        ProfileImage!!.setOnClickListener{
            val gallery:Intent = Intent()
            gallery.type = "image/*"
            gallery.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(gallery, "Sellect Picture"), PICK_IMAGE)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data!!

            try {

                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver , imageUri)
                ProfileImage!!.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
