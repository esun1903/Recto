package com.ssafy.recto.arcore

import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Renderable
import com.ssafy.recto.arcore.VideoScaleType.*

//  class 키워드를 사용하여 Kotiln에서 클래스를 선언할 수 있다
// 즉, VideoAnchorNode는 AnchorNode()의 서브클래스이다라는 의미
// AnchorNode는 시작점 인 것 같다.
class VideoAnchorNode : AnchorNode() {



    //클래스 이름 : VideoAnchorNode : AnchorNode()


    // 코틀린에서 it 이란
    private val videoNode = Node().also { it.setParent(this) }

    //https://developers.google.com/sceneform/reference/com/google/ar/sceneform/rendering/Renderable
    //Override한 함수 setRenderable는 Node with에 연결하여 3D공간에서 렌더링하기위한 기본 클래스이다.
    override fun setRenderable(renderable: Renderable?) {
        videoNode.renderable = renderable
    }


    //영상특성을 설정하는 함수
    fun setVideoProperties(
            videoWidth: Float, videoHeight: Float, videoRotation: Float,
            imageWidth: Float, imageHeight: Float,
            videoScaleType: VideoScaleType
    ) {
        scaleNode(videoWidth, videoHeight, imageWidth, imageHeight, videoScaleType)
        rotateNode(videoRotation)
    }


    private fun scaleNode(
            videoWidth: Float, videoHeight: Float,
            imageWidth: Float, imageHeight: Float,
            videoScaleType: VideoScaleType
    ) {
        //when이란 자바에서 switch문과 동일하다.
        //즉, videoScaleType의 값이 FitXY라면 ~
        videoNode.localScale = when (videoScaleType) {
            FitXY -> scaleFitXY(imageWidth, imageHeight)
            CenterCrop -> scaleCenterCrop(videoWidth, videoHeight, imageWidth, imageHeight)
            CenterInside -> scaleCenterInside(videoWidth, videoHeight, imageWidth, imageHeight)
        }

        //FitXY : 가로/세로 비율에 상관없이 ImageView의 레이아수의 각 면에 꽉차게 출력되는 것을 의미
        //centerCrop : 이미지의 가로/세로의 길이 중 짧은 쪽을 ImageView의 레이아웃에 맞춰서 출력한다.
        //centerInside : 이미지의 가로/세로의 길이 중 긴 쪽을 ImageView의 레이아웃에 맞춰서 출력한다.
        //https://sharp57dev.tistory.com/23

    }

    private fun rotateNode(videoRotation: Float) {
        videoNode.localRotation = Quaternion.axisAngle(Vector3(0.0f, -1.0f, 0.0f), videoRotation)
    }

    // Vector3을 생성하고 각 값을 할당하는 것을 의미
    private fun scaleFitXY(imageWidth: Float, imageHeight: Float) = Vector3(imageWidth, 1.0f, imageHeight)

    //fun 함수명(변수) : 리턴타입
    //즉, scaleCenterCrop의 리턴타입은 Vector3이라는 의미
    private fun scaleCenterCrop(videoWidth: Float, videoHeight: Float, imageWidth: Float, imageHeight: Float): Vector3 {

        //AspectRatio : 가로세로 비
        val isVideoVertical = videoHeight > videoWidth
        val videoAspectRatio = if (isVideoVertical) videoHeight / videoWidth else videoWidth / videoHeight
        val imageAspectRatio = if (isVideoVertical) imageHeight / imageWidth else imageWidth / imageHeight

        return if (isVideoVertical) {
            if (videoAspectRatio > imageAspectRatio) {
                Vector3(imageWidth, 1.0f, imageWidth * videoAspectRatio)
            } else {
                Vector3(imageHeight / videoAspectRatio, 1.0f, imageHeight)
            }
        } else {
            if (videoAspectRatio > imageAspectRatio) {
                Vector3(imageHeight * videoAspectRatio, 1.0f, imageHeight)
            } else {
                Vector3(imageWidth, 1.0f, imageWidth / videoAspectRatio)
            }
        }
    }


    //fun 함수명(변수) : 리턴타입
    //즉, scaleCenterInside의 리턴타입은 Vector3이라는 의미
    private fun scaleCenterInside(videoWidth: Float, videoHeight: Float, imageWidth: Float, imageHeight: Float): Vector3 {

        //AspectRatio : 가로세로 비
        val isVideoVertical = videoHeight > videoWidth
        val videoAspectRatio = if (isVideoVertical) videoHeight / videoWidth else videoWidth / videoHeight
        val imageAspectRatio = if (isVideoVertical) imageHeight / imageWidth else imageWidth / imageHeight

        return if (isVideoVertical) {
            if (videoAspectRatio < imageAspectRatio) {
                Vector3(imageWidth, 1.0f, imageWidth * videoAspectRatio)
            } else {
                Vector3(imageHeight / videoAspectRatio, 1.0f, imageHeight)
            }
        } else {
            if (videoAspectRatio < imageAspectRatio) {
                Vector3(imageHeight * videoAspectRatio, 1.0f, imageHeight)
            } else {
                Vector3(imageWidth, 1.0f, imageWidth / videoAspectRatio)
            }
        }
    }
}