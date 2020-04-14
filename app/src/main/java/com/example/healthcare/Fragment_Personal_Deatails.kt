package com.example.healthcare

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.address_dialog.view.*
import kotlinx.android.synthetic.main.fab_corona.view.*
import kotlinx.android.synthetic.main.fragment__personal__deatails.*
import kotlinx.android.synthetic.main.layout_dialog.view.*
import kotlinx.android.synthetic.main.layout_dialog.view.error
import kotlinx.android.synthetic.main.layout_dialog.view.id_cancel
import kotlinx.android.synthetic.main.layout_dialog.view.id_saveChanges
import java.text.SimpleDateFormat
import java.util.*

class Fragment_Personal_Deatails : Fragment() {
    val PICK_IMAGE:Int = 1
    val pick_image_permission=1001
    var imageUri:Uri? = null
    var image_uri:Uri?= null
    val image_capture_code:Int=1001
    private val permission_code=1000;
    lateinit var  ProfileImage: CircleImageView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment__personal__deatails, container, false)
    }
    @SuppressLint("SetTextI18n")
    private fun editmail(){
        id_mail_layout.setOnClickListener {
            val view=LayoutInflater.from(requireContext()).inflate(R.layout.layout_dialog,null)
            val alertDialog=AlertDialog.Builder(requireContext()).setView(view)
                .setCancelable(false)
                .setTitle("Edit Email Address")
                .setIcon(R.drawable.ic_email_black_24dp)
                .show()
            view.edit_data.hint="Enter Valid Email.."
            view.id_saveChanges.setOnClickListener {
                if(view.edit_data.text.toString().contains("@") && id_mail.text.toString().contains(".com"))
                {
                    id_mail.text=view.edit_data.text.toString()
                    alertDialog.dismiss()
                }
                else
                {
                    view.error.visibility=View.VISIBLE
                    view.error.text="Please Enter Correct Email Address"
                }
            }
            view.id_cancel.setOnClickListener {
                alertDialog.dismiss()
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun editdob(){
        val cal=Calendar.getInstance()
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "MM/dd/yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                val today = Calendar.getInstance()
                var age: Int = today[Calendar.YEAR] - cal.get(Calendar.YEAR)
                if (today[Calendar.DAY_OF_MONTH] < cal.get(Calendar.DAY_OF_MONTH)) {
                    age=age-1
                }
                    id_dob!!.text = "${sdf.format(cal.getTime()) } (${age} Years Old)"

            }
        }
        id_dob_layout!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                context?.let {
                    DatePickerDialog(
                        it,
                        dateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
                }
                Snackbar.make(view,"Please Enter Your Date Of Birth",Snackbar.LENGTH_LONG)
                    .show()
            }
        })

    }
    @SuppressLint("SetTextI18n")
    private fun listenfab(){
        fab.setOnClickListener {
            val view=LayoutInflater.from(requireContext()).inflate(R.layout.fab_corona,null)
            val alertDialog=AlertDialog.Builder(requireContext()).setView(view)
                .setCancelable(false)
                .setTitle("CORONA STATUS")
                .setIcon(R.drawable.ic_accessibility_black_24dp)
                .show()
            view.id_saveChanges.setOnClickListener {
                if(view.corona_positive.isChecked)
                {
                    Toast.makeText(activity,"YOU ARE CORONA POSITIVE",Toast.LENGTH_SHORT).show()
                    fab.setImageResource(R.drawable.ic_corona)
                    alertDialog.dismiss()
                }
                else if(view.corona_negative.isChecked)
                {
                    Toast.makeText(activity,"YOU ARE CORONA NEGATIVE",Toast.LENGTH_SHORT).show()
                    fab.setImageResource(R.drawable.ic_virus)
                    alertDialog.dismiss()
                }
                else
                {
                    view.error.visibility=View.VISIBLE
                    view.error.text="Please Select A Valid Option"
                }
            }
            view.id_cancel.setOnClickListener {
                alertDialog.dismiss()
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun editphone(){
        id_phone_layout.setOnClickListener {
            val view=LayoutInflater.from(requireContext()).inflate(R.layout.layout_dialog,null)
            val alertDialog=AlertDialog.Builder(requireContext()).setView(view).setCancelable(false)
                .setTitle("EDIT MOBILE NUMBER")
                .setIcon(R.drawable.ic_phone_android_black_24dp).show()
                view.edit_data.hint="Enter Phone Number.."
                view.edit_data.inputType= InputType.TYPE_CLASS_NUMBER
                view.id_cancel.setOnClickListener {
                    alertDialog.dismiss()
                }
                view.id_saveChanges.setOnClickListener {
                    if(view.edit_data.text.toString()!="" && view.edit_data.text.toString().length==10)  {
                        id_phone.text = view.edit_data.text.toString()
                        alertDialog.dismiss()
                    }
                    else
                    {
                        view.error.visibility=View.VISIBLE
                        view.error.text="Please Enter Correct Phone Number"
                    }
                }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun editaddress(){
        id_address_layout.setOnClickListener {
            val view=LayoutInflater.from(requireContext()).inflate(R.layout.address_dialog,null)
            val alertDialog=AlertDialog.Builder(requireContext()).setView(view).setCancelable(false)
                .setTitle("EDIT ADDRESS")
                .setIcon(R.drawable.ic_home_black_24dp).show()
            view.street.hint="Enter Street.."
            view.city.hint="Enter City.."
            view.state.hint="Enter State.."
            view.id_cancel.setOnClickListener {
                alertDialog.dismiss()
            }
            view.id_saveChanges.setOnClickListener {
                if(view.street.text.toString()!="" && view.city.text.toString()!="" && view.state.text.toString()!=""
                    && view.street.text.toString().length<=50 && view.city.text.toString().length<=20 && view.state.text.toString().length<=20)  {
                    id_address.text = view.street.text.toString()+","+view.city.text.toString()+","+view.state.text.toString()+"."
                    alertDialog.dismiss()
                }
                else if(view.street.text.toString()=="" || view.street.text.toString().length>50){
                    view.error.visibility=View.VISIBLE
                    view.error.text="Please Enter Street(Max Street Size 50)"
                }
                else if(view.city.text.toString()=="" || view.city.text.toString().length>20 ){
                    view.error.visibility=View.VISIBLE
                    view.error.text="Please Enter City(Max City Size 20)"
                }
                else if(view.state.text.toString()=="" || view.state.text.toString().length>20){
                    view.error.visibility=View.VISIBLE
                    view.error.text="Please Enter State(Max State Size 20)"
                }
                else
                {
                    view.error.visibility=View.VISIBLE
                    view.error.text="Please Enter Correct Address"
                }
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun editname(){
        id_name.setOnClickListener {
            val view=LayoutInflater.from(requireContext()).inflate(R.layout.layout_dialog,null)
            val alertDialog=AlertDialog.Builder(requireContext()).setView(view)
                .setCancelable(false)
                .setTitle("Edit Name")
                .setIcon(R.drawable.ic_perm_identity_black_24dp)
                .show()
            view.edit_data.hint="Enter Your Name.."
            view.id_saveChanges.setOnClickListener {
                if(view.edit_data.text.toString()!="" && view.edit_data.text.toString().length<=40)
                {
                    id_name.text=view.edit_data.text.toString()
                    alertDialog.dismiss()
                }
                else
                {
                    view.error.visibility=View.VISIBLE
                    view.error.text="Please Enter Correct Name(Max length 40)"
                }
            }
            view.id_cancel.setOnClickListener {
                alertDialog.dismiss()
            }
        }
    }
//    fun openDialog(){
//        val email_dialog:dialog= dialog()
//        activity?.supportFragmentManager?.let { email_dialog.show(it, "NoticeDialogFragment") }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ProfileImage=view.findViewById(R.id.Profile_Image)
        editmail()
        editphone()
        editaddress()
        editname()
        listenfab()
        editdob()
        ProfileImage.setOnClickListener{
            checkpermission_gallery()
        }
    }


    private fun checkpermission_gallery() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (context?.let { ActivityCompat.checkSelfPermission(it,android.Manifest.permission.READ_EXTERNAL_STORAGE) } ==PackageManager.PERMISSION_DENIED){
                val permission= arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permission,pick_image_permission)
            }
            else{
                pickImageFromGallery()
            }
        }
        else{
            pickImageFromGallery()
        }
    }
    private fun pickImageFromGallery(){
        val intent:Intent= Intent(Intent.ACTION_PICK)
        intent.type=("image/*")
        startActivityForResult(intent,PICK_IMAGE)
    }

    override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE){
            if (data != null) {
                ProfileImage.setImageURI(data.data)
            }
        }
    }

}