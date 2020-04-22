package com.easy.lib.weight.edittext

import android.content.Context
import android.text.InputFilter
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import com.easy.lib.R
import com.easy.lib.expand.afterTextChanged

/**
 * @author too young
 * @date  2020/4/1 16:40
 */
class EditText(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    constructor(context: Context) : this(context, null)

    var editText: EditText
    var editClear: ImageView
    var passwordShow: ImageView
    var line: View

    private var hasPass = false
    private var isClear = false

    //密码显示隐藏的图标
    private var passwordShowImage: Int = 0
    private var passwordGoneImage: Int = 0

    //选中时输入框下划线颜色
    private var hasFocusColor = ContextCompat.getColor(context, R.color.hasFocusColor)
    //未选中时输入框下划线颜色
    private var noFocusColor = ContextCompat.getColor(context, R.color.noFocusColor)

    init {
        //设置ViewGroup绘制
        setWillNotDraw(false)
        val view = LayoutInflater.from(context).inflate(R.layout.password_edittext, null)
        addView(view)
        editText = view.findViewById(R.id.editText)
        editClear = view.findViewById(R.id.editClear)
        passwordShow = view.findViewById(R.id.imageView)
        line = view.findViewById(R.id.line)
        line.setBackgroundColor(noFocusColor)

        editText.apply {
            setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    line.setBackgroundColor(hasFocusColor)
                } else {
                    line.setBackgroundColor(noFocusColor)
                }
            }
            afterTextChanged {
                if (hasPass) {
                    passwordShow.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
                }
                if (isClear) {
                    editClear.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
                }

            }
            background = null
        }
        editClear.apply {
            visibility = View.GONE
            setOnClickListener { editText.setText("") }
        }
        passwordShow.apply {
            visibility = View.GONE
            setOnClickListener {
                if (editText.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
                    editText.transformationMethod = PasswordTransformationMethod.getInstance()
                    passwordShow.setImageResource(passwordGoneImage)
                } else {
                    editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    passwordShow.setImageResource(passwordShowImage)
                }
            }
        }

    }

    fun setPasswordImage(passwordShowImage: Int, passwordGoneImage: Int) {
        hasPass = true
        this.passwordShowImage = passwordShowImage
        this.passwordGoneImage = passwordGoneImage
        passwordShow.setImageResource(passwordGoneImage)
    }


    fun setClearImage(clearImage: Int) {
        isClear = true
        editClear.setImageResource(clearImage)
    }

    fun setTextColor(color: Int) {
        editText.setTextColor(ContextCompat.getColor(context, color))
    }

    fun setHintTextColor(color: Int) {
        editText.setHintTextColor(ContextCompat.getColor(context, color))
    }

    val text get() = editText.text.toString()

    private var defaultTextSize = 0F
    //设置输入框有文字时的文字大小)
    fun setHasTextSize(@DimenRes hasTextSize: Int, @DimenRes mTextSize: Int = 0) {
        if (mTextSize != 0) {
            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(mTextSize))
        }
        defaultTextSize = editText.textSize
        editText.afterTextChanged {
            editText.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                if (it.isEmpty()) defaultTextSize else resources.getDimension(hasTextSize)
            )
        }

    }

    //设置输入框下划线颜色
    fun setFocusColor(hasFocusColor: Int, noFocusColor: Int) {
        this.hasFocusColor = ContextCompat.getColor(context, R.color.hasFocusColor)
        this.noFocusColor = ContextCompat.getColor(context, R.color.noFocusColor)
        line.setBackgroundColor(noFocusColor)
    }

    //设置输入框下划线高度
    fun setEditLineHeight(editLineHeight: Int) {
        line.layoutParams.height = editLineHeight
        invalidate()
    }

    //隐藏输入框下划线
    fun goneLine() {
        line.visibility = View.GONE
    }

    fun setMaxLength(length: Int) {
        editText.filters = arrayOf(object : InputFilter.LengthFilter(length) {})
    }

}