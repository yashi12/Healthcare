package com.example.healthcare.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.healthcare.Items.Example
import com.example.healthcare.MainActivity
import com.example.healthcare.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_news.view.*
import kushal.application.covaupdates.My_adapter

class News : Fragment() {

    val url = "https://api.covid19india.org/data.json"
    lateinit var mcontext: Context
    lateinit var que: RequestQueue

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val vieww = inflater.inflate(R.layout.fragment_news, container, false)
        mcontext = activity!!.applicationContext

        que = Volley.newRequestQueue(mcontext)
        loadData(vieww)

        return vieww
    }

    private fun loadData(view: View) {

        val request = StringRequest(
            url, Response.Listener { response ->

                val builder = GsonBuilder()
                val gson = builder.create()

                val users = gson.fromJson(response, Example::class.java)
                val list = users.statewise
                val vib = mcontext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

                val adapter = My_adapter(mcontext, list, vib)

                view.recyclerView.visibility = RecyclerView.VISIBLE
                view.recyclerView.layoutManager = LinearLayoutManager(mcontext)
                view.recyclerView.adapter = adapter
                view.progressBar.visibility = ProgressBar.GONE
            },
            Response.ErrorListener {
                view.contianer.visibility = LinearLayout.GONE
                view.animationView.visibility = LinearLayout.VISIBLE
                view.retry_box.visibility = LinearLayout.VISIBLE

                view.retry_box.setOnClickListener {
                    startActivity(Intent(mcontext, MainActivity::class.java))
                    activity?.finish()
                }

                Log.i("error", it.message.toString())
            })

        que.add(request)

    }

}
