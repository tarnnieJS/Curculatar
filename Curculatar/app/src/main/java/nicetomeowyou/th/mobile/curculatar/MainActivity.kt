package nicetomeowyou.th.mobile.curculatar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.recyclerview.widget.GridLayoutManager
import nicetomeowyou.th.mobile.curculatar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()  {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var gestureDetector: GestureDetector


    private val buttonKeysList = mutableListOf<ButtonKeysModel>()
    private val keyList = mutableListOf<String>("AC","+/-","%","÷","7","8","9","X","4","5","6","-","1","2","3","+","0","0",".","=")
    private val isOperatorList = keyList.map { it in setOf("÷", "X", "-", "+", "=") }

    var result = ""
    var firstNumber = ""
    var secondNumber = ""
    var isInputSecondNumber = false
    var isUseOperator = false
    var isUseDot = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        for (i in keyList.indices) {
            val data =
                ButtonKeysModel(text = keyList[i], isOperator = isOperatorList[i], false, false)
            buttonKeysList.add(data)
        }

        binding.editTextInputNumber.isCursorVisible = false
        binding.editTextInputNumber.isFocusable = false
        binding.editTextInputNumberSecond.isCursorVisible = false
        binding.editTextInputNumberSecond.isFocusable = false
        binding.editTextInputNumber.setText(result)
        binding.buttonZero.setOnClickListener {

            val text = binding.editTextInputNumber.text
            if (binding.editTextInputNumber.text.length == 1) {
                binding.editTextInputNumber.setSelection(text.length - 1)
                val lastChar = text.substring(text.length - 1)
                if (lastChar != "0") {
                    val inputConnection: InputConnection =
                        binding.editTextInputNumber.onCreateInputConnection(
                            EditorInfo()
                        )
                    inputConnection.commitText("0", 1)

                }
            } else {
                val inputConnection: InputConnection =
                    binding.editTextInputNumber.onCreateInputConnection(
                        EditorInfo()
                    )
                inputConnection.commitText("0", 1)
            }


        }
        val keysListAdapter = ButtonAdapter(buttonKeysList, object : ButtonAdapter.OnClickListener {
            override fun onClick(position: Int, model: ButtonKeysModel) {
                if (model.text == ".") {

                    if (binding.editTextInputNumber.text.isNotEmpty() && !isUseDot) {
                        isUseDot = true

                        val inputConnection: InputConnection =
                            binding.editTextInputNumber.onCreateInputConnection(
                                EditorInfo()
                            )
                        inputConnection.commitText(".", 1)
                    } else if (!isUseDot) {
                        val inputConnection: InputConnection =
                            binding.editTextInputNumber.onCreateInputConnection(
                                EditorInfo()
                            )
                        isUseDot = true
                        inputConnection.commitText("0.", 1)
                    }


                } else if (model.text == "AC") {
                    isUseDot = false
                    binding.editTextInputNumber.text.clear()
                    binding.editTextInputNumberSecond.text.clear()
                    firstNumber = ""
                    secondNumber = ""
                    isInputSecondNumber = false
                    binding.editTextInputNumber.visibility = View.VISIBLE
                    binding.editTextInputNumberSecond.visibility = View.INVISIBLE
                } else if (model.text == "%") {
                    isUseOperator = true
                } else if (model.text == "X") {
                    isUseOperator = true
                    firstNumber = binding.editTextInputNumber.text.toString()
                    if (secondNumber.isNotEmpty() && firstNumber.isNotEmpty()) {
                        Log.e("ggez", binding.editTextInputNumber.text.toString())
                        val second = secondNumber.toDouble()
                        firstNumber = (second * firstNumber.toDouble()).toString()
                        binding.editTextInputNumber.visibility = View.VISIBLE
                        binding.editTextInputNumberSecond.visibility = View.INVISIBLE
                        binding.editTextInputNumberSecond.text.clear()

                        binding.editTextInputNumber.setText(formatResult(firstNumber.toDouble()))
                        secondNumber = "1"
                    }
                    if (isUseOperator) {
                        Log.e("use", model.text.toString())


                    }
                    Log.e("use", isUseOperator.toString())


                } else if (!model.isOperator) {
                    if (firstNumber.isNotEmpty()) {
                        binding.editTextInputNumber.visibility = View.INVISIBLE
                        binding.editTextInputNumberSecond.visibility = View.VISIBLE
                        val inputConnection: InputConnection =
                            binding.editTextInputNumberSecond.onCreateInputConnection(
                                EditorInfo()
                            )
                        inputConnection.commitText(model.text, 1)
                        secondNumber = binding.editTextInputNumberSecond.text.toString()


                    } else {
                        val inputConnection: InputConnection =
                            binding.editTextInputNumber.onCreateInputConnection(
                                EditorInfo()
                            )
                        inputConnection.commitText(model.text, 1)
                    }


                } else {
                    when (model.text) {
                        "+" -> {
                            val firstInput = binding.editTextInputNumber.text
                            if (firstInput.isNotEmpty()) {
                                firstInput.toString().toDouble()


                            }

                        }

                        "-" -> {
                            Log.e("ggez", binding.editTextInputNumber.text.toString())
                        }

                        "÷" -> {
                            Log.e("ggez", binding.editTextInputNumber.text.toString())
                        }

                        "=" -> {
                            Log.e("ggez", binding.editTextInputNumber.text.toString())
                        }

                        else -> {
                            // Handle other cases if needed
                        }
                    }


                }


            }
        })

        binding.recyclerView.apply {
            val layoutManager = GridLayoutManager(context, 4)
            this.layoutManager = layoutManager
            isNestedScrollingEnabled = true
            adapter = keysListAdapter
        }

        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (e1 == null || e2 == null) {
                    return false
                }

                val distanceX = e2.x - e1.x
                val distanceY = e2.y - e1.y
                val text = binding.editTextInputNumber.text
                val text2  = binding.editTextInputNumberSecond.text

                if (Math.abs(distanceX) > Math.abs(distanceY)) {
                    if (Math.abs(distanceX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (distanceX > 0) {
                            if (text.isNotEmpty()) {
                                binding.editTextInputNumber.setText(text.subSequence(0, text.length - 1))
                                binding.editTextInputNumber.setSelection(text.length - 1)
                                val lastChar = text.substring(text.length - 1)
                                if (lastChar == ".") {
                                    isUseDot = false
                                }
                            }
                            if (text2.isNotEmpty()) {
                                binding.editTextInputNumberSecond.setText(text2.subSequence(0, text2.length - 1))
                                binding.editTextInputNumberSecond.setSelection(text2.length - 1)
                                val lastChar = text2.substring(text2.length - 1)
                                if (lastChar == ".") {
                                    isUseDot = false
                                }
                            }
                        } else {
                            if (text.isNotEmpty()) {
                                binding.editTextInputNumber.setText(text.subSequence(0, text.length - 1))
                                binding.editTextInputNumber.setSelection(text.length - 1)
                                val lastChar = text.substring(text.length - 1)
                                if (lastChar == ".") {
                                    isUseDot = false
                                }
                            }
                            if (text2.isNotEmpty()) {
                                binding.editTextInputNumberSecond.setText(text2.subSequence(0, text2.length - 1))
                                binding.editTextInputNumberSecond.setSelection(text2.length - 1)
                                val lastChar = text2.substring(text2.length - 1)
                                if (lastChar == ".") {
                                    isUseDot = false
                                }
                            }
                        }
                        return true
                    }
                }
                return false
            }
        })

        val editText = binding.editTextInputNumber
        editText.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            false
        }
        val editText2 = binding.editTextInputNumberSecond
        editText2.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            false
        }



    }

    private fun calCulatorCore(operation: String, firstNumber: String) {




    }

    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    fun formatResult(result: Double): String {
        return if (result == result.toInt().toDouble()) {
            result.toInt().toString()
        } else {
            String.format("%.2f", result)
        }
    }




}