package com.example.marketplaceapp.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.marketplaceapp.BazaarSharedPreference
import com.example.marketplaceapp.R
import com.example.marketplaceapp.model.Product
import com.example.marketplaceapp.ui.timeline.TimelineViewModel
import com.example.marketplaceapp.ui.timeline.adapter.TimelineAdapter
import com.example.marketplaceapp.utils.Constant
import com.example.marketplaceapp.utils.deleteQuotes
import com.example.marketplaceapp.utils.getToken
import com.example.marketplaceapp.utils.getUsername

class DetailsFragment : Fragment() {

    private val detailsViewModel: DetailsViewModel by viewModels()

    lateinit var ownerImageView: ImageView
    lateinit var ownerNameTextView: TextView
    lateinit var productNameTextView: TextView
    lateinit var priceTextView: TextView
    lateinit var activeInactiveImageView: ImageView
    lateinit var activeInactiveTextView: TextView
    lateinit var dateTextView: TextView
    lateinit var productDescriptionTextView: TextView

    lateinit var editImageView : ImageView

    private lateinit var progressBar : ProgressBar

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)

        progressBar = view.findViewById(R.id.progress_bar)

        ownerImageView = view.findViewById(R.id.owner_image_view)
        ownerNameTextView = view.findViewById(R.id.owner_name_text_view)
        productNameTextView = view.findViewById(R.id.product_name_text_view)
        priceTextView = view.findViewById(R.id.price_text_view)
        activeInactiveImageView = view.findViewById(R.id.active_inactive_image_view)
        activeInactiveTextView = view.findViewById(R.id.active_inactive_text_view)
        dateTextView = view.findViewById(R.id.date_text_view)
        productDescriptionTextView = view.findViewById(R.id.product_description_text_view)

        editImageView = view.findViewById(R.id.edit_image_view)

        Glide.with(context!!)
            .load(R.drawable.avatar)
            .placeholder(R.drawable.avatar)
            .error(R.drawable.avatar)
            .circleCrop()
            .into(ownerImageView)

        detailsViewModel.getProductResponse.observe(
            viewLifecycleOwner,
            { response ->

                if (response.isSuccessful) {
                    progressBar.visibility = View.GONE

                    Log.d("getProducts", response.body().toString())

                    ownerNameTextView.text = response.body()?.products!![0].username.deleteQuotes()
                    productNameTextView.text = response.body()?.products!![0].title.deleteQuotes()
                    priceTextView.text =
                        (response.body()?.products!![0].pricePerUnit + " " + response.body()?.products!![0].priceType + "/" + response.body()?.products!![0].amountType).deleteQuotes()

                    val username = BazaarSharedPreference.sharedPref.getUsername()
                    if(username != response.body()!!.products[0].username){
                        editImageView.visibility = View.GONE
                    }

                    val activeInactiveColor: Int
                    val activeInactiveText: String
                    if (response.body()?.products!![0].isActive) {
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
                    }

                    activeInactiveTextView.setTextColor(
                        ContextCompat.getColor(
                            activeInactiveImageView.context,
                            activeInactiveColor
                        )
                    )
                    activeInactiveTextView.text = activeInactiveText.deleteQuotes()
                    productDescriptionTextView.text =  response.body()!!.products[0].description.deleteQuotes()

                } else {
                    Log.d("getProducts", response.code().toString())
                }
            })

        val accessToken: String = BazaarSharedPreference.sharedPref.getToken()
        Log.d("accessToken", "get $accessToken")

        val filter = "{\"product_id\": \"" + arguments?.getString(Constant.productId) + "\"}"
        detailsViewModel.getProducts(accessToken, null, filter)

        return view
    }


}