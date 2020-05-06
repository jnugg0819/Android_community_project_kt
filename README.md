유튜브를 이용한 음악공유 커뮤니티
===============================

개발기간 **2019-08~현재(유지보수중)**
------------------------------


## 만들게된 배경


**같이 자취를 하던 친구가 있었는데 친구와 노래듣는 취향이 비슷했다. 그래서 서로 유튜브를 통해 음악을 공유하게됬고
그래서 언제 어디서든지 유튜브를 통해 음악공유할 수 있었으면 했다. 그래서 유튜브API를 통한 커뮤니티를 만들어 볼까 생각했다.**   



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
   Activity 생명주기와 상관 없이 Activity가폐기될때까지 유지 가능하다. 밑에 코드는 주요 코드 내용이다.

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

   <img src="https://github.com/jnugg0819/My_imgae_repo/blob/master/Github/gif/like_algo.png" width="900px" height="300px"></img>

   like_checker로 해당 아이콘이 체크되었는지 체크해준다. 좋아요 버튼을 누르게된다면 like_checker 값이 토글될것이고 맞으면 좋아요 +1해준다음에 아이콘 색상은
   검은색으로 변경하고, 해당 게시글이 저장된 테이블을 Update해주고 유저정보 테이블에 누른 유저를 등록해준준다.

   버튼유지는 먼저 HomeActivity(타임라인 프래그먼트가 전부 붙어있는곳)가 띄어지면 현재등록된 아이디를 이용해서 GET을 해준다.

   HomeActivity.kt
   ```
   companion object{
       //좋아요 누른 사람들 정보(ViewModel에서 접근)
       lateinit var userInfoList:ArrayList<BoardLikeUserInfo>
   }

   override fun onCreate(savedInstanceState:Bundle?){
   ...중략...

   //좋아요 유저정보 불러오기
   viewModel!!.getLikeUserInfo(currentUserEmail)

   }
   ```

   HomeViewModel.kt
   ```
   myAPI.selectLikeUserInfo(userId)
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(object :io.reactivex.Observer<BoardLikeGetUserInfoResponse>{
               override fun onComplete() {

               }

               override fun onSubscribe(d: Disposable) {
               }

               override fun onNext(boardLikeGetUserInfoResponse: BoardLikeGetUserInfoResponse) {
                   //HomeActivity에 등록된 사용자리스트에 대입
                   HomeActivitty.userInfoList=boardLikeGetUserInfoResponse.response
               }

               override fun onError(e: Throwable) {
               }

           })
   ```

   그리고 각 타임라인에서 HomeActivity에 userInfoList를 가져다 쓰기만하면 된다.
   ```
   ...중략...

   override fun onNext(boardResponse: BoardResponse) {
        val newBoard = boardResponse.response
            for(i in newBoard.indices){
                  for(j in HomeActivitty.userInfoList.indices){
                        //데이터가 들어오게 되면 게시글번호 비교로 해당 Board 데이터 클래스에 isLikeCheck값을 변경해주기만 하면 adapter에서 해당 게시글이 set될때마다 아이콘을 변경해준다.
                        if (Integer.parseInt(newBoard[i].boardIdx!!) == HomeActivitty.userInfoList[j].boardIdx) {
                              newBoard[i].isLikeCheck=true
                          }
                      }
                  }
            adapter.setItems(newBoard)
            adapter.notifyDataSetChanged()
    }


   ```   


   4. 마이페이지 이미지 업데이트 및 , 본인이 올리 게시물 보기

  * 이방법도 3번과 방법이 비슷하다 마이페이지로 들어갔을시 서버 API를 조회하여 이미지를 불러온다. 밑에는 Controller부분에서 가져온 주요코드이다.

  MyPageDetailActivity.kt
  ```
  //현재 접속되있는 아이디를 보냄으로써 response값으로 정상인 값이 온다면 image에 넣어주고 아니면 error 기본 유저이미지를 넣어준다.
  Glide.with(this)
           .load("http://192.168.35.30:8080/getMyPageImage?creatorId=$currentUserEmail")
           .error(R.drawable.ic_person_black_36dp)
           .diskCacheStrategy(DiskCacheStrategy.NONE)
           .skipMemoryCache(true)
           .into(mypage_detail_image)


  ```

  밑에 코드는 Spring코드인데 잠깐만 보겠다.

  RestController.java(Spring)
  ```
  //MyPage의 사진 가져오기
  //response값에 해당 사진이 binary값으로 ouput된다.
		@RequestMapping(value="/getMyPageImage", method=RequestMethod.GET)
		public void selectMyPageGetImage(@RequestParam("creatorId") String creatorId , HttpServletResponse response) throws Exception{

			 MyPageDto myPageDto=boardService.selectMyPageGetImage(creatorId);

			if(ObjectUtils.isEmpty(myPageDto) == false) {
				String fileName = myPageDto.getOriginalFileName();

        //사진이 저장된 경로를 가져와준다음에 byte형식으로 전환
				byte[] files = FileUtils.readFileToByteArray(new File(myPageDto.getStoredFilePath()));

        //contentType 및 header지정
				response.setContentType("image/jpeg");
				response.setContentLength(files.length);
				response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName,"UTF-8")+"\";");
				response.setHeader("Content-Transfer-Encoding", "binary");

        //outputstream 가져온다음에 파일 내보내고 flush해서 초기화시켜준다
				response.getOutputStream().write(files);
				response.getOutputStream().flush();
        //stream 닫아보리기
				response.getOutputStream().close();

			}

		}


  ```

  위에 코드는 그냥 사진만 가져올 뿐이다. 하지만 기존에 원래 등록되있는 사진이있다면??? 해당사진을 delete해주고 update해주면된다.

  ```
  //마이페이지 정보 등록
  	@RequestMapping(value="/postMyPageInfo",method=RequestMethod.POST)
  	public HashMap<String, Object> insertMyPageInfo(@RequestParam("existImage") boolean existImage,MultipartHttpServletRequest file,MyPageDto myPageDto) throws Exception{
  		HashMap<String, Object> obj=new HashMap();
      //existImage가 참이면현재 등록되있는 이미지가 있다는 뜻이다 그러므로 지워주고 업데이트해준다.(Service부분에서 File 삭제)
  		if(existImage) {
  			if(boardService.removePriorImage(myPageDto)) {
  				boardService.updateMyPageInfo(myPageDto,file);
  				obj.put("response", "update");
  			}
  		}else {
  			//업으면 그냥 Insert해준다. (Service부분에서 File 등록)
  			boardService.insertMyPageInfo(myPageDto,file);
  			obj.put("response", "insert");
  		}
      //JSON으로 Update되었는지  Insert되었는지 확인
  		return obj;
  	}

  ```

 등록된 사진은 밑에 날짜별로 저장해뒀다.


  <img src="https://github.com/jnugg0819/My_imgae_repo/blob/master/Github/gif/mypage_image_insert.png" width="500px" height="400px"></img>

물론 이것은 dev라 윈도우즈 환경에서 실행됬다 실제는 Centos리눅스 환경에서 실행된다.   




5. 업로드
* 업로드는 여러장의 사진을 등록하기 때문에 Multipart로 구현해 줬다. 업로드 같은 경우에는 단한번만 사용하면 되기때문에 ViewModel을 사용하진 않았다.

```

  override fun onCreate(savedInstanceState: Bundle?) {
      //공유 버튼 클릭시
      upload_share_btn.setOnClickListener {
         uploadDataBase()
     }
  }

  private fun uploadDataBase() {

    //Multipart 선언및 list생성(여러장의 사진을 넣어야 되기때문)
    var part: MultipartBody.Part
    val listMultipartBody = ArrayList<MultipartBody.Part>()

    //이미지가 담긴 리스트만큼 body를 생성해주고 , 이미지 이름과 함께 리스트에 추가해준다.
    for (i in imageRealPath.indices) {
          val image = File(imageRealPath[i])
          val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), image)
          part = MultipartBody.Part.createFormData("uploadImage", image.name, requestFile)
          listMultipartBody.add(part)
      }

      //그후 나머지 데이터들과 함께 이미지를 list형태로 보내준다음 서버에서는 Iterator로 돌리면서 저장해주면 된다.
      myAPI!!.insertUpload(
                  requestUserId,
                  requestGenre,
                  requestYoutubeAdd,
                  requestTitle,
                  requesetMusicName,
                  requestSingerName,
                  requestRelatedSong,
                  requestContents,
                  listMultipartBody
              ).subscribeOn(
                  Schedulers.io()
              ).observeOn(AndroidSchedulers.mainThread())...중략...
  }


```

사용자입장에서 단순히 해당항목만 작성해서 공유한다는것이 너무 단조롭다... 커뮤니티 사이트인 op.gg를 보면 밑에 그림과 같이 서식을 사용 할 수있다.
다음 수정은 이런식으로 구현해 봐야겠다.

<img src="https://github.com/jnugg0819/My_imgae_repo/blob/master/Github/gif/op_gg.png" width="500px" height="450px"></img>



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
