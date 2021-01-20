package com.aoinc.w4d1_class_kotlinjikan.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.aoinc.w4d1_class_kotlinjikan.R
import com.aoinc.w4d1_class_kotlinjikan.model.JikanResult
import com.aoinc.w4d1_class_kotlinjikan.view.adapter.JikanRecyclerViewAdapter
import com.aoinc.w4d1_class_kotlinjikan.viewmodel.JikanViewModel
import org.w3c.dom.Text
import java.util.*

class MainActivity : AppCompatActivity() {

    //implementation "androidx.activity:activity-ktx:1.1.0"
    private val viewModel: JikanViewModel by viewModels();

    private lateinit var searchEditText : EditText

    private lateinit var jikanRecyclerView : RecyclerView
    private val jikanAdapter: JikanRecyclerViewAdapter = JikanRecyclerViewAdapter(mutableListOf())

    private var searchTimer: Timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchEditText = findViewById(R.id.search_text_editText)

        jikanRecyclerView = findViewById(R.id.jikan_recyclerView)
        jikanRecyclerView.adapter = jikanAdapter

        val itemSnapHelper : SnapHelper = LinearSnapHelper()
        itemSnapHelper.attachToRecyclerView(jikanRecyclerView)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // not used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // not used
            }

            override fun afterTextChanged(s: Editable?) {
                searchTimer.cancel()
                searchTimer = Timer()

                searchTimer.schedule(
                    object: TimerTask(){
                        override fun run() {
                            Log.d("TAG_X", "$s")
                            viewModel.searchJikan(s.toString())
                        }
                    }, 2000
                )
            }

        })

        viewModel.jikanLiveData.observe(this, Observer {
            Log.d("TAG_X", "Results obtained... ${it.size}")
            jikanAdapter.updateJikanList(it)
        })

//        viewModel.searchJikan("Goku")
    }
}