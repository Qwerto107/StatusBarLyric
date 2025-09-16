/*
 * StatusBarLyric
 * Copyright (C) 2021-2022 fkj@fkj233.cn
 * https://github.com/Block-Network/StatusBarLyric
 *
 * This software is free opensource software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either
 * version 3 of the License, or any later version and our eula as
 * published by Block-Network contributors.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * and eula along with this software.  If not, see
 * <https://www.gnu.org/licenses/>
 * <https://github.com/Block-Network/StatusBarLyric/blob/main/LICENSE>.
 */

package statusbar.lyric.tools

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.view.animation.RotateAnimation
import androidx.core.view.isGone
import androidx.core.view.isVisible
import statusbar.lyric.tools.Tools.isNotNull

object LyricViewTools {
    fun getAlphaAnimation(into: Boolean, duration: Long = 250): AnimationSet {
        val alphaAnimation = (if (into) AlphaAnimation(0f, 1F) else AlphaAnimation(1F, 0f)).apply {
            this.duration = duration
        }
        return AnimationSet(true).apply {
            addAnimation(alphaAnimation)
        }
    }

    fun switchViewInAnima(type: String?, interpolator: Int?, time: Int?): Animation? {
        val t = time?.toLong() ?: 500L
        val translateAnimation: Animation? = when (type) {
            // Slide 滑入
            "SlideInUp" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f
            )
            "SlideInDown" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, -1f,
                Animation.RELATIVE_TO_SELF, 0f
            )
            "SlideInLeft" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f
            )
            "SlideInRight" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, -1f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f
            )

            // Fade 淡入
            "FadeIn" -> TranslateAnimation(0f, 0f, 0f, 0f)
            "FadeInUp" -> TranslateAnimation(0f, 0f, 100f, 0f)
            "FadeInDown" -> TranslateAnimation(0f, 0f, -100f, 0f)
            "FadeInLeft" -> TranslateAnimation(100f, 0f, 0f, 0f)
            "FadeInRight" -> TranslateAnimation(-100f, 0f, 0f, 0f)

            // RollIn 滚动入场
            "RollIn" -> AnimationSet(true).apply {
                addAnimation(RotateAnimation(
                    0f, 360f, // 旋转360°
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
                ))
                addAnimation(TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, -1f, // 从左侧滚动进入
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f
                ))
            }
            // 缩放
            "ZoomInUp" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(0f, 1f, 0f, 1f))  // 添加缩放动画
                addAnimation(TranslateAnimation(0f, 0f, 100f, 0f))  // 添加平移动画
            }
            "ZoomInDown" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(0f, 1f, 0f, 1f))
                addAnimation(TranslateAnimation(0f, 0f, -100f, 0f))
            }
            "ZoomInLeft" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(0f, 1f, 0f, 1f))
                addAnimation(TranslateAnimation(100f, 0f, 0f, 0f))
            }
            "ZoomInRight" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(0f, 1f, 0f, 1f))
                addAnimation(TranslateAnimation(-100f, 0f, 0f, 0f))
            }

            // RotateIn 旋转进
            "RotateIn" -> RotateAnimation(90f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            "RotateInDownLeft" -> RotateAnimation(
                -90f, 0f, // 从左下方向上旋转
                Animation.RELATIVE_TO_SELF, 0f, // 左下 pivot
                Animation.RELATIVE_TO_SELF, 1f
            )
            "RotateInDownRight" -> RotateAnimation(
                90f, 0f, // 从右下方向上旋转
                Animation.RELATIVE_TO_SELF, 1f, // 右下 pivot
                Animation.RELATIVE_TO_SELF, 1f
            )
            "RotateInUpLeft" -> RotateAnimation(
                90f, 0f, // 从左上方向下旋转
                Animation.RELATIVE_TO_SELF, 0f, // 左上 pivot
                Animation.RELATIVE_TO_SELF, 0f
            )
            "RotateInUpRight" -> RotateAnimation(
                -90f, 0f, // 从右上方向下旋转
                Animation.RELATIVE_TO_SELF, 1f, // 右上 pivot
                Animation.RELATIVE_TO_SELF, 0f
            )
            else -> null
        }?.apply {
            duration = t
        }
        return getAlphaAnimation(true, t).apply {
            translateAnimation?.let { addAnimation(it) }
            switchInterpolator(interpolator)
        }
    }

    fun switchViewOutAnima(type: String?, interpolator: Int?, time: Int?): Animation? {
        val t = time?.toLong() ?: 500L
        val translateAnimation: Animation? = when (type) {
            // Slide 滑出
            "SlideOutUp" -> TranslateAnimation(0f, 0f, 0f, -100f)
            "SlideOutDown" -> TranslateAnimation(0f, 0f, 0f, 100f)
            "SlideOutLeft" -> TranslateAnimation(0f, -100f, 0f, 0f)
            "SlideOutRight" -> TranslateAnimation(0f, 100f, 0f, 0f)

            // Fade 淡出
            "FadeOut" -> TranslateAnimation(0f, 0f, 0f, 0f)
            "FadeOutUp" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, -1f
            )
            "FadeOutDown" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f
            )
            "FadeOutLeft" -> TranslateAnimation(0f, -100f, 0f, 0f)
            "FadeOutRight" -> TranslateAnimation(0f, 100f, 0f, 0f)

            // RotateOut 旋转出
            "RotateOut" -> RotateAnimation(0f, 90f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            "RotateOutDownLeft" -> RotateAnimation(
                0f, -90f, // 向左下旋转退出
                Animation.RELATIVE_TO_SELF, 0f, // 左下 pivot
                Animation.RELATIVE_TO_SELF, 1f
            )
            "RotateOutDownRight" -> RotateAnimation(
                0f, 90f, // 向右下旋转退出
                Animation.RELATIVE_TO_SELF, 1f, // 右下 pivot
                Animation.RELATIVE_TO_SELF, 1f
            )
            "RotateOutUpLeft" -> RotateAnimation(
                0f, 90f, // 向左上旋转退出
                Animation.RELATIVE_TO_SELF, 0f, // 左上 pivot
                Animation.RELATIVE_TO_SELF, 0f
            )
            "RotateOutUpRight" -> RotateAnimation(
                0f, -90f, // 向右上旋转退出
                Animation.RELATIVE_TO_SELF, 1f, // 右上 pivot
                Animation.RELATIVE_TO_SELF, 0f
            )

            // RollOut 滚动出
            "RollOut" -> AnimationSet(true).apply {
                addAnimation(RotateAnimation(
                    0f, -360f, // 反向旋转360°
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
                ))
                addAnimation(TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 1f, // 向右滚动退出
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f
                ))
            }

            // Zoom 缩放出
            "ZoomOutUp" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(1f, 0f, 1f, 0f))  // 添加缩放动画
                addAnimation(TranslateAnimation(0f, 0f, 0f, -100f))  // 修正位移参数（使用像素值而非相对值）
            }
            "ZoomOutDown" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(1f, 0f, 1f, 0f))
                addAnimation(TranslateAnimation(0f, 0f, 0f, 100f))
            }
            "ZoomOutLeft" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(1f, 0f, 1f, 0f))
                addAnimation(TranslateAnimation(0f, -100f, 0f, 0f))
            }
            "ZoomOutRight" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(1f, 0f, 1f, 0f))
                addAnimation(TranslateAnimation(0f, 100f, 0f, 0f))
            }

            else -> null
        }?.apply {
            duration = t
        }
        return getAlphaAnimation(false, t).apply {
            translateAnimation?.let { addAnimation(it) }
            switchInterpolator(interpolator)
        }
    }

    private fun Animation.switchInterpolator(int: Int?) {
        interpolator = when (int) {
            1 -> AccelerateInterpolator()
            2 -> DecelerateInterpolator()
            3 -> AccelerateDecelerateInterpolator()
            4 -> OvershootInterpolator()
            5 -> BounceInterpolator()
            else -> LinearInterpolator()
        }
    }

    private var alphaAnimation: AnimationSet? = null

    fun View.hideView(anim: Boolean = true) {
        if (isGone) return
        if (anim) {
            alphaAnimation = getAlphaAnimation(false, 0).apply {
                setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}
                    override fun onAnimationEnd(animation: Animation) {
                        visibility = View.GONE
                    }

                    override fun onAnimationRepeat(animation: Animation) {}
                })
            }
            startAnimation(alphaAnimation)
        } else {
            visibility = View.GONE
        }
    }

    fun View.cancelAnimation() {
        if (alphaAnimation.isNotNull())
            alphaAnimation!!.cancel()
    }

    fun View.showView() {
        if (isVisible) return
        val alphaAnimation = getAlphaAnimation(true).apply {
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    visibility = View.VISIBLE
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }
        startAnimation(alphaAnimation)
    }
}