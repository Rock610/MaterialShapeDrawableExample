package com.rock.materialshapedrawableexample

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.TypedValue
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.shape.*

class TestParentView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    init {
        val shapePathModel = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(13f.dp)
            .setLeftEdge(CurveEdgeTreatment(true))
            .setRightEdge(CurveEdgeTreatment(false))
            .build()

        val bg = MaterialShapeDrawable().apply {
            setTint(resources.getColor(R.color.white))
            initializeElevationOverlay(context)
            this.elevation = 6f.dp
            shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS
            setShadowColor(ContextCompat.getColor(context, R.color.coupon_shadow_color))
        }

        bg.shapeAppearanceModel = shapePathModel
        background = bg
    }

    class CurveEdgeTreatment(val left: Boolean) : EdgeTreatment() {

        override fun getEdgePath(
            length: Float,
            center: Float,
            interpolation: Float,
            shapePath: ShapePath
        ) {
            val radius = 8f.dp
            val realOffset = center - radius
            shapePath.lineTo(realOffset, 0f)
            shapePath.addArc(
                realOffset, -radius,
                realOffset + 2 * radius, radius, 180f, -180f
            )
            shapePath.lineTo(length, 0f)
        }
    }
}

val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Float.dpOfInt
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    ).toInt()
