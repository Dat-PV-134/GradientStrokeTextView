package com.rekoj134.gradientstroketextview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import kotlin.math.tan

class GradientStrokeTextView : androidx.appcompat.widget.AppCompatTextView {
    private var startColor = Color.BLUE
    private var endColor = Color.GREEN
    private var angle = 0f
    private var isUseOnlyStroke = false
    private var strokeWidth = 4f
    private val listColor by lazy { ArrayList<String>() }
    private val rotationColorMatrix by lazy { Matrix() }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val attributes =
                context.obtainStyledAttributes(attrs, R.styleable.GradientStrokeTextView)
            startColor =
                attributes.getColor(R.styleable.GradientStrokeTextView_startColor, startColor)
            endColor = attributes.getColor(R.styleable.GradientStrokeTextView_endColor, endColor)
            angle = attributes.getFloat(R.styleable.GradientStrokeTextView_angle, angle)
            isUseOnlyStroke = attributes.getBoolean(R.styleable.GradientStrokeTextView_isUseStrokeOnly, false)
            strokeWidth = attributes.getDimension(R.styleable.GradientStrokeTextView_gradientStrokeWidth, strokeWidth)
            attributes.recycle()
        }
    }

    fun setColorText(startColor: Int, endColor: Int) {
        this.startColor = startColor
        this.endColor = endColor
        invalidate()
    }

    fun setListColor(listColor: List<String>) {
        this.listColor.clear()
        this.listColor.addAll(listColor)
        invalidate()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        rotationColorMatrix.setRotate(angle)

        if (!isUseOnlyStroke) {
            paint.setShader(
                LinearGradient(
                    0f, 0f, 0f, lineHeight.toFloat(),
                    currentTextColor,
                    currentTextColor,
                    Shader.TileMode.CLAMP
                )
            )

            paint.style = Paint.Style.FILL_AND_STROKE
            super.onDraw(canvas)
        }

        if (listColor.isEmpty()) {
            val shader = LinearGradient(
                0f, 0f, paint.measureText(text.toString()), lineHeight.toFloat(),
                startColor,
                endColor,
                Shader.TileMode.CLAMP
            )
            shader.setLocalMatrix(rotationColorMatrix)
            paint.setShader(shader)
        } else {
            val colorsArray = IntArray(listColor.size)
            val positionArray = FloatArray(listColor.size)
            listColor.forEachIndexed { index, colorHex ->
                colorsArray[index] = Color.parseColor(colorHex)
                positionArray[index] =
                    index * 1f / listColor.size - index * 1f / listColor.size * 1 / tan(angle) / 1000f
            }

            val shader = LinearGradient(
                0f, 0f, paint.measureText(text.toString()), lineHeight.toFloat(),
                colorsArray,
                if (angle % 360 == 0f) null else positionArray,
                Shader.TileMode.CLAMP
            )
            shader.setLocalMatrix(rotationColorMatrix)
            paint.setShader(shader)
        }

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
        super.onDraw(canvas)
    }
}