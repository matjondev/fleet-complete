package com.reactive.template.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProviders
import androidx.viewbinding.ViewBinding
import com.reactive.template.R
import com.reactive.template.ui.activities.MainActivity
import com.reactive.template.utils.extensions.hideKeyboard
import com.reactive.template.utils.preferences.SharedManager
import org.koin.android.viewmodel.ext.android.viewModel


abstract class BaseFragment<V : ViewBinding> : Fragment() {

    protected lateinit var mainActivity: MainActivity
    protected lateinit var sharedManager: SharedManager
    protected val viewModel by viewModel<BaseViewModel>()
    protected var enableCustomBackPress = false
    protected lateinit var binding: V
    abstract fun getBinding(inflater: LayoutInflater): V

    override fun onAttach(context: Context) {
        mainActivity = (requireActivity() as MainActivity)
        sharedManager = mainActivity.sharedManager
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getBinding(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialize()

        setFocus(view)

        initClicks()

        observe()
    }

    abstract fun initialize()

    fun addFragment(
        fragment: Fragment,
        addBackStack: Boolean = true,
        @IdRes id: Int = parentLayoutId(),
        tag: String = fragment::class.java.simpleName
    ) {
        showProgress(false)
        hideKeyboard()
        activity?.supportFragmentManager?.commit(allowStateLoss = true) {
            if (addBackStack && !fragment.isAdded) addToBackStack(tag)
            setCustomAnimations(
                R.anim.enter_from_bottom,
                R.anim.exit_to_top,
                R.anim.enter_from_top,
                R.anim.exit_to_bottom
            )
            add(id, fragment)
        }
    }

    fun replaceFragment(fragment: Fragment, @IdRes id: Int = parentLayoutId()) {
        hideKeyboard()
        showProgress(false)
        activity?.supportFragmentManager?.commit(allowStateLoss = true) {
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            replace(id, fragment)
        }
    }

    fun finishFragment() {
        hideKeyboard()
        showProgress(false)
        activity?.supportFragmentManager?.popBackStackImmediate()
    }

    fun popInclusive(name: String? = null, flags: Int = FragmentManager.POP_BACK_STACK_INCLUSIVE) {
        hideKeyboard()
        showProgress(false)
        activity?.supportFragmentManager?.popBackStackImmediate(name, flags)
    }

    protected open fun onFragmentBackButtonPressed() {
    }

    protected open fun observe() {
    }

    protected open fun initClicks() {
    }

    protected fun showProgress(show: Boolean) {
        mainActivity.showProgress(show)
    }

    protected fun hideKeyboard() {
        view?.let {
            val imm =
                it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    protected fun showKeyboard(editText: EditText) {
        view?.let {
            editText.requestFocus()
            val imm =
                it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun setFocus(view: View) {
        view.apply {
            isFocusableInTouchMode = true
            requestFocus()
            setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (enableCustomBackPress) onFragmentBackButtonPressed()
                    else activity?.onBackPressed()
                }
                enableCustomBackPress = false
                true
            }
        }
    }

    fun windowAdjustPan() =
        mainActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

    fun windowAdjustResize() =
        mainActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

    private val baseHandler = Handler()
    private var baseRunnable = Runnable {
    }

    fun removePreviousCallback(millis: Long = 400, action: () -> Unit) {
        baseHandler.removeCallbacks(baseRunnable)
        baseRunnable = Runnable { action() }
        baseHandler.postDelayed(baseRunnable, millis)
    }

    override fun onDestroyView() {
        baseHandler.removeCallbacks(baseRunnable)
        showProgress(false)
        super.onDestroyView()
    }
}

const val TARGET_FRAGMENT_REQUEST_CODE = 123
fun Fragment.setAsTargetFragment(
    owner: Fragment,
    requestCode: Int = TARGET_FRAGMENT_REQUEST_CODE
): Fragment {
    this.setTargetFragment(owner, requestCode)
    return this
}

fun Fragment.setResultOk(
    requestCode: Int = TARGET_FRAGMENT_REQUEST_CODE,
    data: Intent? = null
) {
    this.targetFragment?.onActivityResult(requestCode, Activity.RESULT_OK, data)
}

fun FragmentActivity.initialFragment(fragment: Fragment, showAnim: Boolean = false) {
    val containerId = ViewModelProviders.of(this)[BaseViewModel::class.java].parentLayoutId
    supportFragmentManager.commit(allowStateLoss = true) {
        if (showAnim)
            setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            )
        replace(containerId, fragment)
    }
}

fun FragmentActivity.addFragment(
    fragment: Fragment,
    addBackStack: Boolean = true,
    tag: String = fragment.hashCode().toString()
) {
    hideKeyboard(fragment.view)
    val containerId = ViewModelProviders.of(this)[BaseViewModel::class.java].parentLayoutId
    supportFragmentManager.commit(allowStateLoss = true) {
        if (addBackStack && !fragment.isAdded) addToBackStack(tag)
        setCustomAnimations(
            R.anim.enter_from_bottom,
            R.anim.exit_to_top,
            R.anim.enter_from_top,
            R.anim.exit_to_bottom
        )
        add(containerId, fragment)
    }
}

fun FragmentActivity.finishFragment() {
    supportFragmentManager.popBackStack()
}

fun Fragment.parentLayoutId() =
    ViewModelProviders.of(activity!!)[BaseViewModel::class.java].parentLayoutId