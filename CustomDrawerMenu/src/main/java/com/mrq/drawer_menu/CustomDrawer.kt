package com.mrq.drawer_menu

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color.blue
import android.os.Handler
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout

class CustomDrawer : FrameLayout {

    private var context: Activity? = null
    private var layoutFront: ConstraintLayout? = null
    private var layoutBack: ConstraintLayout? = null
    private var view: ImageView? = null

    private var layoutFrontBackgroundOpen: Int = R.drawable.shape_front
    private var layoutFrontBackgroundClose: Int = R.color.ora

    private var statusBarColorWhenClose: Int = R.color.ora
    private var statusBarColorWhenOpen: Int = R.color.blue

    private var isLayoutFrontClickToClose = true
    private var isChangeMenuIconToBackIcon = true
    private var isAnimationRight = false
    private var isChangeStatusBarColorWhenOpenOrColes = true
    private var isOpen = false

    private var duration: Int = 1000

    constructor(context: Activity) : super(context) {
        this.context = context
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val styleable = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomDrawer, 0, 0)
        setAttribute(styleable)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val styleable = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomDrawer, 0, 0)
        setAttribute(styleable)
    }

    private fun setAttribute(styleable: TypedArray) {

        layoutFrontBackgroundOpen = styleable.getInteger(
            R.styleable.CustomDrawer_layoutFrontBackgroundOpen,
            R.drawable.shape_front
        )
        layoutFrontBackgroundClose =
            styleable.getInteger(R.styleable.CustomDrawer_layoutFrontBackgroundClose, R.color.ora)
        duration = styleable.getInteger(R.styleable.CustomDrawer_duration, 1000)

        isLayoutFrontClickToClose =
            styleable.getBoolean(R.styleable.CustomDrawer_isLayoutFrontClickToClose, true)
        isChangeMenuIconToBackIcon =
            styleable.getBoolean(R.styleable.CustomDrawer_isChangeMenuIconToBackIcon, true)
        isAnimationRight = styleable.getBoolean(R.styleable.CustomDrawer_isAnimationRight, false)

        styleable.recycle()
    }

    fun context(context: Activity?): CustomDrawer {
        this.context = context
        return this
    }

    fun layoutFront(layoutFront: ConstraintLayout?): CustomDrawer {
        this.layoutFront = layoutFront
        return this
    }

    fun layoutBack(layoutBack: ConstraintLayout?): CustomDrawer {
        this.layoutBack = layoutBack
        return this
    }

    fun setViewOpenMenu(view: ImageView?): CustomDrawer {
        this.view = view
        return this
    }

    fun setAnimationRight(isAnimationRight: Boolean): CustomDrawer {
        this.isAnimationRight = isAnimationRight
        return this
    }

    fun setLayoutFrontBackgroundOpen(layoutFrontBackgroundOpen: Int): CustomDrawer {
        this.layoutFrontBackgroundOpen = layoutFrontBackgroundOpen
        return this
    }

    fun setLayoutFrontBackgroundClose(layoutFrontBackgroundClose: Int): CustomDrawer {
        this.layoutFrontBackgroundClose = layoutFrontBackgroundClose
        return this
    }

    fun setDuration(duration: Long): CustomDrawer {
        this.duration = duration.toInt()
        return this
    }

    fun isLayoutFrontClickToClose(isLayoutFrontClickToClose: Boolean): CustomDrawer {
        this.isLayoutFrontClickToClose = isLayoutFrontClickToClose
        return this
    }

    fun setOnLayoutFrontClickToClose(onAnimationFinish: () -> Unit): CustomDrawer {
        if (layoutFront != null) {
            if (isLayoutFrontClickToClose) {
                layoutFront!!.setOnClickListener {
                    if (isLayoutFrontClickToClose) {
                        if (isOpen) {
                            isOpen = false
                            animation(onAnimationFinish)
                        }
                    }
                }
            }
        }
        return this
    }

    fun changeMenuIconToBackIcon(isChangeMenuIconToBackIcon: Boolean): CustomDrawer {
        this.isChangeMenuIconToBackIcon = isChangeMenuIconToBackIcon
        return this
    }

    fun build() {
        val parent = FrameLayout(context!!)
        val paramsFront = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        val paramsBack = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        if (layoutBack != null) {
            parent.addView(layoutBack, paramsBack)
        }
        if (layoutFront != null) {
            parent.addView(layoutFront, paramsFront)
        }

        if (view != null) {
            view?.setOnClickListener {
                isOpen = !isOpen
                animation { }
            }
        }

        val parentParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        this.addView(parent, parentParams)
    }

    fun openMenu(onAnimationFinish: () -> Unit) {
        if (!isOpen) {
            isOpen = true
            animation(onAnimationFinish)
        }
    }

    fun closeMenu(onAnimationFinish: () -> Unit) {
        if (isOpen) {
            isOpen = false
            animation(onAnimationFinish)
        }
    }

    private fun animation(onAnimationFinish: () -> Unit) {
        layoutFront!!.isEnabled = false
        view!!.isEnabled = false
        val x: Float
        val y: Float
        val bottomMargin: Int
        val valueAnimator: ValueAnimator
        val metrics = DisplayMetrics()
        context!!.windowManager.defaultDisplay.getMetrics(metrics)
        val width = metrics.widthPixels.toFloat()
        val height = metrics.heightPixels.toFloat()
        if (isOpen) {
            x = width / 2.5f
            y = height / 3.6f
            bottomMargin = (height / 2).toInt() - 300
            valueAnimator = ValueAnimator.ofInt(bottomMargin)
        } else {
            x = 0f
            y = 0f
            bottomMargin = (height / 2).toInt() - 300
            valueAnimator = ValueAnimator.ofInt(bottomMargin, 0)
        }
        valueAnimator.duration = duration.toLong()
        valueAnimator.addUpdateListener { animation: ValueAnimator ->
            val layoutParams = layoutFront!!.layoutParams as LayoutParams
            layoutParams.bottomMargin = (animation.animatedValue as Int)
            layoutFront!!.layoutParams = layoutParams
            if (isOpen) {
                layoutFront!!.setBackgroundResource(layoutFrontBackgroundOpen)
            } else {
                Handler().postDelayed({
                    layoutFront!!.setBackgroundResource(
                        layoutFrontBackgroundClose
                    )
                }, 500)
            }
        }
        valueAnimator.start()

        animationRight(x, y, onAnimationFinish)
        changeMenuIconToBackIcon()

        if (isChangeStatusBarColorWhenOpenOrColes) {
            changeStatusBarColor(statusBarColorWhenOpen)
        }
        if (isChangeStatusBarColorWhenOpenOrColes) {
            changeStatusBarColor(statusBarColorWhenClose)
        }

    }

    private fun animationRight(x: Float, y: Float, onAnimationFinish: () -> Unit) {
        if (!isAnimationRight) {
            layoutFront!!.animate().translationX(x).translationY(y).setDuration(duration.toLong())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        layoutFront!!.isEnabled = true
                        view!!.isEnabled = true
                        onAnimationFinish()
                    }
                })
        } else {
            layoutFront!!.animate().translationX(-x).translationY(y).setDuration(duration.toLong())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        layoutFront!!.isEnabled = true
                        view!!.isEnabled = true
                        onAnimationFinish()
                    }
                })
        }
    }

    private fun changeMenuIconToBackIcon() {
        if (isChangeMenuIconToBackIcon) {
            if (isOpen) {
                view!!.animate().alpha(0f).setDuration(400).rotation(-90f)
                    .withEndAction {
                        view!!.setImageResource(R.drawable.ic_arrow_back)
                        view!!.animate().alpha(1f).setDuration(400).rotation(0f)
                            .start()
                    }.start()
            } else {
                view!!.animate().alpha(0f).setDuration(400).rotation(90f)
                    .withEndAction {
                        view!!.setImageResource(R.drawable.ic_menu)
                        view!!.animate().alpha(1f).setDuration(400).rotation(0f)
                            .start()
                    }.start()
            }
        }
    }

    private fun changeStatusBarColor(color: Int) {
        val window: Window = context!!.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

}