package nicetomeowyou.th.mobile.curculatar

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.marginRight
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
      val context = binding.root.context
        fun bind(button: ButtonKeysModel, position: Int) {
            var backgroundColor = binding.root.context.getDrawable(R.drawable.background_circle_darkgray)
            var backgroundColor2 = AppCompatResources.getDrawable(context,R.drawable.background_circle_orange)
            var backgroundColor3 = AppCompatResources.getDrawable(context,R.drawable.background_circle_lightgray)
            var backgroundColor4 = AppCompatResources.getDrawable(context,R.drawable.background_edit_text_gray_primary_corner_8)

            binding.inputKeyLayout.setOnClickListener {
                listener.onClick(position,button)
            }
            binding.textInput.text = button.text

            if (button.text == "+"||button.text == "-"||button.text == "X"||button.text == "÷"||button.text == "="){
                binding.inputKeyLayout.background = backgroundColor2
            }else if (button.text == "AC"||button.text == "+/-"||button.text == "%"){
                binding.inputKeyLayout.background = backgroundColor3
            }else{
                binding.inputKeyLayout.background = backgroundColor
                if (button.text == "0"){
                    binding.inputKeyLayout.background = backgroundColor4
                    binding.inputKeyLayout.visibility = View.INVISIBLE
                }
            }




        }
    }


}
