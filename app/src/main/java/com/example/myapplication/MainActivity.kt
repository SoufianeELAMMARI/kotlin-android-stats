package com.example.myapplication

import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var greetingCard: MaterialCardView
    private lateinit var statsRecyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private val statsAdapter = StatsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupAnimations()
        loadDashboardData()
    }

    private fun initializeViews() {
        greetingCard = findViewById(R.id.greetingCard)
        statsRecyclerView = findViewById(R.id.statsRecyclerView)
        fab = findViewById(R.id.fab)

        statsRecyclerView.layoutManager = LinearLayoutManager(this)
        statsRecyclerView.adapter = statsAdapter

        fab.setOnClickListener {
            animateFabClick()
        }
    }

    private fun setupAnimations() {
        // Staggered entrance animations
        greetingCard.alpha = 0f
        greetingCard.translationY = 100f

        greetingCard.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(600)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()

        // Animate RecyclerView items
        statsRecyclerView.alpha = 0f
        statsRecyclerView.translationY = 150f

        statsRecyclerView.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(800)
            .setStartDelay(200)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }

    private fun animateFabClick() {
        fab.animate()
            .scaleX(0.9f)
            .scaleY(0.9f)
            .setDuration(100)
            .withEndAction {
                fab.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(100)
                    .start()
            }
            .start()

        // Refresh data with animation
        refreshDashboardData()
    }

    private fun loadDashboardData() {
        val stats = generateSampleStats()
        statsAdapter.updateStats(stats)
    }

    private fun refreshDashboardData() {
        val stats = generateSampleStats()
        statsAdapter.updateStatsWithAnimation(stats)
    }

    private fun generateSampleStats(): List<StatItem> {
        return listOf(
            StatItem("Steps Today", "${Random.nextInt(5000, 12000)}", "👟", "#4CAF50"),
            StatItem("Calories", "${Random.nextInt(1200, 2500)}", "🔥", "#FF5722"),
            StatItem("Water Intake", "${Random.nextInt(6, 12)} glasses", "💧", "#2196F3"),
            StatItem("Sleep", "${Random.nextInt(6, 9)}h ${Random.nextInt(0, 59)}m", "😴", "#9C27B0"),
            StatItem("Focus Time", "${Random.nextInt(2, 8)}h ${Random.nextInt(0, 59)}m", "🎯", "#FF9800"),
            StatItem("Mood Score", "${Random.nextInt(7, 10)}/10", "😊", "#4CAF50")
        )
    }
}