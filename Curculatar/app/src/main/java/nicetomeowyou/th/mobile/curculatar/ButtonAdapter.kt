package nicetomeowyou.th.mobile.curculatar

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import nicetomeowyou.th.mobile.curculatar.databinding.ItemKeysBinding


class ButtonAdapter(  private val buttonList: List<ButtonKeysModel>,val listener: OnClickListener) : RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemKeysBinding.inflate(inflater,parent,false)
        return ButtonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val buttonList = buttonList[position]
        holder.bind(buttonList,position)
    }

    override fun getItemCount(): Int {
        return buttonList.size
    }
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {
        fun onClick(position: Int, model: ButtonKeysModel)
    }


  inner  class ButtonViewHolder(private val binding: ItemKeysBinding) : RecyclerView.ViewHolder(binding.root) {
      private val context: Context = binding.root.context
        fun bind(button: ButtonKeysModel, position: Int) {
            var backgroundColorGray = binding.root.context.getDrawable(R.drawable.background_circle_darkgray)
            var backgroundColor2 = AppCompatResources.getDrawable(context,R.drawable.background_circle_orange)
            var backgroundColor3 = AppCompatResources.getDrawable(context,R.drawable.background_circle_lightgray)
            var backgroundColor4 = AppCompatResources.getDrawable(context,R.drawable.background_edit_text_gray_primary_corner_8)
            var white  = AppCompatResources.getDrawable(context,R.drawable.background_circle_write)
            var blackText = AppCompatResources.getColorStateList(context,R.color.yellow)
            var whiteText = AppCompatResources.getColorStateList(context,R.color.white)

            binding.inputKeyLayout .setOnTouchListener { v, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                                v.background = white

                        }
                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                            if (!button.isOperator){
                                if (button.text == "AC") {
                                    v.background = backgroundColor3
                                    listener.onClick(position, button)
                                } else if (button.text == "%") {
                                    v.background = backgroundColor3
                                    listener.onClick(position, button)
                                } else if (button.text == "+/-") {
                                    v.background = backgroundColor3
                                    listener.onClick(position, button)
                                } else {
                                    v.background = backgroundColorGray
                                    listener.onClick(position, button)
                                }


                            }else{
                                if (button.text == "+"){
                                    v.background = backgroundColor2
                                    listener.onClick(position,button)
                                }
                                if (button.text == "-"){
                                    v.background = backgroundColor2
                                    listener.onClick(position,button)
                                }
                                if (button.text == "X"){
                                    v.background = backgroundColor2
                                    listener.onClick(position,button)
                                }
                                if (button.text == "÷"){
                                    v.background = backgroundColor2
                                    listener.onClick(position,button)
                                }
                                if (button.text == "="){
                                    v.background = backgroundColor2
                                    listener.onClick(position,button)
                                }



                            }

                        }
                    }
                    true
                }







            binding.inputKeyLayout.setOnClickListener {

//                if (binding.inputKeyLayout.background == backgroundColor2){
//                    binding.inputKeyLayout.background = white
//                    binding.textInput.setTextColor(blackText)
//                }
//                if (binding.inputKeyLayout.background == backgroundColor3) {
//                    binding.inputKeyLayout.background = white
//                    binding.textInput.setTextColor(blackText)
//                }

                if (button.text == "X"){


                    Log.e("jj",button.text)
                    Log.e("jj",button.isOperator.toString())

//                    val backgroundName = context.resources.getResourceEntryName(binding.inputKeyLayout.background.constantState?.resourceId ?: 0)
//                    Log.d(TAG, "Background drawable name is $backgroundName")



//                    binding.inputKeyLayout.background = white
//                    binding.textInput.setTextColor(blackText)
                }

            }
            binding.textInput.text = button.text
            if (button.text == "+"||button.text == "-"||button.text == "X"||button.text == "÷"||button.text == "="){
                binding.inputKeyLayout.background = backgroundColor2
            }else if (button.text == "AC"||button.text == "+/-"||button.text == "%"){
                binding.inputKeyLayout.background = backgroundColor3
            }else{
                binding.inputKeyLayout.background = backgroundColorGray
                if (button.text == "0"){
                    binding.inputKeyLayout.background = backgroundColor4
                    binding.inputKeyLayout.visibility = View.INVISIBLE
                }
            }





        }
    }


}
