package com.kotlinpermission

import android.Manifest
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlinpermission.databinding.FragmentHelloBinding
import com.kotlinpermissions.PermissionStatus
import com.kotlinpermissions.allGranted
import com.kotlinpermissions.anyPermanentlyDenied
import com.kotlinpermissions.anyShouldShowRationale
import com.kotlinpermissions.extension.permissionsBuilder
import com.kotlinpermissions.request.PermissionRequest


internal class HelloFragment : Fragment(), PermissionRequest.Listener {

    private lateinit var binding: FragmentHelloBinding

    private lateinit var myContext: Context

    private val request by lazy {
        permissionsBuilder(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.SEND_SMS
        ).build()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHelloBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        request.addListener(this)
        request.addListener {
            if (it.anyPermanentlyDenied()) {
//                Toast.makeText(requireContext(), R.string.additional_listener_msg, Toast.LENGTH_SHORT).show()
                myContext.showToast(R.string.additional_listener_msg.toString())
            }
        }

       binding.btnTestFragmentPermissions.setOnClickListener {
            request.send()
        }

    }

    override fun onPermissionsResult(result: List<PermissionStatus>) {
        val context = requireContext()
        when {
            result.anyPermanentlyDenied() -> context.showPermanentlyDeniedDialog(result, "Please allowed Permission")
            result.anyShouldShowRationale() -> context.showRationaleDialog(result, request)
            result.allGranted() -> context.showGrantedToast(result)
        }
    }


}