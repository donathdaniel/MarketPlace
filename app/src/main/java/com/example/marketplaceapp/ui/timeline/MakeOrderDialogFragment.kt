package com.example.marketplaceapp.ui.timeline

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.marketplaceapp.R
import com.example.marketplaceapp.utils.Constant
import com.example.marketplaceapp.utils.deleteQuotes
import com.google.android.material.textfield.TextInputLayout


class MakeOrderDialogFragment : DialogFragment() {

    lateinit var ownerImageView: ImageView
    lateinit var ownerNameTextView: TextView
    lateinit var productNameTextView: TextView
    lateinit var priceTextView: TextView
    lateinit var activeInactiveImageView: ImageView
    lateinit var activeInactiveTextView: TextView
    lateinit var dateTextView: TextView
    lateinit var amountTextViewLayout: TextInputLayout
    lateinit var commentTextViewLayout: TextInputLayout
    lateinit var cancelButton: Button
    lateinit var sendMyOrderButton: Button

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_make_order_dialog, container, false)

        ownerImageView = view.findViewById(R.id.owner_image_view)
        ownerNameTextView = view.findViewById(R.id.owner_name_text_view)
        productNameTextView = view.findViewById(R.id.product_name_text_view)
        priceTextView = view.findViewById(R.id.price_text_view)
        activeInactiveImageView = view.findViewById(R.id.active_inactive_image_view)
        dateTextView = view.findViewById(R.id.date_text_view)
        activeInactiveTextView = view.findViewById(R.id.active_inactive_text_view)
        amountTextViewLayout = view.findViewById(R.id.amount_text_view_layout)
        commentTextViewLayout = view.findViewById(R.id.comment_text_view_layout)
        cancelButton = view.findViewById(R.id.cancel_button)
        sendMyOrderButton = view.findViewById(R.id.send_my_order_button)


        Glide.with(context!!)
            .load(R.drawable.avatar)
            .placeholder(R.drawable.avatar)
            .error(R.drawable.avatar)
            .circleCrop()
            .into(ownerImageView)

        ownerNameTextView.text = arguments?.getString(Constant.username)?.deleteQuotes()
        productNameTextView.text = arguments?.getString(Constant.title)?.deleteQuotes()
        priceTextView.text =
            (arguments?.getString(Constant.pricePerUnit) + " " + arguments?.getString(Constant.priceType) +
                    "/" + arguments?.getString(Constant.amountType)).deleteQuotes()


        val activeInactiveColor : Int
        val activeInactiveText : String
        if (arguments?.getBoolean(Constant.isActive) == true){
            Glide.with(context!!)
                .load(R.drawable.ic_active_product)
                .placeholder(R.drawable.ic_active_product)
                .error(R.drawable.ic_active_product)
                .circleCrop()
                .into(activeInactiveImageView)

            activeInactiveColor = R.color.colorPrimary
            activeInactiveText = "Active"

        } else {
            Glide.with(context!!)
                .load(R.drawable.ic_inactive_product)
                .placeholder(R.drawable.ic_inactive_product)
                .error(R.drawable.ic_inactive_product)
                .circleCrop()
                .into(activeInactiveImageView)

            activeInactiveColor = R.color.colorHintText
            activeInactiveText = "Inactive"

            sendMyOrderButton.isClickable = false
            sendMyOrderButton.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorHintText))
        }
        activeInactiveTextView.setTextColor(ContextCompat.getColor(context!!, activeInactiveColor))
        activeInactiveTextView.text = activeInactiveText.deleteQuotes()

        productNameTextView.text = arguments?.getString(Constant.title)?.deleteQuotes()

        amountTextViewLayout.suffixText = arguments?.getString(Constant.amountType)?.deleteQuotes()

        cancelButton.setOnClickListener {
            this.dismiss()
        }

        return view
    }

}


//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//
//        val builder = AlertDialog.Builder(context)
//        builder.setTitle("Title")
//        builder.setView(R.layout.fragment_make_order_dialog)
//
//        return super.onCreateDialog(savedInstanceState)
//    }


//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
//        AlertDialog.Builder(requireContext())
//            .setMessage("naaa")
//            .setPositiveButton("ok") { _,_ -> }
//            .create()