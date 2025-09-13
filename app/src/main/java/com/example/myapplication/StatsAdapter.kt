package com.example.myapplication

import android.animation.ValueAnimator
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class StatsAdapter : RecyclerView.Adapter<StatsAdapter.StatViewHolder>() {

    private var stats = mutableListOf<StatItem>()
    private var animatePulse = false

    class StatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: MaterialCardView = view.findViewById(R.id.statCard)
        val icon: TextView = view.findViewById(R.id.statIcon)
        val title: TextView = view.findViewById(R.id.statTitle)
        val value: TextView = view.findViewById(R.id.statValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_stat, parent, false)
        return StatViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
        val stat = stats[position]

        holder.icon.text = stat.icon
        holder.title.text = stat.title
        holder.value.text = stat.value

        // Set dynamic card colors
        val color = Color.parseColor(stat.color)
        holder.card.setCardBackgroundColor(ColorUtils.setAlphaComponent(color, 30))

        // Entrance animation for each item
        holder.card.alpha = 0f
        holder.card.translationX = 100f
        holder.card.animate()
            .alpha(1f)
            .translationX(0f)
            .setDuration(400)
            .setStartDelay(position * 100L)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()

        if (animatePulse) {
            val pulse = ValueAnimator.ofFloat(1f, 1.05f, 1f)
            pulse.duration = 300
            pulse.addUpdateListener { animator ->
                val scale = animator.animatedValue as Float
                holder.card.scaleX = scale
                holder.card.scaleY = scale
            }
            pulse.start()
        }
    }

    override fun getItemCount() = stats.size

    fun updateStats(newStats: List<StatItem>) {
        animatePulse = false
        stats.clear()
        stats.addAll(newStats)
        notifyDataSetChanged()
    }

    fun updateStatsWithAnimation(newStats: List<StatItem>) {
        animatePulse = true
        stats.clear()
        stats.addAll(newStats)
        notifyDataSetChanged()
        animatePulse = false  // reset flag after one refresh
    }
}