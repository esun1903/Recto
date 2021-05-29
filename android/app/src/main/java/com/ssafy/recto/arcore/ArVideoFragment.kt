package com.ssafy.recto.arcore

import android.animation.ValueAnimator
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.RectF
import android.media.MediaMetadataRetriever
import android.media.MediaMetadataRetriever.*
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.Toast
import androidx.core.animation.doOnStart
import androidx.core.graphics.rotationMatrix
import androidx.core.graphics.transform
import com.google.ar.core.AugmentedImage
import com.google.ar.core.AugmentedImageDatabase
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.rendering.ExternalTexture
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.gson.GsonBuilder
import com.ssafy.recto.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.URL


open class ArVideoFragment : ArFragment() {

    private lateinit var mediaPlayer: MediaPlayer //사운드 및 동영상을 재생
    private lateinit var externalTexture: ExternalTexture //surfaceTexture
    private lateinit var videoRenderable: ModelRenderable //sfb파일을 renderable로 변환
    private lateinit var videoAnchorNode: VideoAnchorNode //이미지 및 비디오 차원, 비디오 회전 및 배율 유형
    private var activeAugmentedImage: AugmentedImage? = null //이미지를 처음 감지하여 물리적 크기를 추정하지 못해도 증강 이미지를 반환
    private var videoUrl : String = ""
    private var photoUrl : String = ""
    private var user_uid : String = ""
    private var photo_seq : Int? = null
    private var photo_id : String = ""
    private var userUid : String? = null
    private var currentUser : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaPlayer = MediaPlayer()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? { //layout을 inflate하는 곳, view 객체를 얻어서 초기화
        val view = super.onCreateView(inflater, container, savedInstanceState)

        // User 관련
        val mFirebaseAuth = FirebaseAuth.getInstance()
        currentUser = mFirebaseAuth.getCurrentUser()

        var sharedPreferences: SharedPreferences? = null
        sharedPreferences = this.context?.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        userUid = sharedPreferences?.getString("userUid", "")

        val bundle = arguments
        val photoCard : PhotoVO = bundle?.getSerializable("photoCard") as PhotoVO

        photoUrl = photoCard.photo_url.toString()
        videoUrl = photoCard.video_url.toString()
        user_uid = photoCard.user_uid.toString()
        photo_seq = photoCard.photo_seq
        photo_id = photoCard.photo_id.toString()

        planeDiscoveryController.hide() //planeDiscoveryController는 평면 탐색 표시를 관리
        planeDiscoveryController.setInstructionView(null) //평면 탐색 UX
        arSceneView.planeRenderer.isEnabled = false //평면 렌더러 활성화 여부 (ARCore는 표면을 감지 할 때 나타나는 하얀 점)
        arSceneView.isLightEstimationEnabled = false

        initializeSession()
        createArScene()

        return view
    }

    override fun getSessionConfiguration(session: Session): Config { //https://developers.google.com/ar/develop/java/augmented-images/guide#java_2
        //이미지 데이터베이스 만들기 - 저장된 이미지 데이터베이스로드
        //입력 스트림을 비트맵으로 디코딩

        fun setupAugmentedImageDatabase(config: Config, session: Session): Boolean {
            //이미지 추적 활성화 - 세션 중에 ARCore는 카메라 이미지의 특징점을 이미지 데이터베이스의 특징점과 일치시켜 이미지를 찾는다.
            try {
                var image_task = URLtoBitmapTask().apply {
                    url = URL(photoUrl)
                }
                var bitmap: Bitmap = image_task.execute().get()
                var video = videoUrl.substring(videoUrl.lastIndexOf('/') + 1, videoUrl.length);

                config.augmentedImageDatabase = AugmentedImageDatabase(session).also { db ->
                    db.addImage(video, bitmap, 0.045F) //widthInMeters:0.045F
                } //안드로이드 비트맵에서 증강 이미지 데이터베이스에 알 수 없는 물리적 크기의 단일 명명된 이미지를 추가한다.
                return true
            } catch (e: IllegalArgumentException) {
            } catch (e: IOException) {
            }
            return false
        }

        return super.getSessionConfiguration(session).also {
            it.lightEstimationMode = Config.LightEstimationMode.DISABLED
            //조명 추정 모드를 사용하여 사실성 향상. 가상 객체를 렌더링 할 때 다양한 조명 신호를 모방 할 수있는 상세한 데이터를 제공하여 대부분의 작업을 수행한다. 단서는 그림자 , 주변 광 , 음영 , 반사 하이라이트 및 반사.
            it.focusMode = Config.FocusMode.AUTO
            //카메라 포커스 모드 - 사진 또는 비디오를 캡처하는 동안 AUTO를 사용한다. 최적의 AR추적을 위해 자동 포커스 동작이 더 이상 필요하지 않은 경우 기본 포커스 모드로 되돌린다.

            if (!setupAugmentedImageDatabase(it, session)) {
                toast("처음부터 다시 시도해주세요.")
            }
        }
    }

    private fun createArScene() {
        // Create an ExternalTexture for displaying the contents of the video.
        // 비디오의 내용을 표시하기 위한 외부 텍스처를 만든다.
        externalTexture = ExternalTexture().also {
            mediaPlayer.setSurface(it.surface) //미디어의 비디오 부분 표면을 설정. 표면이 null일 경우 오디오 트랙만 재생
        }

        // Create a renderable with a material that has a parameter of type 'samplerExternal' so that
        // it can display an ExternalTexture.
        ModelRenderable.builder() //sfb 파일을 Renderable로 변환하는 작업 (3D 랜더링 파일 만들기 : obj -> sfb)
                .setSource(requireContext(), R.raw.augmented_video_model)
                .build()
                .thenAccept { renderable -> //Renderable 파일을 실제 Scene에 올리는 역할. Sceneform 라이브러리 사용으로 좌표와 오브젝트 위치 자동 계산.
                    videoRenderable = renderable
                    renderable.isShadowCaster = false
                    renderable.isShadowReceiver = false
                    renderable.material.setExternalTexture("videoTexture", externalTexture)
                }
                .exceptionally { throwable ->
                    return@exceptionally null
                }

        videoAnchorNode = VideoAnchorNode().apply {
            setParent(arSceneView.scene)
        }
    }

    /**
     * In this case, we want to support the playback of one video at a time.
     * Therefore, if ARCore loses current active image FULL_TRACKING we will pause the video.
     * If the same image gets FULL_TRACKING back, the video will resume.
     * If a new image will become active, then the corresponding video will start from scratch.
     */
    /**
     * 이 경우 한 번에 하나의 비디오 재생을 지원하고자 합니다.
     * 따라서 ARCore에서 현재 활성 이미지 FULL_TRACKING이 손실되면 비디오를 일시 중지합니다.
     * 동일한 영상이 FULL_TRACKING되면 영상이 재개됩니다.
     * 새 이미지가 활성화되면 해당 비디오가 처음부터 시작됩니다.
     */
    override fun onUpdate(frameTime: FrameTime) { //AR 시스템의 상태 및 변경 사항을 캡처
        val frame = arSceneView.arFrame ?: return //프레임당 상태
        val updatedAugmentedImages = frame.getUpdatedTrackables(AugmentedImage::class.java) //변경된 특정 유형의 추적 파일을 반환

        // If current active augmented image isn't tracked anymore and video playback is started - pause video playback
        //현재 활성 증강 이미지가 더이상 추적되지 않고 비디오 재생이 시작된 경우 - 비디오 재생 일시 중지
        val nonFullTrackingImages = updatedAugmentedImages.filter { it.trackingMethod != AugmentedImage.TrackingMethod.FULL_TRACKING }
        //FULL_TRACKING : 이미지 모션 추적 상태가 추적 상태인 경우에만 발생.
        activeAugmentedImage?.let { activeAugmentedImage ->
            if (isArVideoPlaying() && nonFullTrackingImages.any { it.index == activeAugmentedImage.index }) {
                pauseArVideo()
            }
        }

        val fullTrackingImages = updatedAugmentedImages.filter { it.trackingMethod == AugmentedImage.TrackingMethod.FULL_TRACKING }
        if (fullTrackingImages.isEmpty()) return

        // If current active augmented image is tracked but video playback is paused - resume video playback
        // 현재 활성 증강 영상이 추적되지만 비디오 재생이 일시 중지된 경우 - 비디오 재생 다시 시작
        activeAugmentedImage?.let { activeAugmentedImage ->
            if (fullTrackingImages.any { it.index == activeAugmentedImage.index }) {
                if (!isArVideoPlaying()) {
                    resumeArVideo()
                }
                return
            }
        }

        // Otherwise - make the first tracked image active and start video playback
        // 그렇지 않은 경우 - 추적된 첫 번째 이미지를 활성화하고 비디오 재생을 시작합니다.
        fullTrackingImages.firstOrNull()?.let { augmentedImage -> //리스트에서 첫번째 값을 가져온다. 없으면 null을 반환.
            try {
                playbackArVideo(augmentedImage)
            } catch (e: Exception) {
            }
        }
    }

    private fun isArVideoPlaying() = mediaPlayer.isPlaying //재생중

    private fun pauseArVideo() { //일시 중지
        videoAnchorNode.renderable = null
        mediaPlayer.pause()
    }

    private fun resumeArVideo() { //다시 시작
        mediaPlayer.start()
        fadeInVideo()
    }

    private fun dismissArVideo() { //리셋
        videoAnchorNode.anchor?.detach()
        videoAnchorNode.renderable = null
        activeAugmentedImage = null
        mediaPlayer.reset()
    }

    private fun playbackArVideo(augmentedImage: AugmentedImage) {

        val metadataRetriever = MediaMetadataRetriever()
        metadataRetriever.setDataSource(videoUrl, HashMap<String, String>())

        val videoWidth = metadataRetriever.extractMetadata(METADATA_KEY_VIDEO_WIDTH)?.toFloatOrNull()
                ?: 0f
        val videoHeight = metadataRetriever.extractMetadata(METADATA_KEY_VIDEO_HEIGHT)?.toFloatOrNull()
                ?: 0f
        val videoRotation = metadataRetriever.extractMetadata(METADATA_KEY_VIDEO_ROTATION)?.toFloatOrNull()
                ?: 0f

        // Account for video rotation, so that scale logic math works properly
        val imageSize = RectF(0f, 0f, augmentedImage.extentX, augmentedImage.extentZ)
                .transform(rotationMatrix(videoRotation))

        val videoScaleType = VideoScaleType.CenterCrop

        videoAnchorNode.setVideoProperties(
                videoWidth = videoWidth, videoHeight = videoHeight, videoRotation = videoRotation,
                imageWidth = imageSize.width(), imageHeight = imageSize.height(),
                videoScaleType = videoScaleType
        )

        // Update the material parameters
        videoRenderable.material.setFloat2(MATERIAL_IMAGE_SIZE, imageSize.width(), imageSize.height())
        videoRenderable.material.setFloat2(MATERIAL_VIDEO_SIZE, videoWidth, videoHeight)
        videoRenderable.material.setBoolean(MATERIAL_VIDEO_CROP, VIDEO_CROP_ENABLED)

        mediaPlayer.reset()
        mediaPlayer.setDataSource(videoUrl)
        mediaPlayer.isLooping = true
        mediaPlayer.prepare()
        mediaPlayer.start()

        videoAnchorNode.anchor?.detach()
        videoAnchorNode.anchor = augmentedImage.createAnchor(augmentedImage.centerPose)

        activeAugmentedImage = augmentedImage

        externalTexture.surfaceTexture.setOnFrameAvailableListener {
            it.setOnFrameAvailableListener(null)
            fadeInVideo()
        }

        val gson = GsonBuilder().setLenient().create();
        val retrofit = Retrofit.Builder().baseUrl("http://k4a204.p.ssafy.io:8080/recto/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        val service = retrofit.create(PhotoService::class.java)

        if (currentUser != null && photo_seq != null) {
            if (!user_uid.equals(userUid) && photo_seq!! > 30) { //포토카드 제작자와 로그인된 사용자가 다르고 photo_seq가 31이상(public 아닐 경우)
                var gift_from = user_uid
                var gift_to = userUid

                if (gift_to != null && photo_id != null) {
                    service.checkPhoto(photo_id, gift_to)?.enqueue(object : Callback<String> {
                        override fun onFailure(call: Call<String>, t: Throwable) {
                        }

                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            var result = response?.body().toString()
                            var gift = GiftVO(gift_from, photo_seq, gift_to) //받은 선물로 저장
                            if (result.equals("success")) {
                                //saveGift
                                service.saveGift(gift)?.enqueue(object : Callback<String> {
                                    override fun onFailure(call: Call<String>?, t: Throwable?) {
                                    }

                                    override fun onResponse(call: Call<String>, response: Response<String>) {
                                        toast("선물받은 카드로 등록되었습니다.")
                                    }
                                })
                            }
                        }

                    })
                }

            }
        }

    }

    private fun fadeInVideo() {
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 400L
            interpolator = LinearInterpolator()
            addUpdateListener { v ->
                videoRenderable.material.setFloat(MATERIAL_VIDEO_ALPHA, v.animatedValue as Float)
            }
            doOnStart { videoAnchorNode.renderable = videoRenderable }
            start()
        }
    }

    override fun onPause() {
        super.onPause()
        dismissArVideo()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    companion object {
        private const val TAG = "ArVideoFragment"

        private const val VIDEO_CROP_ENABLED = true

        private const val MATERIAL_IMAGE_SIZE = "imageSize"
        private const val MATERIAL_VIDEO_SIZE = "videoSize"
        private const val MATERIAL_VIDEO_CROP = "videoCropEnabled"
        private const val MATERIAL_VIDEO_ALPHA = "videoAlpha"
    }

    fun toast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }
}