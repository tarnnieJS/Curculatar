package nicetomeowyou.th.mobile.curculatar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.recyclerview.widget.GridLayoutManager
import nicetomeowyou.th.mobile.curculatar.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var gestureDetector: GestureDetector


    private val buttonKeysList = mutableListOf<ButtonKeysModel>()

    //tarn ตัวจริง
    //hello meow


    private val keyList = mutableListOf<String>(
        "AC",
        "+/-",
        "%",
        "÷",
        "7",
        "8",
        "9",
        "X",
        "4",
        "5",
        "6",
        "-",
        "1",
        "2",
        "3",
        "+",
        "0",
        "0",
        ".",
        "="
    )
    private val isOperatorList = keyList.map { it in setOf("÷", "X", "-", "+", "=") }

    var firstNumber = ""
    var secondNumber = ""
    var isInputSecondNumber = false
    var isUseOperator = false
    var isUseDot = false
    var operationInUse = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.editTextInputNumberDefult.setText("0")



        for (i in keyList.indices) {
            val data =
                ButtonKeysModel(text = keyList[i], isOperator = isOperatorList[i], false, false)
            buttonKeysList.add(data)
        }
//        binding.editTextInputNumber.setText("0")

        binding.editTextInputNumber.isCursorVisible = false
        binding.editTextInputNumber.isFocusable = false
        binding.editTextInputNumberSecond.isCursorVisible = false
        binding.editTextInputNumberSecond.isFocusable = false
        binding.buttonZero.setOnClickListener {
            val inputConnection: InputConnection =
                binding.editTextInputNumber.onCreateInputConnection(EditorInfo())
            if (binding.editTextInputNumber.text?.length ?: 0 > 0 ) {
                inputConnection.commitText("0", 1)
            }
//            val text = binding.editTextInputNumber.text
//            if (binding.editTextInputNumber.text.isNotEmpty()) {
//                binding.editTextInputNumber.setSelection(text.length - 1)
//                val lastChar = text.substring(text.length - 1)
//                if (lastChar != "0") {
//                    val inputConnection: InputConnection =
//                        binding.editTextInputNumber.onCreateInputConnection(
//                            EditorInfo()
//                        )
//                    inputConnection.commitText("0", 1)
//
//                }
//            }
        }
        val keysListAdapter = ButtonAdapter(buttonKeysList, object : ButtonAdapter.OnClickListener {
            override fun onClick(position: Int, model: ButtonKeysModel) {
                if (model.text == ".") {
                    binding.editTextInputNumberDefult.visibility = View.INVISIBLE
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
                    binding.editTextInputNumberDefult.visibility = View.VISIBLE
                    binding.editTextInputNumber.text.clear()
                    isUseDot = false
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
                    if (operationInUse != "X"){
                        operationInUse = "X"
                        isUseOperator = true
                    }else{
                        if (secondNumber.isNotEmpty() && firstNumber.isNotEmpty()) {
//                            Log.e("ggez", binding.editTextInputNumber.text.toString())
//                            val second = secondNumber.toDouble()
//                            firstNumber = (second * firstNumber.toDouble()).toString()
//                            binding.editTextInputNumber.visibility = View.VISIBLE
//                            binding.editTextInputNumberSecond.visibility = View.INVISIBLE
//                            binding.editTextInputNumberSecond.text.clear()
//                            binding.editTextInputNumber.setText(formatResult(firstNumber.toDouble()))
                            Log.e("ggez", binding.editTextInputNumber.text.toString())
                            val second = secondNumber.toDouble()
                            val result = calculate(firstNumber.toDouble(),second,'*')
                            binding.editTextInputNumber.visibility = View.VISIBLE
                            binding.editTextInputNumberSecond.visibility = View.INVISIBLE
                            binding.editTextInputNumberSecond.text.clear()
                            binding.editTextInputNumber.setText(formatResult(result))

                        }

                    }

       //todo numpad
                } else if (!model.isOperator) {
                    binding.editTextInputNumberDefult.visibility = View.INVISIBLE
                    if (firstNumber.isNotEmpty()) {
                        binding.editTextInputNumber.visibility = View.INVISIBLE
                        binding.editTextInputNumberSecond.visibility = View.VISIBLE
                        val inputConnection: InputConnection =
                            binding.editTextInputNumberSecond.onCreateInputConnection(
                                EditorInfo()
                            )
                        inputConnection.commitText(model.text, 1)
                        secondNumber = binding.editTextInputNumberSecond.text.toString()

                    }  else {
                        val inputConnection: InputConnection =
                            binding.editTextInputNumber.onCreateInputConnection(
                                EditorInfo()
                            )
                        inputConnection.commitText(model.text, 1)

                    }


                } else {
                    when (model.text) {
                        "+" -> {
                            isUseOperator = true
                            operationInUse = "+"
                            firstNumber = binding.editTextInputNumber.text.toString()
                            if (secondNumber.isNotEmpty() && firstNumber.isNotEmpty()) {
                                Log.e("ggez", binding.editTextInputNumber.text.toString())
                                val second = secondNumber.toDouble()
                                firstNumber = (second + firstNumber.toDouble()).toString()
                                binding.editTextInputNumber.visibility = View.VISIBLE
                                binding.editTextInputNumberSecond.visibility = View.INVISIBLE
                                binding.editTextInputNumber.setText(formatResult(firstNumber.toDouble()))
                            }

                        }

                        "-" -> {

                        }

                        "÷" -> {
                            Log.e("ggez", binding.editTextInputNumber.text.toString())
                        }

                        "=" -> {
                            summaryValue()
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
        binding.editTextInputNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    Log.e("ss",s.length.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })


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
                val text2 = binding.editTextInputNumberSecond.text

                if (abs(distanceX) > abs(distanceY)) {
                    if (abs(distanceX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (distanceX > 0) {
                            if (text.isNotEmpty()) {
                                binding.editTextInputNumber.setText(
                                    text.subSequence(
                                        0,
                                        text.length - 1
                                    )
                                )
                                binding.editTextInputNumber.setSelection(text.length - 1)
                                val lastChar = text.substring(text.length - 1)
                                if (lastChar == ".") {
                                    isUseDot = false
                                }
                            }
                            if (text2.isNotEmpty()) {
                                binding.editTextInputNumberSecond.setText(
                                    text2.subSequence(
                                        0,
                                        text2.length - 1
                                    )
                                )
                                binding.editTextInputNumberSecond.setSelection(text2.length - 1)
                                val lastChar = text2.substring(text2.length - 1)
                                if (lastChar == ".") {
                                    isUseDot = false
                                }
                            }
                        } else {
                            if (text.isNotEmpty()) {
                                binding.editTextInputNumber.setText(
                                    text.subSequence(
                                        0,
                                        text.length - 1
                                    )
                                )
                                binding.editTextInputNumber.setSelection(text.length - 1)
                                val lastChar = text.substring(text.length - 1)
                                if (lastChar == ".") {
                                    isUseDot = false
                                }
                            }
                            if (text2.isNotEmpty()) {
                                binding.editTextInputNumberSecond.setText(
                                    text2.subSequence(
                                        0,
                                        text2.length - 1
                                    )
                                )
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

    private fun summaryValue() {
        Log.e("first", firstNumber)
        Log.e("second", secondNumber)
        Log.e("op", operationInUse)
        if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty()) {
            when (operationInUse) {
                "X" -> {
                    Log.e("ggez", binding.editTextInputNumber.text.toString())
                    val second = secondNumber.toDouble()
                    val result = calculate(firstNumber.toDouble(),second,'*')
                    binding.editTextInputNumber.visibility = View.VISIBLE
                    binding.editTextInputNumberSecond.visibility = View.INVISIBLE
                    binding.editTextInputNumberSecond.text.clear()
                    binding.editTextInputNumber.setText(formatResult(result))

                }

                "+" -> {
                    isUseOperator = true
                    operationInUse = "+"
                    firstNumber = binding.editTextInputNumber.text.toString()
                    if (secondNumber.isNotEmpty() && firstNumber.isNotEmpty()) {
                        Log.e("ggez", binding.editTextInputNumber.text.toString())
                        val second = secondNumber.toDouble()
                        firstNumber = (second + firstNumber.toDouble()).toString()
                        binding.editTextInputNumber.visibility = View.VISIBLE
                        binding.editTextInputNumberSecond.visibility = View.INVISIBLE
                        binding.editTextInputNumber.setText(formatResult(firstNumber.toDouble()))
                    }


                }

                "-" -> {
                    val text = binding.editTextInputNumber.text
                    binding.editTextInputNumber.setSelection(text.length - 1)
                    val lastChar = text.substring(text.length - 1)
                    Log.e("gg", lastChar)
                    Log.e("gg2", binding.editTextInputNumber.text.length.toString())

                }

                "÷" -> {

                }

            }
        }

    }


//    private fun setInitZero(){
//        val second = (0.0).toString()
//        binding.editTextInputNumber.visibility = View.VISIBLE
//        binding.editTextInputNumberSecond.visibility = View.INVISIBLE
//        binding.editTextInputNumberSecond.text.clear()
//        binding.editTextInputNumber.setText(formatResult(second.toDouble()))
//
//    }
    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    fun formatResult(result: Double): String {

        return if (result == result.toInt().toDouble()) {
//            val formattedString = NumberFormat.getNumberInstance(Locale.US).format(number)
            result.toInt().toString()
        } else {
            String.format("%.2f",result )
        }
    }

    fun calculate(num1: Double, num2: Double, operator: Char): Double {
        return when (operator) {
            '+' -> num1 + num2
            '-' -> num1 - num2
            '*' -> num1 * num2
            '/' -> num1 / num2
            else -> throw IllegalArgumentException("$operator")
        }
    }
    fun formatNumberWithThousandSeparator(number: Long): String {
        val formatter = NumberFormat.getNumberInstance(Locale.US)
        return formatter.format(number)
    }


}