package com.skillbox.github.ui.repository_detail

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.skillbox.github.R
import com.skillbox.github.ui.repository_list.RepositoryViewModel
import kotlinx.android.synthetic.main.fragment_detail_repository.*

class DetailRepositoryFragment : Fragment(R.layout.fragment_detail_repository) {
    private val args: DetailRepositoryFragmentArgs by navArgs()

    private val viewModel: StartViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val avatar = args.avatarLink
        Glide.with(avatarImageViewDetail)
            .load(avatar)
            .placeholder(R.drawable.ic_account)
            .into(avatarImageViewDetail)

        val name = args.name
        nameRepositoryTextViewDetail.text = name

        val id = args.id.toString()
        idRepositoryTextViewDetail.text = id

        val loginOwner = args.loginOwner
        loginOwnerRepTextViewDetail.text = loginOwner

        checkStarByUser(loginOwner, name)

        starButtonDetailRep.setOnClickListener {
            if (viewModel.checkStarLD.value!!) {
                deleteStar(loginOwner, name)
            } else {
                setStar(loginOwner, name)
            }
        }
    }


    private fun deleteStar(loginOwner: String, name: String) {
        viewModel.deleteStar(loginOwner, name)
        testObserveStar()
    }


    private fun checkStarByUser(loginOwner: String, name: String) {
        viewModel.checkStar(loginOwner, name)
        testObserveStar()
    }

    private fun setStar(loginOwner: String, name: String) {
        viewModel.setStar(loginOwner, name)
        testObserveStar()
    }

    private fun testObserveStar() {
        viewModel.checkStarLD.observe(viewLifecycleOwner) { bool ->
            when (bool){
                true -> starButtonDetailRep.setColorFilter(Color.argb(255, 255, 255, 0))
                false -> starButtonDetailRep.setColorFilter(Color.argb(125, 125, 125, 125))
            }
        }
    }

}