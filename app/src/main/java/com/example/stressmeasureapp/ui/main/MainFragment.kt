package com.example.stressmeasureapp.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.stressmeasureapp.R
import com.example.stressmeasureapp.ui.result.ResultFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask
import kotlin.random.Random

class MainFragment : Fragment() {


    var randomNumber : Int? = null
    var errors : Int = 0

    private lateinit var currentView : View
    private lateinit var viewModel: MainViewModel
    private lateinit var text : TextView
    private lateinit var button : FloatingActionButton
    private lateinit var imageAim : ImageView
    private var counter = 0
    private var countDownTimer = 1000L
    lateinit var timerMask: CountDownTimer
    lateinit var numberTimer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text = view.findViewById(R.id.message)
        button = view.findViewById(R.id.floatingActionButton)
        imageAim = view.findViewById(R.id.aimIv)
        currentView = view

//        timerMask = object : CountDownTimer(20000L,950L){
//            override fun onTick(millisUntilFinished: Long) {
//                imageAim.visibility = View.INVISIBLE
//                randomNumber = Random.nextInt(0,9)
//                text.text = randomNumber.toString()
//                text.visibility = View.VISIBLE
//                onFinish()
//            }
//
//            override fun onFinish() {
//                timerNumber()
//            }
//
//        }
//        numberTimer = object : CountDownTimer(20000L,250L){
//            override fun onTick(millisUntilFinished: Long) {
//                imageAim.visibility = View.VISIBLE
//                text.visibility = View.INVISIBLE
//                counter++
//                numberTimer.onFinish()
//            }
//
//            override fun onFinish() {
//                if (counter== 225){
//                    moveToResultFragment(currentView,errors)
//                }else{
//                    timerMask()
//                }
//            }
//
//        }
//        timerMask()
        val timer = object: CountDownTimer(6000000, countDownTimer) {
            override fun onTick(millisUntilFinished: Long) {
                if(imageAim.visibility == View.VISIBLE){
                    imageAim.visibility = View.INVISIBLE
                    countDownTimer = 250L
                    randomNumber = Random.nextInt(0,10)
                    text.text = randomNumber.toString()
                    text.visibility = View.VISIBLE
                    counter++
                    Log.i("Value counter timer", counter.toString())
                }else{
                    countDownTimer = 900L
                    imageAim.visibility = View.VISIBLE
                    text.visibility = View.INVISIBLE

                }
                if (counter== 225){
                    onFinish()
                }
            }

            override fun onFinish() {
                moveToResultFragment(view,errors)
            }
        }
        timer.start()

        button.setOnClickListener {
            if (randomNumber == 3){
                errors++
            }
        }

    }


    private fun moveToResultFragment(currentView : View ,errors: Int){
        activity?.intent?.putExtra("Errors", errors)
        Navigation.findNavController(currentView).navigate(R.id.action_mainFragment_to_resultFragment)
    }

    private fun timerMask(){
        if (::numberTimer.isInitialized){
            numberTimer.cancel()
        }
        timerMask.start()

    }

    private fun timerNumber(){
        timerMask.cancel()
        numberTimer.start()
    }
}