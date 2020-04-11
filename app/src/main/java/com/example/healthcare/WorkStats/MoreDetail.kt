package com.example.healthcare.WorkStats

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import androidx.appcompat.app.AppCompatActivity
import com.example.healthcare.Items.Statewise
import com.example.healthcare.R
import kotlinx.android.synthetic.main.activity_more_detail.*

class MoreDetail : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_detail)

        val x = intent.getSerializableExtra("data") as Statewise
        val vib = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        val inc = x.deltaconfirmed.toString().trim()
        val conf = x.confirmed.trim()

        if (inc.toInt() != 0) {

            val animator = ValueAnimator.ofInt(0, inc.toInt())
            animator.duration = 1500

            animator.addUpdateListener { it1 ->
                dia_increase.text = "+${it1.animatedValue}"
                vib.vibrate(2)
            }
            animator.start()
        } else
            dia_increase.text = "+$inc"

        dia_place.text = x.state.toString().trim()
        dia_update.text = x.lastupdatedtime.trim()
        dia_active.text = x.active.trim()
        dia_confirm.text = conf
        dia_death.text = x.deaths.trim()
        dia_recovered.text = x.recovered.trim()
        dia_increase_percent.text =
            String.format("%.1f", (inc.toFloat() * 100 / conf.toFloat()))
        dia_increase_percent.append("%")
        bars.animate().alpha(1f).duration = 500

        bar_before.post {
            val h = bar_before.height
            bar_before.animate()
                .translationY(inc.toFloat() * h / (inc.toFloat() + conf.toFloat())).duration = 1
        }

    }
}
