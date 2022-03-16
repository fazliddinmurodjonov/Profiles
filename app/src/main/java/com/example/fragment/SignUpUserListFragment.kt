package com.example.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.adapter.UserRecyclerView
import com.example.androidlesson5databasetask.R
import com.example.androidlesson5databasetask.databinding.CustomDialogBinding
import com.example.androidlesson5databasetask.databinding.FragmentSignUpUserListBinding
import com.example.database.UserDatabase
import com.example.model.User
import com.google.android.material.bottomsheet.BottomSheetDialog


class SignUpUserListFragment : Fragment(R.layout.fragment_sign_up_user_list) {
    private val binding: FragmentSignUpUserListBinding by viewBinding()
    lateinit var userDatabase: UserDatabase
    lateinit var userRecyclerView: UserRecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding)
        {
            userDatabase = UserDatabase(requireContext())
            val userList = userDatabase.getAllUser()
            userRecyclerView = UserRecyclerView(userList)
            userRV.adapter = userRecyclerView

            userRecyclerView.setOnItemClick(object : UserRecyclerView.OnItemClick {
                override fun onClick(user: User) {
                    val dialog =
                        BottomSheetDialog(requireContext(), R.style.MyBottomSheetDialogTheme)
                    val view = CustomDialogBinding.inflate(LayoutInflater.from(requireContext()),
                        null,
                        false)
                    dialog.setContentView(view.root)
                    view.userName.text = user.name
                    view.userPhoneNumber.text = user.phoneNumber
                    view.userProfileImage.setImageURI(Uri.parse(user.imagePath))
                    view.call.setOnClickListener {
                        val callIntent = Intent(Intent.ACTION_CALL)
                        callIntent.data = Uri.parse("tel:${user.phoneNumber}")
                        startActivity(callIntent)
                    }

                    dialog.show()
                }
            })

        }
    }

}