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

    fun switchViewInAnima(type: String?, interpolator: Int?, time: Int?, delay: Long = 0L): Animation? {
        val t = time?.toLong() ?: 500L
        val translateAnimation: Animation? = when (type) {
            // ---------------- Slide 滑入 ----------------
            "SlideInUp" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f
            )
            "SlideInDown" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f
            )
            "SlideInLeft" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
            )
            "SlideInRight" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
            )

            // ---------------- Fade 淡入 ----------------
            "FadeIn" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
            )
            "FadeInUp" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f
            )
            "FadeInDown" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, -0.5f, Animation.RELATIVE_TO_SELF, 0f
            )
            "FadeInLeft" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
            )
            "FadeInRight" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, -0.5f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
            )

            // ---------------- Zoom ----------------
            "ZoomInUp" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(
                    0f, 1f, 0f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
                ))
                addAnimation(TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f
                ))
            }
            "ZoomInDown" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(
                    0f, 1f, 0f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
                ))
                addAnimation(TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f
                ))
            }
            "ZoomInLeft" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(
                    0f, 1f, 0f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
                ))
                addAnimation(TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
                ))
            }
            "ZoomInRight" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(
                    0f, 1f, 0f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
                ))
                addAnimation(TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
                ))
            }
            "ZoomInUpLeft" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(0f, 1f, 0f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f))
                addAnimation(TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f, // 左
                    Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f   // 下
                ))
            }
            "ZoomInUpRight" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(0f, 1f, 0f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f))
                addAnimation(TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f,  // 右
                    Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f   // 下
                ))
            }
            "ZoomInDownLeft" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(0f, 1f, 0f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f))
                addAnimation(TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f, // 左
                    Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f  // 上
                ))
            }
            "ZoomInDownRight" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(0f, 1f, 0f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f))
                addAnimation(TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f,  // 右
                    Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f  // 上
                ))
            }

            // ---------------- RollIn ----------------
            "RollIn" -> AnimationSet(true).apply {
                addAnimation(RotateAnimation(
                    -120f, 0f, // 初始 -120° 旋转进入
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
                ))
                addAnimation(TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
                ))
            }

            // ---------------- RotateIn ----------------
            "RotateIn" -> AnimationSet(true).apply {
                addAnimation(RotateAnimation(
                    -90f, 0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
                ))
            }
            "RotateInDownLeft" -> RotateAnimation(
                -90f, 0f, // 从左下旋转进
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f
            )
            "RotateInDownRight" -> RotateAnimation(
                90f, 0f, // 从右下旋转进
                Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 1f
            )
            "RotateInUpLeft" -> RotateAnimation(
                90f, 0f, // 从左上旋转进
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f
            )
            "RotateInUpRight" -> RotateAnimation(
                -90f, 0f, // 从右上旋转进
                Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f
            )
            else -> null
        }?.apply {
            duration = t
        }
        return getAlphaAnimation(true, t).apply {
            translateAnimation?.let { addAnimation(it) }
            switchInterpolator(interpolator)
            startOffset = delay  // 开始动画延迟
        }
    }

    fun switchViewOutAnima(type: String?, interpolator: Int?, time: Int?): Animation? {
        val t = time?.toLong() ?: 500L
        val translateAnimation: Animation? = when (type) {
            // ---------------- Slide 滑出 ----------------
            "SlideOutUp" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f
            )
            "SlideOutDown" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f
            )
            "SlideOutLeft" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
            )
            "SlideOutRight" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
            )

            // ---------------- Fade 淡出 ----------------
            "FadeOut" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
            )
            "FadeOutUp" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.5f
            )
            "FadeOutDown" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0.5f
            )
            "FadeOutLeft" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.5f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
            )
            "FadeOutRight" -> TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
            )

            // ---------------- Zoom Out ----------------
            "ZoomOutUp" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(
                    1f, 0f, 1f, 0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
                ))
                addAnimation(TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f
                ))
            }
            "ZoomOutDown" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(
                    1f, 0f, 1f, 0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
                ))
                addAnimation(TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f
                ))
            }
            "ZoomOutLeft" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(
                    1f, 0f, 1f, 0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
                ))
                addAnimation(TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
                ))
            }
            "ZoomOutRight" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(
                    1f, 0f, 1f, 0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
                ))
                addAnimation(TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
                ))
            }
            "ZoomOutUpLeft" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(1f, 0f, 1f, 0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f))
                addAnimation(TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f, // 左
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f  // 上
                ))
            }
            "ZoomOutUpRight" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(1f, 0f, 1f, 0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f))
                addAnimation(TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f,  // 右
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f  // 上
                ))
            }
            "ZoomOutDownLeft" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(1f, 0f, 1f, 0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f))
                addAnimation(TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f, // 左
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f   // 下
                ))
            }
            "ZoomOutDownRight" -> AnimationSet(true).apply {
                addAnimation(ScaleAnimation(1f, 0f, 1f, 0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f))
                addAnimation(TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f,  // 右
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f   // 下
                ))
            }

            // ---------------- RollOut ----------------
            "RollOut" -> AnimationSet(true).apply {
                addAnimation(RotateAnimation(
                    0f, 120f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
                ))
                addAnimation(TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
                ))
            }

            // ---------------- RotateOut ----------------
            "RotateOut" -> RotateAnimation(
                0f, 90f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            "RotateOutDownLeft" -> RotateAnimation(
                0f, 90f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f
            )
            "RotateOutDownRight" -> RotateAnimation(
                0f, -90f,
                Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 1f
            )
            "RotateOutUpLeft" -> RotateAnimation(
                0f, -90f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f
            )
            "RotateOutUpRight" -> RotateAnimation(
                0f, 90f,
                Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f
            )
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