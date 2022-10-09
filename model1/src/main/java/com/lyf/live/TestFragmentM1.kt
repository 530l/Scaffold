package com.lyf.live

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.lyf.base.BaseFragment
import com.lyf.live.viewmodel.TestViewModel

class TestFragmentM1 : BaseFragment() {

//    val activityViewModel: TestViewModel by activityViewModels() //获取当前依附在activity的viewmodel
//
//    val viewModel: TestViewModel by viewModels()//获取当前fragment特有的 viewmodel
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        //注意，
//        viewModel.getTestM1Result().observe(viewLifecycleOwner) {
//
//        }
//
//        activityViewModel.getTestM1Result().observe(requireActivity()) {
//
//        }
//    }
}