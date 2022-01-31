package edu.us.ischool.lifecounter

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    class Player(var name: String, var count: Int) : Serializable

    private var players: Array<Player> = arrayOf(
        Player("Player 1", 0),
        Player("Player 2", 0),
        Player("Player 3", 0),
        Player("Player 4", 0)
    )

    inner class PlayerAdapter(private val context: Activity, private val data: Array<Player>): ArrayAdapter<Player>(context, R.layout.list_item) {
        override fun getItem(position: Int): Player {
            return data[position]
        }

        override fun getCount(): Int {
            return data.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = if (convertView == null) {
                val inflater = LayoutInflater.from(context)
                inflater.inflate(R.layout.list_item_layout, parent, false)
            } else {
                convertView
            }

            val titleText = view.findViewById(R.id.name) as TextView
            val countText = view.findViewById(R.id.count) as TextView
            val minus = view.findViewById(R.id.minus) as Button
            val minusFive = view.findViewById(R.id.minus_five) as Button
            val plus = view.findViewById(R.id.plus) as Button
            val plusFive = view.findViewById(R.id.plus_five) as Button

            titleText.text = data[position].name
            countText.text = data[position].count.toString()

            minus.setOnClickListener {
                var count: Int = countText.text.toString().toInt()
                count -= 1
                if (count <= 0) {
                    countText.text = "0"
                    val pos: Int = position + 1
                    val msg = "Player $pos LOSES!"
                    Snackbar.make(
                        context.findViewById(R.id.layout),
                        msg,
                        Snackbar.LENGTH_LONG
                    ).show()
                    players[position].count = 0
                } else {
                    countText.text = count.toString()
                    players[position].count = count
                }
            }

            minusFive.setOnClickListener {
                var count: Int = countText.text.toString().toInt()
                count -= 5
                if (count <= 0) {
                    countText.text = "0"
                    val pos: Int = position + 1
                    val msg = "Player $pos LOSES!"
                    Snackbar.make(
                        context.findViewById(R.id.layout),
                        msg,
                        Snackbar.LENGTH_LONG
                    ).show()
                    players[position].count = 0
                } else {
                    countText.text = count.toString()
                    players[position].count = count
                }
            }

            plus.setOnClickListener {
                var count: Int = countText.text.toString().toInt()
                count += 1
                countText.text = count.toString()
                players[position].count = count
            }

            plusFive.setOnClickListener {
                var count: Int = countText.text.toString().toInt()
                count += 5
                countText.text = count.toString()
                players[position].count = count
            }

            return view
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            players = savedInstanceState.getSerializable("players") as Array<Player>
        }

        val listView = findViewById<ListView>(R.id.listView)
        val adapter = PlayerAdapter(this, players)
        listView.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("players", players)
    }
}