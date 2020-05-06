유튜브를 이용한 음악공유 커뮤니티
===============================

개발기간 **2019-08~현재(유지보수중)**
------------------------------




## 로그인

<img src="https://github.com/jnugg0819/My_imgae_repo/blob/master/Github/gif/login.gif" width="300px" height="450px"></img></br>

## 회원가입

<img src="https://github.com/jnugg0819/My_imgae_repo/blob/master/Github/gif/logout.gif" width="300px" height="450px"></img>



## 댓글 달기

<img src="https://github.com/jnugg0819/My_imgae_repo/blob/master/Github/gif/reply.gif" width="300px" height="450px"></img>

## 답글 달기

<img src="https://github.com/jnugg0819/My_imgae_repo/blob/master/Github/gif/reply_slave.gif" width="300px" height="450px"></img>


## 좋아요 누르고 좋아요 버튼유지

<img src="https://github.com/jnugg0819/My_imgae_repo/blob/master/Github/gif/reply_like.gif" width="300px" height="450px"></img>


## 마이페이지 이미지 업데이트및 , 본인이 올린 게시물 보기

<img src="https://github.com/jnugg0819/My_imgae_repo/blob/master/Github/gif/mypage_image_update.gif" width="300px" height="450px"></img>


## 업로드

<img src="https://github.com/jnugg0819/My_imgae_repo/blob/master/Github/gif/upload.gif" width="300px" height="450px"></img>



## 홈게시판 댓글개수 추가 및 좋아요 표시

<img src="https://github.com/jnugg0819/My_imgae_repo/blob/master/Github/gif/boardLike_and_replycount.gif" width="300px" height="450px"></img>


---

## 각 기능 설명

1. 로그인, 회원가입
 * 로그인은 FireBaseAuth를 이용해서 구현해줬다. 왜 로그인만 따로 FirebaseAuth를 썻냐면 password를 잃어버렸을시 SMTP로   
  해당 메일로 메일을 보내 패스워드를 변경해야된다.  FireBaseAuthentication을 이용하면 회원가입부터 로그인, 패스워드분실  
  까지 쉽게 구현가능하다.

2. 댓글, 답글 달기
 * 댓글 및 답글 달기는 ViewModel과 LiveData를 이용했다. 기본적으로 Activity에서 화면이 돌아가거나 다시생성 되게된다면
   리소스가 다 날아가기 때문에 OnSaveInstanceState를 사용한다. 하지만 이 함수는 작은 데이터만 효율적으로 관리 할 수 있다.
   만약 대량의데이터가 저장되고 다시 불러오는 과정을 거친다면 엄청난 리소스가 할애 될것이다. 그래서 나온게 ViewModel이다.
   Activity 생명주기와 상관 없이 Activity폐기될때까지 유지 가능하다. 밑에 코드는 주요 코드 내용이다.

   MasterReplyActivity.kt
   ```
   //Koin(DI)로 한번 ViewModel을 Inject해줘봤다.
    val viewModel:MasterReplyViewModel by viewModel()

     override fun onCreate(savedInstanceState: Bundle?) {
       ...중략...

       //게시물 번호로 해당 게시물 댓글 가져오기
       viewModel.getMasterReply(boardIdx.toInt())

     }
   ```

   MasterReplyViewModel.kt
   ```
   //private으로 지정 외부 접근방지
   private val _masterReplyGetLiveData = MutableLiveData<ReplyGetResponse>()

   //외부접근 가능용
   val masterReplyGetLiveData: LiveData<ReplyGetResponse>
        get() = _masterReplyGetLiveData

   //댓글정보 가져오기
   fun getMasterReply(boardIdx: Int) {
            addDisposable(
                model.getMasterReply(boardIdx)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        it.run {
                            if(response.size>0){                    
                                Log.d("getReply","받기성공")
                                //댓글이 존재하면 data를 Post해줌으로 Observe에게 신호를 보냄
                                _masterReplyGetLiveData.postValue(this)
                            }
                        }

                    },
                        {
                            Log.d("getReply","받기실패")
                        }
                    ))
        }
   ```

   MasterReplyActivity.kt
   ```
   //관찰하고있던 객체에 신호가 들어오면 어댑터에 데이터를 갱신해줌
   viewModel.masterReplyGetLiveData.observe(this, Observer {
           masterReplyAdapter.setItems(it.response)
           masterReplyAdapter.notifyDataSetChanged()
       })

   ```


  3. 좋아요 누르고 좋아요 버튼 유지
   * 가장 생각을 많이 한 부분이었다 Client ,Server 에서 많이 생각을 했던 부분이 아닌가 싶다. 밑에는 클라이언트부터 서버까지 간단한 알고리즘이다.

   <img src="https://github.com/jnugg0819/My_imgae_repo/blob/master/Github/gif/like_algo.png" width="800px" height="300px"></img>




## 만들게된 배경


**같이 자취를 하던 친구가 있었는데 친구와 노래듣는 취향이 비슷했다. 그래서 서로 유튜브를 통해 음악을 공유하게됬고
그래서 생각했다 "언제 어디서든지 친구와 정보를 공유할 수 있는 커뮤니티를 만들자!!"**


---

# 사용된 대표 기술 정리
 ## Android
  * Rxjava, RxAndroid, Retrofit, okhttp
  * Glide, YotubeAPI
  * LiveData, ViewModel(MVVM)
  * facebook(SDK)
  * Google Material Design

 ## Spring-Boot
  * MyBatis, MySQL, log4jdbc
  * Swagger2
  * lombok
  * jackson
  * CI/CD(jenkins), Google cloud platform(Centos7)

---

* 블로그 링크: <https://kanio.tistory.com/>

* 이메일 링크: <jnugg0819@naver.com>

* hashcode: <https://hashcode.co.kr/users/126518/kaka98>

---

참고자료
1. 명품 Java Essential, 황기태 지음, 생능출판사
2. 후니의 쉽게쓴 CISCO네트워킹, 진강훈 외 지음, 성안당
3. DO it! 안드로이드 앱 프로그래밍, 정재곤 지음, 이지스 퍼블리싱
4. 스프링부트 시작하기 차근차근 따라 하는 단계별 학습, 김인우 지음, 인사이트
5. Head First Design Patterns, 에릭 프리먼 외3, 한빛미디어
6. 자바 8 API : <https://docs.oracle.com/javase/8/docs/api/>
7. 스프링 프레임워크 API: <https://docs.spring.io/spring/docs/current/javadoc-api/overview-summary.html>
8. 안드로이드 Developer: <https://developer.android.com/>
9. Youtube API : <https://developers.google.com/youtube/android/player/downloads>
10. Google Material Design: <https://material.io/>
