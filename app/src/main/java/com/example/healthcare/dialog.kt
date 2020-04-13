package com.example.healthcare

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment

class dialog: AppCompatDialogFragment() {
    lateinit var  email:EditText
    internal lateinit var listener: NoticeDialogListener
    interface NoticeDialogListener {
        fun applyTexts(email:String)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder: AlertDialog.Builder = AlertDialog.Builder(it)
            val inflater: LayoutInflater = requireActivity().layoutInflater
            val view: View = inflater.inflate(R.layout.layout_dialog, null)

            builder.setView(view)
                .setTitle("Edit Email")
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id -> getDialog()?.cancel() })
                .setPositiveButton("Save",DialogInterface.OnClickListener { dialog, id ->
                        // sign in the user ...
                        val useremail: String = email.text.toString()
                        listener.applyTexts(useremail)
                    })
            email = view.findViewById(R.id.edit_data)
            builder.create()
        }   ?: throw IllegalStateException("Activity cannot be null")

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }
}