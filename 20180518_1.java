
뉴보스로 데이타를 내려줘야 리포트를 보지

오퍼
	쿠폰
	적립금
	할인
	사은품
--------------------------------
오퍼들
	쿠폰의 삶
	 생성 - 매핑 -> 매핑 I/F
	 지급 - 사용자가 다운받을때(T_USER_COUPON) ->  지급 I/F
	 사용 - 물건살때 TR 내려갈때 -> 매출 I/F
	 소멸 - 다사용시. 기간이 끝났을때

	적립금의 삶
	 생성 - 매핑 -> 매핑 I/F
	 지급 - 사용자가 다운받을때(T_USER_SAVED) ->  지급 I/F
	 사용 - 물건살때 TR 내려갈때 -> 매출 I/F
	 소멸 - 다사용시. 기간이 끝났을때

	신규가입적립금(비매출관련 적립금)
	 생성 - 매핑 -> 매핑 I/F
	 지급 - 사용자가 다운(가입)받을때(T_USER_SAVED) ->  지급 I/F
	 사용 - 물건살때 TR 내려갈때 -> 매출 I/F
	 소멸 - 다사용시. 기간이 끝났을때

	사은품
	 생성 - 매핑 -> 매핑 I/F
	 지급 - 사용자가 지급조건을 충족했을때 ->  지급 I/F
	 소멸 - 다 지급시. 기간이 끝났을때

	할인(오늘만 특가)
	 생성 - 매핑 -> 매핑 I/F
	 지급, 사용 - 물건살때 TR 내려갈때 -> 매출 I/F
	 소멸 - 하루가(기간) 끝났을때

	추가혜택메뉴에 
	상품,브랜드 할인해 주는것
	 생성, 지급 - 매핑 -> 매핑 I/F
	 사용 - 물건살때 TR 내려갈때 -> 매출 I/F
	 소멸 - 기간 끝났을때


	핀코드(?)
	 생성 - 매핑 -> 매핑 I/F
	 지급 - 지급요건시 사용자가 다운받을때(T_USER_SAVED) ->  지급 I/F
	 사용 - 물건살때 TR 내려갈때 -> 매출 I/F
	 소멸 - 다사용시. 기간이 끝났을때

	 온라인 판촉품 <--> 오프라인 판촉품 매핑

매핑 I/F 는 생성시
매출 I/F 는 TR시

--------------------------------

이벤트
	생성 - 매핑 -> 매핑 I/F
	지급 - 이벤트 지급요건 충족시 사용자가 다운(T_USER_COUPON) ->  지급 I/F
		 - 이벤트 지급요건 충족시 사용자가 다운(T_USER_SAVED) ->  지급 I/F

	사용 - 물건살때 TR 내려갈때 -> 매출 I/F
	소멸 - 다사용시. 기간이 끝났을때

온라인 캠페인 <--> 오프라인 캠페인 매핑
온라인 판촉품 <--> 오프라인 판촉품 매핑

판촉품이란 적립금, 쿠폰, 할인, GWP
현재 아는 정보 
- 이벤트는 하나만 매핑
- 판촉품는 두개를 매핑?

조사해봐야 하는것 
	DFIS 판촉품 실적화면
	DFIS 이벤트 실적화면


	생성시 매핑 테이블
------------------------------------------------------------------------------------------------------
	이벤트매핑정보
	mapping_종류					online_ID	offline_ID		name			부점  on기간시작	on기간끝	off기간시작	off기간끝
		event					e-on-1111	e-off-1111		뉴보스이벤트		명동  05.11		05.20		05.10		05.21
		신규가입적립금event		e-on-2222	e-off-2222		신규가입적립금	명동	 05.11		05.20		05.10		05.21

	------------------------------------------------------------------------------------------------------
	행사정보(판촉품매핑정보 + 지급매핑정보)
		판촉품매핑정보(할인제외)
		mapping_종류			online		offline				name			부점  on기간시작	on기간끝	off기간시작 off기간끝 이벤트매핑정보 FK(비식별관계)
			쿠폰생성			c-on-1111	c-off-1111			증정할인쿠폰		명동	  05.11		05.20	05.10		05.21		e-on-1111
			적립금생성		s-on-1111	s-ff-1111			적립금			명동	  05.11		05.20	05.10		05.21		e-on-1111
			적립금생성		s-on-1111	s-ff-1111			신규가입적립금	명동	  05.11		05.20	05.10		05.21		e-on-2222
			사은품(gwp)		g-on-1111	g-off-1111			이니스트리증정	명동	

------------------------------------------------------------------------------------------------------

	이벤트매핑정보
	mapping_종류					online_ID	offline_ID		name			부점  on기간시작	on기간끝	off기간시작	off기간끝
		기타 할인					etc-on-1111 etc-off-1111	기타할인			명동	 05.11		05.20		05.10		05.18
		상품,브랜드 할인(추가혜택)	be-on-1111	be-off-1111		증정				명동	 05.11		05.20		05.10		05.18
		할인(오늘만 특가)			to-on-1111	to-off-1111		오늘만 특가		명동	 05.11		05.11
		기본 적립					ba-on-1111  ba-off-1111		기본적립			명동	 05.11		05.20		05.10		05.18

		------------------------------------------------------------------------------------------------------
		할인매핑정보
		mapping_종류			이벤트매핑정보_FK(식별관계)		부점  on기간시작	on기간끝	   대상		할인율  적립액
		할인률(기타할인)			etc-on-1111				명동	  05.11		05.11	   유저등급1 	 10%
		할인률(기타할인)			etc-on-1111				명동	  05.11		05.11	   유저등급3 	 20%
		할인률(기타할인)			etc-on-1111				명동	  05.11		05.11	   유저등급5	 30%
		할인률(상품,브랜드 할인)	be-on-1111				명동	  05.11		05.11	   브랜드	 20%
		할인률(오늘만 특가)		etc-on-1111				명동	  05.11		05.11	   상품		 10%
		적립액(기본 적립)			ba-on-1111				명동	  05.11		05.11	   상품		 10%	1000

------------------------------------------------------------------------------------------------------

		지급매핑정보(지급,사용,적립정보)
			T_USER_COUPON, T_USER_SAVED (쿠폰 지급,사용,적립정보)
				USER_ID		사용(S,U)	 event_online_ID	event_offline_ID
					1			U			e-on-1111			e-off-1111
					1			U			e-on-2222			e-off-2222

			사은품지급시 지급테이블 확인?


		매출정보(TR시)  e-on-1111	  이벤트에 해당하는 적립금이 얼마나 사용.
					  e-on-2222   신규가입적립금이 얼마나 사용
					  etc-on-1111 매출 나온 상품에 기타할인율가 적용되어 되어있나?
					  to-on-1111  매출 나온 상품에 오늘의 특가 할인율가 얼마나 되어있나?

					  			TR시 사은품 정보도 내려간다?
					  
쿠폰은 뉴보스 쿠폰과 매핑
이벤트는 뉴보스 이벤트와 매핑
---------------------------------------------------------------